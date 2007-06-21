package org.sf.scriptlandia.launcher;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.sf.scriptlandia.pomreader.PomReader;
import org.sf.scriptlandia.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
  private String depsFileName;

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
   * Sets the deps file name.
   * @param depsFileName the pom file name
   */
  public void setDepsFileName(String depsFileName) {
    this.depsFileName = depsFileName;
  }

  /**
   * Resolves dependencies for specified pom maven2 dependencies file.
   *
   * @param depsFileName the pom file name
   * @throws Exception the exception
   */
  public void resolveDependencies(String depsFileName) throws Exception {
    File depsFile = new File(depsFileName);

    if(depsFile.exists()) {
      ClassRealm classRealm = getMainRealm();

      String extension = FileUtil.getExtension(depsFileName);

      if(extension.equalsIgnoreCase("classpath")) {
        BufferedReader reader = new BufferedReader(new FileReader(depsFile));

        boolean done1 = false;

        while(!done1) {
          String line = reader.readLine();
          System.out.println("line " + line);

          if(line == null) {
            done1 = true;
          }
          else {
            boolean done2 = false;

            while(!done2) {
              int index1 = line.indexOf("${");

              if(index1 == -1) {
                done2 = true;
              }
              else {
                int index2 = line.substring(index1+1).indexOf("}");

                if(index2 == -1) {
                  done2 = true;
                }
                else {
                  String propertyName = line.substring(index1+2, index2+1);
                  String property = System.getProperty(propertyName);

                  System.out.println("propertyName " + propertyName);
                  System.out.println("property " + property);
                  System.out.println("idea.home " + System.getProperty("idea.home"));

                  if(property == null) {
                    line = line.substring(0, index1) + "?" + propertyName + "?" + line.substring(index2+2);

                    System.out.println("This property is not specified: " + propertyName);
                  }
                  else {
                    line = line.substring(0, index1) + property + line.substring(index2+2);                    
                  }
                }
              }
            }

            System.out.println("line2 " + line);
            classRealm.addConstituent(new File(line).toURI().toURL());
          }
        }
      }
      else {
        List<URL> deps = pomReader.calculateDependencies(new File(depsFileName));

        for (URL dep : deps) {
          classRealm.addConstituent(dep);
        }
      }
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
  }

  /**
   * Main launcher method.
   *
   * @throws LauncherException the exception
   */
  public void launch() throws LauncherException {
    try {
      resolveDependencies(depsFileName);
    }
    catch (Exception e) {
      throw new LauncherException(e);
    }

    super.launch();
  }

}
