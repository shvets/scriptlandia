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
   * @param classWorld class world
   * @throws org.sf.scriptlandia.launcher.LauncherException the launcher exception
   */
  public ScriptlandiaLauncher(ClassWorld classWorld) throws LauncherException {
    super(classWorld);
  }

  private static UniversalLauncher createLauncher(Map<String, String> properties, ClassWorld classWorld)
          throws LauncherException {
    UniversalLauncher launcher;

    boolean instanceExists = currentExtension != null && instances.get(currentExtension) != null;

    if (instanceExists) {
      launcher = instances.get(currentExtension);
    }
    else {
      launcher = new UniversalLauncher(classWorld);

      launcher.configure(Thread.currentThread().getContextClassLoader(), properties);

      instances.put(currentExtension, launcher);
    }

    launcher.setInteractive(Boolean.parseBoolean(properties.get("is.interactive")));

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
    LauncherCommandLineParser parser = new LauncherCommandLineParser();

    String[] newArgs = parser.parse(args);

    String scriptName = parser.getStarterScriptName();

    if (scriptName != null) {
      currentExtension = FileUtil.getExtension(scriptName);
    } else {
      currentExtension = null;
    }

    UniversalLauncher launcher = createLauncher(parser.getCommandLine(), classWorld);

    launcher.launch(newArgs);
  }

}