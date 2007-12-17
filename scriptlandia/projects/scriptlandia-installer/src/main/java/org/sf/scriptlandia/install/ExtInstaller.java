package org.sf.scriptlandia.install;

import org.jdesktop.jdic.filetypes.*;
import org.apache.tools.ant.*;
import org.sf.scriptlandia.xml.ExtXmlHelper;
import org.sf.jlaunchpad.core.SimpleLauncher;
import org.sf.jlaunchpad.core.LauncherException;
import org.jdom.JDOMException;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.io.File;

/**
 * This is the class for installing extensions.
 *
 * @author Alexander Shvets
 * @version 2.0 09/02/2006
 */
public class ExtInstaller {
  protected final String OS_NAME = System.getProperty("os.name");
  protected final String repositoryHome = System.getProperty("repository.home");
  protected String launcherHome = System.getProperty("launcher.home");
  protected final String scriptlandiaHome = System.getProperty("scriptlandia.home");

  public static final String LAUNCHER_SCRIPT_NAME = "scriptlandia";

  private AssociationService associationService = new AssociationService();

  /**
   * Registers the extensions for languages.
   *
   * @throws LauncherException launcher exception
   */
  public void registerLanguages() throws LauncherException {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    xmlHelper.readLanguages("languages");

    List languages = xmlHelper.getLanguages();

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);

      String name = (String)language.get("name");

      boolean requiresInstallation = Boolean.valueOf(System.getProperty(name + ".install")).booleanValue();

      if(requiresInstallation) {
        registerLanguage(language);
      }
    }

    //registerRubyGemExtensions(new String[] { "gem" });
  }

  /**
   * Install the extensions.
   *
   */
  public void unregisterAllLanguages() {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    xmlHelper.readLanguages("languages");

    List languages = xmlHelper.getLanguages();

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);

      unregisterLanguage(language);
    }
  }

  public void registerLanguage() throws JDOMException, IOException, LauncherException {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    Map language =  xmlHelper.readLanguage();

    registerLanguage(language);
  }

  public void unregisterLanguage() throws JDOMException, IOException, RegisterFailedException, AssociationNotRegisteredException {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    Map language = xmlHelper.readLanguage();

    List extensions = (List) language.get("extensions");

    for(int i=0; i < extensions.size(); i++) {
      unregisterExtension((String)extensions.get(i), (String)language.get("mimeType"), (String)language.get("name"));
    }
  }

  private void registerLanguage(Map language) throws LauncherException {
    List extensions = (List) language.get("extensions");

    Boolean[] registrations = new Boolean[extensions.size()];

    String openAction = getAction(language, getScriptExt(), repositoryHome, scriptlandiaHome) + 
                                   " " + getCommandLineExpression();

    System.out.print("Registering extension(s): " + extensions + "... ");

    for(int i=0; i < extensions.size(); i++) {
      registrations[i] = registerExtension((String)extensions.get(i), (String)language.get("name"), (String)language.get("mimeType"),
                                           (String)language.get("icon"), openAction);

      System.out.println(openAction);
    }

    executeAdditionalTasks(language);

    System.out.println("Registered: " + Arrays.asList(registrations) + ".");
  }

  private void executeAdditionalTasks(Map language) throws LauncherException {
    String name = (String)language.get("name");

    String antInstallName = "languages/" + name + "/install.ant";

    if(new File(antInstallName).exists()) {
      downloadLanguageArtifacts(language);

      File buildFile = new File("languages/" + name + "/install.ant");

      Project project = createAntProject();

      project.setUserProperty("ant.file", buildFile.getAbsolutePath());	

      ProjectHelper.getProjectHelper().parse(project, buildFile);

      project.executeTarget(project.getDefaultTarget());
    }
  }

  private void downloadLanguageArtifacts(Map language) throws LauncherException {
    String[] args = new String[] { "-basedir", "languages/" + language.get("name") + "/core", "-build.required", "true"};

    SimpleLauncher launcher = new SimpleLauncher(args);

    launcher.setMainClassName("org.sf.pomreader.ProjectInstaller");

/*    String repositoryHome = System.getProperty("repository.home");
    
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/jlaunchpad-common/1.0.1/jlaunchpad-common-1.0.1.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/apache/maven/bootstrap/bootstrap-mini/2.0.8/bootstrap-mini-2.0.8.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/pom-reader/1.0.1/pom-reader-1.0.1.jar");
    launcher.addClasspathEntry(repositoryHome + "/org/sf/jlaunchpad/jlaunchpad-launcher/1.0.1/jlaunchpad-launcher-1.0.1.jar");    

    launcher.addClasspathEntry("projects/scriptlandia-installer/target/scriptlandia-installer.jar");
*/
    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  private void unregisterLanguage(Map language) {
    List extensions = (List) language.get("extensions");

   // Boolean[] registrations = new Boolean[extensions.size()];

   // String openAction = getAction(language, getScriptExt(), repositoryHome, scriptlandiaHome) +
  //                                 " " + getCommandLineExpression();

    System.out.print("Unregistering extension(s): " + extensions + "... ");

    for(int i=0; i < extensions.size(); i++) {
      try {
        unregisterExtension((String)extensions.get(i), (String)language.get("mimeType"), (String)language.get("name"));
      }
      catch(Throwable t) {
        System.out.println(t.getMessage());

      }
    }

    System.out.println("Unregistered.");
  }

 /**
   * Creates Ant project.
   * @return Ant project
   */
  private Project createAntProject() {
    Project project = new Project();

    project.init();

    BuildLogger logger = new DefaultLogger();

    logger.setMessageOutputLevel(Project.MSG_INFO);
    logger.setOutputPrintStream(System.out);
    logger.setErrorPrintStream(System.err);

    project.addBuildListener(logger);

    return project;
  }

  /**
   * Registers extensions for artefact specified as (groupId; artefactId; version).
   *
   * @param extensions the list of extensions
   * @param iconName icon name
   * @param artifactId the artefact Id
   * @param mimeType mime type 
   *,@param iconName icon name
   * @param mainClassName the main class name
   * @param additionalParams additional parameters
   */
  private void registerExtensions(String[] extensions, String mimeType, String iconName, 
                                  String artifactId, String mainClassName,
                                  String additionalParams) {
    Boolean[] registrations = new Boolean[extensions.length];

    String groupId = "org.sf.scriptlandia";

    String version = /*project.getProperty("scriptlandia.version")*/"2.2.4";

    StringBuffer openAction = new StringBuffer();
    openAction.append(scriptlandiaHome.replace('/', File.separatorChar));
    openAction.append(File.separatorChar);
    openAction.append(LAUNCHER_SCRIPT_NAME + ".");
    openAction.append(getScriptExt());
    openAction.append(" \"");
    openAction.append("-deps.file.name=");
    openAction.append(repositoryHome.replace('/', File.separatorChar));
    openAction.append(File.separatorChar);
    openAction.append(groupId.replace('.', File.separatorChar));
    openAction.append(File.separatorChar);
    openAction.append(artifactId);
    openAction.append(File.separatorChar);
    openAction.append(version);
    openAction.append(File.separatorChar);
    openAction.append(artifactId);
    openAction.append("-");
    openAction.append(version);
    openAction.append(".pom");
    openAction.append("\"");

    openAction.append(" \"");
    openAction.append("-main.class.name=");
    openAction.append(mainClassName);
    openAction.append("\"");
    openAction.append(" ");

    if(additionalParams != null && additionalParams.trim().length() > 0) {
      openAction.append(additionalParams);
      if(additionalParams.equals("-f")) {
        openAction.append(" ");
      }
    }

    openAction.append(getCommandLineExpression());

    System.out.print("Registering extension(s): " + Arrays.asList(extensions) + "... ");

    for(int i=0; i < extensions.length; i++) {
      registrations[i] = registerExtension(extensions[i], "", mimeType, iconName, openAction.toString());

      System.out.println(openAction.toString());
    }

    System.out.println("Registered: " + Arrays.asList(registrations) + ".");
  }

  /**
   * Registers extensions for artefact specified as (groupId; artefactId; version).
   *
   * @param extensions the list of extensions
   * @param mimeType mime type 
   *,@param iconName icon name
   * @param iconName the 
   * @param artifactId the artefact Id
   * @param mainClassName the main class name
   */
  private void registerExtensions(String[] extensions, String mimeType, String iconName, 
                                  String artifactId, String mainClassName) {
    registerExtensions(extensions, "", mimeType, iconName, artifactId, mainClassName);
  }

  /**
   * Registers extensions for ruby gem.
   *
   * @param extensions the list of extensions
   */
  private void registerRubyGemExtensions(String[] extensions) {
    Boolean[] registrations = new Boolean[extensions.length];

    String openAction =
            scriptlandiaHome + "/" + "gem." +  getScriptExt() +
            " install" +
            " " + getCommandLineExpression();

    System.out.print("Registering extension(s): " + Arrays.asList(extensions) + "... ");

    for(int i=0; i < extensions.length; i++) {
      registrations[i] = registerExtension((String)extensions[i], "text/ruby", openAction, "", "");
    }

    System.out.println("Registered: " + Arrays.asList(registrations) + ".");
  }

  /**
   * Registers single extension.
   *
   * @param extension the extension to be registered
   * @param openAction the action, assosiated with this extension
   * @param mimeType mime type
   *,@param iconName icon name
   * @return true if the extension is registered; false otherwise
   */
  private boolean registerExtension(String extension, String name, String mimeType,
                                    String iconName, String openAction) {
    boolean registered = false;

    Association existingAssociation = associationService.getFileExtensionAssociation(extension);

    if(existingAssociation == null || existingAssociation.getMimeType() == null ||
       (!existingAssociation.getFileExtList().contains("." + extension) ||
        !existingAssociation.getMimeType().equals(mimeType) ||
        !existingAssociation.getActionByVerb("open").getCommand().equals(openAction))) {
      Association association = new Association();
      association.setName(name);
      // Adds specific type to the Association object.
      association.addFileExtension(extension);
      association.setMimeType(mimeType);
      //association.setDescription("tst file");
      association.setIconFileName((scriptlandiaHome + "/languages/" + name + "/" + iconName).replace('/', File.separatorChar));

      // Adds an Action to the Association object that will 
      // open file of this type with some executable. 
      association.addAction(new Action("open", openAction));

      try {
        // Adds the Association to the file types' table 
        // at the user level using an AssociationService object.
        if(OS_NAME.toLowerCase().startsWith("windows")) {
          associationService.registerUserAssociation(association);
        }
        else {
          associationService.registerUserAssociation(association);

         // Register an association in system level.
          // This requires root permission, would generate three files:
          // javaws.keys and javaws.mime under /usr/share/mime-info or /usr/share/gnome/mime-info and
          // javaws.applications under /usr/share/application-registry or /usr/share/gnome/application-registry.
          //assocService.registerSystemAssociation(assoc);

          // Register an association in user level.
          // This would generate three files: javaws.keys and javaws.mime under ~/.gnome/mime-info,
          // and javaws.applications under ~/.gnome/application-info.
          //assocService.registerUserAssociation(assoc);
        }

        registered = true;
      }
      catch (java.lang.IllegalArgumentException e) {
        // This exception will be caught if the given Association is not valid 
        // to be added to the table of file types.
        System.err.println(e);
        //e.printStackTrace();
      }
      catch (AssociationAlreadyRegisteredException e) {
        // This exception will be caught if the Association already
        // exists in the table of file types.

        //System.err.println(e);

        try {
          associationService.unregisterUserAssociation(association);
          associationService.registerUserAssociation(association);

          registered = true;
        }
        catch(Exception e2) {
          ;
        }
      }
      catch (RegisterFailedException e) {
        // This exception will be caught if the Association was
        // unable to be added to the table of file types.

        //System.err.println(e);
        try {
          associationService.unregisterUserAssociation(association);
          associationService.registerUserAssociation(association);

          registered = true;
        }
        catch(Exception e2) {
          System.err.println(e2);
        }
      }
    }
    else {
      System.out.println("Registration is not required.");
    }

    return registered;
  }

  /**
   * Unregisters the extension.
   * @param extension the extension or name
   * @throws AssociationNotRegisteredException the exception
   * @throws RegisterFailedException the exception
   */
  public void unregisterExtension(String extension, String mimeType, String name)
    throws AssociationNotRegisteredException, RegisterFailedException {
    if(OS_NAME.toLowerCase().startsWith("windows")) {
//      Association assosiation = associationService.getFileExtensionAssociation(extension);
//      System.out.println(assosiation);

      Association assosiation = new Association();

      assosiation.addFileExtension(extension);
      assosiation.setMimeType(mimeType);

      associationService.unregisterUserAssociation(assosiation);
    }
    else {
      Association assosiation = new Association();

      assosiation.setName(name);

      // Unregister an association in system level.
      // This requires root permission, would remove all the three files registered/generated:
      // javaws.keys, javaws.mime and javaws.applications at system level.
      associationService.unregisterSystemAssociation(assosiation);

      // Unregister an association in user level.
      // This would remove all the three files registered/generated:
      // javaws.keys, javaws.mime and javaws.applications at user level.
      //associationService.unregisterUserAssociation(assoc);
    }
  }

  /**
   * Calculates the script extension.
   *
   * @return the script extension
   */
  private String getScriptExt() {
    String scriptExt;

    if(OS_NAME.toLowerCase().startsWith("windows")) {
      scriptExt = "bat";
      //scriptExt = "exe";
    }
    else {
      scriptExt = "sh";
    }

    return scriptExt;
  }

  /**
   * Calculates the command line expression.
   *
   * @return the command line expression
   */
  private String getCommandLineExpression() {
    String scriptExt;

    if(OS_NAME.toLowerCase().startsWith("windows")) {
      scriptExt = "%1 %*";
    }
    else {
      //scriptExt = "$<";
      scriptExt = "";
    }

    return scriptExt;
  }

  protected String getScriptName(String path, String name, String ext) {
    StringBuffer scriptName = new StringBuffer();

    scriptName.append(path);
    scriptName.append("/");
    scriptName.append(name);
    scriptName.append(".");
    scriptName.append(ext);

    return scriptName.toString();
  }

  protected String getDepsProperty(Map language, String repositoryHome, char separatorChar) {
    String groupId = (String) language.get("starter.groupId");
    String artifactId = (String) language.get("starter.artifactId");
    String version = (String) language.get("starter.version");

    StringBuffer depsProperty = new StringBuffer();

    depsProperty.append("\"");
    depsProperty.append("-deps.file.name=");
    depsProperty.append(repositoryHome.replace('/', separatorChar).replace('\\', separatorChar));
    depsProperty.append(separatorChar);
    depsProperty.append(groupId.replace('.', separatorChar));
    depsProperty.append(separatorChar);
    depsProperty.append(artifactId);
    depsProperty.append(separatorChar);
    depsProperty.append(version);
    depsProperty.append(separatorChar);
    depsProperty.append(artifactId);
    depsProperty.append("-");
    depsProperty.append(version);
    depsProperty.append(".pom");
    depsProperty.append("\"");

    return depsProperty.toString();
  }

  protected String getMainClassProperty(Map language) {
    String mainClass = (String) language.get("mainClass");

    StringBuffer mainClassProperty = new StringBuffer();

    mainClassProperty.append("\"");
    mainClassProperty.append("-main.class.name=");
    mainClassProperty.append(mainClass);
    mainClassProperty.append("\"");
    mainClassProperty.append(" ");

    return mainClassProperty.toString();
  }

  protected String getCommandLine(Map language) {
    String commandLine = (String) language.get("commandLine");

    if(commandLine == null) {
      commandLine = "";
    }

    return commandLine.trim();
  }

  protected String getAction(Map language, String extension, String repositoryHome, String scriptlandiaHome) {
    String scriptName = getScriptName(scriptlandiaHome, LAUNCHER_SCRIPT_NAME, extension);
    String depsProperty = getDepsProperty(language, repositoryHome, File.separatorChar);
    String mainClassProperty = getMainClassProperty(language);
    String commandLine = getCommandLine(language);

    StringBuffer action = new StringBuffer();

    action.append(scriptName.replace('/', '\\'));
    action.append(" ");
    action.append(depsProperty);
    action.append(" ");

    action.append(mainClassProperty);
    action.append(" ");
    action.append(commandLine);

    if(commandLine.length() > 0) {
      action.append(" ");
    }

    return action.toString();
  }


  public static void main(String[] args) throws Exception {
    ExtInstaller installer = new ExtInstaller();

    installer.registerLanguages();
  }

}