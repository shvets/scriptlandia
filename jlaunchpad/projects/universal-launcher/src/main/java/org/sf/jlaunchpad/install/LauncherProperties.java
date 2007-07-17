package org.sf.jlaunchpad.install;

import org.sf.jlaunchpad.util.FileUtil;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

/**
 * The class represent launcher properties located in "user.home".
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class LauncherProperties extends Properties {
  /** Launcher properties file file location. */
  private final static String LAUNCHER_PROPERTIES =
          System.getProperty("user.home") + File.separatorChar + ".jlaunchpad";

  /** Maven2 settings.xml file location. */
  public static final String SETTINGS_XML =
          System.getProperty("user.home") + File.separatorChar + ".m2" + File.separatorChar + "settings.xml";

  /**
   * Updates GUI component from the property.
   *
   * @param component GUI component
   * @param property the property to be propagated
   */
  public void updateProperty(JComponent component, String property) {
    if(component instanceof JTextField) {
      JTextField textField = (JTextField)component;

      String value = (String)get(property);

      if(value != null) {
        textField.setText(value.replace('/', File.separatorChar));
      }
    }
    else if(component instanceof JCheckBox) {
      JCheckBox checkBox = (JCheckBox)component;

      String value = (String)get(property);

      if(value != null) {
        checkBox.setSelected(Boolean.parseBoolean(value));
      }
    }
    else if(component instanceof JComboBox) {
      JComboBox comboBox = (JComboBox)component;

      String value = (String)get(property);

      if(value != null) {
        comboBox.setSelectedItem(value);
      }
    }
  }

  /**
   * Updates the property from GUI component.
   *
   * @param component GUI component
   * @param property the property to be updated
   */
  public void saveProperty(JComponent component, String property) {
    if(component instanceof JTextField) {
      JTextField textField = (JTextField)component;

      String value = textField.getText().trim();

      put(property, value.replace(File.separatorChar, '/'));
    }
    else if(component instanceof JCheckBox) {
      JCheckBox checkBox = (JCheckBox)component;

      put(property, String.valueOf(checkBox.isSelected()));
    }
    else if(component instanceof JComboBox) {
      JComboBox comboBox = (JComboBox)component;

      put(property, comboBox.getSelectedItem());
    }
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

    File launcherPropsFile = new File(LAUNCHER_PROPERTIES);

    if(launcherPropsFile.exists()) {
      super.load(new FileInputStream(launcherPropsFile));
    }
    else {
      put("java.specification.version", "1.5");
      put("java.home", root + "Java" + File.separatorChar + "jdk1.6.0");
      put("mobile.java.home", "");
      //put("scriptlandia.home", root + "scriptlandia");

      put("use.proxy", "false");

      put("proxyHost", "");
      put("proxyPort", "");
      //put("native.ruby.home", "");
    }

    String repositoryHome;

    String settingsXml =
      System.getProperty("user.home") + File.separatorChar + ".m2" + File.separatorChar + "settings.xml";

    File outSettings =  new File(settingsXml);

    if(!outSettings.exists()) {
      repositoryHome = root + "maven-repository";
    }
    else {
      String settings = new String(FileUtil.getStreamAsBytes(new FileInputStream(SETTINGS_XML)));
      int index1 = settings.indexOf("<localRepository>");
      int index2 = settings.indexOf("</localRepository>");

      repositoryHome = settings.substring(index1 + "<localRepository>".length(), index2);
    }

    put("repository.home", repositoryHome);
  }

  /**
   * Saves the properties file.
   *
   * @throws IOException I/O exception
   */
  public void save() throws IOException {
    File launcherPropsFile = new File(LAUNCHER_PROPERTIES);

    store(new FileOutputStream(launcherPropsFile), "Launcher properties");
  }

}
