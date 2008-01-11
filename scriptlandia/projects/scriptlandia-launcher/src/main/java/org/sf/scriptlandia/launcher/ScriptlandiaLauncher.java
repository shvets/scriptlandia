package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassWorld;
import org.sf.jlaunchpad.util.FileUtil;
import org.sf.jlaunchpad.core.LauncherException;
import org.sf.jlaunchpad.core.LauncherCommandLineParser;
import org.sf.jlaunchpad.JLaunchPadLauncher;

import java.util.*;

/**
 * This is the main launcher class. It should be able to launch any Java program.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public class ScriptlandiaLauncher extends JLaunchPadLauncher {

  /**
   * The singleton object.
   */
//  protected static Map<String, UniversalLauncher> instances = new HashMap<String, UniversalLauncher>();

  /** ScriptName. */
  private String scriptName;

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

  /**
   * Configures the launcher.
   *
   * @param parentClassLoader parent class loader
   * @throws LauncherException the exception
   */
/*  public void configure(ClassLoader parentClassLoader) throws LauncherException {
    Map<String, String> commandLine = parser.getCommandLine();

    setScriptName(commandLine.get("script.name"));

    super.configure(parentClassLoader);
  }
*/

  /**
   * Sets the script name.
   * @param scriptName the script name
   */
  public void setScriptName(String scriptName) {
    this.scriptName = scriptName;
  }

  /**
   * Gets the realm name.
   *
   * @return the realm name
   */
  protected String getRealmName() {
    String realmName = super.getRealmName();

    if (scriptName != null) {
      int index = scriptName.lastIndexOf(".");
      realmName += "-";
      realmName += scriptName.substring(index + 1);
    }

    return realmName;
  }

  private static JLaunchPadLauncher createLauncher(ClassWorld classWorld, LauncherCommandLineParser parser, String[] args)
          throws LauncherException {
    ScriptlandiaLauncher launcher;

    Map<String, Object> commandLine = parser.getCommandLine();

    boolean instanceExists = currentExtension != null && instances.get(currentExtension) != null;

    if (instanceExists) {
      launcher = (ScriptlandiaLauncher)instances.get(currentExtension);

      launcher.setScriptName((String)commandLine.get("script.name"));

      launcher.setArgs(args);
    }
    else {
      launcher = new ScriptlandiaLauncher(parser, args, classWorld);

      launcher.setScriptName((String)commandLine.get("script.name"));

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
  public static JLaunchPadLauncher getInstance() {
    return instances.get(currentExtension);
  }

  /**
   * Checks for "wait" mode.
   *
   * @return true if "wait" mode; false otherwise
   */
  public boolean isWaitMode() {
    ScriptlandiaLauncherCommandLineParser _parser = (ScriptlandiaLauncherCommandLineParser)parser;

    if(_parser.isNailgunClientMode()) {
      return true;
    }

    return super.isWaitMode();
  }

  /**
   * Launches the launcher from the command line.
   *
   * @param args The application command-line arguments.
   * @param classWorld class world
   * @throws LauncherException exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws LauncherException {
    System.out.println("1 " + System.getProperty("java.specification.version.level"));

    ScriptlandiaLauncherCommandLineParser parser = new ScriptlandiaLauncherCommandLineParser();

    String[] newArgs = parser.parse(args);

    String scriptName = parser.getStarterScriptName();

    if (scriptName != null) {
      currentExtension = FileUtil.getExtension(scriptName);
    }
    else {
      currentExtension = null;
    }

    JLaunchPadLauncher launcher = createLauncher(classWorld, parser, newArgs);

    launcher.launch();
    System.out.println("2 " + System.getProperty("java.specification.version.level"));
  }

}
