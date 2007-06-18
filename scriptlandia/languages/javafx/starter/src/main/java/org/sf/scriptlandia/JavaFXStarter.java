package org.sf.scriptlandia;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;

import org.sf.scriptlandia.util.ReflectionUtil;
import org.sf.scriptlandia.launcher.UniversalLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.codehaus.classworlds.ClassRealm;

/**
 * This class is used for running Java FX scripts.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public final class JavaFXStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    String fullFileName = args[0];
    String fullDirName = new File(new File(fullFileName).getCanonicalPath()).getParent();

    String packageName = getPackageName(fullFileName);

    String fileName = new File(fullFileName).getName();
    String scriptName = fileName.substring(0, fileName.lastIndexOf('.'));

    String fullScriptName = (packageName.length() == 0) ? scriptName : packageName + "." + scriptName;

    String root = null;

    if(fullDirName.replace('\\', '/').replace('/', '.').endsWith(packageName)) {
      int index = fullDirName.replace('\\', '/').indexOf(packageName.replace('.', '/'));
      root = fullDirName.substring(0, index);
    }

    if(root != null) {
      mainRealm.addConstituent(new File(root).toURI().toURL());
    }

    String fullClassName = "net.java.javafx.FXShell";

    Class mainClass = mainRealm.loadClass(fullClassName);

    List<String> newArgsList = new ArrayList<String>();

    newArgsList.add(fullScriptName);

    for (int i = 1; i < args.length; i++) {
      newArgsList.add(args[i]);
    }

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    ReflectionUtil.launchClass(mainClass, newArgs,
            "public static void main(String[] argv) main Method is missed.");
  }

  private String getPackageName(String fileName) throws IOException {
    String packageName = "";

    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(fileName));

      boolean  done = false;

      while(!done) {
        String line = reader.readLine();

        if(line == null) {
          done = true;
        }
        else {
          line = line.trim();

          if(line.startsWith("package ")) {
            StringTokenizer st = new StringTokenizer(line);
            st.nextToken();

            String token = st.nextToken();

            packageName = token.substring(0, token.lastIndexOf(';')).trim();

            done = true;
          }
        }
      }
    }
    finally {
      if(reader != null) {
        reader.close();
      }
    }

    return packageName;
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    UniversalLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = launcher.getMainRealm();

    new JavaFXStarter().start(args, mainRealm);
  }

}
