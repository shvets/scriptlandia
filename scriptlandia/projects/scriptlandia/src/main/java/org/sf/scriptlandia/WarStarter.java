package org.sf.scriptlandia;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.sf.scriptlandia.util.ReflectionUtil;
import org.codehaus.classworlds.ClassWorld;
import org.codehaus.classworlds.ClassRealm;
import org.apache.maven.cli.MavenCli;

/**
 * This class is used for starting War file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 05/30/2006
 */
public final class WarStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassWorld classWorld) throws Exception {
//    final String warName = args[0];
    System.out.println("args " + java.util.Arrays.asList(args));

    List<String> newArgsList = new ArrayList<String>();

    for (int i = 1; i < args.length; i++) {
      newArgsList.add(args[i]);
    }

    newArgsList.add("-f");
    newArgsList.add("c:/scriptlandia/demo.webapp");
    newArgsList.add("jetty:run");

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    System.setProperty("application.server.port", "8080");

    MavenCli.main(newArgs, classWorld);
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

    new WarStarter().start(args, classWorld);
  }

}

