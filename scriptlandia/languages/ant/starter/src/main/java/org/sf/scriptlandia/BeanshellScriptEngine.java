package org.sf.scriptlandia;

import org.apache.bsf.util.BSFEngineImpl;
import org.apache.bsf.BSFException;

import javax.script.ScriptEngineManager;
import javax.script.Invocable;
import javax.script.ScriptException;
import javax.script.ScriptEngine;
import java.io.FileReader;
import java.io.FileNotFoundException;

/**
 * This class is used for representing Java FX Scripting Engine as BSF scripting Engine.
 *
 * @author Alexander Shvets
 * @version 1.0 05/19/2007
 */
public class BeanshellScriptEngine extends BSFScriptEngineBridge {

  public BeanshellScriptEngine() {
    super("beanshell");
  }

}