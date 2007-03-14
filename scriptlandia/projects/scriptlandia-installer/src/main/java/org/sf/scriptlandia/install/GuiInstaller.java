package org.sf.scriptlandia.install;

import org.sf.scriptlandia.launcher.LauncherException;
import org.sf.scriptlandia.launcher.SimpleLauncher;
import org.sf.scriptlandia.util.ReflectionUtil;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;

import net.sf.image4j.codec.ico.ICODecoder;

/**
 * The class perform initial (gui) installation of scriprlandia.
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class GuiInstaller extends CoreInstaller implements CaretListener, ActionListener {
  private ScriptlandialProperties scriptlandiaProps = new ScriptlandialProperties();

  private JTextField javaHomeField = new JTextField(35);
  private JTextField mobileJavaHomeField = new JTextField(35);
  private JTextField scriptlandiaHomeField = new JTextField(35);
  private JTextField repositoryHomeField = new JTextField(35);
  private JTextField rubyHomeField = new JTextField(35);
  private JComboBox javaSpecVersionComboBox = new JComboBox(new String[] { "1.5", "1.6", "1.7"});

  private JCheckBox useProxyCheckbox = new JCheckBox();
  private JTextField proxyHostField = new JTextField(35);
  private JTextField proxyPortField = new JTextField(6);

  private JButton installButton = new JButton("Install");
  private JTextArea console = new JTextArea();

  private JTabbedPane tabbedPane = new JTabbedPane();

  private JCheckBox[] checkBoxes = new JCheckBox[0];

  protected java.util.List languages = new java.util.ArrayList();

  private boolean languagesPanelUpdated = false;

  private final PrintStream oldOut = System.out;
  private final PrintStream oldErr = System.err;

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

    javaHomeField.addCaretListener(this);
    scriptlandiaHomeField.addCaretListener(this);
    repositoryHomeField.addCaretListener(this);
    rubyHomeField.addCaretListener(this);

    useProxyCheckbox.addActionListener(this);
    proxyHostField.addCaretListener(this);
    proxyPortField.addCaretListener(this);

    frame.getContentPane().add(createContent(), BorderLayout.CENTER);

    tryEnableInstallButton();
    
    frame.setVisible(true);
  }

  private java.util.List readLanguages() throws LauncherException {
    java.util.List languages;

    SimpleLauncher launcher = new SimpleLauncher();

    prepare(launcher, true);
    String repositoryHome = System.getProperty("repository.home");
    launcher.addClasspathEntry(repositoryHome + "/jdom/jdom/1.0/jdom-1.0.jar");

    launcher.configure(Thread.currentThread().getContextClassLoader());

    try {
      Class clazz = launcher.getClassLoader().loadClass("bsh.Interpreter");
      Object object = clazz.newInstance();

      String command =
        "source(\"projects/scriptlandia-config/src/main/bsh/ext-xml-helper.bsh\");" +
        "ExtXmlHelper xmlHelper = new ExtXmlHelper();" +
        "xmlHelper.read(new File(\"languages.xml\"));" +
        "languages = xmlHelper.getLanguages();";

      languages = (java.util.List)ReflectionUtil.invokePrivateMethod(
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

  public void actionPerformed(ActionEvent e) {
    tryEnableInstallButton();
  }

  private void tryEnableInstallButton() {
    String javaHome = javaHomeField.getText().trim();
    String scriptlandiaHome = scriptlandiaHomeField.getText().trim();
    String repositoryHome = repositoryHomeField.getText().trim();

    boolean enabled = false;

    if (javaHome != null && javaHome.length() > 0 && new File(javaHome).exists()) {
      if (scriptlandiaHome != null && scriptlandiaHome.length() > 0) {
        if (repositoryHome != null && repositoryHome.length() > 0) {
          if (!useProxyCheckbox.isSelected()) {
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
          }
        }
      }
    }

    if (useProxyCheckbox.isSelected()) {
      proxyHostField.setEnabled(true);
      proxyPortField.setEnabled(true);
    } else {
      proxyHostField.setEnabled(false);
      proxyPortField.setEnabled(false);
    }

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
    tabbedPane.setEnabledAt(1, false);

    JComponent panel2 = makeProxySettingsPanel();
    tabbedPane.addTab("Proxy Settings", null, panel2, "Proxy Settings");
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

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

    JLabel javaHomeLabel = new JLabel("Java Home:                  ");

    JButton javaHomeSearchButton = new JButton("Search...");

    javaHomeSearchButton.addActionListener(new ActionListener() {
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

    JLabel repositoryHomeLabel = new JLabel("Repository Home:        ");

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

    JPanel panel10 = new JPanel();

    constraints.gridy = 0;

    panel10.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel10.add(javaSpecVersionLabel, constraints);
    constraints.gridx = 1; 
    panel10.add(javaSpecVersionComboBox, constraints);

    JPanel panel11 = new JPanel();

    constraints.gridy = 1;

    panel11.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel11.add(javaHomeLabel, constraints);
    constraints.gridx = 1; panel11.add(javaHomeField, constraints);
    constraints.gridx = 2; panel11.add(javaHomeSearchButton, constraints);

    JPanel panel12 = new JPanel();

    constraints.gridy = 2;

    panel12.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel12.add(mobileJavaHomeLabel, constraints);
    constraints.gridx = 1; panel12.add(mobileJavaHomeField, constraints);
    constraints.gridx = 2; panel12.add(mobileJavaHomeSearchButton, constraints);

    constraints.gridy = 3;

    JPanel panel13 = new JPanel();

    panel13.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel13.add(scriptlandiaHomeLabel, constraints);
    constraints.gridx = 1; panel13.add(scriptlandiaHomeField, constraints);
    constraints.gridx = 2; panel13.add(scriptlandiaHomeSearchButton, constraints);

    constraints.gridy = 4;

    JPanel panel14 = new JPanel();

    panel14.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel14.add(repositoryHomeLabel, constraints);
    constraints.gridx = 1; panel14.add(repositoryHomeField, constraints);
    constraints.gridx = 2; panel14.add(repositoryHomeSearchButton, constraints);

    constraints.gridy = 5;

    JPanel panel15 = new JPanel();

    panel15.setLayout(new GridBagLayout());

    constraints.gridx = 0; panel15.add(rubyHomeLabel, constraints);
    constraints.gridx = 1; panel15.add(rubyHomeField, constraints);
    constraints.gridx = 2; panel15.add(rubyHomeSearchButton, constraints);

    panel.add(panel10);
    panel.add(panel11);
    panel.add(panel12);
    panel.add(panel13);
    panel.add(panel14);
    panel.add(panel15);

    return panel;
  }

  protected JComponent makeProxySettingsPanel() {
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
      if(System.getProperty("scriptlandia.home") == null) {
        System.setProperty("scriptlandia.home", scriptlandiaHomeField.getText().trim());
      }

      if(System.getProperty("repository.home") == null) {
        System.setProperty("repository.home", repositoryHomeField.getText().trim());
      }

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
            updatePrperties();
            update();

            panel.setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 0.5;

            final int COLUMNS = 3;
            for(int i=0, j=0; i < languages.size(); i++) {
              Map language = (Map)languages.get(i);
              String name = (String)language.get("name");

              File file = new File("projects/scriptlandia-config/bin/images/" + language.get("icon"));

              if(!file.exists()) {
                file = new File("projects/scriptlandia-config/bin/images/" + "scriptlandia.ico");                
              }

              java.util.List<java.awt.image.BufferedImage> images = ICODecoder.read(file);

              ImageIcon icon = new ImageIcon(images.get(0));

              JLabel label = new JLabel(name);
              label.setIcon(icon);

              constraints.gridy = i / COLUMNS;

              constraints.gridx = j++;
              panel.add(label, constraints);

              //System.out.println("constraints1 " + constraints.gridy + " " + constraints.gridx);

              constraints.gridx = j++;
              panel.add(checkBoxes[i], constraints);

              //System.out.println("constraints2 " + constraints.gridy + " " + constraints.gridx);              

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

  private void update() throws LauncherException {
    coreInstall();

    languages = readLanguages();

    checkBoxes = new JCheckBox[languages.size()];

    for(int i=0; i < languages.size(); i++) {
      checkBoxes[i] = new JCheckBox();
    }

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);
      String name = (String)language.get("name");

      scriptlandiaProps.updateProperty(checkBoxes[i], name + ".install");
    }
  }

  private void updatePrperties() {
   System.setProperty("java.specification.version", (String)javaSpecVersionComboBox.getSelectedItem());

   System.setProperty("java.home.internal", javaHomeField.getText().trim());

    if (useProxyCheckbox.isSelected()) {
      System.setProperty("proxy.server.host", proxyHostField.getText().trim());
      System.setProperty("proxy.server.port", proxyPortField.getText().trim());
    } else {
      System.setProperty("proxy.server.host", "");
      System.setProperty("proxy.server.port", "");
    }

    System.setProperty("mobile.java.home", mobileJavaHomeField.getText().trim());

    System.setProperty("scriptlandia.home", scriptlandiaHomeField.getText().trim());
    System.setProperty("repository.home", repositoryHomeField.getText().trim());
    System.setProperty("maven.repo.local", repositoryHomeField.getText().trim());    
    System.setProperty("native.ruby.home", rubyHomeField.getText().trim());
  }

  public void install(final String[] args) throws LauncherException {
/*    final PrintStream oldOut = System.out;
    final PrintStream oldErr = System.err;

    final FilterOutputStream filterOutputStream = new FilterOutputStream(new ByteArrayOutputStream()) {
      public void write(byte b[], int off, int len) throws IOException {
        super.write(b, off, len);

        console.append(new String(b, off, len));
        console.setCaretPosition(console.getDocument().getLength());
      }
    };
  */
    try {
/*      System.setOut(new PrintStream(filterOutputStream));
      System.setErr(new PrintStream(filterOutputStream));
*/

      updatePrperties();

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
    finally {
//      System.setOut(oldOut);
//      System.setErr(oldErr);
    }
  }

  @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
  private void load() throws IOException {
    scriptlandiaProps.load();

    scriptlandiaProps.updateProperty(javaSpecVersionComboBox, "java.specification.version");
    scriptlandiaProps.updateProperty(javaHomeField, "java.home");

    scriptlandiaProps.updateProperty(mobileJavaHomeField, "mobile.java.home");
    scriptlandiaProps.updateProperty(scriptlandiaHomeField, "scriptlandia.home");
    scriptlandiaProps.updateProperty(repositoryHomeField, "repository.home");
    scriptlandiaProps.updateProperty(rubyHomeField, "native.ruby.home");

    scriptlandiaProps.updateProperty(useProxyCheckbox, "use.proxy");
    scriptlandiaProps.updateProperty(proxyHostField, "proxy.server.host");
    scriptlandiaProps.updateProperty(proxyPortField, "proxy.server.port");
  }

  @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
  private void save() throws IOException {
    scriptlandiaProps.load();

    scriptlandiaProps.saveProperty(javaSpecVersionComboBox, "java.specification.version");
    scriptlandiaProps.saveProperty(javaHomeField, "java.home");
    scriptlandiaProps.saveProperty(mobileJavaHomeField, "mobile.java.home");

    scriptlandiaProps.saveProperty(scriptlandiaHomeField, "scriptlandia.home");
    scriptlandiaProps.saveProperty(repositoryHomeField, "repository.home");
    scriptlandiaProps.saveProperty(rubyHomeField, "native.ruby.home");    

    scriptlandiaProps.saveProperty(useProxyCheckbox, "use.proxy");
    scriptlandiaProps.saveProperty(proxyHostField, "proxy.server.host");
    scriptlandiaProps.saveProperty(proxyPortField, "proxy.server.port");

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);
      String name = (String)language.get("name");

      scriptlandiaProps.saveProperty(checkBoxes[i], name + ".install");
    }

    scriptlandiaProps.save();
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

