package org.sf.scriptlandia.util;

import java.io.File;
import java.io.IOException;

import java.util.jar.Manifest;
import java.util.jar.Attributes;

/**
 * This is the class for holding common utilities.
 *
 * @author Alexander Shvets
 * @version 1.0 02/19/2006
 */
public class CommonUtil {

  /**
   * Checks if the Java is working in AWT or Swing mode.
   *
   * @return true if the Java is working in AWT or Swing mode; false otherwise
   */
  public static boolean isGuiMode() {
    Thread currentThread = Thread.currentThread();

    ThreadGroup root = currentThread.getThreadGroup();

    boolean ok = false;

    while (!ok) {
      ThreadGroup parent = root.getParent();

      if (parent == null) {
        ok = true;
      } else {
        root = parent;
      }
    }

    Thread[] list = new Thread[root.activeCount()];

    root.enumerate(list, true);

    boolean isGuiMode = false;

    for (int i = 0; i < list.length && !isGuiMode; i++) {
      Thread thread = list[i];

      if (thread != null) {
        String threadName = thread.getName();

        if (threadName.startsWith("AWT-")) {
          isGuiMode = true;
        }
      }
    }

    return isGuiMode;
  }

  /**
   * Prints threads list.
   */
  public static void printThreads() {
    Thread currentThread = Thread.currentThread();

    ThreadGroup root = currentThread.getThreadGroup();

    boolean ok = false;

    while (!ok) {
      ThreadGroup parent = root.getParent();

      if (parent == null) {
        ok = true;
      } else {
        root = parent;
      }
    }

    Thread[] list = new Thread[root.activeCount()];

    root.enumerate(list, true);

    boolean isGuiMode = false;

    for (int i = 0; i < list.length && !isGuiMode; i++) {
      Thread thread = list[i];

      if (thread != null) {
        System.out.println("Thread: " + thread.getName());
      }
    }
  }

  /**
   * Gets the compiler jar file.
   *
   * @return the compiler jar file
   */
  public static File getCompilerJar() {
    File compilerJar = null;

    File toolsJar = new File(System.getProperty("java.home"), "../lib/tools.jar");

    if (toolsJar.exists()) {
      compilerJar = toolsJar;
    }
    else {
      toolsJar = new File(System.getProperty("java.home.internal"), "/lib/tools.jar");

      if (toolsJar.exists()) {
        compilerJar = toolsJar;
      }
      else {
        String javacInternalVersion = System.getProperty("javac.internal.version");

        toolsJar = new File(System.getProperty("repository.home"),
          "/com/sun/tools/javac/" + javacInternalVersion + "/javac-" + javacInternalVersion + ".jar");
        if (toolsJar.exists()) {
          compilerJar = toolsJar;
        }
      }
    }

    String javaVersion = System.getProperty("java.version");

    if(javaVersion.startsWith("1.5")) {
      String javacInternalVersion = "7.0-b07";

      compilerJar = new File(System.getProperty("repository.home"),
            "/com/sun/tools/javac/" + javacInternalVersion + "/javac-" + javacInternalVersion + ".jar");
    }

    return compilerJar;
  }

  /**
   * Sets up the java specification version.
   */
  public static String getJavaSpecificationVersion() throws IOException {
    String specificationVersion = null;

    String rtJarName = System.getProperty("java.home") + "../jre/lib/rt.jar";

    if (!new File(rtJarName).exists()) {
      rtJarName = System.getProperty("java.home") + "/jre/lib/rt.jar";

      if (!new File(rtJarName).exists()) {  
        rtJarName = System.getProperty("java.home.internal") + "/jre/lib/rt.jar";
      }

      if (new File(rtJarName).exists()) {  
        final Manifest manifest = FileUtil.getManifest(rtJarName);

        final Attributes mainAttributes = manifest.getMainAttributes();

        specificationVersion = mainAttributes.getValue("Specification-Version");
      }
    }

    return specificationVersion;
  }

}
