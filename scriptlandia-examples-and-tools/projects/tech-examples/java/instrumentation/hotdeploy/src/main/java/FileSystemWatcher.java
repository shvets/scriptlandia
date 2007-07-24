//package net.pabrantes.fsWatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FileSystemWatcher implements Runnable {

  private static Comparator<FileRepresentation> comparator = new Comparator<FileRepresentation>() {

    public int compare(FileRepresentation fr0, FileRepresentation fr1) {
      return fr0.getName().compareTo(fr1.getName());
    }

  };

  protected File directory;
  protected Set<FileRepresentation> representations;
  protected List<INotifyClient> clients;

  private FileRepresentation[] cachedArray = null;
  private long sleepTime = 10000;

  public FileSystemWatcher(String directoryName) {
    File directoryToWatch = new File(directoryName);
    if (!directoryToWatch.isDirectory()) {
      throw new RuntimeException("It needs to be a directory");
    }
    directory = directoryToWatch;
    representations = getCurrentRepresentation();
    clients = new ArrayList<INotifyClient>();
  }

  public void setSleepTime(long sleepTime) {
    this.sleepTime = sleepTime;
  }

  public long getSleepTime() {
    return this.sleepTime;
  }

  public void registerClient(INotifyClient client) {
    clients.add(client);
  }

  public Set<FileRepresentation> getCurrentRepresentation() {
    Set<FileRepresentation> representations = new TreeSet<FileRepresentation>(comparator);

    for (File file : directory.listFiles(new JavaRunnableFilenameFilter())) {
      representations.add(new FileRepresentation(file.getName(), file.lastModified()));
    }
    return representations;
  }

  private FileRepresentation[] getCachedArray() {
    if (cachedArray == null) {
      cachedArray = new FileRepresentation[representations.size()];
      representations.toArray(cachedArray);
    }
    return cachedArray;
  }

  public void notifyListeners() {
    for (INotifyClient client : clients) {
      client.notifyModification();
    }
  }

  public void modificationTrigger(Set<FileRepresentation> newData) {
    System.out.println("Detected modification");
    representations = newData;
    cachedArray = null;
    notifyListeners();
  }

  public void run() {

    for (; ;) {
      Set<FileRepresentation> currentRepresentation = getCurrentRepresentation();
      if (currentRepresentation.size() != representations.size()) {
        modificationTrigger(currentRepresentation);
      }

      FileRepresentation[] inCache = getCachedArray();
      FileRepresentation[] current = new FileRepresentation[currentRepresentation.size()];
      currentRepresentation.toArray(current);

      for (int i = 0; i < current.length; i++) {
        if (!current[i].equals(inCache[i])) {
          modificationTrigger(currentRepresentation);
        }
      }

      try {
        Thread.sleep(getSleepTime());
      } catch (InterruptedException e) {
        return;
      }
    }

  }
}

