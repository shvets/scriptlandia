package org.sf.scriptlandia.install;

import org.sf.jlaunchpad.core.LauncherException;
import org.sf.jlaunchpad.core.SimpleLauncher;
import org.sf.jlaunchpad.install.LauncherProperties;
import org.sf.jlaunchpad.util.ReflectionUtil;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * The class perform initial (gui) installation of scriprlandia.
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class GuiInstaller extends CoreInstaller implements CaretListener/*, ActionListener*/ {
  public final static String LAUNCHER_PROPERTIES =
          System.getProperty("user.home") + File.separatorChar + ".jlaunchpad";
  private final static String SCRIPTLANDIA_PROPERTIES =
          System.getProperty("user.home") + File.separatorChar + ".scriptlandia";

  protected LauncherProperties launcherProps = new LauncherProperties(LAUNCHER_PROPERTIES);
  private ScriptlandialProperties scriptlandiaProps = new ScriptlandialProperties(SCRIPTLANDIA_PROPERTIES);

  //private JTextField javaHomeField = new JTextField(35);
  private JTextField mobileJavaHomeField = new JTextField(35);
  private JTextField scriptlandiaHomeField = new JTextField(35);
  private JTextField launcherHomeField = new JTextField(35);
  //private JTextField repositoryHomeField = new JTextField(35);
  private JTextField rubyHomeField = new JTextField(35);
  private JComboBox javaSpecVersionComboBox = new JComboBox(new String[] { "1.5", "1.6", "1.7"});

  //private JCheckBox useProxyCheckbox = new JCheckBox();
  //private JTextField proxyHostField = new JTextField(35);
  //private JTextField proxyPortField = new JTextField(6);

  private JButton installButton = new JButton("Install");
  private JTextArea console = new JTextArea();

  private JTabbedPane tabbedPane = new JTabbedPane();

  private JCheckBox[] checkBoxes = new JCheckBox[0];

  protected java.util.List languages = new java.util.ArrayList();

  private boolean languagesPanelUpdated = false;

  private final FilterOutputStream filterOutputStream = new FilterOutputStream(new ByteArrayOutputStream()) {
    public void write(byte b[], int off, int len) throws IOException {
      super.write(b, off, len);

      console.append(new String(b, off, len));
      console.setCaretPosition(console.getDocument().getLength());
    }
  };

  private GuiInstallerFrame frame = new GuiInstallerFrame();

  private String[] args;

  /**
   * Creates new GUI installer.
   * @param args command line arguments
   * @throws LauncherException the exception
   */
  public GuiInstaller(String[] args) throws LauncherException {
    this.args = args;

    System.setOut(new PrintStream(filterOutputStream));
    System.setErr(new PrintStream(filterOutputStream));

    try {
      load();
    }
    catch (IOException e) {
      throw new LauncherException(e);
    }

    console.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    Font currentFont = console.getFont();
    console.setFont(new Font(currentFont.getName(), Font.BOLD, currentFont.getSize()));

    //javaHomeField.addCaretListener(this);
    scriptlandiaHomeField.addCaretListener(this);
    launcherHomeField.addCaretListener(this);
    //repositoryHomeField.addCaretListener(this);
    //rubyHomeField.addCaretListener(this);

    //useProxyCheckbox.addActionListener(this);
    //proxyHostField.addCaretListener(this);
    //proxyPortField.addCaretListener(this);

    frame.getContentPane().add(createContent(), BorderLayout.CENTER);

    //tryEnableInstallButton();
    
    frame.setVisible(true);
  }

  private java.util.List readLanguages() throws LauncherException {
    java.util.List languages;

    SimpleLauncher launcher = new SimpleLauncher(new String[] {});

    prepare(launcher, true);

    launcher.configure(Thread.currentThread().getContextClassLoader());

    try {
      Class clazz = launcher.getClassLoader().loadClass("bsh.Interpreter");
      Object object = clazz.newInstance();

      String command =
        "source(\"projects/scriptlandia-config/src/main/bsh/ext-xml-helper.bsh\");" +
        "ExtXmlHelper xmlHelper = new ExtXmlHelper();" +
        "xmlHelper.readLanguages(\"languages\");" +
        "languages = xmlHelper.getLanguages();";

      languages = (java.util.List) ReflectionUtil.invokePrivateMethod(
        object, new Object[] { command }, clazz, "eval", new Class[] { String.class });
    }
    catch (Exception e) {
      throw new LauncherException(e);
    }

    return languages;
  }

  public void caretUpdate(CaretEvent e) {
    tryEnableInstallButton();
  }

/*  public void actionPerformed(ActionEvent e) {
    tryEnableInstallButton();
  }
*/

  private void tryEnableInstallButton() {
    //String javaHome = javaHomeField.getText().trim();
    String launcherHome = launcherHomeField.getText().trim();
    String scriptlandiaHome = scriptlandiaHomeField.getText().trim();
    //String repositoryHome = repositoryHomeField.getText().trim();

    boolean enabled = false;

    //if (javaHome != null && javaHome.length() > 0 && new File(javaHome).exists()) {
      if (launcherHome != null && launcherHome.length() > 0) {
        if (scriptlandiaHome != null && scriptlandiaHome.length() > 0) {
          enabled = true;
          //if (repositoryHome != null && repositoryHome.length() > 0) {
            /*if (!useProxyCheckbox.isSelected()) {
              enabled = true;
            } else {
              String proxyHost = proxyHostField.getText().trim();
              String proxyPort = proxyPortField.getText().trim();

              if (proxyHost != null && proxyHost.length() > 0) {
                if (proxyPort != null && proxyPort.length() > 0) {
                  try {
                    Integer.parseInt(proxyPort);

                    enabled = true;
                  }
                  catch (NumberFormatException e) {
                    //noinspection UnnecessarySemicolon
                    ;
                  }
                }
              }
            }*/
          //}
        }
      }
    //}

/*    if (useProxyCheckbox.isSelected()) {
      proxyHostField.setEnabled(true);
      proxyPortField.setEnabled(true);
    } else {
      proxyHostField.setEnabled(false);
      proxyPortField.setEnabled(false);
    }
*/
    installButton.setEnabled(enabled);

    tabbedPane.setEnabledAt(1, enabled);
  }

  public JPanel createContent() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JComponent panel1 = makeBasicSettingsPanel();
    tabbedPane.addTab("Basic Settings", null, panel1, "Specifies important locations.");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

    final JPanel languagesPanel = new JPanel();
    tabbedPane.addTab("Languages/Extensions", null, new JScrollPane(languagesPanel), "Languages/Extensions");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
   //tabbedPane.setEnabledAt(1, false);

/*    JComponent panel2 = makeProxySettingsPanel();
    tabbedPane.addTab("Proxy Settings", null, panel2, "Proxy Settings");
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
*/
    tabbedPane.addChangeListener(new ChangeListener() {
      // This method is called whenever the selected tab changes
      public void stateChanged(ChangeEvent event) {
        JTabbedPane pane = (JTabbedPane)event.getSource();

        // Get current tab
        int selectedIndex = pane.getSelectedIndex();

        if(selectedIndex == 1) {
          makeLanguagesPanel(languagesPanel);
        }
      }
    });

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

//    JLabel javaHomeLabel = new JLabel("Java Home:                  ");

//    JButton javaHomeSearchButton = new JButton("Search...");

/*    javaHomeSearchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(javaHomeField.getText().trim()));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(frame, "Select Java Home...");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          javaHomeField.setText(fileChooser.getSelectedFile().getPath());
        }
      }
    });
*/
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

/*    JLabel repositoryHomeLabel = new JLabel("Repository Home:        ");

    JButton repositoryHomeSearchButton = new JButton("Search...");

    repositoryHomeSearchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(repositoryHomeField.getText().trim()));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(frame, "Select Repository Home...");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          repositoryHomeField.setText(fileChooser.getSelectedFile().getPath());
        }
      }
    });

*/
    JLabel rubyHomeLabel = new JLabel("Ruby Home:                  ");

    JButton rubyHomeSearchButton = new JButton("Search...");

    rubyHomeSearchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(rubyHomeField.getText().trim()));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(frame, "Select Ruby Home...");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          rubyHomeField.setText(fileChooser.getSelectedFile().getPath());
        }
      }
    });

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = 0.5;

    JPanel panel1 = new JPanel();

    constraints.gridy = 0;

    panel1.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel1.add(javaSpecVersionLabel, constraints);
    constraints.gridx = 1; 
    panel1.add(javaSpecVersionComboBox, constraints);

/*    JPanel panel2 = new JPanel();

    constraints.gridy = 1;

    panel2.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel2.add(javaHomeLabel, constraints);
    constraints.gridx = 1; panel2.add(javaHomeField, constraints);
    constraints.gridx = 2; panel2.add(javaHomeSearchButton, constraints);
*/
    JPanel panel2 = new JPanel();

    constraints.gridy = 1;

    panel2.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel2.add(mobileJavaHomeLabel, constraints);
    constraints.gridx = 1; panel2.add(mobileJavaHomeField, constraints);
    constraints.gridx = 2; panel2.add(mobileJavaHomeSearchButton, constraints);

    constraints.gridy = 2;

    JPanel panel3 = new JPanel();

    panel3.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel3.add(launcherHomeLabel, constraints);
    constraints.gridx = 1; panel3.add(launcherHomeField, constraints);
    constraints.gridx = 2; panel3.add(launcherHomeSearchButton, constraints);

    constraints.gridy = 3;

    JPanel panel4 = new JPanel();

    panel4.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel4.add(scriptlandiaHomeLabel, constraints);
    constraints.gridx = 1; panel4.add(scriptlandiaHomeField, constraints);
    constraints.gridx = 2; panel4.add(scriptlandiaHomeSearchButton, constraints);

    constraints.gridy = 4;

/*    JPanel panel15 = new JPanel();

    panel15.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel15.add(repositoryHomeLabel, constraints);
    constraints.gridx = 1; panel15.add(repositoryHomeField, constraints);
    constraints.gridx = 2; panel15.add(repositoryHomeSearchButton, constraints);
*/
    constraints.gridy = 5;

    JPanel panel5 = new JPanel();

    panel5.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel5.add(rubyHomeLabel, constraints);
    constraints.gridx = 1; panel5.add(rubyHomeField, constraints);
    constraints.gridx = 2; panel5.add(rubyHomeSearchButton, constraints);

    panel.add(panel1);
    //panel.add(panel2);
    panel.add(panel2);
    //panel.add(panel4);
    panel.add(panel3);
    //panel.add(panel15);
    panel.add(panel4);
    panel.add(panel5);

    return panel;
  }

/*  protected JComponent makeProxySettingsPanel() {
    JPanel panel = new JPanel();

    SpringLayout layout = new SpringLayout();

    panel.setLayout(layout);

    JLabel label1 = new JLabel("Use Proxy:");
    JLabel label2 = new JLabel("Host/port:");

    panel.add(label1);
    panel.add(useProxyCheckbox);
    panel.add(label2);
    panel.add(proxyHostField);
    panel.add(proxyPortField);

    layout.putConstraint(SpringLayout.WEST, label1, 5, SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.NORTH, label1, 5, SpringLayout.NORTH, panel);

    layout.putConstraint(SpringLayout.WEST, useProxyCheckbox, 5, SpringLayout.EAST, label1);
    layout.putConstraint(SpringLayout.NORTH, useProxyCheckbox, 5, SpringLayout.NORTH, panel);

    layout.putConstraint(SpringLayout.WEST, label2, 5, SpringLayout.EAST, useProxyCheckbox);
    layout.putConstraint(SpringLayout.NORTH, label2, 5, SpringLayout.NORTH, panel);

    layout.putConstraint(SpringLayout.WEST, proxyHostField, 5, SpringLayout.EAST, label2);
    layout.putConstraint(SpringLayout.NORTH, proxyHostField, 5, SpringLayout.NORTH, panel);

    layout.putConstraint(SpringLayout.WEST, proxyPortField, 5, SpringLayout.EAST, proxyHostField);
    layout.putConstraint(SpringLayout.NORTH, proxyPortField, 5, SpringLayout.NORTH, panel);
    
    return panel;
  }
*/
  protected JComponent makeInstallPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    installButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        Thread thread = new Thread() {
          public void run() {
            Component glassPane = frame.getGlassPane();

            glassPane.setVisible(true);

            glassPane.addMouseListener(new MouseAdapter() {
              @SuppressWarnings({"UnnecessarySemicolon"})
              public void mousePressed(MouseEvent e) {
                ; // supress
              }
            });

            try {
              GuiInstaller.this.install(args);
            }
            catch (Exception e) {
              e.printStackTrace();
            }
            finally {
              glassPane.setVisible(false);
            }
          }
        };

        thread.start();
      }
    });

    JButton closeButton = new JButton("Close");

    closeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        frame.cancel();
      }
    });

    JPanel panel11 = new JPanel();
    panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));

    panel11.add(Box.createRigidArea(new Dimension(200, 0)));
    panel11.add(installButton);
    panel11.add(Box.createRigidArea(new Dimension(50, 0)));
    panel11.add(closeButton);
    panel11.add(Box.createRigidArea(new Dimension(200, 0)));

    JPanel panel12 = new JPanel();
    panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));

    console.setRows(10);
    console.setColumns(300);
    panel12.add(new JScrollPane(console));

    panel.add(panel11);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(panel12);

    return panel;
  }

  protected JComponent makeLanguagesPanel(final JPanel panel) {
    if(!languagesPanelUpdated) {
      if(System.getProperty("launcher.home") == null) {
        System.setProperty("launcher.home", launcherHomeField.getText().trim());
      }

      if(System.getProperty("scriptlandia.home") == null) {
        System.setProperty("scriptlandia.home", scriptlandiaHomeField.getText().trim());
      }

/*      if(System.getProperty("repository.home") == null) {
        System.setProperty("repository.home", repositoryHomeField.getText().trim());
      }
*/
      if(System.getProperty("native.ruby.home") == null) {
        System.setProperty("native.ruby.home", rubyHomeField.getText().trim());
      }

      Thread thread = new Thread() {
        public void run() {
          Component glassPane = frame.getGlassPane();

          glassPane.setVisible(true);

          glassPane.addMouseListener(new MouseAdapter() {
            @SuppressWarnings({"UnnecessarySemicolon"})
            public void mousePressed(MouseEvent e) {
              ; // supress
            }
          });

          try {
            updateProperties();

            update();

            panel.setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 0.5;

            final int COLUMNS = 3;
            for(int i=0, j=0; i < languages.size(); i++) {
              Map language = (Map)languages.get(i);
              String name = (String)language.get("name");
              ImageIcon imageIcon = (ImageIcon)language.get("imageIcon");

              JLabel label = new JLabel(name);
              label.setIcon(imageIcon);

              constraints.gridy = i / COLUMNS;

              constraints.gridx = j++;
              panel.add(label, constraints);

              constraints.gridx = j++;
              panel.add(checkBoxes[i], constraints);

              if(j == (COLUMNS*2)) {
                j = 0;
              }
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          finally {
            languagesPanelUpdated = true;
            glassPane.setVisible(false);
          }
        }
      };

      thread.start();
    }

    return panel;
  }

  public boolean isConfigMode() {
    String config = System.getProperty("config");

    return !(config == null || config.equalsIgnoreCase(("false")));
  }

  private void update() throws LauncherException {
    if(!isConfigMode() ) {
      coreInstall();
    }

    languages = readLanguages();

    checkBoxes = new JCheckBox[languages.size()];

    for(int i=0; i < languages.size(); i++) {
      checkBoxes[i] = new JCheckBox();
    }

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);
      String name = (String)language.get("name");

      updateProperty(scriptlandiaProps, checkBoxes[i], name + ".install");
    }
  }

  private void updateProperties() {
   System.setProperty("java.specification.version", (String)javaSpecVersionComboBox.getSelectedItem());

   /*System.setProperty("java.home.internal", javaHomeField.getText().trim());

    if (useProxyCheckbox.isSelected()) {
      System.setProperty("proxyHost", proxyHostField.getText().trim());
      System.setProperty("proxyPort", proxyPortField.getText().trim());
    } else {
      System.setProperty("proxyHost", "");
      System.setProperty("proxyPort", "");
    }
*/
    System.setProperty("mobile.java.home", mobileJavaHomeField.getText().trim());

    System.setProperty("scriptlandia.home", scriptlandiaHomeField.getText().trim());
    System.setProperty("launcher.home", launcherHomeField.getText().trim());
    //System.setProperty("repository.home", repositoryHomeField.getText().trim());
//    System.setProperty("maven.repo.local", repositoryHomeField.getText().trim());    
    System.setProperty("native.ruby.home", rubyHomeField.getText().trim());
  }

  public void install(final String[] args) throws LauncherException {
    try {
      updateProperties();

      if(!languagesPanelUpdated) {
        update();
      }

      for(int i=0; i < languages.size(); i++) {
        Map language = (Map)languages.get(i);
        String name = (String)language.get("name");

        System.setProperty(name + ".install", String.valueOf(checkBoxes[i].isSelected()));
      }

      try {
        save();
      }
      catch (IOException e) {
        throw new LauncherException(e);
      }

      GuiInstaller.super.install(args);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
  private void load() throws IOException {
    launcherProps.load();

    System.setProperty("use.proxy", launcherProps.getProperty("use.proxy"));
    System.setProperty("proxyHost", launcherProps.getProperty("proxyHost"));
    System.setProperty("proxyPort", launcherProps.getProperty("proxyPort"));
    System.setProperty("java.home.internal", launcherProps.getProperty("java.home.internal"));
    System.setProperty("repository.home", launcherProps.getProperty("repository.home"));

    scriptlandiaProps.load();

    updateProperty(scriptlandiaProps, javaSpecVersionComboBox, "java.specification.version");
    updateProperty(scriptlandiaProps, mobileJavaHomeField, "mobile.java.home");
    updateProperty(scriptlandiaProps, scriptlandiaHomeField, "scriptlandia.home");
    updateProperty(scriptlandiaProps, rubyHomeField, "native.ruby.home");
    updateProperty(scriptlandiaProps, launcherHomeField, "launcher.home");

/*    scriptlandiaProps.updateProperty(javaHomeField, "java.home");
    scriptlandiaProps.updateProperty(repositoryHomeField, "repository.home");
    scriptlandiaProps.updateProperty(useProxyCheckbox, "use.proxy");
    scriptlandiaProps.updateProperty(proxyHostField, "proxyHost");
    scriptlandiaProps.updateProperty(proxyPortField, "proxyPort");
*/
  }

  @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
  private void save() throws IOException {
    scriptlandiaProps.load();

    saveProperty(scriptlandiaProps, javaSpecVersionComboBox, "java.specification.version");
    saveProperty(scriptlandiaProps, mobileJavaHomeField, "mobile.java.home");
    saveProperty(scriptlandiaProps, scriptlandiaHomeField, "scriptlandia.home");
    saveProperty(scriptlandiaProps, rubyHomeField, "native.ruby.home");
    saveProperty(scriptlandiaProps, launcherHomeField, "launcher.home");

/*    scriptlandiaProps.saveProperty(javaHomeField, "java.home");
    scriptlandiaProps.saveProperty(repositoryHomeField, "repository.home");
    scriptlandiaProps.saveProperty(useProxyCheckbox, "use.proxy");
    scriptlandiaProps.saveProperty(proxyHostField, "proxyHost");
    scriptlandiaProps.saveProperty(proxyPortField, "proxyPort");
*/
    scriptlandiaProps.put("launcher.version", System.getProperty("launcher.version"));
    scriptlandiaProps.put("jdic.version", System.getProperty("jdic.version"));
    scriptlandiaProps.put("nailgun.version", System.getProperty("nailgun.version"));
    scriptlandiaProps.put("java.compiler.version", System.getProperty("java.compiler.version"));

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);
      String name = (String)language.get("name");

      saveProperty(scriptlandiaProps, checkBoxes[i], name + ".install");
    }

    scriptlandiaProps.save();
  }

  /**
   * Updates GUI component from the property.
   *
   * @param properties properties
   * @param component GUI component
   * @param property the property to be propagated
   */
  public void updateProperty(Properties properties, JComponent component, String property) {
    if(component instanceof JTextField) {
      JTextField textField = (JTextField)component;

      String value = (String)properties.get(property);

      if(value != null) {
        textField.setText(value.replace('/', File.separatorChar));
      }
    }
    else if(component instanceof JCheckBox) {
      JCheckBox checkBox = (JCheckBox)component;

      String value = (String)properties.get(property);

      if(value != null) {
        checkBox.setSelected(Boolean.parseBoolean(value));
      }
    }
    else if(component instanceof JComboBox) {
      JComboBox comboBox = (JComboBox)component;

      String value = (String)properties.get(property);

      if(value != null) {
        comboBox.setSelectedItem(value);
      }
    }
  }

  /**
   * Updates the property from GUI component.
   *
   * @param properties properties
   * @param component GUI component
   * @param property the property to be updated
   */
  public void saveProperty(Properties properties, JComponent component, String property) {
    if(component instanceof JTextField) {
      JTextField textField = (JTextField)component;

      String value = textField.getText().trim();

      properties.put(property, value.replace(File.separatorChar, '/'));
    }
    else if(component instanceof JCheckBox) {
      JCheckBox checkBox = (JCheckBox)component;

      properties.put(property, String.valueOf(checkBox.isSelected()));
    }
    else if(component instanceof JComboBox) {
      JComboBox comboBox = (JComboBox)component;

      properties.put(property, comboBox.getSelectedItem());
    }
  }


  /**
   * Launches the GUI installer.
   *
   * @param args The application command-line arguments.
   * @throws LauncherException exception
   */
  public static void main(String[] args) throws LauncherException {
    new GuiInstaller(args);
  }

}

