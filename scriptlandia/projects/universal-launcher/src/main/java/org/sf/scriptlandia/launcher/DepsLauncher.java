package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.sf.scriptlandia.pomreader.PomReader;
import org.sf.scriptlandia.util.CommonUtil;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * This is the dependencies launcher class. It extends classworlds' classloader functionality
 * with the dependencies resolution, specified in pom.xml instead of classworlds'
 *  configuration file.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public class DepsLauncher extends ClassworldLauncher {
  /** Pom file name. */
  private String pomFileName;

  /** The Pom reader. */
  protected PomReader pomReader = new PomReader();

  /**
   * Creates new dependencies launcher.
   *
   * @param parser the parser
   * @param args command line arguments
   * @param classWorld the classworld
   */
  public DepsLauncher(LauncherCommandLineParser parser, String[] args, ClassWorld classWorld) {
    super(parser, args, classWorld);
  }

  /**
   * Gets the realm name.
   *
   * @return the realm name
   */
  protected String getRealmName() {
    return "pom-launcher";
  }

  /**
   * Sets the pom file name.
   * @param pomFileName the pom file name
   */
  public void setPomFileName(String pomFileName) {
    this.pomFileName = pomFileName;
  }

  /**
   * Resolves dependencies for specified pom maven2 dependencies file.
   *
   * @param pomFileName the pom file name
   * @throws Exception the exception
   */
  public void resolveDependencies(String pomFileName) throws Exception {
    ClassRealm classRealm = getMainRealm();

    List<URL> deps = pomReader.calculateDependencies(new File(pomFileName));

    for (URL dep : deps) {
      classRealm.addConstituent(dep);
    }
  }

  /**
   * Resolves dependencies for specified pom maven2 dependencies file.
   *
   * @throws Exception the exception
   * @param groupId group ID
   * @param artifactId artifact ID
   * @param version version
   */
  public void resolveDependencies(String groupId, String artifactId, String version)
         throws Exception {
    resolveDependencies(groupId, artifactId, version, null);
  }

  /**
   * Resolves dependencies for specified pom maven2 dependencies file.
   *
   * @throws Exception the exception
   * @param groupId group ID
   * @param artifactId artifact ID
   * @param version version
   * @param classifier the classifier
   */
  public void resolveDependencies(String groupId, String artifactId, String version, String classifier)
         throws Exception {
    ClassRealm classRealm = getMainRealm();

    List<URL> deps = pomReader.calculateDependencies(groupId, artifactId, version, classifier);

    for (URL dep : deps) {
      classRealm.addConstituent(dep);
    }
  }

  /**
   * Configures the launcher.
   *
   * @param parentClassLoader parent class loader
   * @throws LauncherException the exception
   */
  public void configure(ClassLoader parentClassLoader) throws LauncherException {
    try {
      pomReader.init();
    }
    catch (Exception e) {
      throw new LauncherException(e);
    }

    super.configure(parentClassLoader);

    File compilerJar = CommonUtil.getCompilerJar();

    if(compilerJar != null) {
      try {
        ClassRealm mainRealm = getMainRealm();
        mainRealm.addConstituent(compilerJar.toURI().toURL());
        //System.out.println("Using Java compiler: " + compilerJar);
      }
      catch (Exception e) {
        throw new LauncherException(e);
      }
    }
    else {
      System.out.println("Compiler jar file could not be found: " + compilerJar);
    }
  }

  /**
   * Main launcher method.
   *
   * @throws LauncherException the exception
   */
  public void launch() throws LauncherException {
    try {
      resolveDependencies(pomFileName);
    }
    catch (Exception e) {
      throw new LauncherException(e);
    }

    super.launch();
  }

}
