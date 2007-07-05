package org.sf.scriptlandia;

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.sf.launcher.util.FileUtil;
import org.sf.launcher.util.CommonUtil;

import java.io.File;
import java.net.URL;
import java.util.Iterator;


/**
 * This is the starter for Apt-Jelly scripts.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public final class AptStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    final String scriptName = args[0];

    String factoryClassName = "";

    if(FileUtil.getExtension(scriptName).equals("apt-fmt")) {
      factoryClassName = "net.sf.jelly.apt.freemarker.FreemarkerProcessorFactory";
    }
    else if(FileUtil.getExtension(scriptName).equals("apt-jelly")) {
      factoryClassName = "net.sf.jelly.apt.APTJellyProcessorFactory";
    }

    StringBuffer sb = new StringBuffer();

    sb.append("-factory");
    sb.append(" ");
    sb.append(factoryClassName);
    sb.append(" ");

    if(FileUtil.getExtension(scriptName).equals("apt-jelly")) {
      sb.append("-AjellyScript=");
      sb.append(scriptName);
    }
    else {
      sb.append("-Atemplate=");
      sb.append(scriptName);
    }

    sb.append(" ");
    sb.append("-nocompile");
    sb.append(" ");

    for (int i = 1; i < args.length; i++) {
      sb.append(args[i]);

      if(i < args.length-1) {
        sb.append(" ");
      }
    }

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
    javaTask.setArgs(sb.toString());

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
    Iterator iterator = classWorld.getRealms().iterator();

    if(iterator.hasNext()) {
      ClassRealm mainRealm = ((ClassRealm)iterator.next());

      new AptStarter().start(args, mainRealm);
    }
  }

}
