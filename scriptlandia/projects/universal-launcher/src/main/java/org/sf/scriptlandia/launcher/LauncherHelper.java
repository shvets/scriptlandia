package org.sf.scriptlandia.launcher;

import org.sf.scriptlandia.util.FileUtil;

import java.util.Properties;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
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
  /** First properties file name, located in "user.home". */
  private final static String SCRIPTLANDIA_PROPERTIES1 =
          System.getProperty("user.home") + File.separatorChar + ".scriptlandia";

  /** Second properties file name, located in "scriptlandia.home". */
  private final static String SCRIPTLANDIA_PROPERTIES2 = "scriptlandia.properties";

  /** First properties file, located in "user.home". */
  private Properties scriptlandiaProps1 = new Properties();

  /** Second properties file, located in "scriptlandia.home". */
  private Properties scriptlandiaProps2 = new Properties();

  /**
   * Creates new launcher helper.
   */
  public LauncherHelper() {
    File scriptlandiaPropsFile = new File(SCRIPTLANDIA_PROPERTIES1);

    if(scriptlandiaPropsFile.exists()) {
      try {
        scriptlandiaProps1.load(new FileInputStream(SCRIPTLANDIA_PROPERTIES1));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

    String scriptlandiaHome = (String) scriptlandiaProps1.get("scriptlandia.home");

    File scriptlandiaPropsFile2 = new File(scriptlandiaHome + "/" + SCRIPTLANDIA_PROPERTIES2);

    if(scriptlandiaPropsFile2.exists()) {
      try {
        scriptlandiaProps2.load(new FileInputStream(scriptlandiaHome + "/" + SCRIPTLANDIA_PROPERTIES2));
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
    String repositoryHome = (String) scriptlandiaProps1.get("repository.home");
    String scriptlandiaHome = (String) scriptlandiaProps1.get("scriptlandia.home");
    //String rubyHome = System.getProperty("ruby.home");
    String rubyHome = (String) scriptlandiaProps1.get("native.ruby.home");

    String scalaVersion = (String)scriptlandiaProps1.get("scala.version");
    String scriptlandiaVersion = (String)scriptlandiaProps1.get("scriptlandia.version");
    String jaskellVersion = (String)scriptlandiaProps1.get("jaskell.version");
    String jrubyVersion = (String)scriptlandiaProps1.get("jruby.version");
    String jythonVersion = (String)scriptlandiaProps1.get("jython.version");
//    String javacInternalVersion = (String)scriptlandiaProps2.get("java.compiler.version");

    String javaCompilerVersion = (String) scriptlandiaProps2.get("java.compiler.version");
    
    System.setProperty("maven.repo.local", repositoryHome);
    System.setProperty("repository.home", repositoryHome);
    System.setProperty("scriptlandia.home", scriptlandiaHome);

//    System.setProperty("javac.internal.base", javacInternalBase);    
//    System.setProperty("javac.internal.version", javacInternalVersion);
    System.setProperty("java.compiler.version", javaCompilerVersion);

    System.setProperty("python.home", repositoryHome + "/jython/jython/" + jythonVersion);
    System.setProperty("python.cachedir", repositoryHome + "/jython/jython/" + jythonVersion + "/cachedir");

//    String jrubyHome = repositoryHome + "/jruby/jruby-lib/" + jrubyVersion;

/*    if(rubyHome != null && new File(rubyHome).exists()) {
      System.setProperty("jruby.home", rubyHome);
    }
    else {

      System.setProperty("jruby.home", jrubyHome);
    }
*/
    String jRubyHome = repositoryHome + "/jruby/jruby/" + jrubyVersion;

    System.setProperty("jruby.shell", "cmd.exe");
    System.setProperty("jruby.script", "jruby.bat");
//    System.setProperty("jruby.lib", jRubyHome + "/lib");
    System.setProperty("jruby.home", jRubyHome);
//    System.setProperty("jruby.base", jRubyHome);

    System.setProperty("jaskell.home", repositoryHome + "/jaskell/jaskell/" + jaskellVersion);
    System.setProperty("scala.version", scalaVersion);
    System.setProperty("scriptlandia.version", scriptlandiaVersion);

    System.setProperty("proxyHost", (String) scriptlandiaProps1.get("proxyHost"));
    System.setProperty("proxyPort", (String) scriptlandiaProps1.get("proxyPort"));
    System.setProperty("http.proxyHost", (String) scriptlandiaProps1.get("proxyHost"));
    System.setProperty("http.proxyPort", (String) scriptlandiaProps1.get("proxyPort"));

    setupJavaSpecificationVersion();
  }
  
  /**
   * Sets up the java specification version.
   */
  private void setupJavaSpecificationVersion() {
    String javaHome = System.getProperty("java.home.internal");
    String rtJarName = javaHome + "/jre/lib/rt.jar";

    final Manifest manifest;
    try {
      manifest = FileUtil.getManifest(rtJarName);

      final Attributes mainAttributes = manifest.getMainAttributes();

      String javaSpecificationVersion = mainAttributes.getValue("Specification-Version");

      System.setProperty("java.specification.version", javaSpecificationVersion);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets up libraries, required for running scriptlandia.
   * @param launcher the launcher
   * @throws LauncherException the exception
   */
  public void setupRequiredLibraries(CoreLauncher launcher) throws LauncherException {
    String repositoryHome = (String) scriptlandiaProps1.get("repository.home");

    String scriptlandiaVersion = (String)scriptlandiaProps1.get("scriptlandia.version");
    String nailgunVersion = (String)scriptlandiaProps2.get("nailgun.version");
    String jdicVersion = (String)scriptlandiaProps2.get("jdic.version");

    launcher.addClasspathEntry(repositoryHome + "/org/apache/maven/bootstrap/bootstrap-mini/2.0.6/bootstrap-mini-2.0.6.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/scriptlandia/pomreader/" + scriptlandiaVersion +
      "/pomreader-" + scriptlandiaVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/scriptlandia/universal-launcher/" + scriptlandiaVersion +
      "/scriptlandia-launcher-" + scriptlandiaVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/scriptlandia/scriptlandia-nailgun/" + scriptlandiaVersion +
      "/scriptlandia-nailgun-" + scriptlandiaVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/scriptlandia/scriptlandia-installer/" + scriptlandiaVersion +
      "/scriptlandia-installer-" + scriptlandiaVersion + ".jar");

    launcher.addClasspathEntry(repositoryHome + "/com/martiansoftware/nailgun/" + nailgunVersion + "/nailgun-" +
      nailgunVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/jdesktop/jdic/" + jdicVersion + "/jdic-" + jdicVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/commons-cli/commons-cli/1.0/commons-cli-1.0.jar");
  }

}
