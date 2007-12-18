package org.sf.scriptlandia;


import org.codehaus.classworlds.ClassRealm;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;

import java.io.File;

/**
 * This class is used for executing script in Scala.
 *
 * @author Alexander Shvets
 * @version 1.0 12/02/2006
 */
public final class ScalaStarter2 extends Java {
  private final static String RUNNER_CLASS = "scala.tools.nsc.MainGenericRunner";

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    String fullFileName = args[0];

    Project project = createProject();

    Java javaTask = createJavaTask(project, RUNNER_CLASS);
    Path classpath = getClasspath(project);
    classpath.setLocation(new File(fullFileName));
    
    javaTask.setClasspath(classpath);

    for (String arg : args) {
      javaTask.createArg().setValue(arg);
    }

    javaTask.execute();
  }

  private Java createJavaTask(Project project, String className) {
    Java javaTask = (Java)project.createTask("java");
    javaTask.setProject(project);

    javaTask.setFork(true);
    javaTask.setClassname(className);

    return javaTask;
  }

  private Project createProject() {
    Project project = new Project();
    project.init();

    BuildLogger logger = new DefaultLogger();

    logger.setMessageOutputLevel(Project.MSG_INFO);
    logger.setOutputPrintStream(System.out);
    logger.setErrorPrintStream(System.err);

    project.addBuildListener(logger);

    return project;
  }


  private Path getClasspath(Project project) {
    String repositoryHome = System.getProperty("repository.home");
    String scalaVersion = System.getProperty("scala.version");

    Path classpath = new Path(project);

    classpath.setLocation(new File(repositoryHome + "/scala/scala-library/" + scalaVersion +
       "/scala-library-" + scalaVersion + ".jar"));
   classpath.setLocation(new File(repositoryHome + "/scala/scala-compiler/" + scalaVersion +
        "/scala-compiler-" + scalaVersion + ".jar"));
    classpath.setLocation(new File(repositoryHome + "/scala/scala-dbc/" + scalaVersion +
        "/scala-dbc-" + scalaVersion + ".jar"));
    classpath.setLocation(new File(repositoryHome + "/scala/scala-decoder/" + scalaVersion +
        "/scala-decoder-" + scalaVersion + ".jar"));

    return classpath;
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    JLaunchPadLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = launcher.getMainRealm();

    new ScalaStarter2().start(args, mainRealm);
  }

}

