package org.sf.scriptlandia.antrun;

import org.apache.tools.ant.Main;

import java.util.Properties;

/**
 * The purpouse of this class is to disable System.out() from parent class.
 *
 * @author Alexander Shvets
 * @version 1.0 02/19/2006
 */
public class AntRun extends Main {

  /**
   * Exits the program.
   *
   * @param exitCode the exit code
   */
  protected void exit(int exitCode) {
    // do nothing: supress System.exit() from the parens
  }

  /**
   * Starts the ant launcher.
   *
   * @param args command line parameters
   * @param additionalUserProperties additional user properties
   * @param coreLoader the core loader
   */
  public static void start(String[] args, Properties additionalUserProperties,
                           ClassLoader coreLoader) {
    AntRun m = new AntRun();

    m.startAnt(args, additionalUserProperties, coreLoader);
  }

  /**
   * Command line entry point. This method kicks off the building
   * of a project object and executes a build using either a given
   * target or the default target.
   *
   * @param args Command line arguments. Must not be <code>null</code>.
   */
  public static void main(String[] args) {
    start(args, null, null);
  }

}
