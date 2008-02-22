package org.apache.maven.plugin.beanshell;

import java.util.Set;
import java.util.Iterator;
import java.io.File;

import bsh.Interpreter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;

/**
 * @author <a href="mailto:alexander.shvets@gmail.com">Alexander Shvets</a>
 *
 * @goal run
 * @description Runs the nested ant source
 * //requiresDependencyResolution test
 */
public class BeanshellMojo extends AbstractMojo {

  /**
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter expression="${source}"
   */
  private String source;

  /**
   * @parameter expression="${content}"
   */
  private String content;

  /**
   * Sets the project.
   *
   * @param project the project.
   */
  public void setProject(MavenProject project) {
    this.project = project;
  }

  /**
   * Sets the source.
   *
   * @param source the source.
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * Sets the content.
   *
   * @param content the content.
   */
   public void setContent(String content) {
    this.content = content;
  }

  public void execute() throws MojoExecutionException {
    try {
      Interpreter interpreter = new Interpreter();

      interpreter.setOut(System.out);
      interpreter.setErr(System.err);

      Set artifacts = project.getArtifacts();

      if(artifacts != null) {
        for ( Iterator i = artifacts.iterator(); i.hasNext(); ) {
          Artifact a = (Artifact) i.next();
          File file = a.getFile();

          if ( file == null ) {
            throw new DependencyResolutionRequiredException( a );
          }

          interpreter.eval("addClasPath(" + file.getPath() + ")");
        }
      }

      if(source != null) {
        interpreter.source(source);
      }
      else if (content != null) {
        interpreter.eval(content);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new MojoExecutionException("Error executing beanshell", e);
    }
  }

}
