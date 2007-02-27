package org.sf.scriptlandia;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.DebuggingInformation;
import org.sf.scriptlandia.util.ReflectionUtil;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is used for starting class file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 05/14/2006
 */
public final class JaninoStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    final String fileName = args[0];

    int index1 = fileName.lastIndexOf(File.separatorChar);
    int index2 = fileName.lastIndexOf(".");

    String fullClassName = fileName.substring(index1+1, index2);

    final List<String> newArgsList = new ArrayList<String>();

    for (int i = 1; i < args.length; i++) {
      newArgsList.add(args[i]);
    }

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

    ClassLoader cl = new JavaSourceClassLoader(
        contextClassLoader,  // parentClassLoader
        new File[] { new File(".") }, // optionalSourcePath
        null,                     // optionalCharacterEncoding
        DebuggingInformation.NONE          // debuggingInformation
    );

    Class mainClass = cl.loadClass(fullClassName);

    ReflectionUtil.launchClass(mainClass, newArgs,
            "public static void main(String[] argv) main Method is missed.");

  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws Exception {
    Iterator iterator = classWorld.getRealms().iterator();

    if(iterator.hasNext()) {
      ClassRealm mainRealm = ((ClassRealm)iterator.next());

      new JaninoStarter().start(args, mainRealm);
    }
  }

}
