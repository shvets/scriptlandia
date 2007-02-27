package org.sf.scriptlandia.pomreader;

import org.apache.maven.bootstrap.model.Model;
import org.apache.maven.bootstrap.model.ModelReader;
import org.apache.maven.bootstrap.model.Repository;
import org.apache.maven.bootstrap.model.Dependency;
import org.apache.maven.bootstrap.download.ArtifactResolver;
import org.apache.maven.bootstrap.download.OfflineArtifactResolver;
import org.apache.maven.bootstrap.settings.Settings;
import org.apache.maven.bootstrap.settings.Proxy;
import org.apache.maven.bootstrap.settings.Mirror;
import org.xml.sax.SAXException;
import org.sf.scriptlandia.util.FileUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.*;
import java.net.URL;

/**
 * This class can read pom.xml file.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public class PomReader {
  private Map<String, File> modelFileCache = new HashMap<String, File>();
  private Map<String, Model> modelCache = new HashMap<String, Model>();

  private ArtifactResolver resolver;

  /**
   * Creates new POM reader.
   */
  public PomReader() {}

  /**
   * Initializes POM reader.
   *
   * @throws Exception the exception
   */
  public void init() throws Exception {
    Settings settings = new Settings();

    if(settings.getLocalRepository() == null) {
      settings.setLocalRepository(System.getProperty("repository.home"));
    }

    String proxyServerHost = System.getProperty("proxy.server.host");
    String proxyServerPort = System.getProperty("proxy.server.port");

    if (proxyServerHost != null && proxyServerHost.trim().length() > 0) {
      Proxy proxy = new Proxy();

      proxy.setHost(proxyServerHost);
      proxy.setPort(proxyServerPort);
      proxy.setActive(true);

      settings.addProxy(proxy);
    }

    resolver = setupRepositories(settings);
  }

  /**
   * Builds the model based on pom/xml file.
   *
   * @param file pom file
   * @return the model
   * @throws ParserConfigurationException the exception
   * @throws SAXException the exception
   * @throws IOException the exception
   */
  public Model readModel(File file) throws ParserConfigurationException, SAXException, IOException {
    return readModel(file, true);
  }

  /**
   * Builds the model based on pom/xml file.
   *
   * @param file pom file
   * @param resolveTransitiveDependencies true if transitive dependencies should be resolved; false otherwise
   * @return the model
   * @throws ParserConfigurationException the exception
   * @throws SAXException the exception
   * @throws IOException the exception
   */
  public Model readModel(File file, boolean resolveTransitiveDependencies)
          throws ParserConfigurationException, SAXException, IOException {

    ModelReader modelReader = new ModelReader(resolver, resolveTransitiveDependencies);

    Model model = modelReader.parseModel(file, Collections.EMPTY_LIST);

    resolver.addBuiltArtifact(model.getGroupId(), model.getArtifactId(), "pom", file);

    String id = model.getGroupId() + ":" + model.getArtifactId();

    modelFileCache.put(id, file);
    modelCache.put(id, model);

    return model;
  }

  /**
   * Resolves dependencies for specified pom maven2 dependencies file.
   * @param pom the pom file
   * @throws Exception the exception
   * @return the list of dependent URLs
   */
  public List<URL> calculateDependencies(File pom) throws Exception {
    List<URL> dependencies = new ArrayList<URL>();

    Model model = readModel(pom, true);

    if(model.getPackaging() != null && model.getPackaging().equals("jar")) {
      Dependency currentDep = new Dependency(new ArrayList());
      currentDep.setGroupId(model.getGroupId());
      currentDep.setArtifactId(model.getArtifactId());
      currentDep.setVersion(model.getVersion());

      model.getParentDependencies().put(currentDep.getConflictId(), currentDep);
    }

    for (Object o : model.getAllDependencies()) {
      Dependency dependency = (Dependency) o;

      //noinspection unchecked
      dependency.getRepositories().addAll(model.getRepositories());

      File file = getArtifactFile(dependency);

      if (!FileUtil.getExtension(file).equals("pom")) {
        dependencies.add(file.toURI().toURL());
      }
    }

    resolver.downloadDependencies(model.getAllDependencies());

    return dependencies;
  }

  /**
   * Sets up repositories.
   *
   * @param settings settings object
   * @return artifact resolver
   * @throws Exception the exception
   */
  private ArtifactResolver setupRepositories(Settings settings) throws Exception {
    String scriptlandiaHome = System.getProperty("scriptlandia.home");
    List<Repository> repositories;

    RepositoriesReader reader = new RepositoriesReader();
    File file = new File("repositories.xml");

    if(!file.exists()) {
      file = new File(scriptlandiaHome + File.separatorChar + "repositories.xml");
    }

    if(!file.exists()) {
      System.out.println("File \"repository.xml\" cannot be found.");
      repositories = new ArrayList<Repository>();
    }
    else {
      reader.parse(file);
      repositories = reader.getRepositories();
    }

    boolean online = true;

    String onlineProperty = System.getProperty("maven.online");

    if (onlineProperty != null && onlineProperty.equals("false")) {
      online = false;
    }

    Repository localRepository =
            new Repository("local", settings.getLocalRepository(), Repository.LAYOUT_DEFAULT, false, false);

    File repoLocalFile = new File(localRepository.getBasedir());
    repoLocalFile.mkdirs();

    if (!repoLocalFile.canWrite()) {
      throw new Exception("Can't write to " + repoLocalFile);
    }

    ArtifactResolver resolver;
    if (online) {
      OnlineArtifactDownloader downloader = new OnlineArtifactDownloader(localRepository);
      downloader.setRemoteRepositories(repositories);

      resolver = downloader;
      if (settings.getActiveProxy() != null) {
        Proxy proxy = settings.getActiveProxy();
        downloader.setProxy(proxy.getHost(), proxy.getPort(), proxy.getUserName(), proxy.getPassword());
      }

      List<Repository> remoteRepos = downloader.getRemoteRepositories();
      List<Repository> newRemoteRepos = new ArrayList<Repository>();

      for (Object remoteRepo : remoteRepos) {
        Repository repo = (Repository) remoteRepo;

        boolean foundMirror = false;
        for (Iterator j = settings.getMirrors().iterator(); j.hasNext() && !foundMirror;) {
          Mirror m = (Mirror) j.next();
          if (m.getMirrorOf().equals(repo.getId())) {
            newRemoteRepos.add(new Repository(m.getId(), m.getUrl(), repo.getLayout(), repo.isSnapshots(),
              repo.isReleases()));
            foundMirror = true;
          }
        }
        if (!foundMirror) {
          newRemoteRepos.add(repo);
        }
      }

      downloader.setRemoteRepositories(newRemoteRepos);
    }
    else {
      resolver = new OfflineArtifactResolver(localRepository);
    }

    return resolver;
  }

  /**
   * Gets the artifact resolver.
   *
   * @return the artifact resolver
   */
  public ArtifactResolver getResolver() {
    return resolver;
  }

  /**
   * Gets the artifact file.
   *
   * @param dependency the dependency
   * @return the dependency
   */
  public File getArtifactFile(Dependency dependency) {
    return resolver.getArtifactFile(dependency);
  }

  /**
   * Gets the cached model.
   *
   * @param groupId the group ID
   * @param artifactId the artifact ID
   * @return the cached model
   */
  public Model getCachedModel(String groupId, String artifactId) {
    return modelCache.get(groupId + ":" + artifactId);
  }

  /**
   * Gets the cached model file.
   *
   * @param id the ID
   * @return the cached model file
   */
  public File getCachedModelFile(String id) {
    return modelFileCache.get(id);
  }

}
