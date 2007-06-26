package org.sf.scriptlandia;

import fr.jayasoft.ivy.Ivy;
import fr.jayasoft.ivy.report.ResolveReport;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * This is the class for holding convenient methods when working with Ivy.
 *
 * @author Alexander Shvets
 * @version 2.0 06/30/2007
 */
public class IvyHelper {

  private static final String RETRIEVE_PATTERN = "[organisation]/[module]/[revision]/[artifact]-[revision].[ext]";


  /**
   * Disables object creation.
   */
  private IvyHelper() {}

  /**
   * Resolves and downloads (if required) all dependencies specified inside ivy file.
   *
   * @throws IOException I/O Exception
   * @throws ParseException Parse Exception
   */
  public static void resolve() throws IOException, ParseException {
    String configName = System.getProperty("ivy.conf.file");
    String depsName = System.getProperty("ivy.dep.file");

    if(configName == null) {
      System.out.println("System property: \"ivy.conf.file\" is not specified.");
    }
    else if(depsName == null) {
      System.out.println("System property: \"ivy.dep.file\" is not specified.");
    }
    else {
      resolve(configName, depsName);
    }
  }

  /**
   * Resolves and downloads (if required) all dependencies specified inside ivy file.
   *
   * @param configName config file name
   * @param depsName dependencies file name
   * @throws IOException I/O Exception
   * @throws ParseException Parse Exception
   */
  public static void resolve(String configName, String depsName) throws IOException, ParseException {
    String repositoryHome = System.getProperty("repository.home");
    Ivy ivy = new Ivy();
    ivy.configure(new File(configName));
    ivy.setCacheIvyPattern(repositoryHome.replace('\\', '/') + "/" + RETRIEVE_PATTERN);

    ResolveReport r = ivy.resolve(new File(depsName));

    System.out.println("Ivy has errors: " + r.hasError());
  }

}
