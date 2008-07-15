import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.scriptlandia.install.CoreInstaller;

public class InstallerTest extends TestCase {
  private JLaunchPadLauncher launcher;

  protected void setUp() throws Exception {
    super.setUp();

    System.setProperty("repository.home", "c:/maven-repository");
    System.setProperty("ant.version.internal", "1.7.1");    
    //System.setProperty("jlaunchpad.home", "c:/launcher");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

 /* public void testResolveDependencies() {
    String[] args = new String[]{};

    try {
      CoreInstaller installer = new CoreInstaller();

      installer.coreInstall();

      installer.install(args);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  */
  
  public static void main(String[] args) {
    TestSuite suite = new TestSuite();

    suite.addTestSuite(InstallerTest.class);

    TestRunner.run(suite);
  }

}