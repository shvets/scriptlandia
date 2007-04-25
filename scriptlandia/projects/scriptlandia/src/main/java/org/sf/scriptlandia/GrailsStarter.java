package org.sf.scriptlandia;

import org.sf.scriptlandia.util.FileUtil;
import org.sf.scriptlandia.util.ReflectionUtil;
import org.sf.scriptlandia.launcher.LauncherException;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.codehaus.classworlds.ClassRealm;

/**
 * This class is used for running F3 scripts.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public final class GrailsStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    Class mainClass = mainRealm.loadClass("org.codehaus.groovy.grails.cli.GrailsScriptRunner");

//    String[] newArgs = new String[newArgsList.size()];
//    newArgsList.toArray(newArgs);

    ReflectionUtil.launchClass(mainClass, args,
            "public static void main(String[] argv) main Method is missed.");
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

    new GrailsStarter().start(args, mainRealm);
  }

}
