package org.sf.scriptlandia.bsf;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.bsf.util.BSFEngineImpl;
import org.sf.scriptlandia.util.FileUtil;
import scala.tools.nsc.ScriptRunner;
import scala.tools.nsc.GenericRunnerCommand;
import scala.runtime.BoxedArray;
import scala.runtime.BoxedAnyArray;
import scala.Function1;

import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.rmi.RemoteException;
import java.io.*;

/**
 * Engine for running Scala in the
 * <a href="http://jakarta.apache.org/bsf/">Bean Scripting Framework</a>.
 * <p/>
 * <p>It does not implement the compile methods because i couldn't
 * figure out what a CodeBuffer was for.
 */

public class ScalaEngine extends BSFEngineImpl {

  public Object call(Object object, String name, Object[] args) throws BSFException {
    System.out.println("In call");

    BoxedArray argsarray = new BoxedAnyArray(args.length);
    argsarray.copyFrom(args, 0, 0, args.length);

    BoxedArray argsArray = new BoxedAnyArray(args.length);
    argsArray.copyFrom(args, 0, 0, args.length);

    ErrorHandlerFunction errors = new ErrorHandlerFunction();

    GenericRunnerCommand command = new GenericRunnerCommand(argsArray.toList(), errors);

    ScriptRunner.runScript(command.settings(), name, command.arguments());

    return errors.getMessages();
  }

  public Object eval(String string, int i, int i1, Object object) throws BSFException {
    System.out.println("In eval");
    return null;
  }

  public void exec(String name, int lineNo, int columnNo, Object script)
    throws BSFException {
    System.out.println("In exec: " + name);
    System.out.println("In exec: " + script.getClass().getName());

    final File file;
    try {
      byte[] bytes = ((String)script).getBytes();
      file = FileUtil.copyToTempFile(new ByteArrayInputStream(bytes), "scala-", ".scala");

      //file.deleteOnExit();

      BoxedArray argsArray = new BoxedAnyArray(0);

      ErrorHandlerFunction errors = new ErrorHandlerFunction();

      GenericRunnerCommand command = new GenericRunnerCommand(argsArray.toList(), errors);

    System.out.println("file.getAbsolutePath(): " + file.getCanonicalPath());

      ScriptRunner.runScript(command.settings(), file.getCanonicalPath(), command.arguments());

      System.out.println("errors: " + errors.getMessages());
    }
    catch (IOException e) {
      ;
    }
  }

  public void initialize(BSFManager mgr, String lang, Vector declaredBeans) throws BSFException {
    super.initialize(mgr, lang, declaredBeans);
  }

  private class ErrorHandlerFunction implements Function1 {
    private List messages = new ArrayList();

    public Object apply(Object arg0) {
      messages.add(arg0.toString());
      
      return null;
    }

    public Function1 andThen(Function1 function1) {
      return null;
    }

    public Function1 compose(Function1 arg0) {
      return null;
    }

    public int $tag() throws RemoteException {
      return 0;
    }

    public List getMessages() {
      return messages;
    }
  }

  /**
   * Apply a procedure to arguments. *
   */
/*
  public Object apply(String source, int lineNo, int columnNo, Object funcBody,
                      Vector paramNames, Vector arguments)
    throws BSFException {
*/
/*    try {
      return js.apply((SchemeProcedure) funcBody,
        (arguments.toArray(new Object[arguments.size()])));
    } catch (Throwable t) {
      return error(t);
    }
*/
/*
    return new Object();
  }
*/

//  /**
//   * Assume object is null for now. *
//   */
//  public Object call(Object object, String name, Object[] args) throws BSFException {
//    //return (((SchemeProcedure) js.getGlobalValue(name)).apply(args));
//    return new Object();
//  }
//
//  /**
//   * A DeclaredBean can be referred to as a free variable. *
//   */
//  public void declareBean(BSFDeclaredBean bean) throws BSFException {
//    //js.setGlobalValue(bean.name, bean.bean);
//  }
//
//  public Object eval(String source, int lineNo, int columnNo, Object expr)
//    throws BSFException {
//    //return js.eval(expr);
//    return new Object();
//  }
//
///*  private Object error(Throwable e) throws BSFException {
//    if (e == null) return null;
//    else throw new BSFException(100, "Yow!", e);
//  }
//*/
  
//  public void exec(String source, int lineNo, int columnNo, Object script)
//    throws BSFException {
///*    try {
//      if (script instanceof String) {
//        Enumeration es = REPL.readStream(new StringReader((String) script));
//        while (es.hasMoreElements()) js.eval(es.nextElement());
//      } else      // What should this do?
//        js.call((SchemeProcedure) script);
//    } catch (Throwable e) {
//      error(e);
//    }
//    */
//  }
//
//
//  public void undeclareBean(BSFDeclaredBean bean) throws BSFException {
//  //  js.setGlobalValue(bean.name, jsint.U.UNDEFINED);
//  }
}
