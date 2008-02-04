package org.sf.scriptlandia;

import java.io.File;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.util.*;
import java.util.zip.ZipEntry;

import org.sf.jlaunchpad.util.FileUtil;
import org.sf.jlaunchpad.util.ReflectionUtil;
import org.sf.jlaunchpad.LauncherException;
import org.sf.jlaunchpad.JLaunchPadLauncher;
import org.sf.scriptlandia.launcher.ScriptlandiaLauncher;
import org.codehaus.classworlds.ClassRealm;

/**
 * This class is used for starting Jar file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 05/14/2004
 */
public final class JarStarter {

  /** The location of ant project file inside jar. */
  public static final String DEFAULT_ANT_PROJECT_LOCATION = "META-INF/default.ant";

  /** The jars cache. */
  protected final Map<JarFile, List<File>> jarsCache = new HashMap<JarFile, List<File>>();

  /**
   * The main starter method.
   *
   * @param args command line arguments
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  public void start(final String[] args, ClassRealm mainRealm) throws Exception {
    Runtime.getRuntime().addShutdownHook(
      new Thread() {
        public void run() {
          try {
            releaseResources();
          }
          catch (LauncherException e) {
            e.printStackTrace();
          }
        }
      });

    final String jarName = args[0];

    final Manifest manifest = FileUtil.getManifest(jarName);

    if (manifest != null) {
      final Attributes mainAttributes = manifest.getMainAttributes();

      String midletName = mainAttributes.getValue("MIDlet-Name");

      if (midletName != null) {
        String midlet1 = mainAttributes.getValue("MIDlet-1");

        if (midlet1 != null) {
          int index = midlet1.lastIndexOf(',');

          String midletClassName = midlet1.substring(index + 1).trim();

          mainRealm.addConstituent(new File(jarName).toURI().toURL());

          JadStarter.launchMobileJarFile(jarName, midletClassName, args, mainAttributes, mainRealm);
        }
      } else {
        mainRealm.addConstituent(new File(jarName).toURI().toURL());

        List<String> newArgsList = new ArrayList<String>();

        for (int i = 1; i < args.length; i++) {
          newArgsList.add(args[i]);
        }

        String[] newArgs = new String[newArgsList.size()];
        newArgsList.toArray(newArgs);

        final String classPath = mainAttributes.getValue(Attributes.Name.CLASS_PATH);

        final File buildFile =
                  FileUtil.getFileFromArchive(jarName, DEFAULT_ANT_PROJECT_LOCATION, "default-", ".ant");

        if (buildFile != null) {
          buildFile.deleteOnExit();

          launchAntProject(jarName, buildFile, newArgs, classPath, mainRealm);
        }
        else {
          String mainClassName = mainAttributes.getValue(Attributes.Name.MAIN_CLASS);

          if (mainClassName != null) {
            launchJarFile(jarName, mainClassName, newArgs, classPath, mainRealm);
          }
        }
      }
    }
  }

  /**
   * Launches regular jar file.
   *
   * @param mainClassName the main class name
   * @param args the list of arguments
   * @throws Exception the exception
   * @param jarName the jar name
   * @param classPath the classpath
   * @param mainRealm main class realm
   */
  private void launchJarFile(String jarName, String mainClassName, String[] args, String classPath,
                             ClassRealm mainRealm)
          throws Exception {
    final JarFile jarFile = new JarFile(jarName);

    collectJars(jarFile, classPath, mainRealm);

    Class mainClass = mainRealm.loadClass(mainClassName);

    ReflectionUtil.launchClass(mainClass, args,
            "public static void main(String[] argv) method is missed.");
  }

  /**
   * Launches ant script from inside jar file.
   *
   * @param jarName the jar file name
   * @param buildFile the build file
   * @param args the list of arguments
   * @param classPath the classpath
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  private void launchAntProject(String jarName, File buildFile, String[] args, String classPath,
                                ClassRealm mainRealm)
          throws Exception {
    System.setProperty("jar.file", jarName);

    final JarFile jarFile = new JarFile(jarName);

    final List<String> newArgsList = new ArrayList<String>();

    newArgsList.add("-f");
    newArgsList.add(buildFile.getPath());
    newArgsList.add("-Dbasedir=" + new File(new File(jarName).getCanonicalPath()).getParent());

    for (String arg : args) {
      newArgsList.add(arg);
    }

    AntStarter antStarter = new AntStarter();
    antStarter.setAllowInput(false);

    String[] newArgs = new String[newArgsList.size()];
    newArgsList.toArray(newArgs);

    collectJars(jarFile, classPath, mainRealm);

    antStarter.start(newArgs);
  }

  /**
   * Discovers jars in Class-Path attribute.
   *
   * @param jarFile   the jar file
   * @param classPath the class path attribute
   * @param mainRealm main class realm
   * @throws Exception the exception
   */
  private void collectJars(final JarFile jarFile, final String classPath, ClassRealm mainRealm)
          throws Exception {
    if (classPath != null) {
      final StringTokenizer st = new StringTokenizer(classPath);

      while (st.hasMoreTokens()) {
        final String jarName = st.nextToken();

        final ZipEntry zipEntry = jarFile.getEntry(jarName);

        if (zipEntry != null) {
          final File file = FileUtil.copyToTempFile(jarFile.getInputStream(zipEntry), "jar-", ".tmp");
          file.deleteOnExit();

          mainRealm.addConstituent(file.toURI().toURL());
          addJarFileEntry(jarFile, file);
        }
      }
    }
  }

  /**
   * Removes temporary libraries. If the jar file has another jar
   * files, they wull be unzipped temporary and at the end - deleted.
   *
   * @throws LauncherException launcher exception
   */
  protected void releaseResources() throws LauncherException {
    String tmpdir = System.getProperty("java.io.tmpdir");
    File tmpdirFile = new File(tmpdir);
    File[] filesList = tmpdirFile.listFiles();

    for (File file : filesList) {
      String name = file.getName();
      if (name.startsWith("jar-") && name.endsWith(".tmp")) {
        file.delete();
      }
    }

    for (JarFile jarFile : jarsCache.keySet()) {
      final List<File> values = jarsCache.get(jarFile);

      for (final File file : values) {
        file.delete();
      }
    }
  }

  /**
   * Adds new jar file entry to the launcher's cache.
   *
   * @param jarFile the jar file
   * @param file    the jar file entry
   * @throws Exception the exception
   */
  public void addJarFileEntry(JarFile jarFile, File file) throws Exception {
    List<File> values = jarsCache.get(jarFile);

    if (values == null) {
      values = new ArrayList<File>();

      jarsCache.put(jarFile, values);
    }

    values.add(file);
  }

  /**
   * The main method.
   *
   * @param args the command line arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {
    JLaunchPadLauncher launcher = ScriptlandiaLauncher.getInstance();

    ClassRealm mainRealm = launcher.getMainRealm();

    new JarStarter().start(args, mainRealm);
  }

}

