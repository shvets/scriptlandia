import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.codehaus.classworlds.ClassWorld;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.scriptlandia.ScriptlandiaHelper;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncherCommandLineParser;

import java.util.ArrayList;
import java.util.List;

public class ScriptlandiaHelperTest extends TestCase {
  private JLaunchPadLauncher launcher;

  protected void setUp() throws Exception {
    super.setUp();

    System.setProperty("repository.home", "c:/maven-repository");
    System.setProperty("jlaunchpad.home", "c:/jlaunchpad");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testResolveDependencies() {
    String[] args = new String[]{};

    try {
      ClassWorld classWorld = new ClassWorld();

      ScriptlandiaLauncherCommandLineParser parser = new ScriptlandiaLauncherCommandLineParser();

      List<String> depsFileNames = new ArrayList<String>();

      depsFileNames.add("C:/maven-repository/org/sf/scriptlandia/beanshell-starter/2.2.5/beanshell-starter-2.2.5.pom ");
      parser.getCommandLine().put("main.class.name", "bsh.Interpreter");
      parser.getCommandLine().put("deps.file.name", depsFileNames);

      String[] newArgs = parser.parse(args);

      JLaunchPadLauncher launcher = new ScriptlandiaLauncher(parser, newArgs, classWorld);

      launcher.configure(Thread.currentThread().getContextClassLoader());

      ScriptlandiaHelper.resolveDependencies("com.google.gdata", "gdata-youtube", "1.0", launcher);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  public static void main(String[] args) {
    TestSuite suite = new TestSuite();

    suite.addTestSuite(ScriptlandiaHelperTest.class);

    TestRunner.run(suite);
  }

}