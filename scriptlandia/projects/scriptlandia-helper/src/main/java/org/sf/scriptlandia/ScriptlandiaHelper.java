package org.sf.scriptlandia;

import org.sf.jlaunchpad.JLaunchPadLauncher;
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

    JLaunchPadLauncher launcher = ScriptlandiaLauncher.getInstance();

    String pom;

    if(manager.equalsIgnoreCase("bsf")) {
      pom = repositoryHome + "/org/sf/scriptlandia/" + name + "/" + scriptlandiaVersion + "/" + name + "-" + scriptlandiaVersion + ".pom";
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
   * Resolves dependencies for specified artifact.
   *
   * @param groupId group ID
   * @param artifactId artifact ID
   * @param version version
   * @param classifier classifier
   * @param launcher the launcher
   * @throws Exception the exception
   */
  public static void resolveDependencies(String groupId, String artifactId, String version, String classifier, JLaunchPadLauncher launcher)
         throws Exception {
    launcher.resolveDependencies(groupId, artifactId, version, classifier);
  }

  /**
   * Resolves dependencies for specified artifact.
   *
   * @throws Exception the exception
   * @param groupId group ID
   * @param artifactId artifact ID
   * @param version version
   * @param classifier classifier
   */
  public static void resolveDependencies(String groupId, String artifactId, String version, String classifier)
         throws Exception {
    resolveDependencies(groupId, artifactId, version, classifier, ScriptlandiaLauncher.getInstance());
  }

  /**
   * Resolves dependencies for specified artifact.
   *
   * @param groupId group ID
   * @param artifactId artifact ID
   * @param version version
   * @param launcher the launcher
   * @throws Exception the exception
   *  */
  public static void resolveDependencies(String groupId, String artifactId, String version, JLaunchPadLauncher launcher)
         throws Exception {
    launcher.resolveDependencies(groupId, artifactId, version);
  }
  /**
   * Resolves dependencies for specified artifact.
   *
   * @throws Exception the exception
   * @param groupId group ID
   * @param artifactId artifact ID
   * @param version version
   */
  public static void resolveDependencies(String groupId, String artifactId, String version)
         throws Exception {
    resolveDependencies(groupId, artifactId, version, ScriptlandiaLauncher.getInstance());
  }

    /**
     * Resolves dependencies for specified dependencies file.
     *
     * @param depsFileName dependencies file name
     * @param ignore ignore dependency from download
     * @param launcher the launcher
     * @throws Exception the exception
     */
    public static void resolveDependencies(String depsFileName, boolean ignore, JLaunchPadLauncher launcher) throws Exception {
      launcher.resolveDependencies(depsFileName, ignore);
    }

  /**
   * Resolves dependencies for specified dependencies file.
   *
   * @throws Exception the exception
   * @param depsFileName dependencies file name
   */
  public static void resolveDependencies(String depsFileName) throws Exception {
    resolveDependencies(depsFileName, false, ScriptlandiaLauncher.getInstance());
  }

  /**
   * Resolves dependencies for specified dependencies file.
   *
   * @throws Exception the exception
   * @param depsFileName dependencies file name
   * @param ignore ignore it
   */
  public static void resolveDependencies(String depsFileName, boolean ignore) throws Exception {
    resolveDependencies(depsFileName, ignore,ScriptlandiaLauncher.getInstance());
  }

  /**
   * Resolves dependencies for specified dependencies file.
   *
   * @throws Exception the exception
   */
  public static void resolveDependencies() throws Exception {
    resolveDependencies("pom.xml", false, ScriptlandiaLauncher.getInstance());
  }

  /**
   * Resolves dependencies for specified dependencies file.
   *
   * @throws Exception the exception
   */
  public static void resolveDependencies(boolean ignore) throws Exception {
    resolveDependencies("pom.xml", ignore, ScriptlandiaLauncher.getInstance());
  }

  public static JLaunchPadLauncher getLauncher() {
    return ScriptlandiaLauncher.getInstance();
  }
     
}
