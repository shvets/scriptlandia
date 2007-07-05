package org.sf.scriptlandia.classworlds.launcher;

import java.util.Properties;
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
  private final static String SCRIPTLANDIA_PROPERTIES =
          System.getProperty("user.home") + File.separatorChar + ".scriptlandia";

  /** First properties file, located in "user.home". */
  private Properties scriptlandiaProps = new Properties();

  /**
   * Creates new launcher helper.
   */
  public LauncherHelper() {
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
    String repositoryHome = (String) scriptlandiaProps.get("repository.home");
    
    System.setProperty("maven.repo.local", repositoryHome);

    System.setProperty("python.home", (String)scriptlandiaProps.get("jython.base"));
    System.setProperty("python.cachedir", scriptlandiaProps.get("jython.base") + "/cachedir");

    System.setProperty("jruby.shell", "cmd.exe");
    System.setProperty("jruby.script", "jruby.bat");
    System.setProperty("jruby.home", (String)scriptlandiaProps.get("jruby.base"));

    System.setProperty("jaskell.home", (String)scriptlandiaProps.get("jaskell.base"));

    for (Object o : scriptlandiaProps.keySet()) {
      String key = (String) o;

      if (!key.equals("java.home")) {
        System.setProperty(key, (String) scriptlandiaProps.get(key));
      }
    }
  }

}
