package org.apache.maven.plugin.beanshell;

import java.util.HashMap;
import java.net.URL;

import junit.framework.TestCase;
import org.apache.maven.project.MavenProject;

public class BeanshellMojoTest extends TestCase {
  private BeanshellMojo mojo;

  protected void setUp() throws Exception {
    super.setUp();

    mojo = new BeanshellMojo();
    mojo.setPluginContext(new HashMap());
  }

  protected void tearDown() throws Exception {
    super.tearDown();

    mojo = null;
  }

  /**
   * Test method for BeanShellMojo
   */
  public void testExecuteWithSource() throws Exception {
    System.out.println("Executing testExecuteWithSource...");

    String fileName = "testscript.bsh";

    URL resource = mojo.getClass().getClassLoader().getResource(fileName);

    mojo.setProject(new MavenProject());

    mojo.setSource(resource.getFile());

    try {
      mojo.execute();
    }
    catch(Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  /**
   * Test method for BeanShellMojo
   */
  public void testExecuteWithContent() throws Exception {
    System.out.println("Executing testExecuteWithContent...");
    String content = "System.out.println(\"hello world\");";

    mojo.setProject(new MavenProject());
    
    mojo.setContent(content);

    try {
      mojo.execute();
    }
    catch(Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

}
