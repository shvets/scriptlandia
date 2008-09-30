package org.sf.scriptlandia;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

import org.apache.maven.cli.MavenCli;
import org.codehaus.classworlds.ClassWorld;

/**
 * This class is used for starting gradle file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 09/28/2008
 */
public final class GradleStarter {
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

    if(args.length > 0 && !args[0].equals("-b")) {
      String name = new File(args[0]).getName();

      newArgsList.add("-b");
      newArgsList.add(name);

      newArgsList.addAll(Arrays.asList(args).subList(1, args.length));
    }
    else {
      String name = new File(args[1]).getName();

      newArgsList.add("-b");
      newArgsList.add(name);

      newArgsList.addAll(Arrays.asList(args).subList(2, args.length));
    }


    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    try {
      org.gradle.Main.main(newArgs);
    }
    catch(Throwable t) {
      t.printStackTrace();
    }
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws Exception {
    new GradleStarter().start(args, classWorld);
  }

}
