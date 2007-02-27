package org.sf.scriptlandia;

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

    MavenCli.main(args, classWorld);
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
