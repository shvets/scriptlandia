package org.sf.scriptlandia;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import org.sf.scriptlandia.util.ReflectionUtil;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.codehaus.classworlds.ClassRealm;

/**
 * This class is used for running Java FX scripts.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public final class FxStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    String fileName = new File(args[0]).getName();

    String fullScriptName = getScriptName(fileName);

    File parent = new File(new File(fileName).getCanonicalPath()).getParentFile();

    mainRealm.addConstituent(parent.getParentFile().toURI().toURL());

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
        System.out.println("5");
  }

  private String getScriptName(String fileName) {
    return "tutorial.Driver";
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    ScriptlandiaLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = launcher.getMainRealm();

    new FxStarter().start(args, mainRealm);
  }

}
