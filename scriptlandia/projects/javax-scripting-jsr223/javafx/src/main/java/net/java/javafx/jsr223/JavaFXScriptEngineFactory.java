package net.java.javafx.jsr223;

import java.util.*;
import javax.script.*;


public class JavaFXScriptEngineFactory
  implements ScriptEngineFactory {

  private static final String ENGINE_NAME = "JavaFXEngine";
  private static final String ENGINE_VERSION = "0.1a";
  private static final String LANGUAGE_NAME = "FX";
  private static final String LANGUAGE_VERSION = "0.1";
  private static final String LANGUAGE_EXTENSION = "fx";

  public JavaFXScriptEngineFactory() {
  }

  public String getEngineName() {
    return "JavaFXEngine";
  }

  public String getEngineVersion() {
    return "0.1a";
  }

  public List getExtensions() {
    return Collections.nCopies(1, "fx");
  }

  public String getLanguageName() {
    return "FX";
  }

  public String getLanguageVersion() {
    return "0.1";
  }

  public String getMethodCallSyntax(String object, String methodName, String arguments[]) {
    StringBuilder builder = new StringBuilder();
    builder.append(object);
    builder.append('.');
    builder.append(methodName);
    builder.append('(');
    boolean more = false;
    String arr$[] = arguments;
    int len$ = arr$.length;
    for(int i$ = 0; i$ < len$; i$++) {
      String argument = arr$[i$];
      if(more) {
        builder.append(',');
      }
      builder.append(argument);
      more = true;
    }

    builder.append(')');
    return builder.toString();
  }

  public List getMimeTypes() {
    return Collections.emptyList();
  }

  public List getNames() {
    return Collections.nCopies(1, "FX");
  }

  public String getOutputStatement(String toDisplay) {
    StringBuilder builder = new StringBuilder();
    builder.append("println(");
    builder.append('\'');
    builder.append(toDisplay.replaceAll("'", "\\'"));
    builder.append('\'');
    builder.append(");");
    return builder.toString();
  }

  public Object getParameter(String key) {
    if(key.equals("javax.script.engine")) {
      return getEngineName();
    }
    if(key.equals("javax.script.engine_version")) {
      return getEngineVersion();
    }
    if(key.equals("javax.script.name")) {
      return getEngineName();
    }
    if(key.equals("javax.script.language")) {
      return getLanguageName();
    }
    if(key.equals("javax.script.language_version")) {
      return getLanguageVersion();
    } else {
      return null;
    }
  }

  public String getProgram(String statements[]) {
    StringBuilder builder = new StringBuilder();
    String arr$[] = statements;
    int len$ = arr$.length;
    for(int i$ = 0; i$ < len$; i$++) {
      String statement = arr$[i$];
      builder.append(statement);
      builder.append("\n");
    }

    return builder.toString();
  }

  public ScriptEngine getScriptEngine() {
    return new JavaFXScriptEngine();
  }
}
