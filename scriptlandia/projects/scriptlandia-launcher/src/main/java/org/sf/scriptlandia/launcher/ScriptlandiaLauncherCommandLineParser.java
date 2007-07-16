package org.sf.scriptlandia.launcher;

import org.sf.jlaunchpad.core.LauncherCommandLineParser;

import java.util.*;

/**
 * The command line parser for scriptlandia launcher.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public class ScriptlandiaLauncherCommandLineParser extends LauncherCommandLineParser {

  /**
   * Parses the command line.
   *
   * @param args the array of arguments
   * @return the modified command line
   */
  public String[] parse(String[] args) {
    args = super.parse(args);

    List<String> newArgsList = new ArrayList<String>();

    for (int i=0; i < args.length; i++) {
      String arg = args[i];

      if (arg.startsWith("-")) {
        if (arg.toLowerCase().startsWith("-script.name")) {
//          int index = arg.indexOf("=");
//          commandLine.put("script.name", arg.substring(index+1));
//          newArgsList.add(arg.substring(index+1));

          newArgsList.add(args[i+1]);
          ++i;

        }
        else if (arg.toLowerCase().startsWith("-launcher")) {
          commandLine.put("is.launcher.mode", "");
        }
        else if (arg.toLowerCase().startsWith("-ngserver")) {
          commandLine.put("is.nailgun.server.mode", "");
        }
        else if (arg.toLowerCase().startsWith("-ngtray")) {
          commandLine.put("is.nailgun.tray.mode", "");
        }
        else if (arg.toLowerCase().startsWith("-ng")) {
          commandLine.put("is.nailgun.client.mode", "");
        }
        else if (arg.toLowerCase().startsWith("-config")) {
          commandLine.put("is.config.mode", "");
        }
        else if (arg.equalsIgnoreCase("-f") && i < args.length - 1) {
          commandLine.put("script.name", args[i+1]);
          newArgsList.add("-f");
          newArgsList.add(args[i+1]);
          ++i;
        }
        else if (arg.toLowerCase().startsWith("-expr")) {
          int index = arg.indexOf("=");
          String expr = arg.substring(index+1);

          int index2= expr.indexOf(":");

          commandLine.put("language.name", expr.substring(0, index2));
          commandLine.put("language.expr", expr.substring(index2+1));
        }
        else {
          newArgsList.add(arg);
        }
      }
      else {
        newArgsList.add(arg);
      }
    }

    String scriptName = getStarterScriptName();

    if(scriptName == null || scriptName.trim().length() == 0) {
      if(newArgsList.size() > 0) {
        commandLine.put("script.name", newArgsList.get(0));
      }
    }

    String[] newArgs = new String[newArgsList.size()];

    newArgsList.toArray(newArgs);

    return newArgs;
  }

  /**
   * Gets the starter script name.
   *
   * @return starter script name
   */
  public String getStarterScriptName() {
    return commandLine.get("script.name");
  }

  /**
   * Gets the launcher class name.
   *
   * @return launcher class name
   */
  public String getLauncherClassName() {
    String launcherClassName;

    if(isConfigMode()) {
      launcherClassName = "org.sf.scriptlandia.install.GuiInstaller";
    }
    else if(isNailgunServerMode()) {
      launcherClassName = "org.sf.scriptlandia.nailgun.NGServerLauncher";
    }
    else if(isNailgunTrayMode()) {
      launcherClassName = "org.sf.scriptlandia.nailgun.NailgunTray";
    }
    else {
      launcherClassName = "org.sf.scriptlandia.launcher.ScriptlandiaLauncher";
    }

    return launcherClassName;
  }

  /**
   * Checks if "nailgun server" mode has been requested.
   *
   * @return true if "nailgun server" mode has been requested; false otherwise
   */
  public boolean isNailgunServerMode() {
    return commandLine.get("is.nailgun.server.mode") != null;
  }

  /**
   * Checks if "nailgun tray" mode has been requested.
   *
   * @return true if "nailgun tray" mode has been requested; false otherwise
   */
  public boolean isNailgunTrayMode() {
    return commandLine.get("is.nailgun.tray.mode") != null;
  }

  /**
   * Checks for nailgun client mode.
   *
   * @return true if nailgun client mode; false otherwise
   */
  public boolean isNailgunClientMode() {
    String ngClientMode = commandLine.get("nailgun.client.mode");

    return ngClientMode != null && ngClientMode.equalsIgnoreCase("true");
  }

  /**
   * Checks if "config" mode has been requested.
   *
   * @return true if "tray" mode has been requested; false otherwise
   */
  public boolean isConfigMode() {
    return commandLine.get("is.config.mode") != null;
  }

  /**
   * Checks if "config" mode has been requested.
   *
   * @return true if "tray" mode has been requested; false otherwise
   */
  public boolean isLauncherMode() {
    return commandLine.get("is.launcher.mode") != null ||
           (!isConfigMode() && !isNailgunServerMode() && !isNailgunTrayMode());
  }

}
