package org.sf.scriptlandia;

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Commandline;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.sf.jlaunchpad.util.CommonUtil;

import java.io.File;
import java.net.URL;


/**
 * This is the starter for Apt-Jelly scripts.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public final class AptFreemarkerStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    final String scriptName = args[0];

    String factoryClassName = "net.sf.jelly.apt.freemarker.FreemarkerProcessorFactory";

    Project project = new Project();
    project.init();

    BuildLogger logger = new DefaultLogger();

    logger.setMessageOutputLevel(Project.MSG_INFO);
    logger.setOutputPrintStream(System.out);
    logger.setErrorPrintStream(System.err);

    project.addBuildListener(logger);

    Java javaTask = new Java();
    javaTask.setProject(project);

    javaTask.setClassname("com.sun.tools.apt.Main");
    javaTask.setFork(true);
    javaTask.setTaskName("apt-freemarker");

    Commandline.Argument arg1 = javaTask.createArg();
    arg1.setLine("-factory " + factoryClassName);

    Commandline.Argument arg2 = javaTask.createArg();
    arg2.setLine("-Atemplate=" + scriptName);

    Commandline.Argument arg3 = javaTask.createArg();
    arg3.setValue("-nocompile");

    for (int i = 1; i < args.length; i++) {
      Commandline.Argument argI = javaTask.createArg();

      argI.setValue(args[i]);
    }

    Path classpath = new Path(project);

    URL[] constituents = mainRealm.getConstituents();

    for (URL url : constituents) {
      classpath.setLocation(new File(url.getFile().substring(1)));
    }

    File toolsJar = CommonUtil.getCompilerJar();

    if ( toolsJar.exists() ) {
      classpath.setLocation(toolsJar);
    }

    javaTask.setClasspath(classpath);

    javaTask.execute();
  }

  /**
   * The main method.
   *
   * @param args       the command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws Throwable {
    new AptFreemarkerStarter().start(args, classWorld.getRealm("pom-launcher-apt-fmt"));
  }

}
