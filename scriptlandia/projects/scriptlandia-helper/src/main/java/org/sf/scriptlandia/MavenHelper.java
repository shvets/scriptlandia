package org.sf.scriptlandia;

import org.apache.maven.artifact.ant.DependenciesTask;
import org.apache.maven.artifact.ant.LocalRepository;
import org.apache.maven.artifact.ant.Pom;
import org.apache.maven.artifact.ant.RemoteRepository;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.bootstrap.model.Repository;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.sf.jlaunchpad.UniversalLauncher;
import org.sf.pomreader.RepositoriesReader;
import org.sf.pomreader.PomReader;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the class for holding convenient methods when working with Maven.
 *
 * @author Alexander Shvets
 * @version 2.0 06/30/2007
 */
public class MavenHelper {

  /**
   * Disables object creation.
   */
  private MavenHelper() {
  }

  /**
   * Executes Maven project.
   *
   * @param pomName the Maven project name
   * @param args    the list of command-line arguments
   * @throws Exception the exception
   */
  public static void executeMaven(String pomName, String[] args) throws Exception {
    executeMaven(pomName, args, ScriptlandiaLauncher.getInstance());
  }

  /**
   * Executes Maven project.
   *
   * @param pomName  the Maven project name
   * @param args     the list of command-line arguments
   * @param launcher launcher
   * @throws Exception the exception
   */
  public static void executeMaven(String pomName, String[] args, UniversalLauncher launcher) throws Exception {
    List<String> newArgsList = new ArrayList<String>();

    if (pomName != null) {
      newArgsList.add("-f");
      newArgsList.add(pomName);
    }

    newArgsList.addAll(Arrays.asList(args));

    String[] newArgs = new String[newArgsList.size()];

    newArgsList.toArray(newArgs);

    ClassWorld classWorld = launcher.getMainRealm().getWorld();

    //System.out.println("Maven args: " + java.util.Arrays.asList(newArgs));
    MavenCli.main(newArgs, classWorld);
  }

  /**
   * Executes Maven project; "pom.xml" is used maven project name.
   *
   * @param args the list of command-line arguments
   * @throws Exception the exception
   */
  public static void executeMaven(String[] args) throws Exception {
    executeMaven("pom.xml", args);
  }

  /**
   * Creates Ant project.
   *
   * @return Ant project
   */
  private static Project createAntProject() {
    Project project = new Project();

    project.init();

    BuildLogger logger = new DefaultLogger();

    logger.setMessageOutputLevel(Project.MSG_INFO);
    logger.setOutputPrintStream(System.out);
    logger.setErrorPrintStream(System.err);

    project.addBuildListener(logger);

    return project;
  }

  /**
   * Gets the maven project.
   *
   * @param pomName the pom project name
   * @return the maven project
   * @throws Exception ome exception
   */
  public static List<URL> getDependencies(String pomName) throws Exception {
 /*   MavenEmbedder embedder = new MavenEmbedder();

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    embedder.setClassLoader(classLoader);

    MavenEmbedderConsoleLogger logger = new MavenEmbedderConsoleLogger();

    embedder.setLogger(logger);

    embedder.start();

    File pomFile = new File(pomName);

    //MavenProject mavenProject = embedder.readProjectWithDependencies(pomFile);

    //EventMonitor eventMonitor = new DefaultEventMonitor(new PlexusLoggerAdapter(logger));

    //embedder.execute(mavenProject, Collections.singletonList( "validate" ), eventMonitor, new ConsoleDownloadMonitor(),
    //        null, new File("target"));

    return embedder.readProjectWithDependencies(pomFile);
    */

    PomReader pomReader = new PomReader();
    pomReader.init();

    List<URL> deps = pomReader.calculateDependencies(new File(pomName));

    return deps;
  }

  /**
   * Adds maven exceptions to the class realm.
   *
   * @throws Exception some exception
   */
  public static void addMavenDependencies() throws Exception {
    addMavenDependencies("pom.xml");
  }

  /**
   * Adds maven exceptions to the class realm.
   *
   * @param pomName the pom project name
   * @throws Exception some exception
   */
  public static void addMavenDependencies(String pomName) throws Exception {
    UniversalLauncher launcher = ScriptlandiaLauncher.getInstance();
    ClassRealm classRealm = launcher.getMainRealm();

    addMavenDependencies(pomName, classRealm);
  }

  /**
   * Adds maven exceptions to the class realm.
   *
   * @param pomName    the pom project name
   * @param classRealm the class realm
   * @throws Exception some exception
   */
  public static void addMavenDependencies(String pomName, ClassRealm classRealm) throws Exception {
    /*MavenProject project = getDependencies(pomName);

    Set<Artifact> artifacts = project.getArtifacts();

    for (Artifact artifact : artifacts) {
      File file = artifact.getFile();

      classRealm.addConstituent(file.toURI().toURL());
    }
    */

    List<URL> deps = getDependencies(pomName);

    for (URL dep : deps) {
      classRealm.addConstituent(dep);
    }

  }

  /**
   * Resolves Maven dependencies and saves them as Ant's reference: "maven.compile.classpath"
   * of Path type.
   *
   * @return Ant project
   * @throws Exception some exception
   */
  public static Project resolveMavenDependencies() throws Exception {
    return resolveMavenDependencies("pom.xml", "compile", true);
  }

  /**
   * Resolves Maven dependencies and saves them as Ant's reference: "maven.<useScope>.classpath"
   * of Path type.
   *
   * @param useScope the used scope
   * @return Ant project
   * @throws Exception some exception
   */
  public static Project resolveMavenDependencies(String useScope) throws Exception {
    return resolveMavenDependencies("pom.xml", useScope, true);
  }

  /**
   * Resolves Maven dependencies and saves them as Ant's reference: "maven.<useScope>.classpath"
   * of Path type.
   *
   * @param pomName  the Maven project file name
   * @param useScope the used scope
   * @return Ant project
   * @throws Exception some exception
   */
  public static Project resolveMavenDependencies(String pomName, String useScope)
      throws Exception {
    return resolveMavenDependencies(pomName, useScope, true);
  }

  /**
   * Resolves Maven dependencies and saves them as Ant's reference: "maven.<useScope>.classpath"
   * of Path type.
   *
   * @param pomName  the Maven project file name
   * @param useScope the used scope
   * @param online   if online flag is set
   * @return Ant project
   * @throws Exception some exception
   */
  public static Project resolveMavenDependencies(String pomName, String useScope, boolean online)
      throws Exception {
    Project project = createAntProject();

    resolveMavenDependencies(project, pomName, useScope, online);

    return project;
  }

  /**
   * Resolves Maven dependencies and saves them as Ant's reference: "maven.<useScope>.classpath"
   * of Path type.
   *
   * @param project  the aAnt project
   * @param pomName  the Maven project file name
   * @param useScope the used scope
   * @throws Exception the exception
   */
  public static void resolveMavenDependencies(Project project, String pomName, String useScope) throws Exception {
    resolveMavenDependencies(project, pomName, useScope, true);
  }

  /**
   * Resolves Maven dependencies and saves them as Ant's reference: "maven.<useScope>.classpath"
   * of Path type.
   *
   * @param project  the aAnt project
   * @param pomName  the Maven project file name
   * @param useScope the used scope
   * @param online   if online flag is set
   * @throws Exception the exception
   */
  public static void resolveMavenDependencies(Project project, String pomName, String useScope,
                                              boolean online) throws Exception {
    createLocalRepository();

    String id = "maven.project";

    Pom pom = createPom(pomName, id);

    project.addReference(id, pom);

    DependenciesTask dependenciesTask = createDependenciesTask(project, id, useScope, online);

    String launcherHome = System.getProperty("launcher.home");
    List<org.apache.maven.bootstrap.model.Repository> repositories;

    RepositoriesReader reader = new RepositoriesReader();
    File file = new File("repositories.xml");

    if (!file.exists()) {
      file = new File(launcherHome + File.separatorChar + "repositories.xml");
    }

    if (!file.exists()) {
      System.out.println("File " + file.getName() + " cannot be found.");
      repositories = new ArrayList<org.apache.maven.bootstrap.model.Repository>();
    } else {
      reader.parse(file);
      repositories = reader.getRepositories();
    }

      for (Repository r : repositories) {
          RemoteRepository repository = new RemoteRepository();
          repository.setId(r.getId());
          repository.setLayout(r.getLayout());
          repository.setUrl(r.getBasedir());

          dependenciesTask.addRemoteRepository(repository);
      }

    dependenciesTask.execute();

    UniversalLauncher launcher = ScriptlandiaLauncher.getInstance();
    ClassRealm classRealm = launcher.getMainRealm();

    Path path = (Path) project.getReference("maven." + useScope + ".classpath");

    resolvePath(path, classRealm);
  }

  /**
   * Iterates over the path and add path entries to the class realm.
   *
   * @param path       the path
   * @param classRealm the class realm
   * @throws MalformedURLException some exception
   */
  private static void resolvePath(Path path, ClassRealm classRealm) throws MalformedURLException {
    String[] entries = path.list();

    for (String entry : entries) {
      classRealm.addConstituent(new File(entry).toURI().toURL());
    }
  }

  /**
   * Creates class for representing local Maven repository.
   */
  private static void createLocalRepository() {
    LocalRepository localRepository = new LocalRepository();

    localRepository.setLocation(new Location(System.getProperty("repository.home")));
  }

  /**
   * Creates maven pom object.
   *
   * @param pomName the maven project file name
   * @param id      maven project ID
   * @return maven pom object
   */
  private static Pom createPom(String pomName, String id) {
    Pom pom = new Pom();
    pom.setFile(new File(pomName));
    pom.setId(id);

    return pom;
  }

  /**
   * Creates Ant Dependency task for maven.
   *
   * @param project  Ant project
   * @param id       maven project ID
   * @param useScope the used scope
   * @param online   the online flag
   * @return Ant Dependency task for maven
   * @throws Exception the exception
   */
  private static DependenciesTask createDependenciesTask(Project project, String id, String useScope,
                                                         boolean online) throws Exception {
    DependenciesTask dependenciesTask = new DependenciesTask();

    dependenciesTask.setProject(project);

    dependenciesTask.setPathId("maven." + useScope + ".classpath");
    dependenciesTask.setFilesetId(useScope + ".fileset");
    dependenciesTask.setUseScope(useScope);
    dependenciesTask.setPomRefId(id);

//    Embedder embedder = (Embedder)ReflectionUtil.invokePrivateMethod(
//       dependenciesTask, new Object[] {}, AbstractArtifactTask.class, "getEmbedder", new Class[] {});

//    WagonManager wagonManager = (WagonManager) embedder.lookup(WagonManager.ROLE);

//    wagonManager.setOnline(online);

    return dependenciesTask;
  }

}
