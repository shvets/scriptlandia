package org.sf.scriptlandia;

import freemarker.template.Template;
import freemarker.template.Configuration;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Map;

import bsh.Interpreter;

/**
 * This is the starter for FreeMarker scripts.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public final class FreeMarkerStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @throws Exception the exception
   */
  public void start(final String[] args) throws Exception {
    String fullTemplateName = args[0];
    String contextName;

    String templateName = new File(fullTemplateName).getName();
    String templateDir = new File(new File(fullTemplateName).getAbsolutePath()).getParent();

    if (args.length < 2) {
      contextName = /*templateDir + */"context.bsh";
    } else {
      contextName = args[1];
    }


    Interpreter interpreter = new Interpreter();

    Map context = (Map) interpreter.source(contextName);

    Configuration configuration;

    // Initialize the FreeMarker configuration;
    // - Create a configuration instance
     configuration = new Configuration();
    // - Templates are stoted in the WEB-INF/templates directory of the Web app.

    configuration.setDirectoryForTemplateLoading(new File(templateDir));

    Template template = configuration.getTemplate(templateName);

    if (template != null) {
      BufferedWriter writer = null;

      try {
        if (args.length == 3) {
          writer = new BufferedWriter(new FileWriter(args[2]));
        } else {
          writer = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        template.process(context, writer);
      }
      finally {
        if(writer != null) {
          writer.close();
        }
      }
    }
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Throwable {
    new FreeMarkerStarter().start(args);
  }

}
