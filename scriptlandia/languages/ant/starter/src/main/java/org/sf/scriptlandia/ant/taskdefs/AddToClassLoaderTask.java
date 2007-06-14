package org.sf.scriptlandia.ant.taskdefs;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import org.sf.scriptlandia.launcher.UniversalLauncher;
import org.sf.scriptlandia.launcher.LauncherException;

/**
 * Adds new libraries to the current class realm.
 *
 * @author Alexander Shvets
 * @version 1.0 05/14/2004
 */
public final class AddToClassLoaderTask extends Task {
  //Example:
  // <addtoclassloader>
  //  <path>
  //    <fileset dir="lib">
  //      <include name="**/*.jar"/>
  //    </fileset>
  //  </path>
  // </addtoclassloader>
  //

  /**
   * The path with libraries to be added.
   */
  private Path path;

  /**
   * Sets the path.
   *
   * @param s the path
   */
  public void setPath(Path s) {
    createPath();

    path.append(s);
  }

  /**
   * Creates a nested path element.
   *
   * @return the path
   */
  public Path createPath() {
    if (path == null) {
      path = new Path(getProject());
    }

    return path;
  }

  /**
   * Adds a reference to a PATH defined elsewhere.
   *
   * @param reference the reference to path
   */
  public void setPathRef(Reference reference) {
    createPath();

    path.setRefid(reference);
  }

  /**
   * The method executing the task.
   *
   * @throws BuildException the build exception
   */
  public void execute() throws BuildException {
    if (path == null) {
      throw new BuildException("Path attribute must be set!", getLocation());
    }

    final String [] list = path.list();

    if (list.length > 0) {
      UniversalLauncher launcher = UniversalLauncher.getInstance();

      for (String aList : list) {
        final File file = new File(aList);

        if (file.exists()) {
          try {
            launcher.addClasspathEntry(file.toURI().toURL());
          }
          catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
          }
          catch (LauncherException e) {
            throw new IllegalArgumentException(e);
          }
        }
      }
    }
  }

}
