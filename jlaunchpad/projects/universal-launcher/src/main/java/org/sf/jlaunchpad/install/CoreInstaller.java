package org.sf.jlaunchpad.install;


import org.sf.jlaunchpad.core.LauncherException;
import org.sf.jlaunchpad.util.StringUtil;
import org.sf.pomreader.ProjectInstaller;

import java.io.*;

/**
 * The class perform initial (command line) installation of scriprlandia.
 *
 * @author Alexander Shvets
 * @version 1.0 07/14/2007
 */
public class CoreInstaller {

  /**
   * Creates installer.
   *
   * @throws LauncherException the exception
   */
  public CoreInstaller() throws LauncherException {}

  /**
   * Performs installation of initial components/projects.
   *
   * @param args the command line arguments
   * @throws LauncherException the exception
   */
  public void install(String[] args) throws LauncherException {
    System.out.println("Installing JLaunchPad...");

    try {
      ProjectInstaller installer = new ProjectInstaller();

      installer.install("projects/bootstrap-mini", false);
      installer.install("projects/universal-launcher-common", false);
      installer.install("projects/pom-reader", false);      
      installer.install("projects/universal-launcher", false);
      installer.install("projects/classworlds", false);

      File[] files = new File("projects/universal-launcher/src/main/config").listFiles();

      String launcherHome = System.getProperty("launcher.home");
      File launcherHomeFile = new File(launcherHome);

      if(!launcherHomeFile.exists()) {
        launcherHomeFile.mkdirs();
      }

      for(File fromFile : files) {
        if(fromFile.exists() && !fromFile.isHidden() && !fromFile.isDirectory()) {
          File toFile = new File(launcherHomeFile.toString(), fromFile.getName());

          BufferedReader reader = null;
          BufferedWriter writer = null;

          try {
            reader = new BufferedReader(new FileReader(fromFile));
            writer = new BufferedWriter (new FileWriter(toFile));

            boolean done = false;

            while(!done) {
              String line = reader.readLine();

              if(line == null) {
                done = true;
              }
              else {
                writer.write(StringUtil.substituteProperties(line, "@", "@"));
                writer.newLine();
              }
            }
          }
          finally {
            if(reader != null) {
              reader.close();
            }

            if(writer != null) {
              writer.close();
            }
          }
        }
      }
    }
    catch (Exception e) {
      throw new LauncherException(e);
    }

    System.out.println("Installed JLaunchPad.");
  }

  /**
   * Launches the installer from the command line.
   *
   * @param args The application command-line arguments.
   * @throws LauncherException exception
   */
  public static void main(String[] args) throws LauncherException {
    CoreInstaller installer = new CoreInstaller();

    installer.install(args);
  }

}
