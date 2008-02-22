package org.sf.scriptlandia.commandlauncher;

import org.sf.scriptlandia.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A command launcher for VMS that writes the command to a temporary DCL
 * script before launching commands.  This is due to limitations of both
 * the DCL interpreter and the Java VM implementation.
 */
public class VmsCommandLauncher extends Java13CommandLauncher {
  private static final FileUtils FILE_UTILS = FileUtils.getFileUtils();
  
  public VmsCommandLauncher() throws NoSuchMethodException {
    super();
  }

  /**
   * Launches the given command in a new process.
   *
   * @param cmd     the command line to execute as an array of strings.
   * @param env     the environment to set as an array of strings.
   * @return the created Process.
   * @throws java.io.IOException forwarded from the exec method of the
   *                             command launcher.
   */
  public Process exec(String[] cmd, String[] env)
      throws IOException {
    File cmdFile = createCommandFile(cmd, env);
    Process p
        = super.exec(new String[]{cmdFile.getPath()}, env);
    deleteAfter(cmdFile, p);
    return p;
  }

  /**
   * Launches the given command in a new process, in the given working
   * directory.  Note that under Java 1.3.1, 1.4.0 and 1.4.1 on VMS this
   * method only works if <code>workingDir</code> is null or the logical
   * JAVA$FORK_SUPPORT_CHDIR needs to be set to TRUE.
   *
   * @param cmd        the command line to execute as an array of strings.
   * @param env        the environment to set as an array of strings.
   * @param workingDir working directory where the command should run.
   * @return the created Process.
   * @throws IOException forwarded from the exec method of the
   *                     command launcher.
   */
  public Process exec(String[] cmd, String[] env,
                      File workingDir) throws IOException {
    File cmdFile = createCommandFile(cmd, env);
    Process p = super.exec(new String[]{cmdFile.getPath()},
        env, workingDir);
    deleteAfter(cmdFile, p);
    return p;
  }

  /*
  * Writes the command into a temporary DCL script and returns the
  * corresponding File object.  The script will be deleted on exit.
  * @param cmd the command line to execute as an array of strings.
  * @param env the environment to set as an array of strings.
  * @return the command File.
  * @throws IOException if errors are encountered creating the file.
  */
  private File createCommandFile(String[] cmd, String[] env)
      throws IOException {
    File script = FILE_UTILS.createTempFile("ANT", ".COM", null);
    script.deleteOnExit();
    PrintWriter out = null;
    try {
      out = new PrintWriter(new FileWriter(script));

      // add the environment as logicals to the DCL script
      if (env != null) {
        int eqIndex;
        for (int i = 0; i < env.length; i++) {
          eqIndex = env[i].indexOf('=');
          if (eqIndex != -1) {
            out.print("$ DEFINE/NOLOG ");
            out.print(env[i].substring(0, eqIndex));
            out.print(" \"");
            out.print(env[i].substring(eqIndex + 1));
            out.println('\"');
          }
        }
      }
      out.print("$ " + cmd[0]);
      for (int i = 1; i < cmd.length; i++) {
        out.println(" -");
        out.print(cmd[i]);
      }
    } finally {
      if (out != null) {
        out.close();
      }
    }
    return script;
  }

  private void deleteAfter(final File f, final Process p) {
    new Thread() {
      public void run() {
        try {
          p.waitFor();
        } catch (InterruptedException e) {
          //ignore
        }
        FileUtils.delete(f);
      }
    }
        .start();
  }
}