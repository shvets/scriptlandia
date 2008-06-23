package org.sf.scriptlandia;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.sf.jlaunchpad.util.ReflectionUtil;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is used for starting Fan script as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 06/21/2008
 */
public final class FanStarter {
  private final static String MAIN_CLASS_NAME = "fanx.tools.Fan";

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    final String fileName = args[0];

    int index1 = fileName.lastIndexOf(File.separatorChar);
    int index2 = fileName.lastIndexOf(".");

    final List<String> newArgsList = new ArrayList<String>();
    String[] newArgs;

    String ext = fileName.substring(index2+1);

    if(ext.equalsIgnoreCase("pod")) {
      String podName = fileName.substring(index1+1, index2);

      newArgsList.add(podName);

      for (int i = 1; i < args.length; i++) {
        newArgsList.add(args[i]);
      }

      newArgs = new String[newArgsList.size()];
      newArgsList.toArray(newArgs);
    }
    else {
      newArgs = args;
    }

    Class mainClass = mainRealm.loadClass(MAIN_CLASS_NAME);

    ReflectionUtil.launchClass(mainClass, newArgs,
            "public static void main(String[] argv) main Method is missed.");
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws Exception {
/*    Iterator iterator = classWorld.getRealms().iterator();

    if(iterator.hasNext()) {
      ClassRealm mainRealm = ((ClassRealm)iterator.next());

      new FanStarter().start(args, mainRealm);
    }
*/

    JLaunchPadLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = launcher.getMainRealm();

    new FanStarter().start(args, mainRealm);
  }

}
