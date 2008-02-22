package org.sf.scriptlandia.commandlauncher;

import java.io.IOException;

/**
 * A command launcher that proxies another command launcher.
 * <p/>
 * Sub-classes override exec(args, env, workdir).
 */
public class CommandLauncherProxy extends CommandLauncher {
  private CommandLauncher myLauncher;

  public CommandLauncherProxy(CommandLauncher launcher) {
    myLauncher = launcher;
  }

  /**
   * Launches the given command in a new process.  Delegates this
   * method to the proxied launcher.
   *
   * @param cmd     the command line to execute as an array of strings.
   * @param env     the environment to set as an array of strings.
   * @return the created Process.
   * @throws java.io.IOException forwarded from the exec method of the
   *                             command launcher.
   */
  public Process exec(String[] cmd, String[] env)
      throws IOException {
    return myLauncher.exec(cmd, env);
  }
}