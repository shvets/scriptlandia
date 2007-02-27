package org.sf.scriptlandia;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.sf.scriptlandia.util.ReflectionUtil;
import org.codehaus.classworlds.ClassWorld;
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
    jadProperties.load(new FileInputStream(jadName));

    String midlet1 = (String)jadProperties.get("MIDlet-1");

    if (midlet1 != null) {
      int index = midlet1.lastIndexOf(',');

      String midletClassName = midlet1.substring(index+1).trim();

      final String jarName = (String)jadProperties.get("MIDlet-Jar-URL");

      mainRealm.addConstituent(new File(jarName).toURI().toURL());

      Map<String, Object> properties = new HashMap<String, Object>();
      properties.put("MIDlet-Jar-URL", jarName);
      properties.put("MicroEdition-Configuration", jadProperties.get("MicroEdition-Configuration"));
      properties.put("MicroEdition-Profile", jadProperties.get("MicroEdition-Profile"));

      final List<String> newArgsList = new ArrayList<String>();

      newArgsList.add("-classpath");

      newArgsList.add(JarStarter.prepareMobileClasspath(properties));

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
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   * @param classWorld class world
   */
  public static void main(String[] args, ClassWorld classWorld) throws Exception {
    Iterator iterator = classWorld.getRealms().iterator();

    if(iterator.hasNext()) {
      ClassRealm mainRealm = ((ClassRealm)iterator.next());

      new JadStarter().start(args, mainRealm);
    }
  }

}

