package org.sf.scriptlandia;

import java.util.List;
import java.util.ArrayList;

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

    newArgsList.add("-f");

    for (int i = 0; i < args.length; i++) {
      newArgsList.add(args[i]);
    }

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

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
