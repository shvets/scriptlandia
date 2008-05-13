package org.sf.scriptlandia.install;

import com.sun.deploy.config.Config;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.jlaunchpad.LauncherException;
import org.sf.jlaunchpad.install.LauncherProperties;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * The class perform initial (gui) installation of scriprlandia.
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class GuiInstaller extends CoreInstaller implements CaretListener {
  public final static String LAUNCHER_PROPERTIES =
      System.getProperty("user.home") + File.separatorChar + ".jlaunchpad";
  private final static String SCRIPTLANDIA_PROPERTIES =
      System.getProperty("user.home") + File.separatorChar + ".scriptlandia";

  protected LauncherProperties launcherProps = new LauncherProperties(LAUNCHER_PROPERTIES);
  private ScriptlandiaProperties scriptlandiaProps = new ScriptlandiaProperties(SCRIPTLANDIA_PROPERTIES);

  private JTextField mobileJavaHomeField = new JTextField(35);
  private JTextField scriptlandiaHomeField = new JTextField(35);
  private JTextField launcherHomeField = new JTextField(35);
  private JTextField rubyHomeField = new JTextField(35);
  private JComboBox javaSpecVersionComboBox = new JComboBox(new String[] {"1.5", "1.6", "1.7"});

  private JButton installCoreButton = new JButton("Install Core");
  private JButton installLanguagesButton = new JButton("Install");
  private JButton uninstallLanguagesButton = new JButton("Uninstall");
  private JButton closeButton;

  private Console console = new Console();

  private JLabel statusLabel = new JLabel();

  private boolean inProcess = false;

  private JTabbedPane tabbedPane = new JTabbedPane();

  private JCheckBox[] checkBoxes = new JCheckBox[0];

  protected java.util.List languages = new java.util.ArrayList();

  private boolean languagesPanelUpdated = false;

  private GuiInstallerFrame frame = new GuiInstallerFrame(this);

  private String[] args;

  /**
   * Creates new GUI installer.
   *
   * @param args command line arguments
   * @throws LauncherException the exception
   */
  public GuiInstaller(String[] args) throws LauncherException {
    this.args = args;

    try {
      load();
    }
    catch (IOException e) {
      throw new LauncherException(e);
    }

    scriptlandiaHomeField.addCaretListener(this);
    launcherHomeField.addCaretListener(this);

    frame.getContentPane().add(createContent(), BorderLayout.CENTER);

    tryEnableInstallButton();

    frame.setVisible(true);
  }

  public void caretUpdate(CaretEvent e) {
    tryEnableInstallButton();
  }

  private void tryEnableInstallButton() {
    String launcherHome = launcherHomeField.getText().trim();
    String scriptlandiaHome = scriptlandiaHomeField.getText().trim();

    boolean enabled = false;

    if (launcherHome != null && launcherHome.length() > 0) {
      if (scriptlandiaHome != null && scriptlandiaHome.length() > 0) {
        enabled = true;
      }
    }

    installCoreButton.setEnabled(enabled);
    installLanguagesButton.setEnabled(enabled);
    uninstallLanguagesButton.setEnabled(enabled);

    tabbedPane.setEnabledAt(1, enabled);
  }

  private void enableControls() {
    applyEnabledFlag(tabbedPane, true);

    installCoreButton.setEnabled(true);

    tryEnableInstallButton();
  }

  private void applyEnabledFlag(Container container, boolean enabled) {
    Component[] components = container.getComponents();

    for (Component component : components) {
      component.setEnabled(enabled);

      if (component instanceof Container) {
        applyEnabledFlag((Container) component, enabled);
      } else {
        component.setEnabled(enabled);
      }
    }
  }

  private void disableControls() {
    applyEnabledFlag(tabbedPane, false);
    installCoreButton.setEnabled(false);

    console.getComponent().setEnabled(true);
  }

  public boolean isInProcess() {
    return inProcess;
  }

  public JPanel createContent() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JComponent basicSettingPanel = makeBasicSettingsPanel();
    tabbedPane.addTab("Basic Settings", null, basicSettingPanel, "Specifies important locations.");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

    final JPanel languagesPanel = new JPanel();
    tabbedPane.addTab("Languages/Extensions", null, languagesPanel, "Languages/Extensions");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

    tabbedPane.addChangeListener(new ChangeListener() {
      // This method is called whenever the selected tab changes
      public void stateChanged(ChangeEvent event) {
        JTabbedPane pane = (JTabbedPane) event.getSource();

        // Get current tab
        int selectedIndex = pane.getSelectedIndex();

        if (selectedIndex == 1) {
          fillLanguagesPanel(languagesPanel);
        }
      }
    });

    final JPanel consolePanel = makeConsolePanel();
    tabbedPane.addTab("Console", null, consolePanel, "Console");
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

    //The following line enables to use scrolling tabs.
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(tabbedPane);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(makeInstallPanel());
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    return panel;
  }

  protected JComponent makeBasicSettingsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel javaSpecVersionLabel = new JLabel("Java Specification Version:");

    JLabel mobileJavaHomeLabel = new JLabel("Mobile Java Home:     ");

    JButton mobileJavaHomeSearchButton = new JButton("Search...");

    mobileJavaHomeSearchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(mobileJavaHomeField.getText().trim()));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(frame, "Select Mobile Java Home...");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          mobileJavaHomeField.setText(fileChooser.getSelectedFile().getPath());
        }
      }
    });

    JLabel launcherHomeLabel = new JLabel("Launcher Home:     ");

    JButton launcherHomeSearchButton = new JButton("Search...");

    launcherHomeSearchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(launcherHomeField.getText().trim()));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(frame, "Select Launcher Home...");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          launcherHomeField.setText(fileChooser.getSelectedFile().getPath());
        }
      }
    });

    JLabel scriptlandiaHomeLabel = new JLabel("Scriptlandia Home:     ");

    JButton scriptlandiaHomeSearchButton = new JButton("Search...");

    scriptlandiaHomeSearchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(scriptlandiaHomeField.getText().trim()));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(frame, "Select Scriptlandia Home...");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          scriptlandiaHomeField.setText(fileChooser.getSelectedFile().getPath());
        }
      }
    });

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = 0.5;

    JPanel panel1 = new JPanel();
    constraints.gridy = 0;

    panel1.setLayout(new GridBagLayout());

    constraints.gridx = 0;
    panel1.add(javaSpecVersionLabel, constraints);
    constraints.gridx = 1;
    panel1.add(javaSpecVersionComboBox, constraints);

    JPanel panel2 = new JPanel();
    constraints.gridy = 1;

    panel2.setLayout(new GridBagLayout());

    constraints.gridx = 0;
    panel2.add(launcherHomeLabel, constraints);
    constraints.gridx = 1;
    panel2.add(launcherHomeField, constraints);
    constraints.gridx = 2;
    panel2.add(launcherHomeSearchButton, constraints);

    JPanel panel3 = new JPanel();
    constraints.gridy = 2;

    panel3.setLayout(new GridBagLayout());

    constraints.gridx = 0;
    panel3.add(scriptlandiaHomeLabel, constraints);
    constraints.gridx = 1;
    panel3.add(scriptlandiaHomeField, constraints);
    constraints.gridx = 2;
    panel3.add(scriptlandiaHomeSearchButton, constraints);

    JPanel panel4 = new JPanel();
    constraints.gridy = 3;

    panel4.setLayout(new GridBagLayout());

    constraints.gridx = 0;
    panel4.add(mobileJavaHomeLabel, constraints);
    constraints.gridx = 1;
    panel4.add(mobileJavaHomeField, constraints);
    constraints.gridx = 2;
    panel4.add(mobileJavaHomeSearchButton, constraints);

    panel.add(panel1);
    panel.add(panel2);
    panel.add(panel3);
    panel.add(panel4);
    panel.add(Box.createRigidArea(new Dimension(0, 180)));

    return panel;
  }

  private JPanel makeConsolePanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JPanel panel11 = new JPanel();
    panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));

    panel11.add(new JScrollPane(console.getComponent()));

    JPanel panel12 = new JPanel();
    panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));

    JButton clearButton = new JButton("Clear Console");
    clearButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        console.clear();
      }
    });

    panel12.add(clearButton);

    panel.add(panel11);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(panel12);

    return panel;
  }

  protected JComponent makeInstallPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    installCoreButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        Thread thread = new Thread() {
          public void run() {
            blockControls("Installing Scriptlandia core...");

            try {
              GuiInstaller.this.install(args);
            }
            catch (Exception e) {
              e.printStackTrace();
            }
            finally {
              unblockControls("Scriptlandia core installed.");
            }
          }
        };

        thread.start();
      }
    });

    closeButton = new JButton("Close");

    closeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        frame.exit();
      }
    });

    JPanel panel10 = new JPanel();
    panel10.setLayout(new BoxLayout(panel10, BoxLayout.X_AXIS));

    panel10.add(Box.createRigidArea(new Dimension(10, 0)));
    panel10.add(statusLabel);
    panel10.add(Box.createRigidArea(new Dimension(600, 0)));

    JPanel panel11 = new JPanel();
    panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));

    panel11.add(Box.createRigidArea(new Dimension(200, 0)));
    panel11.add(installCoreButton);
    panel11.add(Box.createRigidArea(new Dimension(50, 0)));
    panel11.add(closeButton);
    panel11.add(Box.createRigidArea(new Dimension(200, 0)));

    panel.add(panel10);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(panel11);

    return panel;
  }

  private int selectedTabIndex;

  private void unblockControls(String message) {
    closeButton.setText("Close");
    enableControls();
    inProcess = false;
    statusLabel.setText(message);
    tabbedPane.setSelectedIndex(selectedTabIndex);
  }

  private void blockControls(String message) {
    selectedTabIndex = tabbedPane.getSelectedIndex();
    tabbedPane.setSelectedIndex(2);

    statusLabel.setText(message);
    inProcess = true;
    disableControls();
    closeButton.setText("Cancel");
  }

  protected JComponent fillLanguagesPanel(final JPanel panel) {
    if (!languagesPanelUpdated) {
      if (System.getProperty("jlaunchpad.home") == null) {
        System.setProperty("jlaunchpad.home", launcherHomeField.getText().trim());
      }

      if (System.getProperty("scriptlandia.home") == null) {
        System.setProperty("scriptlandia.home", scriptlandiaHomeField.getText().trim());
      }

      if (System.getProperty("native.ruby.home") == null) {
        System.setProperty("native.ruby.home", rubyHomeField.getText().trim());
      }

      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

      final JPanel panel1 = new JPanel();
      panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
      panel1.setBorder(new TitledBorder(new EtchedBorder(), "Install/Uninstall Single Language"));

      final JPanel panel20 = new JPanel();
      panel20.setBorder(new TitledBorder(new EtchedBorder(), "Install/Uninstall Multiple Languages"));

      final JPanel panel2 = new JPanel(new CardLayout());

      panel20.add(panel2);

      final JPanel panel3 = new JPanel();
      panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

      panel.add(Box.createRigidArea(new Dimension(0, 10)));
      panel.add(panel1);
      panel.add(Box.createRigidArea(new Dimension(0, 20)));
      panel.add(panel20);
      panel.add(Box.createRigidArea(new Dimension(0, 20)));
      panel.add(panel3);
      panel.add(Box.createRigidArea(new Dimension(0, 100)));

      blockControls("Installing required dependencies...");

      try {
        updateProperties();

        update();

        final JComboBox languageCombo = new JComboBox();

        final JCheckBox installLanguageCheckBox = new JCheckBox("Install/Uninstall");
        installLanguageCheckBox.setSelected(true);
        JButton executeSingleLanguageButton = new JButton("Run");

        executeSingleLanguageButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String name = (String) ((Map) languageCombo.getSelectedItem()).get("name");

            try {
              if (installLanguageCheckBox.isSelected()) {
                blockControls("Installing \"" + name + "\" language...");
                installLanguage(name, languages);
                unblockControls("Language \"" + name + "\" installed.");
              } else {
                blockControls("Unnstalling \"" + name + "\" language...");
                uninstallLanguage(name, languages);
                unblockControls("Language \"" + name + "\" uninstalled.");
              }
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }
        });

        panel1.add(Box.createRigidArea(new Dimension(10, 0)));
        panel1.add(new JLabel("Select language:"));
        panel1.add(Box.createRigidArea(new Dimension(10, 0)));
        panel1.add(languageCombo);

        panel1.add(Box.createRigidArea(new Dimension(10, 0)));
        panel1.add(installLanguageCheckBox);
        panel1.add(Box.createRigidArea(new Dimension(10, 0)));
        panel1.add(executeSingleLanguageButton);
        panel1.add(Box.createRigidArea(new Dimension(500, 0)));

        languageCombo.setModel(new LanguageModel(languages));
        languageCombo.setSelectedIndex(0);
        languageCombo.setRenderer(new LanguageRenderer());

        panel2.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;

        final int COLUMNS = 7;
        for (int i = 0, j = 0; i < languages.size(); i++) {
          Map language = (Map) languages.get(i);
          String name = (String) language.get("name");
          ImageIcon imageIcon = (ImageIcon) language.get("imageIcon");

          JLabel label = new JLabel(name);
          label.setIcon(imageIcon);

          constraints.gridy = i / COLUMNS;

          constraints.gridx = j++;
          panel2.add(checkBoxes[i], constraints);

          constraints.gridx = j++;
          panel2.add(label, constraints);

          if (j == (COLUMNS * 2)) {
            j = 0;
          }
        }

        installLanguagesButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            Thread thread = new Thread() {
              public void run() {
                blockControls("Installing Scriptlandia languages...");

                try {
                  GuiInstaller.this.installLanguages();
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
                finally {
                  unblockControls("Scriptlandia languages installed.");
                }
              }
            };

            thread.start();
          }
        });

        uninstallLanguagesButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            Thread thread = new Thread() {
              public void run() {
                blockControls("Uninstalling Scriptlandia languages...");

                try {
                  GuiInstaller.this.uninstallLanguages();
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
                finally {
                  unblockControls("Scriptlandia languages uninstalled.");
                }
              }
            };

            thread.start();
          }
        });
        JButton selectUnselectAllButton = new JButton("Select/Unselect All");

        selectUnselectAllButton.addActionListener(new ActionListener() {
          private boolean state = true;

          public void actionPerformed(ActionEvent event) {
            setupLanguageCheckboxes(panel2, state);
            state = !state;
          }
        });

        panel3.add(Box.createRigidArea(new Dimension(200, 0)));
        panel3.add(selectUnselectAllButton);
        panel3.add(Box.createRigidArea(new Dimension(50, 0)));
        panel3.add(installLanguagesButton);
        panel3.add(Box.createRigidArea(new Dimension(50, 0)));
        panel3.add(uninstallLanguagesButton);
        panel3.add(Box.createRigidArea(new Dimension(500, 0)));
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      finally {
        languagesPanelUpdated = true;
        unblockControls("");
      }
    }

    return panel;
  }

  private void setupLanguageCheckboxes(Container container, boolean selected) {
    Component[] components = container.getComponents();

    for (Component component : components) {
      if (component instanceof JCheckBox) {
        JCheckBox checkBox = (JCheckBox) component;

        checkBox.setSelected(selected);
      }
    }
  }

  private void update() throws LauncherException {
    languages = readLanguages();

    checkBoxes = new JCheckBox[languages.size()];

    for (int i = 0; i < languages.size(); i++) {
      checkBoxes[i] = new JCheckBox();
    }

    for (int i = 0; i < languages.size(); i++) {
      Map language = (Map) languages.get(i);
      String name = (String) language.get("name");

      updateProperty(scriptlandiaProps, checkBoxes[i], name + ".install");
    }
  }

  private void updateProperties() {
    System.setProperty("java.specification.version.level", (String) javaSpecVersionComboBox.getSelectedItem());

    System.setProperty("mobile.java.home", mobileJavaHomeField.getText().trim());

    System.setProperty("scriptlandia.home", scriptlandiaHomeField.getText().trim());
    System.setProperty("jlaunchpad.home", launcherHomeField.getText().trim());
    System.setProperty("native.ruby.home", rubyHomeField.getText().trim());
  }

  public void install(final String[] args) throws LauncherException {
    try {
      updateProperties();

      try {
        save();
      }
      catch (IOException e) {
        throw new LauncherException(e);
      }

      super.install(args);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void installLanguages() throws LauncherException {
    try {
      updateProperties();

      if (!languagesPanelUpdated) {
        update();
      }

      for (int i = 0; i < languages.size(); i++) {
        Map language = (Map) languages.get(i);
        String name = (String) language.get("name");

        System.setProperty(name + ".install", String.valueOf(checkBoxes[i].isSelected()));


        String version = (String) language.get("version");
        
        if(version != null) {
          System.setProperty(name + ".version", version);
        }
      }

      try {
        save();
      }
      catch (IOException e) {
        throw new LauncherException(e);
      }

      super.installLanguageProjects();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void uninstallLanguages() {
    try {
      updateProperties();

      if (!languagesPanelUpdated) {
        update();
      }

      for (int i = 0; i < languages.size(); i++) {
        Map language = (Map) languages.get(i);
        String name = (String) language.get("name");

        System.setProperty(name + ".install", String.valueOf(checkBoxes[i].isSelected()));

        if(!checkBoxes[i].isSelected()) {
          String version = (String) language.get("version");

          System.setProperty(name + ".version", "");
        }
      }

      try {
        save();
      }
      catch (IOException e) {
        throw new LauncherException(e);
      }

      super.uninstallLanguageProjects();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
  private void load() throws IOException {
    launcherProps.load();

    System.setProperty("java.home.internal", launcherProps.getProperty("java.home.internal"));
    System.setProperty("repository.home", launcherProps.getProperty("repository.home"));

    scriptlandiaProps.load();

    updateProperty(scriptlandiaProps, javaSpecVersionComboBox, "java.specification.version.level");
    updateProperty(scriptlandiaProps, mobileJavaHomeField, "mobile.java.home");
    updateProperty(scriptlandiaProps, scriptlandiaHomeField, "scriptlandia.home");
    updateProperty(scriptlandiaProps, rubyHomeField, "native.ruby.home");
    updateProperty(scriptlandiaProps, launcherHomeField, "jlaunchpad.home");
  }

  @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
  private void save() throws IOException {
    scriptlandiaProps.load();

    saveProperty(scriptlandiaProps, javaSpecVersionComboBox, "java.specification.version.level");
    saveProperty(scriptlandiaProps, mobileJavaHomeField, "mobile.java.home");
    saveProperty(scriptlandiaProps, scriptlandiaHomeField, "scriptlandia.home");
    saveProperty(scriptlandiaProps, rubyHomeField, "native.ruby.home");
    saveProperty(scriptlandiaProps, launcherHomeField, "jlaunchpad.home");

    scriptlandiaProps.put("jlaunchpad.version", System.getProperty("jlaunchpad.version"));
    //scriptlandiaProps.put("jdic.version", System.getProperty("jdic.version"));
    scriptlandiaProps.put("nailgun.version", System.getProperty("nailgun.version"));
    scriptlandiaProps.put("java.compiler.version", System.getProperty("java.compiler.version"));

    for (int i = 0; i < languages.size(); i++) {
      Map language = (Map) languages.get(i);
      String name = (String) language.get("name");

      saveProperty(scriptlandiaProps, checkBoxes[i], name + ".install");

      String version = (String) language.get("version");

      if(version != null) {
        scriptlandiaProps.put(name + ".version", version);
      }
    }

    scriptlandiaProps.save();
  }

  /**
   * Updates GUI component from the property.
   *
   * @param properties properties
   * @param component  GUI component
   * @param property   the property to be propagated
   */
  public void updateProperty(Properties properties, JComponent component, String property) {
    if (component instanceof JTextField) {
      JTextField textField = (JTextField) component;

      String value = (String) properties.get(property);

      if (value != null) {
        textField.setText(value.replace('/', File.separatorChar));
      }
    } else if (component instanceof JCheckBox) {
      JCheckBox checkBox = (JCheckBox) component;

      String value = (String) properties.get(property);

      if (value != null) {
        checkBox.setSelected(Boolean.parseBoolean(value));
      }
    } else if (component instanceof JComboBox) {
      JComboBox comboBox = (JComboBox) component;

      String value = (String) properties.get(property);

      if (value != null) {
        comboBox.setSelectedItem(value);
      }
    }
  }

  /**
   * Updates the property from GUI component.
   *
   * @param properties properties
   * @param component  GUI component
   * @param property   the property to be updated
   */
  public void saveProperty(Properties properties, JComponent component, String property) {
    if (component instanceof JTextField) {
      JTextField textField = (JTextField) component;

      String value = textField.getText().trim();

      properties.put(property, value.replace(File.separatorChar, '/'));
    } else if (component instanceof JCheckBox) {
      JCheckBox checkBox = (JCheckBox) component;

      properties.put(property, String.valueOf(checkBox.isSelected()));
    } else if (component instanceof JComboBox) {
      JComboBox comboBox = (JComboBox) component;

      properties.put(property, comboBox.getSelectedItem());
    }
  }

  public static void loadLibDeploy() {
    String osName = System.getProperty("os.name").toLowerCase();

    if(osName.startsWith("windows")) {
      System.load(Config.getJavaHome() + File.separator + "bin" + File.separator + "deploy.dll");
    }
    else {
      String libDir = System.getProperty("os.arch");

      if (libDir.equals("x86")) {
        libDir = "i386";
      }

      System.load(Config.getJavaHome() + File.separator + "lib" + File.separator + libDir + File.separator + "libdeploy.so");
    }
  }

  /**
   * Launches the GUI installer.
   *
   * @param args The application command-line arguments.
   * @throws LauncherException exception
   */
  public static void main(String[] args) throws LauncherException {
    JLaunchPadLauncher launcher = JLaunchPadLauncher.getInstance();

    launcher.addClasspathEntry(System.getProperty("java.home") + File.separator + "lib" + File.separator + "deploy.jar");

    try {
      loadLibDeploy();
    }
    catch (Throwable t) {
      throw new LauncherException(t);
    }

    new GuiInstaller(args);
  }

}

