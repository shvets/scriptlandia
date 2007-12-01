import junit.framework.TestCase;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.codehaus.classworlds.Launcher;
import org.sf.jlaunchpad.UniversalLauncher;
import org.sf.scriptlandia.MavenHelper;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncherCommandLineParser;

import java.util.ArrayList;
import java.util.List;

public class MavenHelperTest extends TestCase {
  private UniversalLauncher launcher;

  protected void setUp() throws Exception {
    super.setUp();

    System.setProperty("repository.home", "c:/maven-repository");
    System.setProperty("launcher.home", "c:/launcher");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testExecuteMaven() {
    String[] args = new String[]{
        "install:install-file",
        "-Dfile=C:\\Work\\Projects\\jlaunchpad\\trunk\\launcher\\projects\\bootstrap-mini\\target\\bootstrap-mini-sources.jar",
        "-DgroupId=test",
        "-DartifactId=test",
        "-Dversion=1.0",
        "-Dpackaging=jar",
        "-DgeneratePom=true"
    };

    try {
      ClassWorld classWorld = new ClassWorld();

      ScriptlandiaLauncherCommandLineParser parser = new ScriptlandiaLauncherCommandLineParser();

      List<String> depsFileName = new ArrayList<String>();

      depsFileName.add("C:/maven-repository/org/sf/scriptlandia/beanshell-starter/1.0.0/beanshell-starter-1.0.0.pom ");
      parser.getCommandLine().put("main.class.name", "bsh.Interpreter");
      parser.getCommandLine().put("deps.file.name", depsFileName);

      String[] newArgs = parser.parse(args);

      UniversalLauncher launcher = new ScriptlandiaLauncher(parser, newArgs, classWorld);

      launcher.configure(Thread.currentThread().getContextClassLoader());
      MavenHelper.executeMaven(null, args, launcher);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  public void testAddMavenDependencies() {
    String[] args = new String[]{};

    try {
      ClassWorld classWorld = new ClassWorld();

      ScriptlandiaLauncherCommandLineParser parser = new ScriptlandiaLauncherCommandLineParser();

      List<String> depsFileName = new ArrayList<String>();

      depsFileName.add("C:/maven-repository/org/sf/scriptlandia/beanshell-starter/1.0.0/beanshell-starter-1.0.0.pom ");
      parser.getCommandLine().put("main.class.name", "bsh.Interpreter");
      parser.getCommandLine().put("deps.file.name", depsFileName);

      String[] newArgs = parser.parse(args);

      UniversalLauncher launcher = new ScriptlandiaLauncher(parser, newArgs, classWorld);

      launcher.configure(Thread.currentThread().getContextClassLoader());

      String pomName = "C:\\Work\\Projects\\scriptlandia\\trunk\\scriptlandia-examples-and-tools\\projects\\tech-examples\\web-examples\\jetty\\pom.xml";

      ClassRealm classRealm = launcher.getMainRealm();

      MavenHelper.addMavenDependencies(pomName, classRealm);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  public void testExecutLauncher() {
    String[] args = new String[]{
        "-deps.file.name=C:\\maven-repository\\org\\sf\\scriptlandia\\maven-starter\\1.0.0\\maven-starter-1.0.0.pom",
        "-main.class.name=org.sf.scriptlandia.MavenStarter",
        "-f", "pom.xml", "clean"
    };

    try {
      System.setProperty("classworlds.conf", "C:/scriptlandia/classworlds.conf");
      System.setProperty("launcher.version", "1.0.1");

      Launcher.main(args);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

}
