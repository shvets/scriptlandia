package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassWorld;
import org.sf.scriptlandia.util.FileUtil;
import org.apache.maven.bootstrap.model.Model;
import org.apache.maven.bootstrap.model.Dependency;

import java.io.*;
import java.util.*;
import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.util.jar.Attributes;

/**
 * This is the main launcher class. It should be able to launch any Java program.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public class ScriptlandiaLauncher extends DepsLauncher {

  /**
   * The singleton object.
   */
  protected static Map<String, ScriptlandiaLauncher> instances = new HashMap<String, ScriptlandiaLauncher>();

  /** The current used extension within instances collection. */
  protected static String currentExtension;

  /**
   * Creates new launcher.
   *
   * @param classWorld class world
   * @throws LauncherException the launcher exception
   */
  public ScriptlandiaLauncher(ClassWorld classWorld) throws LauncherException {
    super(classWorld);
  }

  private static ScriptlandiaLauncher createLauncher(Map<String, String> properties, ClassWorld classWorld)
          throws LauncherException {
    ScriptlandiaLauncher launcher;

    boolean instanceExists = currentExtension != null && instances.get(currentExtension) != null;

    if (instanceExists) {
      launcher = instances.get(currentExtension);
    }
    else {
      launcher = new ScriptlandiaLauncher(classWorld);

      launcher.setMainClassName(properties.get("main.class.name"));
      launcher.setPomFileName(properties.get("deps.file.name"));
      launcher.setScriptName(properties.get("script.name"));

      launcher.configure(Thread.currentThread().getContextClassLoader());
      
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
  public static ScriptlandiaLauncher getInstance() {
    return instances.get(currentExtension);
  }

  /**
   * Finds main class name from pom file.
   *
   * @param pomFileName the pom file name
   * @return main class name
   * @throws Exception the exception
   */
  public List<String> findMainClassNames(String pomFileName) throws Exception {
    List<String> names = new ArrayList<String>();

    File pom = new File(pomFileName);

    Model model = pomReader.readModel(pom, false);

    for (Object o : model.getAllDependencies()) {
      Dependency dependency = (Dependency) o;

      File file = pomReader.getArtifactFile(dependency);

      if (!FileUtil.getExtension(file).equals("pom")) {
        final Manifest manifest = FileUtil.getManifest(new JarFile(file));
        if (manifest != null) {
          final Attributes mainAttributes = manifest.getMainAttributes();
          final String className = mainAttributes.getValue(Attributes.Name.MAIN_CLASS);

          if (className != null) {
            names.add(className);
          }
        }
      }
    }

    String groupId = model.getGroupId();
    String artifactId = model.getArtifactId();

    List<String> closestNames = new ArrayList<String>();

    for (Iterator<String> i = names.iterator(); i.hasNext();) {
      String className = i.next();

      if(className.startsWith(groupId) || className.startsWith(groupId + "." + artifactId)) {
        i.remove();
        closestNames.add(className);
      }
    }

    closestNames.addAll(names);

    if(closestNames.size() == 0) {
      closestNames.add(groupId + "." + artifactId);
    }

    return closestNames;
  }

  /**
   * Launches the launcher from the command line.
   *
   * @param args The application command-line arguments.
   * @param classWorld class world
   * @throws LauncherException exception
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

    ScriptlandiaLauncher launcher = createLauncher(parser.getCommandLine(), classWorld);

    launcher.launch(newArgs);
  }

}
