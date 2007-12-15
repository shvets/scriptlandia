package org.sf.scriptlandia.install;


import org.sf.jlaunchpad.core.SimpleLauncher;
import org.sf.jlaunchpad.core.LauncherException;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
  public CoreInstaller() throws LauncherException {}

  public void coreInstall() throws LauncherException {
    //installBasicDependencies();

    installAntRun();
  }

  public boolean isConfigMode() {
    String config = System.getProperty("config");

    return !(config == null || config.equalsIgnoreCase(("false")));
  }

  /**
   * Performs installation of initial components/projects.
   *
   * @param args the command line arguments
   * @throws LauncherException the exception
   */
  public void install(String[] args) throws LauncherException {
    String config = System.getProperty("config");

    if(config == null || !Boolean.parseBoolean(config)) {
      instalRequiredlProjects(args);
    }
    else if(Boolean.parseBoolean(config)) {
      config(args);
    }
  }

  /**
   * Installs basic dependencies.
   *
   * @throws LauncherException the exception
   */
  private void installBasicDependencies() throws LauncherException {
    SimpleLauncher launcher = new SimpleLauncher(getBasicDependenciesArgsList());

    launcher.setMainClassName("org.sf.pomreader.ProjectInstaller");

    addCoreJars(launcher);

    launcher.addClasspathEntry("projects/scriptlandia-installer/target/scriptlandia-installer.jar");

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  /**
   * Gets the basic dependencies arguments list.
   *
   * @return the basic dependencies arguments list
   */
  private static String[] getBasicDependenciesArgsList() {
    List<String> newArgsList = new ArrayList<String>();

    newArgsList.add("-basedir");
    newArgsList.add("projects/scriptlandia-startup");
    newArgsList.add("-build.required");
    newArgsList.add("true");

    String[] newArgs = new String[newArgsList.size()];

    newArgsList.toArray(newArgs);

    return newArgs;
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

    addCoreJars(launcher);

    launcher.addClasspathEntry("projects/scriptlandia-installer/target/scriptlandia-installer.jar");

    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant-launcher/" + antVersion +
                                "/ant-launcher-" + antVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant/" + antVersion +
                                "/ant-" + antVersion + ".jar");

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  private void addCoreJars(SimpleLauncher launcher) throws LauncherException {
    String repositoryHome = System.getProperty("repository.home");
    
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/jlaunchpad-common/1.0.1/jlaunchpad-common-1.0.1.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/apache/maven/bootstrap/bootstrap-mini/2.0.8/bootstrap-mini-2.0.8.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/pom-reader/1.0.1/pom-reader-1.0.1.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/jlaunchpad-launcher/1.0.1/jlaunchpad-launcher-1.0.1.jar");    
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
   * Installs required projects.
   *
   * @param args the command line arguments
   * @throws LauncherException the exception
   */
  private void instalRequiredlProjects(String[] args) throws LauncherException {
    SimpleLauncher launcher = new SimpleLauncher(args);

//    launcher.addClasspathEntry(getScriptlandiaInstallerJarName());
    launcher.addClasspathEntry("projects/scriptlandia-installer/target/scriptlandia-installer.jar");

    prepare(launcher, false);

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  /**
   * Performs the configuration of scriptlandia.
   *
   * @param args the command line arguments
   * @throws LauncherException the exception
   */
  public void config(String[] args) throws LauncherException {
    SimpleLauncher launcher = new SimpleLauncher(getConfigArgsList(args));

    prepare(launcher, true);

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  /**
   * Gets the config arguments list.
   *
   * @param args command line argumemts
   * @return the config arguments list
   */
  private static String[] getConfigArgsList(String[] args) {
    List<String> newArgsList = new ArrayList<String>();

//    newArgsList.add("-f");
//    newArgsList.add("config.ant");
    newArgsList.add("config");

    newArgsList.addAll(Arrays.asList(args));

    String[] newArgs = new String[newArgsList.size()];

    newArgsList.toArray(newArgs);

    return newArgs;
  }

  protected void prepare(SimpleLauncher launcher, boolean config) throws LauncherException {
    String antVersion = System.getProperty("ant.version.internal");
    String beanshellVersion = System.getProperty("beanshell.version");
    String scriptlandiaVersion = System.getProperty("scriptlandia.version");
    String repositoryHome = System.getProperty("repository.home");

    launcher.setMainClassName("org.sf.scriptlandia.antrun.AntRun");

    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant-launcher/" + antVersion +
                               "/ant-launcher-" + antVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant/" + antVersion +
                               "/ant-" + antVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant-nodeps/" + antVersion +
                               "/ant-nodeps-" + antVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/org/apache/ant/ant-apache-bsf/" + antVersion +
                               "/ant-apache-bsf-" + antVersion + ".jar");

    launcher.addClasspathEntry(repositoryHome + "/bsh/bsh/" + beanshellVersion + "/bsh-" + beanshellVersion + ".jar");
    launcher.addClasspathEntry(repositoryHome + "/commons-logging/commons-logging/1.0.4/commons-logging-1.0.4.jar");
    launcher.addClasspathEntry(repositoryHome + "/bsf/bsf/2.4.0/bsf-2.4.0.jar");

    if(config) {
      launcher.addClasspathEntry(repositoryHome + "/org/sf/scriptlandia/antrun/" +
        scriptlandiaVersion + "/antrun-" + scriptlandiaVersion + ".jar");
    }
    else {
      launcher.addClasspathEntry("projects/antrun/target/antrun.jar");
    }
  }

  /**
   * Installs languages projects.
   *
   * @param args the command line arguments
   * @throws LauncherException the exception
   */
  protected void instalLanguageProjects(String[] args) throws LauncherException {
    SimpleLauncher launcher = new SimpleLauncher(getLanguageProjectsArgsList(args));

    prepare(launcher, isConfigMode());
  
//    launcher.addClasspathEntry(getScriptlandiaInstallerJarName());
    launcher.addClasspathEntry("projects/scriptlandia-installer/target/scriptlandia-installer.jar");

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  /**
   * Gets the required projects arguments list.
   *
   * @param args command line argumemts
   * @return the required  arguments list
   */
  private static String[] getLanguageProjectsArgsList(String[] args) {
    List<String> newArgsList = new ArrayList<String>();

    newArgsList.add("install.languages");

    newArgsList.addAll(Arrays.asList(args));

    String[] newArgs = new String[newArgsList.size()];

    newArgsList.toArray(newArgs);

    return newArgs;
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
