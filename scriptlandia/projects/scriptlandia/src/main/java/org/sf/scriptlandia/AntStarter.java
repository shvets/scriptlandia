package org.sf.scriptlandia;

import org.apache.tools.ant.*;
import org.apache.tools.ant.util.ProxySetup;
import org.apache.bsf.BSFManager;
import org.sf.scriptlandia.ant.taskdefs.AddToClassLoaderTask;
import org.sf.scriptlandia.util.ReflectionUtil;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.lang.reflect.InvocationTargetException;

/**
 * This class is used for starting Ant file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 02/20/2006
 */
public class AntStarter extends Main {
  private final List<String> arguments = new ArrayList<String>();
  private final List<String> specialArguments = new ArrayList<String>();

  /**
   * Constructor used when creating Main for later arg processing
   * and startup
   */
  public AntStarter() {
  }

  /**
   * Exits the program.
   *
   * @param exitCode the exit code
   */
  protected void exit(int exitCode) {
    // do nothing: supress System.exit() from the parens
  }

  /**
   * Starts the ant launcher.
   *
   * @param args command line parameters
   */
  public void start(String[] args) {
    String debug = System.getProperty("debug");

    if (debug != null && debug.trim().toLowerCase().equals("true")) {
      System.out.println("args: " + java.util.Arrays.asList(args));
    }

    //Properties additionalUserProperties = new Properties();
    ClassLoader classLoader = getClass().getClassLoader();

    parseCommandLine(args, arguments, specialArguments);

    String[] newArgs = arguments.toArray(new String[arguments.size()]);

    try {
      Diagnostics.validateVersion();
      processArgs(newArgs);
    } catch (Throwable exc) {
      handleLogfile();
      printMessage(exc);
      exit(1);
      return;
    }

    /*if (additionalUserProperties != null) {
      for (Enumeration e = additionalUserProperties.keys();
           e.hasMoreElements();) {
        String key = (String) e.nextElement();
        String property = additionalUserProperties.getProperty(key);
        ((Properties)getField("definedProps")).put(key, property);
      }
    }
    */

    // expect the worst
    int exitCode = 1;
    try {
      try {
        runBuild(classLoader);
        exitCode = 0;
      } catch (ExitStatusException ese) {
        exitCode = ese.getStatus();
        if (exitCode != 0) {
          throw ese;
        }
      }
    } catch (BuildException be) {
      if (getStaticField("err") != System.err) {
        printMessage(be);
      }
    } catch (Throwable exc) {
      exc.printStackTrace();
      printMessage(exc);
    } finally {
      handleLogfile();
    }
    exit(exitCode);
  }

  /**
   * Executes the build. If the constructor for this instance failed
   * (e.g. returned after issuing a warning), this method returns
   * immediately.
   *
   * @param coreLoader The classloader to use to find core classes.
   *                   May be <code>null</code>, in which case the
   *                   system classloader is used.
   * @throws BuildException if the build fails
   */
  private void runBuild(ClassLoader coreLoader) throws BuildException {
    if (!(Boolean)getField("readyToRun")) {
      return;
    }

    final Project project = new Project();
    project.setCoreLoader(coreLoader);

    final String name = "addtoclassloader";
    final String className = AddToClassLoaderTask.class.getName();

    final AntTypeDefinition def = new AntTypeDefinition();
    def.setName(name);
    def.setClassName(className);
    def.setClassLoader(coreLoader);
    def.setAdaptToClass(Task.class);
    def.setAdapterClass(TaskAdapter.class);

    BSFManager.registerScriptingEngine("scala", "org.sf.scriptlandia.bsf.ScalaEngine", new String[] { "scala" });
    BSFManager.registerScriptingEngine("jscheme", "jscheme.bsf.Engine", new String[] { "scm" });
    BSFManager.registerScriptingEngine("jaskell", "jfun.jaskell.bsf.BsfJaskellEngine", new String[] { "jaskell" });

    final ComponentHelper componentHelper = ComponentHelper.getComponentHelper(project);

    final Hashtable antTypeTable = componentHelper.getAntTypeTable();

    antTypeTable.put(name, def);

    project.addReference("parameters", specialArguments);

    Throwable error = null;

    try {
      addBuildListeners(project);
      addInputHandler(project);

      PrintStream savedErr = System.err;
      PrintStream savedOut = System.out;
      InputStream savedIn = System.in;

      // use a system manager that prevents from System.exit()
      SecurityManager oldsm;
      oldsm = System.getSecurityManager();

      //SecurityManager can not be installed here for backwards
      //compatibility reasons (PD). Needs to be loaded prior to
      //ant class if we are going to implement it.
      //System.setSecurityManager(new NoExitSecurityManager());
      try {
        if ((Boolean)getField("allowInput")) {
          project.setDefaultInputStream(System.in);
        }
        System.setIn(new DemuxInputStream(project));
        System.setOut(new PrintStream(new DemuxOutputStream(project, false)));
        System.setErr(new PrintStream(new DemuxOutputStream(project, true)));

        if (!(Boolean)getField("projectHelp")) {
          project.fireBuildStarted();
        }

        Integer threadPriority = (Integer)getField("threadPriority");

        // set the thread priorities
        if (threadPriority != null) {
          try {
            project.log("Setting Ant's thread priority to "
              + threadPriority, Project.MSG_VERBOSE);
            Thread.currentThread().setPriority(threadPriority.intValue());
          } catch (SecurityException swallowed) {
            //we cannot set the priority here.
            project.log("A security manager refused to set the -nice value");
          }
        }

        project.init();

        Properties definedProps = (Properties)getField("definedProps");

        // set user-define properties
        Enumeration e = definedProps.keys();
        while (e.hasMoreElements()) {
          String arg = (String) e.nextElement();
          String value = (String) definedProps.get(arg);
          project.setUserProperty(arg, value);
        }

        project.setUserProperty(MagicNames.ANT_FILE,
          ((File)getField("buildFile")).getAbsolutePath());

        project.setKeepGoingMode((Boolean)getField("keepGoingMode"));

        if ((Boolean)getField("proxy")) {
          //proxy setup if enabled
          ProxySetup proxySetup = new ProxySetup(project);
          proxySetup.enableProxies();
        }

        ProjectHelper.configureProject(project, (File)getField("buildFile"));

        if ((Boolean)getField("projectHelp")) {
          printDescription(project);
          printTargets(project, (Integer)getField("msgOutputLevel") > Project.MSG_INFO);
          return;
        }

        // make sure that we have a target to execute
        if (((Vector)getField("targets")).size() == 0) {
          if (project.getDefaultTarget() != null) {
            ((Vector)getField("targets")).addElement(project.getDefaultTarget());
          }
        }

        project.executeTargets((Vector)getField("targets"));
      } finally {
        // put back the original security manager
        //The following will never eval to true. (PD)
        if (oldsm != null) {
          System.setSecurityManager(oldsm);
        }

        System.setOut(savedOut);
        System.setErr(savedErr);
        System.setIn(savedIn);
      }
    } catch (RuntimeException exc) {
      error = exc;
      throw exc;
    } catch (Error e) {
      error = e;
      throw e;
    } finally {
      if (!(Boolean)getField("projectHelp")) {
        project.fireBuildFinished(error);
      } else if (error != null) {
        project.log(error.toString(), Project.MSG_ERR);
      }
    }
  }

  private void processArgs(String[] args) {
    try {
      ReflectionUtil.invokePrivateMethod(
        this, new Object[] { args }, Main.class, "processArgs", new Class[] { String[].class });
    }
    catch (InvocationTargetException e) {
      if(e.getTargetException() instanceof BuildException) {
        e.getMessage();
      }
      else {
        e.printStackTrace();
      }  
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void handleLogfile() {
    try {
      ReflectionUtil.invokePrivateMethod(
        null, new Object[] {}, Main.class, "handleLogfile", new Class[] {});
    }
    catch (InvocationTargetException e) {
      if(e.getTargetException() instanceof BuildException) {
        e.getMessage();
      }
      else {
        e.printStackTrace();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void printMessage(Throwable throwable) {
    try {
      ReflectionUtil.invokePrivateMethod(
       null, new Object[] { throwable }, Main.class, "printMessage", new Class[] { Throwable.class });
    }
    catch (InvocationTargetException e) {
      if(e.getTargetException() instanceof BuildException) {
        e.getMessage();
      }
      else {
        e.printStackTrace();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void addInputHandler(Project project) throws BuildException {
    try {
      ReflectionUtil.invokePrivateMethod(
       this, new Object[] { project }, Main.class, "addInputHandler", new Class[] { Project.class });
    }
    catch (InvocationTargetException e) {
      if(e.getTargetException() instanceof BuildException) {
        e.getMessage();
      }
      else {
        e.printStackTrace();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void printDescription(Project project) {
    try {
      ReflectionUtil.invokePrivateMethod(
       null, new Object[] { project }, Main.class, "printDescription", new Class[] { Project.class });
    }
    catch (InvocationTargetException e) {
      if(e.getTargetException() instanceof BuildException) {
        e.getMessage();
      }
      else {
        e.printStackTrace();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void printTargets(Project project, boolean b) {
    try {
      ReflectionUtil.invokePrivateMethod(
       null, new Object[] { project, b }, Main.class, "printTargets", new Class[] { Project.class, boolean.class });
    }
    catch (InvocationTargetException e) {
      if(e.getTargetException() instanceof BuildException) {
        e.getMessage();
      }
      else {
        e.printStackTrace();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Object getField(String name) {
    try {
      return ReflectionUtil.getPrivateField(this, Main.class, name);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private static Object getStaticField(String name) {
    try {
      return ReflectionUtil.getPrivateField(null, Main.class, name);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private void setField(String name, Boolean value) {
    try {
      ReflectionUtil.setPrivateField(this, Main.class, name, value);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<String> getArguments() {
    return arguments;
  }

  public List<String> getSpecialArguments() {
    return specialArguments;
  }

  /**
   * Sets allow input flag.
   *
   * @param allowInput allow input flag
   */
  public void setAllowInput(boolean allowInput) {
    setField("allowInput", allowInput);
  }

  /**
   * Parses the command line.
   *
   * @param args original arguments
   * @param arguments arguments
   * @param specialArguments special arguments
   */
  private void parseCommandLine(final String[] args, List<String> arguments, List<String> specialArguments) {
    for (final String arg : args) {
      if (isSpecialArgument(arg)) {
        if (!specialArguments.contains(arg)) {
          specialArguments.add(arg);
        }
      } else {
        if (!arguments.contains(arg)) {
          arguments.add(arg);
        }
      }
    }
  }

  /**
   * Checks if the parameter of command line should be treated as special.
   * It requires for antlets to separate targets from regular parameters.
   *
   * @param arg the argument
   * @return true if the parameter is special; false otherwise
   */
  private static boolean isSpecialArgument(final String arg) {
    return arg.startsWith("[") && arg.endsWith("]");
  }

  /**
   * Command line entry point. This method kicks off the building
   * of a project object and executes a build using either a given
   * target or the default target.
   *
   * @param args Command line arguments. Must not be <code>null</code>.
   */
  public static void main(String[] args) {
    AntStarter antStarter = new AntStarter();

    antStarter.start(args);
  }

}
