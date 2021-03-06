/*
 * Copyright 2003-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sf.scriptlandia;

import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.jlaunchpad.util.ReflectionUtil;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.groovy.tools.LoaderConfiguration;
import org.codehaus.groovy.tools.RootLoader;

import java.lang.reflect.*;
import java.io.FileInputStream;

/**
 * Helper class to help classworlds to load classes.
 */
public class GroovyStarter {

  public static void rootLoader(String args[], ClassRealm mainRealm) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
/*    String conf = System.getProperty("groovy.starter.conf", null);
    LoaderConfiguration lc = new LoaderConfiguration();

    // evaluate parameters
    boolean hadMain = false, hadConf = false, hadCP = false;
    int argsOffset = 0;
    while (args.length - argsOffset > 0 && !(hadMain && hadConf && hadCP)) {
      if (args[argsOffset].equals("--classpath")) {
        if (hadCP) break;
        if (args.length == argsOffset + 1) {
          exit("classpath parameter needs argument");
        }
        lc.addClassPath(args[argsOffset + 1]);
        argsOffset += 2;
      } else if (args[argsOffset].equals("--main")) {
        if (hadMain) break;
        if (args.length == argsOffset + 1) {
          exit("main parameter needs argument");
        }
        lc.setMainClass(args[argsOffset + 1]);
        argsOffset += 2;
      } else if (args[argsOffset].equals("--conf")) {
        if (hadConf) break;
        if (args.length == argsOffset + 1) {
          exit("conf parameter needs argument");
        }
        conf = args[argsOffset + 1];
        argsOffset += 2;
      } else {
        break;
      }
    }

*/
/*
    // we need to know the class we want to start
    if (lc.getMainClass() == null && conf == null) {
      exit("no configuration file or main class specified");
    }
*/
/*    // copy arguments for main class
    String[] newArgs = new String[args.length - argsOffset];
    for (int i = 0; i < newArgs.length; i++) {
      newArgs[i] = args[i + argsOffset];
    }
    // load configuration file
    if (conf != null) {
      try {
        lc.configure(new FileInputStream(conf));
        //System.out.println("config...");
      } catch (Exception e) {
        System.err.println("exception while configuring main class loader:");
        exit(e);
      }
    }
    */
    // create loader and execute main class

//    ClassLoader loader = new RootLoader(lc);
//      ClassLoader loader = ClassLoader.getSystemClassLoader();
   // ClassLoader loader = Thread.currentThread().getContextClassLoader();

     Class mainClass = mainRealm.loadClass(/*lc.getMainClass()*/"groovy.ui.GroovyMain");

     ReflectionUtil.launchClass(mainClass, args,
             "public static void main(String[] argv) main Method is missed.");
  }

/*  private static void exit(Exception e) {
    e.printStackTrace();
    System.exit(1);
  }

  private static void exit(String msg) {
    System.err.println(msg);
    System.exit(1);
  }
 */
  // after migration from classworlds to the rootloader rename
  // the rootLoader method to main and remove this method as
  // well as the classworlds method
  /* public static void main(String args[],ClassWorld classWorld ) {
      classworlds(args,classWorld);
  }*/

  public static void main(String args[]) throws Exception {
    JLaunchPadLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = launcher.getMainRealm();

    try {
      rootLoader(args, mainRealm);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

}
