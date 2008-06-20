package net.java.javafx.jsr223;

import java.io.*;
import java.util.*;
import javax.script.*;
import net.java.javafx.type.*;
import net.java.javafx.type.expr.*;
import net.java.javafx.typeImpl.*;

public class JavaFXScriptEngine extends AbstractScriptEngine {

  private ScriptEngineFactory factory;
  private Module module;
  private Compilation compilation;

  public JavaFXScriptEngine() {
    factory = null;
    module = (new TypeFactoryImpl()).createModule();
    compilation = new Compilation(module);
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    if(contextClassLoader != null) {
      module.setClassLoader(contextClassLoader);
    }
  }

  public Bindings createBindings() {
    return new SimpleBindings();
  }

  public Object eval(String script, ScriptContext context) throws ScriptException {
    return eval(((Reader) (new StringReader(script))), context);
  }

  public Object eval(Reader reader, ScriptContext context) throws ScriptException {
    Object scriptName = context.getAttribute("javax.script.filename", 100);
    if(scriptName == null) {
      scriptName = "JavaFXScript";
    }
    applyBindings(200, context);
    applyBindings(100, context);
    CompilationUnit compilationUnit = compilation.readCompilationUnit(scriptName.toString(), reader);
    net.java.javafx.type.expr.ValidationError err = compilation.getLastError();
    if(err != null) {
      throw new ScriptException(err);
    }
    ValueList result = null;
    try {
      result = compilationUnit.execute();
    }
    catch(Exception e) {
      throw new ScriptException(e);
    }
    if(result == null) {
      return null;
    }
    Object arr[] = new Object[result.getSize()];
    for(int i = 0; i < arr.length; i++) {
      Value value = result.getValue(i);
      arr[i] = value.get();
      if(arr[i] == null) {
        arr[i] = value;
      }
    }

    if(arr.length == 1) {
      return arr[0];
    } else {
      return ((Object) (arr));
    }
  }

  private void applyBindings(int scope, ScriptContext context) throws ScriptException {
    if(context == null) {
      return;
    }
    Bindings bindings = context.getBindings(scope);
    if(bindings == null) {
      return;
    }
    for(Iterator i$ = bindings.keySet().iterator(); i$.hasNext();) {
      String objectName = (String)i$.next();
      int colon = objectName.indexOf(":");
      Object object = bindings.get(objectName);
      Type type = null;
      if(colon > 0) {
        String objName = objectName.substring(0, colon);
        String typeName = objectName.substring(colon + 1);
        type = module.getType(typeName);
        if(type == null) {
          throw new ScriptException((new StringBuilder()).append("No such type: ").append(typeName).toString());
        }
        ValueList valueList = module.createValueList(object);
        int i = 0;
        for(int size = valueList.getSize(); i < size; i++) {
          if(!type.isAssignableFrom(valueList.getValue(i))) {
            throw new ScriptException((new StringBuilder()).append(valueList.getValue(i)).append(" is not an instance of ").append(type.getName()).toString());
          }
        }

        type.addNamedValue(objName, valueList);
      } else {
        throw new ScriptException((new StringBuilder()).append("Invalid binding name '").append(objectName).append("'. Must be of the form 'beanName:javaTypeFQN'").toString());
      }
    }

  }

  public ScriptEngineFactory getFactory() {
    if(factory == null) {
      factory = new JavaFXScriptEngineFactory();
    }
    return factory;
  }
}
