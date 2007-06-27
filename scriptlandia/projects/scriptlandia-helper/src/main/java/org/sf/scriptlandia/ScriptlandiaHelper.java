package org.sf.scriptlandia;

import org.sf.scriptlandia.launcher.UniversalLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;

import java.io.File;

/**
 * This is the class for holding convenient methods when working with
 * Scriptlandia CLASSPATH.
 *
 * @author Alexander Shvets
 * @version 2.0 04/29/2006
 */
public class ScriptlandiaHelper {

  /**
   * Disables object creation.
   */
  private ScriptlandiaHelper() {}


  /**
   * Resolves and downloads (if required) all libraries for the language.
   *
   * @param name language name
   * @param manager language manager ("bsf" or "javax")
   * @throws Exception the exception
   */
  public static void resolveLanguageDependencies(String name, String manager)
         throws Exception {
    String repositoryHome = System.getProperty("repository.home");
    String scriptlandiaVersion = System.getProperty("scriptlandia.version");

    UniversalLauncher launcher = ScriptlandiaLauncher.getInstance();

    String pom;

    if(manager.equalsIgnoreCase("bsf")) {
      pom = repositoryHome + "/org/sf/scriptlandia/" + name + "/" + scriptlandiaVersion +
            "/" + name + "-" + scriptlandiaVersion + ".pom";
      launcher.resolveDependencies(pom);
    }
    else {
      pom = repositoryHome + "/javax/script/" + name + "-engine/1.0/" + name + "-engine-1.0.pom";

      File pomFile = new File(pom);

      if(pomFile.exists()) {
        launcher.resolveDependencies(pom);
      }
      else {
        String groupId = "javax.script";
        String artifactId = name + "-engine";
        String version = "1.0";

        String javaVersion = System.getProperty("java.version");

        String classifier = "jdk1.5";

        if(javaVersion.startsWith("1.5")) {
          classifier = "jdk1.5";
        }
        else if(javaVersion.startsWith("1.6")) {
          classifier = "jdk1.6";
        }

        launcher.resolveDependencies(groupId, artifactId, version, classifier);
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
   * @param classifier classifier
   */
  public static void resolveDependencies(String groupId, String artifactId, String version, String classifier)
         throws Exception {
    UniversalLauncher launcher = ScriptlandiaLauncher.getInstance();

    launcher.resolveDependencies(groupId, artifactId, version, classifier);
  }

  /**
   * Resolves dependencies for specified pom maven2 dependencies file.
   *
   * @throws Exception the exception
   * @param groupId group ID
   * @param artifactId artifact ID
   * @param version version
   */
  public static void resolveDependencies(String groupId, String artifactId, String version)
         throws Exception {
    UniversalLauncher launcher = ScriptlandiaLauncher.getInstance();

    launcher.resolveDependencies(groupId, artifactId, version, null);
  }
  
}
