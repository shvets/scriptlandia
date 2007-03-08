package org.sf.scriptlandia;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.codehaus.classworlds.ClassRealm;

/**
 * This class is used for starting Jad file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 05/30/2006
 */
public final class JadStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    final String jadName = args[0];

    Properties jadProperties = new Properties();

    FileInputStream is = null;
    try {
      is = new FileInputStream(jadName);
      jadProperties.load(is);
    }
    finally {
      if(is != null) {
        is.close();
      }
    }

    String midlet1 = (String)jadProperties.get("MIDlet-1");

    if (midlet1 != null) {
      int index = midlet1.lastIndexOf(',');

      String midletClassName = midlet1.substring(index+1).trim();

      final String jarName = (String)jadProperties.get("MIDlet-Jar-URL");

      mainRealm.addConstituent(new File(jarName).toURI().toURL());

      JarStarter.launchMobileJarFile(jarName, midletClassName, args, jadProperties, mainRealm);
    }
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    ScriptlandiaLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = launcher.getMainRealm();

    new JadStarter().start(args, mainRealm);
  }

}

