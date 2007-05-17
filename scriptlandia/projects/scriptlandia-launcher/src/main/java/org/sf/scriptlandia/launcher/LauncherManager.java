package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassWorld;
import org.sf.scriptlandia.util.FileUtil;

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
  protected static Map<String, CoreLauncher> instances = new HashMap<String, CoreLauncher>();

  /** The current used extension within instances collection. */
  protected static String currentExtension;

  /**
   * Creates new launcher manager.
   */
  public LauncherManager() {
  }

  /**
   * Creates new launcher (launcher factory method).
   *
   * @param launcherClassName the launcher class name
   * @param classWorld the classworld
   * @return new launcher
   * @throws Exception the exception
   */
  protected CoreLauncher createLauncher(String launcherClassName, ClassWorld classWorld) throws Exception {
    boolean instanceExists = currentExtension != null && instances.get(currentExtension) != null;

    CoreLauncher launcher;

    if (instanceExists) {
      launcher = instances.get(currentExtension);
    }
    else {
      LauncherHelper launcherHelper = new LauncherHelper();
      launcherHelper.setupProperties();

      launcher = new CoreLauncher(classWorld);
      launcher.setMainClassName(launcherClassName);
      launcher.configure(Thread.currentThread().getContextClassLoader());

      launcherHelper.setupRequiredLibraries(launcher);

      instances.put(currentExtension, launcher);
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
    System.out.println("in LaunchManager main: " + Arrays.asList(args));
    LauncherCommandLineParser parser = new LauncherCommandLineParser();

    String[] newArgs = parser.parse(args);

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

    if (scriptName != null) {
      currentExtension = FileUtil.getExtension(scriptName);
    } else {
      currentExtension = null;
    }
    
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    
    LauncherManager launcherManager = new LauncherManager();

    System.out.println("launcherClassName " + launcherClassName);

    CoreLauncher launcher = launcherManager.createLauncher(launcherClassName, classWorld);

    if(parser.isLauncherMode()) {
      launcher.launch(args);
    }
    else {
      launcher.configure(classLoader);

      if(parser.isConfigMode()) {
        System.setProperty("config", "true");
      }

      launcher.launch(newArgs);

      Thread.currentThread().join();
    }

    System.exit(launcher.getExitCode());
  }

}
