package org.sf.scriptlandia.install;


import org.sf.jlaunchpad.core.LauncherException;
import org.sf.jlaunchpad.core.SimpleLauncher;
import org.sf.pomreader.ProjectInstaller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class perform initial (command line) installation of scriptlandia.
 *
 * @author Alexander Shvets
 * @version 1.0 01/14/2007
 */
public class CoreInstaller {

  /**
   * Creates installer.
   *
   * @throws LauncherException the exception
   */
  public CoreInstaller() throws LauncherException {
  }

  public void coreInstall() throws LauncherException {
    installAntRun();
  }

  /**
   * Installs AntRun project.
   *
   * @throws LauncherException the exception
   */
  private void installAntRun() throws LauncherException {
    String antVersion = System.getProperty("ant.version.internal");
    String repositoryHome = System.getProperty("repository.home");

    SimpleLauncher launcher = new SimpleLauncher(getAntRunArgsList());

    launcher.setMainClassName("org.sf.pomreader.ProjectInstaller");

    launcher.addClasspathEntry("projects/scriptlandia-installer/target/scriptlandia-installer.jar");

    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant-launcher/" + antVersion +
        "/ant-launcher-" + antVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant/" + antVersion +
        "/ant-" + antVersion + ".jar");

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  /**
   * Gets the ant run arguments list.
   *
   * @return the ant run arguments list
   */
  private static String[] getAntRunArgsList() {
    List<String> newArgsList = new ArrayList<String>();

    newArgsList.add("-basedir");
    newArgsList.add("projects/antrun");
    newArgsList.add("-build.required");
    newArgsList.add("false");

    String[] newArgs = new String[newArgsList.size()];

    newArgsList.toArray(newArgs);

    return newArgs;
  }

  /**
   * Performs installation of initial components/projects.
   *
   * @param args the command line arguments
   * @throws LauncherException the exception
   */
  public void install(String[] args) throws LauncherException {
      instalRequiredlProjects(args);

      try {
        installProjects();

        generateScripts();

        System.out.println("Scriptlandia core installed.");
      } catch (Exception e) {
        throw new LauncherException(e.getMessage());
      }
  }

  /**
   * Installs required projects.
   *
   * @param args the command line arguments
   * @throws LauncherException the exception
   */
  private void instalRequiredlProjects(String[] args) throws LauncherException {
    SimpleLauncher launcher = new SimpleLauncher(args);

    launcher.addClasspathEntry("projects/scriptlandia-installer/target/scriptlandia-installer.jar");

    prepare(launcher);

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  protected void prepare(SimpleLauncher launcher/*, boolean config*/) throws LauncherException {
    launcher.setMainClassName("org.sf.scriptlandia.antrun.AntRun");

      launcher.addClasspathEntry("projects/antrun/target/antrun.jar");
  }

  private void installProjects() throws Exception {
    ProjectInstaller installer = new ProjectInstaller();

    installer.install("projects/scriptlandia-nailgun", false);
    installer.install("projects/scriptlandia-launcher", false);
    installer.install("projects/scriptlandia-helper", false);
  }

  private void generateScripts() throws IOException {
    ScriptGenerator scriptGenerator = new ScriptGenerator();

    scriptGenerator.generate();
  }

  /**
   * Installs languages projects.
   *
   * @throws LauncherException the exception
   */
  protected void installLanguageProjects() throws Exception {
    installLanguages("core", false);

    installLanguages("starter", false);

    new ExtInstaller().registerLanguages();

    System.out.println("Scriptlandia supported languages installed.");
  }

  private void installLanguages(String section, boolean install) throws Exception {
    ProjectInstaller installer = new ProjectInstaller(true);

    File[] files = new File("languages").listFiles();

    for (int i = 0; i < files.length; i++) {
      if (!files[i].isHidden() && files[i].isDirectory()) {
        String name = files[i].getName();

        boolean requiresInstallation = false;

        if (Boolean.valueOf(System.getProperty(name + ".install")).booleanValue() || install == true) {
          requiresInstallation = true;
        }

        if (requiresInstallation) {
          boolean compile = false;

          if (new File("languages/" + name + "/" + section + "/src/main/java").exists() &&
              !new File("languages/" + name + "/" + section + "/target/" + name + "-" + section + ".jar").exists()) {
            compile = true;
          }

          installer.install("languages/" + name + "/" + section, compile);
        }
      }
    }
  }

  protected List readLanguages() throws LauncherException {
    java.util.List languages;

    SimpleLauncher launcher = new SimpleLauncher(new String[] {});

    prepare(launcher);

    launcher.configure(Thread.currentThread().getContextClassLoader());

    try {
      ExtXmlHelper xmlHelper = new ExtXmlHelper();
      xmlHelper.readLanguages("languages");

      languages = xmlHelper.getLanguages();
    }
    catch (Exception e) {
      throw new LauncherException(e);
    }

    return languages;
  }

  /**
   * Launches the installer from the command line.
   *
   * @param args The application command-line arguments.
   * @throws LauncherException exception
   */
  public static void main(String[] args) throws LauncherException {
    CoreInstaller installer = new CoreInstaller();

    installer.coreInstall();
    installer.install(args);
  }

}
