package org.sf.scriptlandia;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.Random;
import java.text.DecimalFormat;

public class FileUtils {
    private static final FileUtils PRIMARY_INSTANCE = new FileUtils();

  private static boolean onNetWare = OsUtils.isFamily("netware");
  private static boolean onDos = OsUtils.isFamily("dos");

  //get some non-crypto-grade randomness from various places.
  private static Random rand = new Random(System.currentTimeMillis()
          + Runtime.getRuntime().freeMemory());
 
    public static FileUtils getFileUtils() {
        return PRIMARY_INSTANCE;
    }

    /**
     * Empty constructor.
     */
    protected FileUtils() {
    }

  /**
   * Interpret the filename as a file relative to the given file
   * unless the filename already represents an absolute filename.
   * Differs from <code>new File(file, filename)</code> in that
   * the resulting File's path will always be a normalized,
   * absolute pathname.  Also, if it is determined that
   * <code>filename</code> is context-relative, <code>file</code>
   * will be discarded and the reference will be resolved using
   * available context/state information about the filesystem.
   *
   * @param file the "reference" file for relative paths. This
   * instance must be an absolute file and must not contain
   * &quot;./&quot; or &quot;../&quot; sequences (same for \ instead
   * of /).  If it is null, this call is equivalent to
   * <code>new java.io.File(filename).getAbsoluteFile()</code>.
   *
   * @param filename a file name.
   *
   * @return an absolute file.
   * @throws java.lang.NullPointerException if filename is null.
   */
  public File resolveFile(File file, String filename) {
      if (!isAbsolutePath(filename)) {
          char sep = File.separatorChar;
          filename = filename.replace('/', sep).replace('\\', sep);
          if (isContextRelativePath(filename)) {
              file = null;
              // on cygwin, our current directory can be a UNC;
              // assume user.dir is absolute or all hell breaks loose...
              String udir = System.getProperty("user.dir");
              if (filename.charAt(0) == sep && udir.charAt(0) == sep) {
                  filename = dissect(udir)[0] + filename.substring(1);
              }
          }
          filename = new File(file, filename).getAbsolutePath();
      }
      return normalize(filename);
  }

  /**
   * On DOS and NetWare, the evaluation of certain file
   * specifications is context-dependent.  These are filenames
   * beginning with a single separator (relative to current root directory)
   * and filenames with a drive specification and no intervening separator
   * (relative to current directory of the specified root).
   * @param filename the filename to evaluate.
   * @return true if the filename is relative to system context.
   * @throws java.lang.NullPointerException if filename is null.
   * @since Ant 1.7
   */
  public static boolean isContextRelativePath(String filename) {
      if (!(onDos || onNetWare) || filename.length() == 0) {
          return false;
      }
      char sep = File.separatorChar;
      filename = filename.replace('/', sep).replace('\\', sep);
      char c = filename.charAt(0);
      int len = filename.length();
      return (c == sep && (len == 1 || filename.charAt(1) != sep))
          || (Character.isLetter(c) && len > 1
          && filename.indexOf(':') == 1
          && (len == 2 || filename.charAt(2) != sep));
  }

  /**
   * Verifies that the specified filename represents an absolute path.
   * Differs from new java.io.File("filename").isAbsolute() in that a path
   * beginning with a double file separator--signifying a Windows UNC--must
   * at minimum match "\\a\b" to be considered an absolute path.
   * @param filename the filename to be checked.
   * @return true if the filename represents an absolute path.
   * @throws java.lang.NullPointerException if filename is null.
   * @since Ant 1.6.3
   */
  public static boolean isAbsolutePath(String filename) {
      int len = filename.length();
      if (len == 0) {
          return false;
      }
      char sep = File.separatorChar;
      filename = filename.replace('/', sep).replace('\\', sep);
      char c = filename.charAt(0);
      if (!(onDos || onNetWare)) {
          return (c == sep);
      }
      if (c == sep) {
          if (!(onDos && len > 4 && filename.charAt(1) == sep)) {
              return false;
          }
          int nextsep = filename.indexOf(sep, 2);
          return nextsep > 2 && nextsep + 1 < len;
      }
      int colon = filename.indexOf(':');
      return (Character.isLetter(c) && colon == 1
          && filename.length() > 2 && filename.charAt(2) == sep)
          || (onNetWare && colon > 0);
  }

  /**
   * Dissect the specified absolute path.
   * @param path the path to dissect.
   * @return String[] {root, remaining path}.
   * @throws java.lang.NullPointerException if path is null.
   * @since Ant 1.7
   */
  public String[] dissect(String path) {
      char sep = File.separatorChar;
      path = path.replace('/', sep).replace('\\', sep);

      // make sure we are dealing with an absolute path
      if (!isAbsolutePath(path)) {
          throw new ForkerException(path + " is not an absolute path");
      }
      String root = null;
      int colon = path.indexOf(':');
      if (colon > 0 && (onDos || onNetWare)) {

          int next = colon + 1;
          root = path.substring(0, next);
          char[] ca = path.toCharArray();
          root += sep;
          //remove the initial separator; the root has it.
          next = (ca[next] == sep) ? next + 1 : next;

          StringBuffer sbPath = new StringBuffer();
          // Eliminate consecutive slashes after the drive spec:
          for (int i = next; i < ca.length; i++) {
              if (ca[i] != sep || ca[i - 1] != sep) {
                  sbPath.append(ca[i]);
              }
          }
          path = sbPath.toString();
      } else if (path.length() > 1 && path.charAt(1) == sep) {
          // UNC drive
          int nextsep = path.indexOf(sep, 2);
          nextsep = path.indexOf(sep, nextsep + 1);
          root = (nextsep > 2) ? path.substring(0, nextsep + 1) : path;
          path = path.substring(root.length());
      } else {
          root = File.separator;
          path = path.substring(1);
      }
      return new String[] {root, path};
  }

  /**
   * &quot;Normalize&quot; the given absolute path.
   *
   * <p>This includes:
   * <ul>
   *   <li>Uppercase the drive letter if there is one.</li>
   *   <li>Remove redundant slashes after the drive spec.</li>
   *   <li>Resolve all ./, .\, ../ and ..\ sequences.</li>
   *   <li>DOS style paths that start with a drive letter will have
   *     \ as the separator.</li>
   * </ul>
   * Unlike {@link File#getCanonicalPath()} this method
   * specifically does not resolve symbolic links.
   *
   * @param path the path to be normalized.
   * @return the normalized version of the path.
   *
   * @throws java.lang.NullPointerException if path is null.
   */
  public File normalize(final String path) {
      Stack s = new Stack();
      String[] dissect = dissect(path);
      s.push(dissect[0]);

      StringTokenizer tok = new StringTokenizer(dissect[1], File.separator);
      while (tok.hasMoreTokens()) {
          String thisToken = tok.nextToken();
          if (".".equals(thisToken)) {
              continue;
          } else if ("..".equals(thisToken)) {
              if (s.size() < 2) {
                  // Cannot resolve it, so skip it.
                  return new File(path);
              }
              s.pop();
          } else { // plain component
              s.push(thisToken);
          }
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < s.size(); i++) {
          if (i > 1) {
              // not before the filesystem root and not after it, since root
              // already contains one
              sb.append(File.separatorChar);
          }
          sb.append(s.elementAt(i));
      }
      return new File(sb.toString());
  }

  /**
   * Create a temporary file in a given directory.
   *
   * <p>The file denoted by the returned abstract pathname did not
   * exist before this method was invoked, any subsequent invocation
   * of this method will yield a different file name.</p>
   * <p>
   * The filename is prefixNNNNNsuffix where NNNN is a random number.
   * </p>
   * <p>This method is different from File.createTempFile() of JDK 1.2
   * as it doesn't create the file itself.  It uses the location pointed
   * to by java.io.tmpdir when the parentDir attribute is null.</p>
   *
   * @param prefix prefix before the random number.
   * @param suffix file extension; include the '.'.
   * @param parentDir Directory to create the temporary file in;
   * java.io.tmpdir used if not specified.
   *
   * @return a File reference to the new temporary file.
   * @since Ant 1.5
   */
  public File createTempFile(String prefix, String suffix, File parentDir) {
      return createTempFile(prefix, suffix, parentDir, false);
  }

  /**
   * Create a temporary file in a given directory.
   *
   * <p>The file denoted by the returned abstract pathname did not
   * exist before this method was invoked, any subsequent invocation
   * of this method will yield a different file name.</p>
   * <p>
   * The filename is prefixNNNNNsuffix where NNNN is a random number.
   * </p>
   * <p>This method is different from File.createTempFile() of JDK 1.2
   * as it doesn't create the file itself.  It uses the location pointed
   * to by java.io.tmpdir when the parentDir attribute is null.</p>
   *
   * @param prefix prefix before the random number.
   * @param suffix file extension; include the '.'.
   * @param parentDir Directory to create the temporary file in;
   * @param deleteOnExit whether to set the tempfile for deletion on
   *        normal VM exit.
   * java.io.tmpdir used if not specified.
   *
   * @return a File reference to the new temporary file.
   * @since Ant 1.7
   */
  public File createTempFile(String prefix, String suffix, File parentDir,
                             boolean deleteOnExit) {
      File result = null;
      String parent = (parentDir == null)
          ? System.getProperty("java.io.tmpdir")
          : parentDir.getPath();

      DecimalFormat fmt = new DecimalFormat("#####");
      synchronized (rand) {
          do {
              result = new File(parent,
                                prefix + fmt.format(Math.abs(rand.nextInt()))
                                + suffix);
          } while (result.exists());
      }
      if (deleteOnExit) {
          result.deleteOnExit();
      }
      return result;
  }

  /**
   * Close a Writer without throwing any exception if something went wrong.
   * Do not attempt to close it if the argument is null.
   * @param device output writer, can be null.
   */
  public static void close(Writer device) {
      if (device != null) {
          try {
              device.close();
          } catch (IOException ioex) {
              //ignore
          }
      }
  }

  /**
   * Close a stream without throwing any exception if something went wrong.
   * Do not attempt to close it if the argument is null.
   *
   * @param device Reader, can be null.
   */
  public static void close(Reader device) {
      if (device != null) {
          try {
              device.close();
          } catch (IOException ioex) {
              //ignore
          }
      }
  }

  /**
   * Close a stream without throwing any exception if something went wrong.
   * Do not attempt to close it if the argument is null.
   *
   * @param device stream, can be null.
   */
  public static void close(OutputStream device) {
      if (device != null) {
          try {
              device.close();
          } catch (IOException ioex) {
              //ignore
          }
      }
  }

  /**
   * Close a stream without throwing any exception if something went wrong.
   * Do not attempt to close it if the argument is null.
   *
   * @param device stream, can be null.
   */
  public static void close(InputStream device) {
      if (device != null) {
          try {
              device.close();
          } catch (IOException ioex) {
              //ignore
          }
      }
  }

  /**
   * Delete the file with {@link File#delete()} if the argument is not null.
   * Do nothing on a null argument.
   * @param file file to delete.
   */
  public static void delete(File file) {
      if (file != null) {
          file.delete();
      }
  }

}
