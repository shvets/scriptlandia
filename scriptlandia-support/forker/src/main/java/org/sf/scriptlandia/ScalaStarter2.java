package org.sf.scriptlandia;

import org.codehaus.classworlds.ClassRealm;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;

import java.io.File;

/**
 * This class is used for executing script in Scala.
 *
 * @author Alexander Shvets
 * @version 1.0 12/02/2006
 */
public final class ScalaStarter2 {
  private final static String RUNNER_CLASS = "scala.tools.nsc.MainGenericRunner";

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    //String fullFileName = args[0];

    Forker forker = new Forker(args);

    forker.setClassname(RUNNER_CLASS);

    String repositoryHome = System.getProperty("repository.home");
    String scalaVersion = System.getProperty("scala.version");

    forker.addClasspathLocation(new File(repositoryHome + "/org/scala-lang/scala-library/" + scalaVersion +
        "/scala-library-" + scalaVersion + ".jar"));
    forker.addClasspathLocation(new File(repositoryHome + "/org/scala-lang/scala-compiler/" + scalaVersion +
        "/scala-compiler-" + scalaVersion + ".jar"));
    forker.addClasspathLocation(new File(repositoryHome + "/org/scala-lang/scala-dbc/" + scalaVersion +
        "/scala-dbc-" + scalaVersion + ".jar"));
    forker.addClasspathLocation(new File(repositoryHome + "/org/scala-lang/scala-decoder/" + scalaVersion +
        "/scala-decoder-" + scalaVersion + ".jar"));

 /*   for (String arg : args) {
      forker.createArg().setValue(arg);
    }
   */

    forker.execute();
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    JLaunchPadLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = null;

    if(launcher != null) {
      mainRealm = launcher.getMainRealm();

      System.out.println("constituents: " + java.util.Arrays.asList(mainRealm.getConstituents()));
    }


    new ScalaStarter2().start(args, mainRealm);
  }

}

