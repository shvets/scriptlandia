package org.sf.scriptlandia;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

import org.apache.maven.cli.MavenCli;
import org.codehaus.classworlds.ClassWorld;

/**
 * This class is used for starting maven file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 05/14/2006
 */
public final class MavenStarter {
  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassWorld classWorld) throws Exception {
    String debug = System.getProperty("debug");

    if(debug != null && debug.trim().toLowerCase().equals("true")) {
      System.out.println("args: " + java.util.Arrays.asList(args));
    }

    final List<String> newArgsList = new ArrayList<String>();

   // newArgsList.add("-f");
    if(args.length > 0 && !args[0].equals("-f") && !args[0].equals("-file")) {
       String name = new File(args[0]).getName();

       if(name.endsWith(".mvn") || name.endsWith(".maven") || name.endsWith(".pom")) {
          newArgsList.add("-f");
       }
    }

    newArgsList.addAll(Arrays.asList(args));

    newArgsList.add(0, System.getProperty("jlaunchpad.home") + File.separatorChar + "settings.xml");
    newArgsList.add(0, "-s");

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    System.out.println("new args " + Arrays.asList(newArgs));
    MavenCli.main(newArgs, classWorld);
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws Exception {
    new MavenStarter().start(args, classWorld);
  }

}
