package org.sf.scriptlandia.commandlauncher;

import java.io.File;
import java.io.IOException;

/**
 * A command launcher for a particular JVM/OS platform.  This class is
 * a general purpose command launcher which can only launch commands in
 * the current working directory.
 */
public class CommandLauncher {
  /**
   * Launches the given command in a new process.
   *
   * @param cmd     The command to execute.
   * @param env     The environment for the new process.  If null,
   *                the environment of the current process is used.
   * @return the created Process.
   * @throws java.io.IOException if attempting to run a command in a
   *                             specific directory.
   */
  public Process exec(String[] cmd, String[] env)
      throws IOException {
    return Runtime.getRuntime().exec(cmd, env);
  }

  /**
   * Launches the given command in a new process, in the given working
   * directory.
   *
   * @param cmd        The command to execute.
   * @param env        The environment for the new process.  If null,
   *                   the environment of the current process is used.
   * @param workingDir The directory to start the command in.  If null,
   *                   the current directory is used.
   * @return the created Process.
   * @throws IOException if trying to change directory.
   */
  public Process exec(String[] cmd, String[] env,
                      File workingDir) throws IOException {
    if (workingDir == null) {
      return exec(cmd, env);
    }
    
    throw new IOException("Cannot execute a process in different "
        + "directory under this JVM");
  }

}
