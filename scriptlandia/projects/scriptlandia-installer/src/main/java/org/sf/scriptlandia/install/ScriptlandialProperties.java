package org.sf.scriptlandia.install;

import org.sf.jlaunchpad.install.LauncherProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The class represent scriptlandia properties located in "user.home".
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class ScriptlandialProperties extends LauncherProperties {
  /** Scriptlandia properties file file location. */
//  private final static String SCRIPTLANDIA_PROPERTIES =
//          System.getProperty("user.home") + File.separatorChar + ".scriptlandia";

  /** Maven2 settings.xml file location. */
  public static final String SETTINGS_XML =
          System.getProperty("user.home") + File.separatorChar + ".m2" + File.separatorChar + "settings.xml";

  public ScriptlandialProperties(String fileName) {
    super(fileName);
  }

  /**
   * Loads the properties file.
   *
   * @throws IOException I/O exception
   */
  public void load() throws IOException {
    String root = "/";

    if(System.getProperty("os.name").toLowerCase().startsWith("win")) {
      root = System.getProperty("user.dir").substring(0, 1) + ":\\";
    }

    File scriptlandiaPropsFile = new File(fileName);

    if(!scriptlandiaPropsFile.exists()) {
      //super.load(new FileInputStream(scriptlandiaPropsFile));
    //}
    //else {
      //put("java.specification.version", "1.5");
      //put("java.home", root + "Java" + File.separatorChar + "jdk1.6.0");
      put("mobile.java.home", "");
      put("scriptlandia.home", root + "scriptlandia");

      //put("use.proxy", "false");

      //put("proxyHost", "");
      //put("proxyPort", "");
      put("native.ruby.home", "");
    }

    super.load();
  }

}
