package org.sf.scriptlandia.launcher;

import org.sf.jlaunchpad.LauncherException;
import org.sf.jlaunchpad.CoreLauncher;

import java.util.Properties;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This is helper class for reading all properties from external files.
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class LauncherHelper {
  public final static String LAUNCHER_PROPERTIES =
          System.getProperty("user.home") + File.separatorChar + ".jlaunchpad";

  /** First properties file name, located in "user.home". */
  private final static String SCRIPTLANDIA_PROPERTIES =
          System.getProperty("user.home") + File.separatorChar + ".scriptlandia";

  private Properties launcherProps = new Properties();

  /** First properties file, located in "user.home". */
  private Properties scriptlandiaProps = new Properties();

  /**
   * Creates new launcher helper.
   */
  public LauncherHelper() {
    File launcherPropsFile = new File(LAUNCHER_PROPERTIES);

    if(launcherPropsFile.exists()) {
      try {
        launcherProps.load(new FileInputStream(LAUNCHER_PROPERTIES));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

    File scriptlandiaPropsFile = new File(SCRIPTLANDIA_PROPERTIES);

    if(scriptlandiaPropsFile.exists()) {
      try {
        scriptlandiaProps.load(new FileInputStream(SCRIPTLANDIA_PROPERTIES));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Sets up system properties, required for running scriptlandia.
   */
  public void setupProperties() {
    String repositoryHome = (String) launcherProps.get("repository.home");

    System.setProperty("java.home.internal", (String) launcherProps.get("java.home.internal"));

    String scriptlandiaHome = (String) scriptlandiaProps.get("scriptlandia.home");
    String launcherHome = (String) launcherProps.get("jlaunchpad.home");

    String scalaVersion = (String)scriptlandiaProps.get("scala.version");
    String scriptlandiaVersion = (String)scriptlandiaProps.get("scriptlandia.version");
    String launcherVersion = (String)scriptlandiaProps.get("jlaunchpad.version");
    String jaskellVersion = (String)scriptlandiaProps.get("jaskell.version");
    String jrubyVersion = (String)scriptlandiaProps.get("jruby.version");
    String jythonVersion = (String)scriptlandiaProps.get("jython.version");

    String javaCompilerVersion = (String) scriptlandiaProps.get("java.compiler.version");
    
//    System.setProperty("maven.repo.local", repositoryHome);
    System.setProperty("repository.home", repositoryHome);
    System.setProperty("scriptlandia.home", scriptlandiaHome);
    System.setProperty("jlaunchpad.home", launcherHome);

    System.setProperty("java.compiler.version", javaCompilerVersion);

    System.setProperty("python.home", repositoryHome + "/jython/jython/" + jythonVersion);
    System.setProperty("python.cachedir", repositoryHome + "/jython/jython/" + jythonVersion + "/cachedir");

    String jRubyHome = repositoryHome + "/org/jruby/jruby/" + jrubyVersion;

    System.setProperty("jruby.shell", "cmd.exe");
    System.setProperty("jruby.script", "jruby.bat");
    System.setProperty("jruby.home", jRubyHome);

    System.setProperty("jaskell.home", repositoryHome + "/jaskell/jaskell/" + jaskellVersion);
    System.setProperty("scala.version", scalaVersion);
    System.setProperty("scriptlandia.version", scriptlandiaVersion);
    System.setProperty("jlaunchpad.version", launcherVersion);

    System.setProperty("jdic.version", "0.9.3");
    System.setProperty("nailgun.version", "0.7.1");

    Iterator iterator = scriptlandiaProps.keySet().iterator();

    while(iterator.hasNext()) {
      String key = (String)iterator.next();

      int index = key.indexOf(".");

      if(index != -1) {
        String suffix = key.substring(index);

        if(suffix.equalsIgnoreCase(".version")) {
          System.setProperty(key, (String)scriptlandiaProps.get(key));
        }

        if(suffix.equalsIgnoreCase(".home")) {
          System.setProperty(key, (String)scriptlandiaProps.get(key));
        }
      }
    }


    //setupJavaSpecificationVersion();
  }
  
  /**
   * Sets up the java specification version.
   */
 /* private void setupJavaSpecificationVersion() {
    String javaHome = System.getProperty("java.home.internal");
    String rtJarName = javaHome + "/jre/lib/rt.jar";

    final Manifest manifest;
    try {
      manifest = FileUtil.getManifest(rtJarName);

      final Attributes mainAttributes = manifest.getMainAttributes();

      String javaSpecificationVersion = mainAttributes.getValue("Specification-Version");

      System.setProperty("java.specification.version.level", javaSpecificationVersion);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  */

  /**
   * Sets up libraries, required for running scriptlandia.
   * @param launcher the launcher
   * @throws LauncherException the exception
   */
  public void setupRequiredLibraries(CoreLauncher launcher) throws LauncherException {
    String repositoryHome = (String) launcherProps.get("repository.home");

    String scriptlandiaVersion = (String)scriptlandiaProps.get("scriptlandia.version");
    String launcherVersion = (String)scriptlandiaProps.get("jlaunchpad.version");
    String nailgunVersion = (String)scriptlandiaProps.get("nailgun.version");
    String jdicVersion = (String)scriptlandiaProps.get("jdic.version");

    launcher.addClasspathEntry(repositoryHome + "/org/apache/maven/bootstrap/bootstrap-mini/2.0.9/bootstrap-mini-2.0.9.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/pom-reader/" + launcherVersion +
      "/pom-reader-" + launcherVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/jlaunchpad-common/" + launcherVersion +
      "/jlaunchpad-common-" + launcherVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/jlaunchpad-launcher/" + launcherVersion +
      "/jlaunchpad-launcher-" + launcherVersion + ".jar");

    String slNailgunsLib = repositoryHome + "/org/sf/scriptlandia/scriptlandia-nailgun/" + scriptlandiaVersion +
      "/scriptlandia-nailgun-" + scriptlandiaVersion + ".jar";

    String nailgunsLib = repositoryHome + "/com/martiansoftware/nailgun/" + nailgunVersion + "/nailgun-" + nailgunVersion + ".jar";

    if(new File(slNailgunsLib).exists()) {
      launcher.addClasspathEntry(slNailgunsLib);
    }

    if(new File(nailgunsLib).exists()) {
      launcher.addClasspathEntry(nailgunsLib);
    }

//    launcher.addClasspathEntry(repositoryHome + "/org/sf/scriptlandia/scriptlandia-installer/" + scriptlandiaVersion +
//      "/scriptlandia-installer-" + scriptlandiaVersion + ".jar");

    launcher.addClasspathEntry(repositoryHome + "/org/jdesktop/jdic/" + jdicVersion + "/jdic-" + jdicVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/commons-cli/commons-cli/1.1/commons-cli-1.1.jar");
  }

}
