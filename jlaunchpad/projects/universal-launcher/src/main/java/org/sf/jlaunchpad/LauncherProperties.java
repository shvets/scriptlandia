package org.sf.jlaunchpad;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class reads and populate all required system properties form
 * propreties file to system properties.
 *
 * @author Alexander Shvets
 * @version 1.0 07/21/2007
 */
public class LauncherProperties extends Properties {

  /**
   * Creates new launcher helper.
   */
  public LauncherProperties(String fileName) throws IOException {
    File propertiesFile = new File(fileName);

    if(propertiesFile.exists()) {
      load(new FileInputStream(propertiesFile));
    }
  }

  /**
   * Sets up system properties, required for running launcher.
   */
  public void setupProperties() {
    for (Object o : keySet()) {
      String key = (String) o;

      if (!key.equals("java.home")) {
        System.setProperty(key, (String) get(key));
      }
    }
  }

}
