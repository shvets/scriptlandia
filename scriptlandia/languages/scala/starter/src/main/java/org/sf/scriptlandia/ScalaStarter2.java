package org.sf.scriptlandia;

import org.codehaus.classworlds.ClassRealm;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.jlaunchpad.util.FileUtil;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.taskdefs.Java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.StringTokenizer;
import java.net.URL;

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
   * @param mainRealm  main realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    Project project = createProject();

    Java forker = createJavaTask(project, RUNNER_CLASS);
    Path classpath = getClasspath(project, mainRealm);

    forker.setClasspath(classpath);
    forker.setDescription("forker");

    for (int i=0; i < args.length; i++) {
      forker.createArg().setValue(args[i]);
    }

    forker.execute();
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

  private Path getClasspath(Project project, ClassRealm mainRealm) {
    Path classpath = new Path(project);

    URL[] constituents = mainRealm.getConstituents();

    for (URL constituent : constituents) {
      classpath.setLocation(new File(constituent.getFile()));
    }

    StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path"), File.pathSeparator);

    while(st.hasMoreTokens()) {
        classpath.setLocation(new File(st.nextToken()));
    }

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

    ClassRealm mainRealm = null;

    if(launcher != null) {
      mainRealm = launcher.getMainRealm();
    }

    new ScalaStarter2().start(args, mainRealm);
  }

}