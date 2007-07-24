//package net.pabrantes.classLoaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClassLoader extends ClassLoader {

  private Map<String, ClassHolder> classTimeStamps;
  private List<String> classesToDelegate;

  public MyClassLoader(ClassLoader classLoader) {
    super(classLoader);

    classTimeStamps = new HashMap<String, ClassHolder>();
    classesToDelegate = new ArrayList<String>();
  }

  public void addClassToDelegateList(String name) {
    classesToDelegate.add(name);
  }

  protected boolean isClassInDelagationList(String name) {
    return classesToDelegate.contains(name);
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    if (!classTimeStamps.containsKey(name) || oldTimeStamp(name)) {
      System.out.println(name + " needs loading");
      classTimeStamps.remove(name);
      return new RuntimeClassLoader(this).loadClass(name);
    } else {
      System.out.println(name + " is still up to date");
      return classTimeStamps.get(name).getClazz();
    }
  }

  private boolean oldTimeStamp(String name) {
    File file = new File(name.replace(".", "/") + ".class");
    return file.lastModified() > classTimeStamps.get(name).getTimeStamp();
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    return (isClassInDelagationList(name)) ? getParent().loadClass(name) : findClass(name);
  }

  private class ClassHolder {
    private Class clazz;
    private long timeStamp;

    public ClassHolder(Class clazz, long timeStamp) {
      this.clazz = clazz;
      this.timeStamp = timeStamp;
    }

    public Class getClazz() {
      return clazz;
    }

    public long getTimeStamp() {
      return timeStamp;
    }

  }

  private class RuntimeClassLoader extends ClassLoader {
    public RuntimeClassLoader(ClassLoader classLoader) {

      super(classLoader);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
      return loadClass(name);
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
      if (isClassInDelagationList(name)) {
        return getParent().loadClass(name);
      }
      byte[] classBytes = null;
      try {
        classBytes = getBytes(name.replace(".", "/") + ".class");
      } catch (IOException e) {
        return findSystemClass(name);
      }

      Class clazz = defineClass(name, classBytes, 0, classBytes.length);
      File file = new File(name.replace(".", "/") + ".class");
      classTimeStamps.put(name, new ClassHolder(clazz, file.lastModified()));
      return clazz;
    }

    private byte[] getBytes(String filename) throws IOException {

      File file = new File(filename);
      long len = file.length();
      byte raw[] = new byte[(int) len];
      FileInputStream fin = new FileInputStream(file);
      int r = fin.read(raw);
      if (r != len) throw new IOException("Can't read all, " + r + " != " + len);
      fin.close();
      return raw;
    }
  }

}

