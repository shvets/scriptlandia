package org.sf.scriptlandia;

import org.apache.tools.ant.util.KeepAliveInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

public class Forker {
  private File dir = null;

  protected Redirector redirector = new Redirector();

  private InputStream defaultInputStream = System.in;

  private String[] args;
  private List classpath = new ArrayList();
  private String className;

  public Forker(String[] args) {
    this.args = args;
  }

  public void addClasspathLocation(File location) {
    classpath.add(location);
  }

  /**
   * Do the execution.
   *
   * @throws ForkerException if failOnError is set to true and the application
   *                        returns a nonzero result code.
   */
  public void execute() throws ForkerException {
    if (className == null) {
      throw new ForkerException("Classname must not be null.");
    }

    setupRedirector();

    List newArgsList = new ArrayList();
    newArgsList.add(JavaEnvUtils.getJreExecutable("java"));

    newArgsList.add("-classpath");

    StringBuffer sb = new StringBuffer();

    for(int i=0; i < classpath.size(); i++) {
      try {
        sb.append(((File)classpath.get(i)).getCanonicalPath());

        if(i < classpath.size()-1) {
          sb.append(File.pathSeparatorChar);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    newArgsList.add(sb.toString());

     newArgsList.add(className);

    for(int i=0; i < args.length; i++) {
      newArgsList.add(args[i]);
    }

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    fork(newArgs);
  }

  /**
   * Set up properties on the redirector that we needed to store locally.
   */
  protected void setupRedirector() {
      redirector.setInputStream(new KeepAliveInputStream(defaultInputStream));
  }

  /**
   * Executes the given classname with the given arguments in a separate VM.
   *
   * @param command String[] of command-line arguments.
   */
  private int fork(String[] command) throws ForkerException {
    Execute exe
        = new Execute(redirector.createHandler() /*, createWatchdog()*/);
    exe.setWorkingDirectory(dir);
    exe.setCommandline(command);

    try {
      int rc = exe.execute();
      redirector.complete();
      return rc;
    } catch (IOException e) {
      throw new ForkerException(e);
    }
  }

  public void setClassname(String className) throws ForkerException {
    this.className = className;
  }

}
