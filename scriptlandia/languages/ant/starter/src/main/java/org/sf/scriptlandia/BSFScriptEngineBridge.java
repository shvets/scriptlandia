package org.sf.scriptlandia;

import org.apache.bsf.util.BSFEngineImpl;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.Invocable;
import java.io.FileReader;
import java.io.FileNotFoundException;


/**
 * This class is used for bridging between Java Scripting Engine and BSF framework.
 *
 * @author Alexander Shvets
 * @version 1.0 05/19/2007
 */
public class BSFScriptEngineBridge extends BSFEngineImpl {
  private ScriptEngineManager manager = new ScriptEngineManager();

  private String language;

  public BSFScriptEngineBridge(String language) {
    this.language = language;
  }

  public Object call(Object object, String string, Object[] objects)
         throws BSFException {
    System.out.println("call");
    System.out.println("object " + object);
    System.out.println("string " + string);
    System.out.println("objects " + objects);

    if(object instanceof Invocable) {
      System.out.println("Invocable ");

      Invocable invocable = (Invocable)object;
      try {
        return invocable.invokeFunction(string, objects);
      }
      catch (ScriptException e) {
      throw new BSFException(e.getMessage());
      }
      catch (NoSuchMethodException e) {
      throw new BSFException(e.getMessage());
      }
    }

    return null;
  }

  public Object eval(String string, int i, int i1, Object object)
         throws BSFException {
    System.out.println("eval");
    System.out.println("string " + string);
//    System.out.println("i" + i);
    System.out.println("language " + language);
    System.out.println("object " + object);

    ScriptEngine engine = manager.getEngineByName(language);

    System.out.println("engine " + engine);

    try {
      return engine.eval(string);
    }
    catch (ScriptException e) {
      throw new BSFException(e.getMessage());
    }
  }

}
