package org.sf.scriptlandia.commandlauncher;

import org.apache.tools.ant.Project;

import java.io.File;
import java.io.IOException;

/**
 * A command launcher for Mac that uses a dodgy mechanism to change
 * working directory before launching commands.
 */
public class MacCommandLauncher extends CommandLauncherProxy {
  private String antWorkingDirectory;

  public MacCommandLauncher(CommandLauncher launcher, String antWorkingDirectory) {
    super(launcher);

    this.antWorkingDirectory = antWorkingDirectory;
  }

  /**
   * Launches the given command in a new process, in the given working
   * directory.
   *
   * @param project    the Ant project.
   * @param cmd        the command line to execute as an array of strings.
   * @param env        the environment to set as an array of strings.
   * @param workingDir working directory where the command should run.
   * @return the created Process.
   * @throws java.io.IOException forwarded from the exec method of the
   *                             command launcher.
   */
  public Process exec(Project project, String[] cmd, String[] env,
                      File workingDir) throws IOException {
    if (workingDir == null) {
      return exec(cmd, env);
    }
    System.getProperties().put("user.dir", workingDir.getAbsolutePath());
    try {
      return exec(cmd, env);
    } finally {
      System.getProperties().put("user.dir", antWorkingDirectory);
    }
  }
}