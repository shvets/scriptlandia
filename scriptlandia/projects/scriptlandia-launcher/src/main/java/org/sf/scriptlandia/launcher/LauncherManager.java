package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassWorld;
import org.sf.jlaunchpad.util.FileUtil;
import org.sf.jlaunchpad.core.LauncherCommandLineParser;
import org.sf.jlaunchpad.*;

import java.util.*;

/**
 * The class represents launcher manager, that manages all the instances of launchers.
 * it's also responsible for creating other classes required for running scriptlandia.
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class LauncherManager {
  /**
   * The singleton object.
   */
//  protected static Map<String, CoreLauncher> instances = new HashMap<String, CoreLauncher>();

  protected static CoreLauncher instance;

  /** The current used extension within instances collection. */
//  protected static String currentExtension;

  /**
   * Creates new launcher manager.
   */
  public LauncherManager() {
  }

  /**
   * Gets the singleton instance.
   *
   * @return the singleton instance
   */
/*  public static CoreLauncher getLauncher() {
    return instances.get(currentExtension);
  }
*/
  /**
   * Creates new launcher (launcher factory method).
   *
   * @param launcherClassName the launcher class name
   * @param classWorld the classworld
   * @return new launcher
   * @param parser the parser 
   * @param args command line arguments
   * @throws Exception the exception
   */
  protected CoreLauncher createLauncher(LauncherCommandLineParser parser, String[] args,
                                        String launcherClassName, ClassWorld classWorld) throws Exception {
/*    boolean instanceExists = currentExtension != null && instances.get(currentExtension) != null;

    CoreLauncher launcher;

    if (instanceExists) {
      launcher = instances.get(currentExtension);
    }
    else {
      LauncherHelper launcherHelper = new LauncherHelper();
      launcherHelper.setupProperties();

      launcher = new CoreLauncher(parser, args, classWorld);
      launcher.setMainClassName(launcherClassName);
      launcher.configure(Thread.currentThread().getContextClassLoader());

      launcherHelper.setupRequiredLibraries(launcher);

      instances.put(currentExtension, launcher);
    }
  */

    CoreLauncher launcher;

    if(instance == null) {
      LauncherHelper launcherHelper = new LauncherHelper();
      launcherHelper.setupProperties();

      launcher = new CoreLauncher(parser, args, classWorld);
      launcher.setMainClassName(launcherClassName);
      launcher.configure(Thread.currentThread().getContextClassLoader());

      launcherHelper.setupRequiredLibraries(launcher);
    }
    else {
      launcher = instance;
    }

    return launcher;
  }

  /**
   * Launch the launcher from the command line.
   * Will exit using System.exit with an exit code of 0 for success, 100 if there was an unknown exception,
   * or some other code for an application error.
   *
   * @param args The application command-line arguments.
   * @throws Exception exception
   * @param classWorld class world
   */
  public static void main(String[] args, ClassWorld classWorld) throws Exception {
    ScriptlandiaLauncherCommandLineParser parser = new ScriptlandiaLauncherCommandLineParser();

    parser.parse(args);

    String launcherClassName = parser.getLauncherClassName();

    if(parser.isLauncherMode()) {
      String starterClassName = parser.getStarterClassName();
      String depsFileName = parser.getStarterDepsFileName();

      if(starterClassName == null || starterClassName.trim().length() == 0 ||
         depsFileName == null || depsFileName.trim().length() == 0) {

        System.out.print("Usage: launcher ");
        System.out.print("-deps.file.name=<your-dependencies-file> ");
        System.out.print("-main.class.name=<your-main-class-name> ");
        System.out.print("<optional-script-name> ");
        System.out.print("<command-line-parameters>");

        System.exit(1);
      }
    }

    String scriptName = parser.getStarterScriptName();

/*    if (scriptName != null) {
      currentExtension = FileUtil.getExtension(scriptName);
    }
    else {
      currentExtension = null;
    }
*/
    LauncherManager launcherManager = new LauncherManager();

    CoreLauncher launcher = launcherManager.createLauncher(parser, args, launcherClassName, classWorld);

    if(parser.isLauncherMode()) {
      launcher.setArgs(args);

      launcher.launch();
    }
    else {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
 
      launcher.configure(classLoader);
      launcher.launch();

      Thread.currentThread().join();
    }

    System.exit(launcher.getExitCode());
  }

}
