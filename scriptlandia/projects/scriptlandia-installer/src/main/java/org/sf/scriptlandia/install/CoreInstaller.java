package org.sf.scriptlandia.install;


import org.sf.jlaunchpad.core.LauncherException;
import org.sf.jlaunchpad.core.SimpleLauncher;
import org.sf.pomreader.ProjectInstaller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    launcher.addClasspathEntry("projects/antrun/target/antrun.jar");

    launcher.setMainClassName("org.sf.scriptlandia.antrun.AntRun");

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
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

    ExtInstaller extInstaller = new ExtInstaller();

    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    xmlHelper.readLanguages("languages");

    List languages = xmlHelper.getLanguages();

    for (Object language1 : languages) {
      Map language = (Map) language1;

      String name = (String) language.get("name");

      boolean requiresInstallation = Boolean.valueOf(System.getProperty(name + ".install")).booleanValue();

      if (requiresInstallation) {
        extInstaller.registerLanguage(language);
      }
    }

    System.out.println("Scriptlandia supported languages installed.");
  }

  private void installLanguages(String section, boolean install) throws Exception {
    ProjectInstaller installer = new ProjectInstaller(true);

    File[] files = new File("languages").listFiles();

    for (File file : files) {
      installLanguage(section, install, installer, file);
    }
  }

  public void installLanguage(String name, List languages) throws Exception {
    ProjectInstaller installer = new ProjectInstaller(true);

    String path = "languages" + File.separatorChar + name;

    installLanguage("core", true, installer, new File(path));
    installLanguage("starter", true, installer, new File(path));

    Map language = findLanguage(languages, name);
    
    ExtInstaller extInstaller = new ExtInstaller();
    extInstaller.registerLanguage(language);
  }

  private Map findLanguage(List languages, String name) {
    Map language = null;

    for(int i=0; i < languages.size() && language == null; i++) {
      Map currentLanguage = (Map)languages.get(i);
      String currentName = (String)currentLanguage.get("name");

       if(currentName.equalsIgnoreCase(name)) {
         language = currentLanguage;
       }
    }

    return language;
  }

 protected void uninstallLanguageProjects() {
   ExtInstaller extInstaller = new ExtInstaller();

   ExtXmlHelper xmlHelper = new ExtXmlHelper();
   xmlHelper.readLanguages("languages");

   List languages = xmlHelper.getLanguages();

   for (Object language1 : languages) {
     Map language = (Map) language1;

     String name = (String) language.get("name");

     boolean requiresInstallation = Boolean.valueOf(System.getProperty(name + ".install")).booleanValue();

     if (requiresInstallation) {
       try {
         extInstaller.unregisterLanguage(language);
       }
       catch (Exception e) {
         System.out.println("Exception: " + e.getMessage());
       }
     }
   }

   System.out.println("Scriptlandia supported languages uninstalled.");
  }

  public void uninstallLanguage(String name, List languages) {
    Map language = findLanguage(languages, name);

    ExtInstaller extInstaller = new ExtInstaller();

     try {
       extInstaller.unregisterLanguage(language);
     }
     catch (Exception e) {
       System.out.println("Exception: " + e.getMessage());
     }
  }

  private void installLanguage(String section, boolean install, ProjectInstaller installer, File file) throws Exception {
    if (!file.isHidden() && file.isDirectory()) {
      String name = file.getName();

      boolean requiresInstallation = false;

      if (Boolean.valueOf(System.getProperty(name + ".install")).booleanValue() || install) {
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

  protected List readLanguages() throws LauncherException {
    java.util.List languages;

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

}
