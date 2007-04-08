package org.sf.scriptlandia;

import gnu.mapping.*;
import gnu.text.Path;

import gnu.expr.Language;
import kawa.standard.Scheme;
import kawa.MessageArea;
import kawa.Shell;

import java.io.PrintWriter;

public class KawaStarter {
  Language language;
  Environment environment;
  Future thread;

  gnu.text.QueueReader in_r;
  OutPort out_p, err_p;

  //MessageArea message = null;

  public KawaStarter(Language language, Environment penvironment, boolean shared) {
    this.language = language;

    in_r = new gnu.text.QueueReader();
    //message = new MessageArea(in_r);
    kawa.repl.exitIncrement();

    out_p = new OutPort(/*message.getStdout()*/new PrintWriter(System.out), true, Path.valueOf("<msg_stdout>"));
    err_p = new OutPort(/*message.getStderr()*/new PrintWriter(System.err), true, Path.valueOf("<msg_stderr>"));
    InPort in_p = new InPort(in_r, Path.valueOf("<msg_stdin>")/*,out_p, message*/);

    thread = new Future(new kawa.repl(language),
            penvironment, in_p, out_p, err_p);
    Environment env = thread.getEnvironment();
    if (shared)
      env.setIndirectDefines();

    this.environment = env;
    thread.start();
  }

  public static void main(String[] args) {
    Language language = Scheme.getInstance();

    //Environment environment = Environment.getCurrent();

    //new KawaStarter(language, environment, false);
    //Shell.run(language, Environment.getCurrent());

	  Language.setDefaults(language);    
    /*boolean ok = */Shell.runFile(args[0], 0);
  }

}
