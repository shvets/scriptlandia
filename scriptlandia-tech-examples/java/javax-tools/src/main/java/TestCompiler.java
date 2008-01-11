//package test;

import java.io.*;
import java.util.*;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import test.*;

final public class TestCompiler {
  private static String template =
    "package $packageName;\n" +
    "import static java.lang.Math.*;\n" +
    "public class $className \n" +
    "           implements Function {\n" +
    "\n" +
    "   public double f(double x) {\n" +
    "      return ($expression);\n" +
    "   }\n" +
    "}\n";

   private static String fillTemplate(String packageName, String className, String expression)
         throws IOException {
      // simplest "template processor":
      String source = template.replace("$packageName", packageName)//
            .replace("$className", className)//
            .replace("$expression", expression);
      return source;
   }

   private static void log(final DiagnosticCollector<JavaFileObject> diagnostics) {
      final StringBuilder msgs = new StringBuilder();
      for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
            .getDiagnostics()) {
         System.out.println(diagnostic.getMessage(null));
      }
      
      System.out.println(msgs.toString());
   }

  public static void main(final String[] args) throws Exception {
    String expr = "x * sin(x) * cos(x)";

    // generate semi-secure unique package and class names
    final String packageName = "test";
    final String className = "TestFunction";
    final String qName = packageName + '.' + className;
   
    // generate the source class as String
    final String source = fillTemplate(packageName, className, expr);
        
    // compile the generated Java source
    final DiagnosticCollector<JavaFileObject> errs = new DiagnosticCollector<JavaFileObject>();
 
    CharSequenceCompiler<Function> compiler = new CharSequenceCompiler<Function>(
         TestCompiler.class.getClassLoader(), Arrays.asList(new String[] { "-target", "1.5" }));
 
    Class<Function> compiledFunction = compiler.compile(qName, source, errs,
          new Class<?>[] { Function.class });
    log(errs);
    
    Function function = compiledFunction.newInstance();

    System.out.println("function: " + function.f(25));
  }

}
