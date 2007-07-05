package org.sf.scriptlandia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.sf.classfile.ClassFile;
import org.sf.classfile.ConstPool;
import org.sf.classfile.PoolEntry;
import org.sf.launcher.util.ReflectionUtil;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.codehaus.classworlds.ClassWorld;
import org.codehaus.classworlds.ClassRealm;

/**
 * This class is used for starting class file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 05/14/2006
 */
public final class ClassStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    final String classFileName = args[0];

    ClassFile classFile = new ClassFile();

    classFile.read(classFileName);

    ConstPool constPool = classFile.getConstPool();

    short thisClassIndex = classFile.getThisClassIndex();

    PoolEntry entry = constPool.getEntry(thisClassIndex);

    String fullClassName = entry.resolve(constPool).replace('/', '.');

    String tmp1 = fullClassName;
    String tmp2 = classFileName.substring(0, classFileName.length()-6);

    boolean ok = false;

    while(!ok) {
      int index1 = tmp1.lastIndexOf('.');

      if(index1 == -1) {
        ok = true;
      }
      else {
        int index2 = tmp2.lastIndexOf(File.separatorChar);

        String name1 = tmp1.substring(index1+1);
        String name2 = tmp2.substring(index2+1);

        if(name1.equals(name2)) {
          tmp1 = tmp1.substring(0, index1);
          tmp2 = tmp2.substring(0, index2);
        }
        else {
          ok = true;
        }
      }
    }

    String parent = new File(tmp2).getParent();

    ScriptlandiaLauncher.getInstance().addClasspathEntry(new File(parent).toURI().toURL());

    final List<String> newArgsList = new ArrayList<String>();

    for (int i = 1; i < args.length; i++) {
      newArgsList.add(args[i]);
    }

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    Class mainClass = mainRealm.loadClass(fullClassName);

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

      new ClassStarter().start(args, mainRealm);
    }
  }

}

