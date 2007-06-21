package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassWorld;
import org.apache.maven.bootstrap.model.Model;
import org.apache.maven.bootstrap.model.Dependency;
import org.sf.scriptlandia.util.FileUtil;

import java.util.*;
import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.util.jar.Attributes;
import java.io.File;

/**
 * This is the main launcher class. It should be able to launch any Java program.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public class UniversalLauncher extends DepsLauncher {

  /**
   * Creates new launcher.
   *
   * @param parser the parser
   * @param args command line arguments
   * @param classWorld class world
   * @throws LauncherException the launcher exception
   */
  public UniversalLauncher(LauncherCommandLineParser parser, String[] args, ClassWorld classWorld)
         throws LauncherException {
    super(parser, args, classWorld);
  }

  /**
   * Configures the launcher.
   *
   * @param parentClassLoader parent class loader
   * @throws LauncherException the exception
   */
  public void configure(ClassLoader parentClassLoader) throws LauncherException {
    Map<String, String> commandLine = parser.getCommandLine();

    setMainClassName(parser.getCommandLine().get("main.class.name"));

    String pomFileName = commandLine.get("deps.file.name");

    if(pomFileName != null) {
      if(new File(pomFileName).exists()) {
        setPomFileName(pomFileName);
      }
      else {
        System.out.println("File " + pomFileName + " does not exist.");
      }
    }

    setScriptName(commandLine.get("script.name"));

    super.configure(parentClassLoader);
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

    UniversalLauncher launcher = new UniversalLauncher(parser, args, classWorld);

    launcher.configure(Thread.currentThread().getContextClassLoader());

    launcher.launch();
  }

}
