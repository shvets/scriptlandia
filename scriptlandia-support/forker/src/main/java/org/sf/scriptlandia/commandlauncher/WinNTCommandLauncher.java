package org.sf.scriptlandia.commandlauncher;

/**
 * A command launcher for Windows XP/2000/NT that uses 'cmd.exe' when
 * launching commands in directories other than the current working
 * directory.
 */
public class WinNTCommandLauncher extends CommandLauncherProxy {
  public WinNTCommandLauncher(CommandLauncher launcher) {
    super(launcher);
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
    File commandDir = workingDir;
    if (workingDir == null) {
      if (project != null) {
        commandDir = project.getBaseDir();
      } else {
        return exec(cmd, env);
      }
    }
    // Use cmd.exe to change to the specified directory before running
    // the command
    final int preCmdLength = 6;
    String[] newcmd = new String[cmd.length + preCmdLength];
    newcmd[0] = "cmd";
    newcmd[1] = "/c";
    newcmd[2] = "cd";
    newcmd[3] = "/d";
    newcmd[4] = commandDir.getAbsolutePath();
    newcmd[5] = "&&";
    System.arraycopy(cmd, 0, newcmd, preCmdLength, cmd.length);

    return exec(project, newcmd, env);
  }
 */

}
