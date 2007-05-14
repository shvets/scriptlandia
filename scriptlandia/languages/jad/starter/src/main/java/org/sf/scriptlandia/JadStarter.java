package org.sf.scriptlandia;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.sf.scriptlandia.util.ReflectionUtil;
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

      launchMobileJarFile(jarName, midletClassName, args, jadProperties, mainRealm);
    }
  }

  /**
   * Launches mobile jar file.
   *
   * @param jarName the jar name
   * @param midletClassName the midlet class name
   * @param args the list of arguments
   * @param attributes main attributes
   * @param mainRealm main class realm
   * @throws Exception the exception  
   */
  public static void launchMobileJarFile(String jarName, String midletClassName, String[] args,
                                  Map attributes, ClassRealm mainRealm) throws Exception {
    String javaMobileHome = System.getProperty("mobile.java.home");

    if(!new File(javaMobileHome).exists()) {
      launchMobileJarFileWithMicroEmu(midletClassName, args, mainRealm);
    }
    else {
      launchMobileJarFileWithMicroJava(jarName, midletClassName, args, attributes, mainRealm);
    }
  }

  /**
    * Launches mobile jar file.
    *
    * @param midletClassName the midlet class name
    * @param args the list of arguments
    * @param mainRealm main class realm
    * @throws Exception the exception
    */
   private static void launchMobileJarFileWithMicroEmu(String midletClassName, String[] args, ClassRealm mainRealm)
           throws Exception {
     final List<String> newArgsList = new ArrayList<String>();

     newArgsList.add(midletClassName);

     for (int i = 1; i < args.length - 1; i++) {
       newArgsList.add(args[i]);
     }

     String[] newArgs = new String[newArgsList.size()];
     newArgsList.toArray(newArgs);

     Class mainClass = mainRealm.loadClass("org.microemu.app.Main");

     ReflectionUtil.launchClass(mainClass, newArgs,
             "public static void main(String[] argv) main Method is missed.");
   }

  private static void launchMobileJarFileWithMicroJava(String jarName, String midletClassName, String[] args,
                                                Map attributes, ClassRealm mainRealm) throws Exception {
    final List<String> newArgsList = new ArrayList<String>();
    newArgsList.add("-classpath");

    Map<String, String> properties = new HashMap<String, String>();
    properties.put("MIDlet-Jar-URL", jarName);
    properties.put("MicroEdition-Configuration", (String)attributes.get("MicroEdition-Configuration"));
    properties.put("MicroEdition-Profile", (String)attributes.get("MicroEdition-Profile"));

    newArgsList.add(prepareMobileClasspath(properties));

    newArgsList.add(midletClassName);

    for (int i = 1; i < args.length - 1; i++) {
      newArgsList.add(args[i]);
    }

    newArgsList.add("0");

    Class mainClass = mainRealm.loadClass("com.sun.kvem.environment.EmulatorWrapper");

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    ReflectionUtil.launchClass(mainClass, newArgs,
            "public static void main(String[] argv) main Method is missed.");
  }

  /**
   * Prepares mobile classpath.
   *    
   * @param properties the properties
   * @return mobile classpath
   */
  public static String prepareMobileClasspath(Map properties) {
    StringBuffer sb = new StringBuffer();

    String javaMobileHome = System.getProperty("mobile.java.home");

    String configurationVersion = (String)properties.get("MicroEdition-Configuration");

    if(configurationVersion != null) {
      if(configurationVersion.equalsIgnoreCase("CLDC-1.0")) {
        sb.append(javaMobileHome).append("/lib/cldcapi10.jar");
      }
      else if(configurationVersion.equalsIgnoreCase("CLDC-1.1")) {
        sb.append(javaMobileHome).append("/lib/cldcapi11.jar");
      }
      else {
        sb.append(javaMobileHome).append("/lib/cldcapi11.jar");
      }

      sb.append(File.pathSeparatorChar);
    }

    String profileVersion = (String)properties.get("MicroEdition-Profile");

    if(profileVersion != null) {
      if(profileVersion.equalsIgnoreCase("MIDP-1.0")) {
        sb.append(javaMobileHome).append("/lib/midpapi10.jar");
      }
      else if(profileVersion.equalsIgnoreCase("MIDP-2.0")) {
        sb.append(javaMobileHome).append("/lib/midpapi20.jar");
      }
      else {
        sb.append(javaMobileHome).append("/lib/midpapi20.jar");
      }

      sb.append(File.pathSeparatorChar);
    }

    sb.append(javaMobileHome).append("/lib/wma20.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/mmapi.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/j2me-ws.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/jsr75.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/jsr082.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/jsr177.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/jsr179.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/jsr184.jar");
    sb.append(File.pathSeparatorChar);

    sb.append(javaMobileHome).append("/lib/jsr211.jar");
    sb.append(File.pathSeparatorChar);

    String jarName = (String)properties.get("MIDlet-Jar-URL");

    if(jarName != null) {
      sb.append(jarName);
    }

    return sb.toString();
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

