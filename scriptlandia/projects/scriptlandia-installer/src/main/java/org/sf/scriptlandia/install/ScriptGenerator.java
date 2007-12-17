package org.sf.scriptlandia.install;

import org.sf.scriptlandia.xml.ExtXmlHelper;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
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

    generateBatchFile(languages, scriptlandiaHome);
    generateShellFile(languages, scriptlandiaHome);
  }

  public void generateBatchFile(List languages, String dir) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "/sl.bat"));

    writer.write("@echo off"); writer.newLine();
    writer.newLine();

    writer.write("rem sl.bat"); writer.newLine();
    writer.newLine();

    writer.write("SET REPOSITORY_HOME=" + repositoryHome.replace('/', '\\'));
    writer.newLine();

    writer.write("SET LAUNCHER_HOME=" + launcherHome.replace('/', '\\'));
    writer.newLine();

    writer.write("SET SCRIPTLANDIA_HOME=" + scriptlandiaHome.replace('/', '\\'));
    writer.newLine();
    writer.newLine();

    String scriptName = getScriptName("%SCRIPTLANDIA_HOME%", LAUNCHER_SCRIPT_NAME, "bat");

    writer.write("SET SCRIPT_NAME=" + scriptName.replace('/', '\\'));
    writer.newLine();

    writer.write("SET NAME=%~n1");
    writer.newLine();

    writer.write("SET EXT=%~x1");
    writer.newLine();

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);

      String name = (String)language.get("name");

      List extensions = (List) language.get("extensions");

      for(int j=0; j < extensions.size(); j++) {
        String extension = (String)extensions.get(j);

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

    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);

      String name = (String)language.get("name");

      String depsProperty = getDepsProperty(language, "%REPOSITORY_HOME%", '\\');
      String mainClassProperty = getMainClassProperty(language);
      String commandLine = getCommandLine(language);

      writer.newLine();
      writer.write(":" + name + "Setup");
      writer.newLine();
      writer.write("SET DEPS_PROPERTY=" + depsProperty);
      writer.newLine();
      writer.write("SET MAIN_CLASS_PROPERTY=" + mainClassProperty);
      writer.newLine();
      writer.write("SET CMD_LINE=" + commandLine);
      writer.newLine();
      writer.write("goto execute ");
      writer.newLine();
    }

    writer.newLine();
    writer.write(":execute"); writer.newLine();
    writer.write("%SCRIPT_NAME% %DEPS_PROPERTY% %MAIN_CLASS_PROPERTY% %CMD_LINE% %*");
    writer.newLine();
    writer.newLine();

    writer.write(":end");
    writer.newLine();

    writer.close();
  }

  public void generateShellFile(List languages, String dir) throws IOException {
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

    String scriptName = getScriptName("$SCRIPTLANDIA_HOME", LAUNCHER_SCRIPT_NAME, "sh");

    writer.write("SCRIPT_NAME=" + scriptName.replace('\\', '/'));
    writer.newLine();

    writer.write("EXT=`echo $1 | awk -F. '{print $NF}'`");
    writer.newLine();
    writer.newLine();
    writer.write("case \"$EXT\" in");
    writer.newLine();
    for(int i=0; i < languages.size(); i++) {
      Map language = (Map)languages.get(i);

      String name = (String)language.get("name");

      String action = getAction(language, "sh", "$REPOSITORY_HOME", "$SCRIPTLANDIA_HOME");

      String depsProperty = getDepsProperty(language, "$REPOSITORY_HOME", '/');
      String mainClassProperty = getMainClassProperty(language);
      String commandLine = getCommandLine(language);

      List extensions = (List) language.get("extensions");

      for(int j=0; j < extensions.size(); j++) {
        String extension = (String)extensions.get(j);

        writer.write("  '" + extension + "')");
        writer.newLine();
        writer.write("    DEPS_PROPERTY=" + depsProperty);
        writer.newLine();
        writer.write("    MAIN_CLASS_PROPERTY=" + mainClassProperty);
        writer.newLine();
        writer.write("    CMD_LINE=" + commandLine);
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

}
