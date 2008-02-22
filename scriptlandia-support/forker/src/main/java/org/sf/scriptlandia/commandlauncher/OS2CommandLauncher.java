package org.sf.scriptlandia.commandlauncher;

/**
 * A command launcher for OS/2 that uses 'cmd.exe' when launching
 * commands in directories other than the current working
 * directory.
 * <p/>
 * <p>Unlike Windows NT and friends, OS/2's cd doesn't support the
 * /d switch to change drives and directories in one go.</p>
 */
public class OS2CommandLauncher extends CommandLauncherProxy {
  public OS2CommandLauncher(CommandLauncher launcher) {
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
        commandDir = ".";
      } else {
        return exec(cmd, env);
      }
    }
    // Use cmd.exe to change to the specified drive and
    // directory before running the command
    final int preCmdLength = 7;
    final String cmdDir = commandDir.getAbsolutePath();
    String[] newcmd = new String[cmd.length + preCmdLength];
    newcmd[0] = "cmd";
    newcmd[1] = "/c";
    newcmd[2] = cmdDir.substring(0, 2);
    newcmd[3] = "&&";
    newcmd[4] = "cd";
    newcmd[5] = cmdDir.substring(2);
    newcmd[6] = "&&";
    System.arraycopy(cmd, 0, newcmd, preCmdLength, cmd.length);

    return exec(newcmd, env);
  }
  */

}
