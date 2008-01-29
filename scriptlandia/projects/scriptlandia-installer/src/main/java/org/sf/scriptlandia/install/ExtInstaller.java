package org.sf.scriptlandia.install;

//import org.jdesktop.jdic.filetypes.*;

import com.sun.deploy.association.*;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.jdom.JDOMException;
import org.sf.jlaunchpad.core.LauncherException;
import org.sf.jlaunchpad.core.SimpleLauncher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

  private AssociationService associationService = new AssociationService();

  /**
   * Install the extensions.
   */
  public void unregisterAllLanguages() {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    xmlHelper.readLanguages("languages");

    List languages = xmlHelper.getLanguages();

    for (Object language : languages) {
      try {
        unregisterLanguage((Map) language);
      }
      catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
      }
    }
  }

  public void registerLanguage() throws JDOMException, IOException, LauncherException {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    Map language = xmlHelper.readLanguage();

    registerLanguage(language);
  }

  public void registerLanguage(Map language) throws LauncherException {
    List extensions = (List) language.get("extensions");

    Boolean[] registrations = new Boolean[extensions.size()];

    String openAction = getAction(getScriptExt(), scriptlandiaHome) + " " + getCommandLineExpression();

    System.out.print("Registering extension(s): " + extensions + "... ");

    for (int i = 0; i < extensions.size(); i++) {
      try {
        registrations[i] = registerExtension((String) extensions.get(i), (String) language.get("name"), (String) language.get("mimeType"),
            (String) language.get("icon"), openAction);
        System.out.println(openAction);
      }
      catch (Throwable t) {
        System.out.println("Exception: " + t.getMessage());
      }
    }

    executeAdditionalTasks(language);

    System.out.println("Registered: " + Arrays.asList(registrations) + ".");
  }

  private void executeAdditionalTasks(Map language) throws LauncherException {
    String name = (String) language.get("name");

    String antInstallName = "languages/" + name + "/install.ant";

    if (new File(antInstallName).exists()) {
      downloadLanguageArtifacts(language);

      File buildFile = new File("languages/" + name + "/install.ant");

      Project project = createAntProject();

      project.setUserProperty("ant.file", buildFile.getAbsolutePath());

      ProjectHelper.getProjectHelper().parse(project, buildFile);

      project.executeTarget(project.getDefaultTarget());
    }
  }

  private void downloadLanguageArtifacts(Map language) throws LauncherException {
    String[] args = new String[]{"-basedir", "languages/" + language.get("name") + "/core", "-build.required", "true"};

    SimpleLauncher launcher = new SimpleLauncher(args);

    launcher.setMainClassName("org.sf.pomreader.ProjectInstaller");

    launcher.configure(Thread.currentThread().getContextClassLoader());
    launcher.launch();
  }

  public void unregisterLanguage(Map language) {
    List extensions = (List) language.get("extensions");

    Boolean[] registrations = new Boolean[extensions.size()];

    System.out.print("Unregistering extension(s): " + extensions + "... ");

    for (int i = 0; i < extensions.size(); i++) {
      try {
        registrations[i] = unregisterExtension((String) extensions.get(i), (String) language.get("mimeType"), (String) language.get("name"));
      }
      catch (Throwable t) {
        t.printStackTrace();
        System.out.println(t.getMessage());
      }
    }

    System.out.println("Unregistered: " + Arrays.asList(registrations) + ".");
  }

  /**
   * Creates Ant project.
   *
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
   * Registers single extension.
   *
   * @param extension  the extension to be registered
   * @param iconName   icon name
   * @param openAction the action, assosiated with this extension
   * @param name       name
   * @param mimeType   mime type
   *                   ,@param iconName icon name
   * @return true if the extension is registered; false otherwise
   * @throws AssociationAlreadyRegisteredException
   *                                 exception
   * @throws RegisterFailedException exception
   */
  private boolean registerExtension(String extension, String name, String mimeType,
                                    String iconName, String openAction) throws AssociationAlreadyRegisteredException, RegisterFailedException {
    boolean registered = false;

    Association association = null;

    try {

      if (OS_NAME.toLowerCase().startsWith("windows")) {
        Association existingAssociation = associationService.getFileExtensionAssociation(extension);

        if (existingAssociation == null || existingAssociation.getMimeType() == null ||
            (!existingAssociation.getFileExtList().contains("." + extension) ||
                !existingAssociation.getMimeType().equals(mimeType) ||
                (existingAssociation.getActionByVerb("open") != null && !existingAssociation.getActionByVerb("open").getCommand().equals(openAction)))) {

          association = new Association();

          association.setName(name);
          // Adds specific type to the Association object.
          association.addFileExtension(extension);
          association.setMimeType(mimeType);
          //association.setDescription("tst file");
          association.setIconFileName((scriptlandiaHome + "/images/" + /*name.replace('/', File.separatorChar) + "/" + */ iconName));

          // Adds an Action to the Association object that will
          // open file of this type with some executable.
          association.addAction(new Action("open", openAction));

          // Register an association in system level.
          //associationService.registerSystemAssociation(association);
          associationService.registerUserAssociation(association);
        } else {
          System.out.println("Registration is not required.");
        }
      } else {

//        association = new Association();
//
//        association.setName(name.replace('\\', File.separatorChar));
//        association.addFileExtension(extension.toLowerCase());
//        association.addFileExtension(extension.toUpperCase());
//        association.setMimeType(mimeType);
//        //association.setDescription(mimeType);
//        association.setIconFileName(scriptlandiaHome + "/images/" + /* name.replace('/', File.separatorChar) + "/" + */ iconName);
//
//        // Be careful!!! Here neither "%1" not "%f" is required, and only "open" verb
//        // is used for this API.
//
//        association.addAction(new Action("open", openAction.replace('\\', File.separatorChar)));
//
//        // Register an association in system level.
//        // This requires root permission, would generate three files:
//        // javaws.keys and javaws.mime under /usr/share/mime-info or /usr/share/gnome/mime-info and
//        // javaws.applications under /usr/share/application-registry or /usr/share/gnome/application-registry.
//        //assocService.registerSystemAssociation(assoc);
//
//        // Register an association in user level.
//        // This would generate three files: javaws.keys and javaws.mime under ~/.gnome/mime-info,
//        // and javaws.applications under ~/.gnome/application-info.
//        associationService.registerUserAssociation(association);
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
        if (association != null) {
          associationService.unregisterUserAssociation(association);
          associationService.registerUserAssociation(association);

          registered = true;
        }
      }
      catch (Exception e2) {
        ;
      }
    }
    catch (RegisterFailedException e) {
      // This exception will be caught if the Association was
      // unable to be added to the table of file types.

      //System.err.println(e);
      try {
        if (association != null) {
          associationService.unregisterUserAssociation(association);
          associationService.registerUserAssociation(association);

          registered = true;
        }
      }
      catch (Exception e2) {
        System.err.println(e2);
      }
    }


    return registered;
  }

  public boolean unregisterExtension(String extension, String mimeType, String name) {
    boolean registered = false;

    if (OS_NAME.toLowerCase().startsWith("windows")) {
      Association association = new Association();

      association.addFileExtension(extension);

      try {
        associationService.unregisterUserAssociation(association);

        registered = true;
      }
      catch (AssociationNotRegisteredException e) {
        ;
      }
      catch (RegisterFailedException e) {
        ;
      }
    } else {
      Association assosiation = new Association();

      assosiation.setName(name);

      // Unregister an association in system level.
      // This requires root permission, would remove all the three files registered/generated:
      // javaws.keys, javaws.mime and javaws.applications at system level.
      try {
        associationService.unregisterSystemAssociation(assosiation);

        registered = true;
      }
      catch (AssociationNotRegisteredException e) {
        ;
      }
      catch (RegisterFailedException e) {
        ;
      }

      // Unregister an association in user level.
      // This would remove all the three files registered/generated:
      // javaws.keys, javaws.mime and javaws.applications at user level.
      //associationService.unregisterUserAssociation(assoc);
    }

    return registered;
  }

  /**
   * Calculates the script extension.
   *
   * @return the script extension
   */
  private String getScriptExt() {
    String scriptExt;

    if (OS_NAME.toLowerCase().startsWith("windows")) {
      scriptExt = "bat";
      //scriptExt = "exe";
    } else {
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

    if (OS_NAME.toLowerCase().startsWith("windows")) {
      scriptExt = "%1 %*";
    } else {
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

    if (commandLine == null) {
      commandLine = "";
    }

    return commandLine.trim();
  }

  protected String getAction(String extension, String scriptlandiaHome) {
    String fullScriptName = getScriptName(scriptlandiaHome, "sl", extension);

    StringBuffer action = new StringBuffer();

    action.append(fullScriptName.replace('/', '\\'));

    return action.toString();
  }

}
