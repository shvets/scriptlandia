package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassWorld;
import org.sf.scriptlandia.util.FileUtil;

import java.util.*;

/**
 * This is the main launcher class. It should be able to launch any Java program.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public class ScriptlandiaLauncher extends UniversalLauncher {

  /**
   * The singleton object.
   */
  protected static Map<String, UniversalLauncher> instances = new HashMap<String, UniversalLauncher>();

  /** The current used extension within instances collection. */
  protected static String currentExtension;

  /**
   * Creates new launcher.
   *
   * @param parser the parser
   * @param args command line arguments
   * @param classWorld class world
   * @throws LauncherException the launcher exception
   */
  public ScriptlandiaLauncher(LauncherCommandLineParser parser, String[] args, ClassWorld classWorld)
          throws LauncherException {
    super(parser, args, classWorld);
  }

  private static UniversalLauncher createLauncher(ClassWorld classWorld, LauncherCommandLineParser parser, String[] args)
          throws LauncherException {
    UniversalLauncher launcher;

    boolean instanceExists = currentExtension != null && instances.get(currentExtension) != null;

    if (instanceExists) {
      launcher = instances.get(currentExtension);
    }
    else {
      launcher = new UniversalLauncher(parser, args, classWorld);

      launcher.configure(Thread.currentThread().getContextClassLoader());

      instances.put(currentExtension, launcher);
    }

    launcher.setInteractive(parser.isInteractive());

    return launcher;
  }

  /**
   * Gets the singleton instance.
   *
   * @return the singleton instance
   */
  public static UniversalLauncher getInstance() {
    return instances.get(currentExtension);
  }

  /**
   * Launches the launcher from the command line.
   *
   * @param args The application command-line arguments.
   * @param classWorld class world
   * @throws org.sf.scriptlandia.launcher.LauncherException exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws LauncherException {
    ScriptlandiaLauncherCommandLineParser parser = new ScriptlandiaLauncherCommandLineParser();

    parser.parse(args);

    String scriptName = parser.getStarterScriptName();

    if (scriptName != null) {
      currentExtension = FileUtil.getExtension(scriptName);
    } else {
      currentExtension = null;
    }

    UniversalLauncher launcher = createLauncher(classWorld, parser, args);

    launcher.launch();
  }

}