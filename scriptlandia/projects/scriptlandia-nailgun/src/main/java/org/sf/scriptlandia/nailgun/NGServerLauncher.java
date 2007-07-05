package org.sf.scriptlandia.nailgun;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.sf.launcher.util.ReflectionUtil;

/**
 * This class is used for starting NG Server.
 *
 * @author Alexander Shvets
 * @version 1.0 05/30/2006
 */
public final class NGServerLauncher {
  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @throws Exception the exception
   */
  public void start(final String[] args) throws Exception {
    CommandLine commandLine = prepareCommandLine(args);

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Class mainClass = classLoader.loadClass("com.martiansoftware.nailgun.NGServer");

    ReflectionUtil.launchClass(mainClass, commandLine.getArgs(),
            "public static void main(String[] argv) main.");
  }

  private CommandLine prepareCommandLine(String[] args) throws ParseException {
    Option debugOption = new Option("debug", false, "The debug option.");

    Options options = new Options();
    options.addOption(debugOption);

    CommandLineParser parser = new GnuParser();

    return parser.parse(options, args);
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    new NGServerLauncher().start(args);
  }

}
