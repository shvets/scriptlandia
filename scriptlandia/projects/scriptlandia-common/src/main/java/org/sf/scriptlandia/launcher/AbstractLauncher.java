package org.sf.scriptlandia.launcher;

/**
 * The class represents abstract implementation of the launcher behavior.
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public abstract class AbstractLauncher implements Launcher {

  /** The main class name. */
  protected String mainClassName;

  /** The exit code. */
  protected int exitCode = 0;

  /**
   * Gets the main class name.
   *
   * @return the main class name
   */
  public String getMainClassName() {
    return mainClassName;
  }

  /**
   * Sets the main class name.
   *
   * @param mainClassName the main class name
   */
  public void setMainClassName(String mainClassName) {
    this.mainClassName = mainClassName;
  }

  /**
   * Gets the exit code.
   *
   * @return the exit code
   */
  public int getExitCode() {
    return exitCode;
  }

}
