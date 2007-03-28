package org.sf.scriptlandia.classworlds.launcher;

import org.sf.scriptlandia.util.FileUtil;
import org.sf.scriptlandia.util.CommonUtil;

import java.util.Iterator;
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
    String rubyHome = (String) scriptlandiaProps1.get("native.ruby.home");

    String jaskellVersion = (String)scriptlandiaProps1.get("jaskell.version");
    String jrubyVersion = (String)scriptlandiaProps1.get("jruby.version");
    String jythonVersion = (String)scriptlandiaProps1.get("jython.version");
    String javacInternalVersion = (String)scriptlandiaProps2.get("javac.internal.version");

    String javacInternalBase = (String) scriptlandiaProps2.get("javac.internal.base");
    
    System.setProperty("maven.repo.local", repositoryHome);

    System.setProperty("javac.internal.base", javacInternalBase);    
    System.setProperty("javac.internal.version", javacInternalVersion);

    System.setProperty("python.home", repositoryHome + "/jython/jython/" + jythonVersion);
    System.setProperty("python.cachedir", repositoryHome + "/jython/jython/" + jythonVersion + "/cachedir");

    String jRubyHome = repositoryHome + "/jruby/jruby/" + jrubyVersion;

    System.setProperty("jruby.shell", "cmd.exe");
    System.setProperty("jruby.script", "jruby.bat");
    System.setProperty("jruby.home", jRubyHome);

    System.setProperty("jaskell.home", repositoryHome + "/jaskell/jaskell/" + jaskellVersion);

    Iterator iterator = scriptlandiaProps1.keySet().iterator();

    while(iterator.hasNext()) {
      String key = (String)iterator.next();

      System.setProperty(key, (String)scriptlandiaProps1.get(key));

    }

    try {
      System.setProperty("java.specification.version", CommonUtil.getJavaSpecificationVersion());
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
 

}
