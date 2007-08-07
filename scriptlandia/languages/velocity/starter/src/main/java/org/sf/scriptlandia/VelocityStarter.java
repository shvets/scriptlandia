package org.sf.scriptlandia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import bsh.Interpreter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * This is the starter for Velocity scripts.
 *
 * @author Alexander Shvets
 * @version 2.0 02/19/2006
 */
public final class VelocityStarter {

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

    System.setProperty("user.dir", templateDir);
    System.setProperty("file.resource.loader.path", templateDir);
    System.setProperty("runtime.log", templateDir + "/velocity.log");

    if (args.length < 2) {
      contextName = /*templateDir + */"context.bsh";
    } else {
      contextName = args[1];
    }

    Velocity.init();

    Interpreter interpreter = new Interpreter();

    VelocityContext context = (VelocityContext) interpreter.source(contextName);

    Template template = Velocity.getTemplate(templateName);

    if (template != null) {
      BufferedWriter writer = null;

      try {
        if (args.length == 3) {
          writer = new BufferedWriter(new FileWriter(args[2]));
        } else {
          writer = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        template.merge(context, writer);
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
    new VelocityStarter().start(args);
  }

}
