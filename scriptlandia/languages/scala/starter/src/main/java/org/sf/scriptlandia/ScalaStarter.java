package org.sf.scriptlandia;

import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.BuildLogger;

import java.io.File;

/**
 * This class is used for compiling scala source file and then
 * executing resulting class file.
 *
 * @author Alexander Shvets
 * @version 1.0 12/02/2006
 */
public final class ScalaStarter {
  private final static String COMPILER_CLASS = "scala.tools.nsc.Main";
  private final static String RUNNER_CLASS = "scala.tools.nsc.MainGenericRunner";

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @throws Exception the exception
   */
  public void start(final String[] args) throws Exception {
    String fullFileName = args[0];
    String fileName = new File(fullFileName).getName();

    File file = File.createTempFile("scala-tmp", "");
    file.delete();
    file.mkdir();

    Project project = createProject();

    Java javaTask1 = createJavaTask(project, COMPILER_CLASS);
    Path classpath1 = getClasspath(project);

    javaTask1.setClasspath(classpath1);

    javaTask1.createArg().setValue("-d");
    javaTask1.createArg().setValue(file.getPath());
    javaTask1.createArg().setValue(fullFileName);

    javaTask1.execute();

    Java javaTask2 = createJavaTask(project, RUNNER_CLASS);
    Path classpath2 = getClasspath(project);
    classpath2.setLocation(/*new File(".")*/file);
    
    javaTask2.setClasspath(classpath2);

    args[0] = fileName.substring(0, fileName.indexOf(".scala"));

    for (String arg : args) {
      javaTask2.createArg().setValue(arg);
    }

    javaTask2.execute();

    file.deleteOnExit();
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
    classpath.setLocation(new File(repositoryHome + "/scala/scala-actors/" + scalaVersion +
        "/scala-actors-" + scalaVersion + ".jar"));
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
    new ScalaStarter().start(args);
  }

}

