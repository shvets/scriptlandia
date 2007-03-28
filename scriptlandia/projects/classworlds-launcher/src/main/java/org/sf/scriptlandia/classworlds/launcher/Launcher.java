package org.sf.scriptlandia.classworlds.launcher;

public class Launcher extends org.codehaus.classworlds.Launcher {

  public static void main(String[] args) {
    LauncherHelper launcherHelper = new LauncherHelper();
    launcherHelper.setupProperties();

    org.codehaus.classworlds.Launcher.main(args);
  }

}
