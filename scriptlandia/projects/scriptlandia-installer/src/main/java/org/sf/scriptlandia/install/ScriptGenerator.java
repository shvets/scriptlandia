package org.sf.scriptlandia.install;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Thisclass generates scripts for windows and unix systems.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2007
 */
public class ScriptGenerator extends ExtInstaller {

  public void generate() throws IOException {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    xmlHelper.readLanguages("languages");
    List languages = xmlHelper.getLanguages();

    generateSlBatchFile(languages, scriptlandiaHome);
    generateSlShellFile(languages, scriptlandiaHome);

    for (Object language : languages) {
      generateLanguageBatchScript((Map)language, scriptlandiaHome);
      generateLanguageShellScript((Map)language, scriptlandiaHome);
    }
  }

  public void generateSlBatchFile(List languages, String dir) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "/sl.bat"));

    writer.write("@echo off");
    writer.newLine();
    writer.newLine();

    writer.write("rem sl.bat");
    writer.newLine();
    writer.newLine();

    writer.write("SET REPOSITORY_HOME=" + repositoryHome.replace('/', '\\'));
    writer.newLine();

    writer.write("SET LAUNCHER_HOME=" + launcherHome.replace('/', '\\'));
    writer.newLine();

    writer.write("SET SCRIPTLANDIA_HOME=" + scriptlandiaHome.replace('/', '\\'));
    writer.newLine();
    writer.newLine();

    String scriptName = getScriptName("%SCRIPTLANDIA_HOME%", "scriptlandia", "bat");

    writer.write("SET SCRIPT_NAME=" + scriptName.replace('/', '\\'));
    writer.newLine();

    writer.write("SET NAME=%~n1");
    writer.newLine();

    writer.write("SET EXT=%~x1");
    writer.newLine();

    for (Object language : languages) {
      String name = (String) ((Map)language).get("name");

      List extensions = (List) ((Map)language).get("extensions");

      for (Object extension : extensions) {
        writer.newLine();
        writer.write("if \"%EXT%\" == \"." + extension + "\" goto " + name + "Setup");
        writer.newLine();
      }
    }

    writer.newLine();
    writer.write("if \"%NAME%%EXT%\" == \"build.xml\" goto antSetup");
    writer.newLine();

    writer.newLine();
    writer.write("if \"%NAME%%EXT%\" == \"pom.xml\" goto mavenSetup");
    writer.newLine();
    writer.newLine();

    writer.write("echo \"Unsupported extension: %EXT%\" ");
    writer.newLine();
    writer.write("goto end");
    writer.newLine();

    for (Object language : languages) {
      String name = (String) ((Map)language).get("name");

      String depsProperty = getDepsProperty(((Map)language), "%REPOSITORY_HOME%", '\\');
      String mainClassProperty = getMainClassProperty(((Map)language));
      String commandLine = getCommandLine(((Map)language));

      writer.newLine();
      writer.write(":" + name + "Setup");
      writer.newLine();
      writer.write("SET APP_NAME=" + name);
      writer.newLine();
      writer.write("SET DEPS_PROPERTY=" + depsProperty);
      writer.newLine();
      writer.write("SET MAIN_CLASS_PROPERTY=" + mainClassProperty);
      writer.newLine();
      writer.write("SET CMD_LINE=");

      //if(name.equals("ant") || name.equals("maven")) {
      //  writer.write("-f ");
      //}

      writer.write(commandLine);
      writer.newLine();
      writer.write("goto execute ");
      writer.newLine();
    }

    writer.newLine();
    writer.write(":execute");
    writer.newLine();
    writer.write("%SCRIPT_NAME% %DEPS_PROPERTY% %MAIN_CLASS_PROPERTY% %CMD_LINE% %*");
    writer.newLine();
    writer.newLine();

    writer.write(":end");
    writer.newLine();

    writer.close();
  }

  public void generateSlShellFile(List languages, String dir) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "/sl.sh"));

    writer.write("#!/bin/sh");
    writer.newLine();
    writer.newLine();

    writer.write("REPOSITORY_HOME=" + repositoryHome.replace('\\', '/'));
    writer.newLine();

    writer.write("LAUNCHER_HOME=" + launcherHome.replace('\\', '/'));
    writer.newLine();

    writer.write("SCRIPTLANDIA_HOME=" + scriptlandiaHome.replace('\\', '/'));
    writer.newLine();
    writer.newLine();

    String scriptName = getScriptName("$SCRIPTLANDIA_HOME", "scriptlandia", "sh");

    writer.write("SCRIPT_NAME=" + scriptName.replace('\\', '/'));
    writer.newLine();

    writer.write("EXT=`echo $1 | awk -F. '{print $NF}'`");
    writer.newLine();
    writer.newLine();
    writer.write("case \"$EXT\" in");
    writer.newLine();
    for (Object language : languages) {
      String name = (String) ((Map)language).get("name");

      //String action = getAction(language, "sh", "$REPOSITORY_HOME", "$SCRIPTLANDIA_HOME");

      String depsProperty = getDepsProperty((Map)language, "$REPOSITORY_HOME", '/');
      String mainClassProperty = getMainClassProperty((Map)language);
      String commandLine = getCommandLine((Map)language);

      List extensions = (List) ((Map)language).get("extensions");

      for (Object extension : extensions) {
        writer.write("  '" + extension + "')");
        writer.newLine();
        writer.write("    APP_NAME=" + name);
        writer.newLine();
        writer.write("    DEPS_PROPERTY=" + depsProperty);
        writer.newLine();
        writer.write("    MAIN_CLASS_PROPERTY=" + mainClassProperty);
        writer.newLine();
        writer.write("    CMD_LINE=");

        //if(name.equals("ant") || name.equals("maven")) {
        //  writer.write("-f ");
        //}

        writer.write(commandLine);

        writer.newLine();
        writer.write("    ;;");
        writer.newLine();
      }
    }

    writer.write("  *)");
    writer.newLine();

    writer.write("    ;;");
    writer.newLine();

    writer.write("esac");
    writer.newLine();
    writer.newLine();

    writer.write("$SCRIPT_NAME $DEPS_PROPERTY $MAIN_CLASS_PROPERTY $CMD_LINE $*");
    writer.newLine();

    writer.close();
  }

  private void generateLanguageBatchScript(Map language, String dir) throws IOException {
    String name = (String) language.get("name");
    String scriptName = (String) language.get("scriptName");

    BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "/" + scriptName + ".bat"));

    writer.write("@echo off");
    writer.newLine();
    writer.newLine();

    writer.write("rem " + scriptName + ".bat");
    writer.newLine();
    writer.newLine();

    writer.write("SET REPOSITORY_HOME=" + repositoryHome.replace('/', '\\'));
    writer.newLine();

    writer.write("SET SCRIPTLANDIA_HOME=" + scriptlandiaHome.replace('/', '\\'));
    writer.newLine();

    String fullScriptName = getScriptName("%SCRIPTLANDIA_HOME%", "scriptlandia", "bat");

    writer.write("SET SCRIPT_NAME=" + fullScriptName.replace('/', '\\'));
    writer.newLine();
    writer.newLine();

    String depsProperty = getDepsProperty(language, "%REPOSITORY_HOME%", '\\');
    String mainClassProperty = getMainClassProperty(language);
    String commandLine = getCommandLine(language);

    writer.write("SET APP_NAME=" + name);
    writer.newLine();
    writer.write("SET DEPS_PROPERTY=" + depsProperty);
    writer.newLine();
    writer.write("SET MAIN_CLASS_PROPERTY=" + mainClassProperty);
    writer.newLine();
    writer.write("SET CMD_LINE=" + commandLine);
    writer.newLine(); writer.newLine();

    writer.write("%SCRIPT_NAME% %DEPS_PROPERTY% %MAIN_CLASS_PROPERTY% %CMD_LINE% %*");
    writer.newLine();

    writer.close();
  }

  private void generateLanguageShellScript(Map language, String dir) throws IOException {
    String name = (String) language.get("name");
    String scriptName = (String) language.get("scriptName");

    BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "/" + scriptName + ".sh"));

    writer.write("#!/bin/sh");
    writer.newLine();
    writer.newLine();

    writer.write("REPOSITORY_HOME=" + repositoryHome.replace('\\', '/'));
    writer.newLine();

    writer.write("SCRIPTLANDIA_HOME=" + scriptlandiaHome.replace('\\', '/'));
    writer.newLine();

    String fullScriptName = getScriptName("$SCRIPTLANDIA_HOME", "scriptlandia", "sh");

    writer.write("SCRIPT_NAME=" + fullScriptName.replace('\\', '/'));
    writer.newLine();
    writer.newLine();

    String depsProperty = getDepsProperty(language, "$REPOSITORY_HOME", '/');
    String mainClassProperty = getMainClassProperty(language);
    String commandLine = getCommandLine(language);

    writer.write("APP_NAME=" + name);
    writer.newLine();
    writer.write("DEPS_PROPERTY=" + depsProperty);
    writer.newLine();
    writer.write("MAIN_CLASS_PROPERTY=" + mainClassProperty);
    writer.newLine();
    writer.write("CMD_LINE=" + commandLine);
    writer.newLine();
    writer.newLine();

    writer.write("$SCRIPT_NAME $DEPS_PROPERTY $MAIN_CLASS_PROPERTY $CMD_LINE $*");
    writer.newLine();

    writer.close();
  }

}
