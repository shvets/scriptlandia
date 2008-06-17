package org.sf.scriptlandia;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.jlaunchpad.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

/**
 * This class is used for starting dependency file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 09/17/2006
 */
public final class ScriptlandiaStarter {
  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    JLaunchPadLauncher launcher = ScriptlandiaLauncher.getInstance();

    String scriptName = args[0];

    launcher.resolveDependencies(scriptName);

    List<String> mainClassNames = launcher.findMainClassNames(scriptName);

    String fullClassName = null;

    if(mainClassNames.size() == 0) {
      System.out.println("Cannot find Main Class Name.");
    }
    else if(mainClassNames.size() == 1) {
      fullClassName = mainClassNames.get(0);
    }
    else {
      System.out.println("More than one option for Main Class Name: " + mainClassNames + ".");
    }

    if(fullClassName != null) {
      Class mainClass = mainRealm.loadClass(fullClassName);

      List<String> newArgsList = new ArrayList<String>();

      newArgsList.addAll(Arrays.asList(args).subList(1, args.length));

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

    //if(iterator.hasNext()) {
      //ClassRealm mainRealm = ((ClassRealm)iterator.next());

      //new ScriptlandiaStarter().start(args, mainRealm);
    //}

    ClassRealm mainRealm = null;

    try {
      mainRealm = classWorld.getRealm("pom-launcher-scriptlandia");
    }
    catch(Exception e) {
      mainRealm = classWorld.getRealm("pom-launcher-sland");
    }

    new ScriptlandiaStarter().start(args, mainRealm);
  }

}

