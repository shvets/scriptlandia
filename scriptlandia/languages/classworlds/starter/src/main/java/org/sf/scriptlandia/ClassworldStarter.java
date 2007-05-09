package org.sf.scriptlandia;

import org.codehaus.classworlds.*;
import org.sf.scriptlandia.util.ReflectionUtil;
import org.sf.scriptlandia.pomreader.PomReader;
import org.apache.maven.bootstrap.model.Dependency;
import org.apache.maven.bootstrap.download.ArtifactResolver;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.*;
import java.net.URL;

/**
 * This class is used for starting classworld file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 09/17/2006
 */
public final class ClassworldStarter {

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassWorld classWorld) throws Exception {
    String scriptName = args[0];

    PomReader pomReader = new PomReader();
    pomReader.init();

    ClassRealm classRealm = classWorld.getRealm("pom-launcher-cwd");

    org.codehaus.classworlds.Launcher cwLauncher = null;

    try {
      cwLauncher = createClassworldLauncher(scriptName);
    }
    catch(FileNotFoundException e) {
      String repositoryHome = System.getProperty("repository.home").replace(File.separatorChar, '/');
      File missingFile = new File(e.getMessage());
      String missingFileName = missingFile.getPath().replace(File.separatorChar, '/');

      if(missingFileName.startsWith(repositoryHome)) {
        String name1 = missingFile.getName();
        int index1 = repositoryHome.length();
        int index2 = missingFile.getPath().indexOf(name1);

        String name2 = missingFileName.substring(index1, index2-1);
        int index3 = name2.lastIndexOf("/");

        String name3 = name2.substring(0, index3);
        int index4 = name3.lastIndexOf("/");

        String version = name2.substring(index3+1);
        String artifactId = name3.substring(index4+1);
        String groupId = name3.substring(1, index4).replace('/', '.');

        Dependency dependency = new Dependency(new ArrayList());
        dependency.setGroupId(groupId);
        dependency.setArtifactId(artifactId);
        dependency.setVersion(version);

        ArtifactResolver resolver = pomReader.getResolver();

        Collection<Dependency> dependencies = new ArrayList< Dependency >();
        dependencies.add(dependency);
        resolver.downloadDependencies(dependencies);

        cwLauncher = createClassworldLauncher(scriptName);
      }
    }

    ClassRealm mainRealm = cwLauncher.getMainRealm();

    URL[] urls = mainRealm.getConstituents();

    for (URL url : urls) {
      classRealm.addConstituent(url);

      String path = url.toURI().getPath();
      int index = path.lastIndexOf(".");

      String pomFileName = path.substring(1, index) + ".pom";
      File pomFile = new File(pomFileName);

      if(pomFile.exists()) {
        List<URL> deps;
        try {
          deps = pomReader.calculateDependencies(pomFile);
        }
        catch(Throwable t) {
          deps = new ArrayList<URL>();
        }

        for(URL dep : deps) {
          classRealm.addConstituent(dep);
        }
      }
    }

    String fullClassName = cwLauncher.getMainClassName();

    if(fullClassName != null) {
      Class mainClass = classRealm.loadClass(fullClassName);

      List<String> newArgsList = new ArrayList<String>();

      for (int i = 1; i < args.length; i++) {
        newArgsList.add(args[i]);
      }

      String[] newArgs = new String[newArgsList.size()];
      newArgsList.toArray(newArgs);

      ReflectionUtil.launchClass(mainClass, newArgs,
              "public static void main(String[] argv) main Method is missed.");
    }
  }

  /**
   * Creates new classworld launcher.
   *
   * @param scriptName the script name
   * @return classworld launcher
   * @throws Exception the exception
   */
  private Launcher createClassworldLauncher(String scriptName) throws Exception {
    org.codehaus.classworlds.Launcher cwl = new org.codehaus.classworlds.Launcher();
    cwl.setWorld(new ClassWorld());

    InputStream is = new FileInputStream(scriptName);

    Configurator configurator = new Configurator(cwl);
    configurator.setClassWorld(cwl.getWorld());
    configurator.configure(is);

    return cwl;
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @param classWorld class world
   * @throws Exception the exception
   */
  public static void main(String[] args, ClassWorld classWorld) throws Exception {
    new ClassworldStarter().start(args, classWorld);
  }

}

