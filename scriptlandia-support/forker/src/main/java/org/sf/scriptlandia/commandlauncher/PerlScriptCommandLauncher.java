package org.sf.scriptlandia.commandlauncher;

import org.sf.scriptlandia.FileUtils;

/**
 * A command launcher that uses an auxiliary perl script to launch commands
 * in directories other than the current working directory.
 */
public class PerlScriptCommandLauncher
    extends CommandLauncherProxy {
   private static final FileUtils FILE_UTILS = FileUtils.getFileUtils();

  private String myScript;

  public PerlScriptCommandLauncher(String script, CommandLauncher launcher) {
    super(launcher);
    myScript = script;
  }

  /**
   * Launches the given command in a new process, in the given working
   * directory.
   *
   * @param cmd        the command line to execute as an array of strings.
   * @param env        the environment to set as an array of strings.
   * @param workingDir working directory where the command should run.
   * @return the created Process.
   * @throws java.io.IOException forwarded from the exec method of the
   *                             command launcher.
   */
/*  public Process exec(String[] cmd, String[] env,
                      File workingDir) throws IOException {
    if (project == null) {
      if (workingDir == null) {
        return exec(cmd, env);
      }
      throw new IOException("Cannot locate antRun script: "
          + "No project provided");
    }
    // Locate the auxiliary script
    String antHome = project.getProperty(MagicNames.ANT_HOME);
    if (antHome == null) {
      throw new IOException("Cannot locate antRun script: "
          + "Property '" + MagicNames.ANT_HOME + "' not found");
    }
    String antRun =
        FILE_UTILS.resolveFile(project.getBaseDir(),
            antHome + File.separator + myScript).toString();

    // Build the command
    File commandDir = workingDir;
    if (workingDir == null && project != null) {
      commandDir = project.getBaseDir();
    }
    String[] newcmd = new String[cmd.length + 3];
    newcmd[0] = "perl";
    newcmd[1] = antRun;
    newcmd[2] = commandDir.getAbsolutePath();
    System.arraycopy(cmd, 0, newcmd, 3, cmd.length);

    return exec(newcmd, env);
  }
  */
}
