package org.sf.scriptlandia.commandlauncher;

import org.sf.scriptlandia.ForkerException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A command launcher for JDK/JRE 1.3 (and higher).  Uses the built-in
 * Runtime.exec() command.
 */
public class Java13CommandLauncher extends CommandLauncher {
  private Method myExecWithCWD;

  public Java13CommandLauncher() throws NoSuchMethodException {
    // Locate method Runtime.exec(String[] cmdarray,
    //                            String[] envp, File dir)
    myExecWithCWD = Runtime.class.getMethod("exec",
        new Class[]{String[].class, String[].class, File.class});
  }

  /**
   * Launches the given command in a new process, in the given working
   * directory.
   *
   * @param cmd        the command line to execute as an array of strings.
   * @param env        the environment to set as an array of strings.
   * @param workingDir the working directory where the command
   *                   should run.
   * @return the created Process.
   * @throws java.io.IOException probably forwarded from Runtime#exec.
   */
  public Process exec(String[] cmd, String[] env,
                      File workingDir) throws IOException {
    try {
      return (Process) myExecWithCWD.invoke(Runtime.getRuntime(),
          /* the arguments: */ new Object[]{cmd, env, workingDir});
    } catch (InvocationTargetException exc) {
      Throwable realexc = exc.getTargetException();
      if (realexc instanceof ThreadDeath) {
        throw (ThreadDeath) realexc;
      } else if (realexc instanceof IOException) {
        throw (IOException) realexc;
      } else {
        throw new ForkerException("Unable to execute command",
            realexc);
      }
    } catch (Exception exc) {
      // IllegalAccess, IllegalArgument, ClassCast
      throw new ForkerException("Unable to execute command", exc);
    }
  }
}