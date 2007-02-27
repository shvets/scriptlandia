package org.sf.scriptlandia;

import net.java.f3.type.expr.ValidationError;
import net.java.f3.type.expr.CompilationUnit;
import net.java.f3.type.Module;
import net.java.f3.type.ValueList;
import net.java.f3.type.Value;
import net.java.f3.typeImpl.TypeFactoryImpl;
import net.java.f3.typeImpl.Compilation;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

/**
 * This class is used for running F3 scripts.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public final class F3Starter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @throws Exception the exception
   */
  public void start(final String[] args) throws Exception {
    String fileName = new File(args[0]).getName();
 
    Module module = (new TypeFactoryImpl()).createModule();
    Compilation compilation = new Compilation(module);
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

    if(contextClassLoader != null) {
      module.setClassLoader(contextClassLoader);
    }

    File file = new File(fileName);

    Reader reader = new BufferedReader(new FileReader(file));

    try {
      CompilationUnit compilationUnit = compilation.readCompilationUnit(file.toURI().toURL().toString(), reader);

      ValueList result = compilationUnit.execute();

      if(result != null) {
        Object arr[] = new Object[result.getSize()];

        for(int i = 0; i < arr.length; i++) {
          Value value = result.getValue(i);

          System.out.println(value);
        }
      }
    }  
    catch(ValidationError e) {
      for(; e != null; e = e.getNextError()) {
        System.out.println(e.getMessage());
      }
    }
    catch(Throwable e) {
      e.printStackTrace();
    }
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    new F3Starter().start(args);
  }

}
