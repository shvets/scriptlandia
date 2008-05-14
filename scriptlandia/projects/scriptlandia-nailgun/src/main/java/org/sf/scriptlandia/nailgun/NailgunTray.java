package org.sf.scriptlandia.nailgun;

import com.martiansoftware.nailgun.NGConstants;
import com.martiansoftware.nailgun.NGServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

/**
 * This class system tray for NG server.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public class NailgunTray {

  /**
   * The Nailgun server instance.
   */
  private NGServer server;

  private Object trayIcon;

  private boolean isJava6OrMore = false;

  /**
   * Creates new system tray. /
   *
   * @throws Exception the exception
   */
  public NailgunTray() throws Exception {
    String javaVersion = System.getProperty("java.version");
    int versionNumber = Integer.parseInt(javaVersion.substring(2, 3));

    if (versionNumber > 5) {
      isJava6OrMore = true;
    }

    if (isJava6OrMore) {
      trayIcon = createJava6TrayIcon();
    } else {
      trayIcon = createJdicTrayIcon();
    }
  }

  private Object createJava6TrayIcon() throws Exception {
    Image image = Toolkit.getDefaultToolkit().getImage(NailgunTray.class.getResource("/images/scriptlandia.gif"));

    Class trayIconClass = Class.forName("java.awt.TrayIcon");

    Constructor trayIconConstructor = trayIconClass.getConstructor(Image.class);

    Object trayIcon = trayIconConstructor.newInstance(image);

    Method setImageAutoSizeMethod = trayIconClass.getDeclaredMethod("setImageAutoSize", boolean.class);
    setImageAutoSizeMethod.invoke(trayIcon, Boolean.TRUE);

    Method setToolTipMethod = trayIconClass.getDeclaredMethod("setToolTip", String.class);
    setToolTipMethod.invoke(trayIcon, "Scriptlandia Launching Pad");

    PopupMenu popupMenu = new PopupMenu("Menu");

    final MenuItem exitMenuItem = new MenuItem("Exit");
    exitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exit();
      }
    });

    MenuItem serverMenuItem = new MenuItem();

    serverMenuItem.setLabel("Start Nailgun Server");

    serverMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();

        if (executeServerMenuItem()) {
          menuItem.setLabel("Stop Nailgun Server");
          exitMenuItem.setEnabled(false);
        } else {
          menuItem.setLabel("Start Nailgun Server");
          exitMenuItem.setEnabled(true);
        }
      }
    });

    popupMenu.add(serverMenuItem);
    popupMenu.add(exitMenuItem);

    Method setPopupMenuMethod = trayIconClass.getDeclaredMethod("setPopupMenu", PopupMenu.class);
    setPopupMenuMethod.invoke(trayIcon, popupMenu);

    return trayIcon;
  }

  private Object createJdicTrayIcon() throws Exception {
    Icon icon = new ImageIcon(NailgunTray.class.getResource("/images/scriptlandia.gif"));

    Class trayIconClass = Class.forName("org.jdesktop.jdic.tray.TrayIcon");
    Constructor trayIconConstructor = trayIconClass.getConstructor(Icon.class);

    Object trayIcon = trayIconConstructor.newInstance(icon);

    Method setCaptionMethod = trayIconClass.getDeclaredMethod("setCaption", String.class);
    setCaptionMethod.invoke(trayIcon, "Scriptlandia Launching Pad");

    JPopupMenu popupMenu = new JPopupMenu("Menu");

    final JMenuItem exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exit();
      }
    });

    JMenuItem serverMenuItem = new JMenuItem();

    serverMenuItem.setText("Start Nailgun Server");

    serverMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();

        if (executeServerMenuItem()) {
          menuItem.setText("Stop Nailgun Server");
          exitMenuItem.setEnabled(false);
        } else {
          menuItem.setText("Start Nailgun Server");
          exitMenuItem.setEnabled(true);
        }
      }
    });

    popupMenu.add(serverMenuItem);
    popupMenu.add(exitMenuItem);

    Method setPopupMenuMethod = trayIconClass.getDeclaredMethod("setPopupMenu", JPopupMenu.class);
    setPopupMenuMethod.invoke(trayIcon, popupMenu);

    return trayIcon;
  }

  private void addToSystemTray() throws Exception {
    if (isJava6OrMore) {
      Class systemTrayClass = Class.forName("java.awt.SystemTray");
      Class trayIconClass = Class.forName("java.awt.TrayIcon");

      Method getSystemTrayMethod = systemTrayClass.getDeclaredMethod("getSystemTray");
      Object systemTray = getSystemTrayMethod.invoke(trayIcon);

      Method addMethod = systemTrayClass.getDeclaredMethod("add", trayIconClass);
      addMethod.invoke(systemTray, trayIcon);
    } else {
      Class systemTrayClass = Class.forName("org.jdesktop.jdic.tray.SystemTray");
      Class trayIconClass = Class.forName("org.jdesktop.jdic.tray.TrayIcon");

      Method getDefaultSystemTrayMethod = systemTrayClass.getDeclaredMethod("getDefaultSystemTray");
      Object systemTray = getDefaultSystemTrayMethod.invoke(trayIcon);

      Method addTrayIconMethod = systemTrayClass.getDeclaredMethod("addTrayIcon", trayIconClass);
      addTrayIconMethod.invoke(systemTray, trayIcon);
    }
  }

  private void displayMessage(String message) {
    try {
      if (isJava6OrMore) {
        Class trayIconClass = Class.forName("java.awt.TrayIcon");
        Class trayIconMessageTypeInfoClass = Class.forName("java.awt.TrayIcon$MessageType");

        Method valueOfMethod = trayIconMessageTypeInfoClass.getDeclaredMethod("valueOf", String.class);
        Object infoMessageType = valueOfMethod.invoke(trayIcon, "INFO");

        Method displayMessageMethod = trayIconClass.getDeclaredMethod("displayMessage", String.class, String.class, trayIconMessageTypeInfoClass);
        displayMessageMethod.invoke(trayIcon, message, "Nailgun Server", infoMessageType);
      } else {
        Class trayIconClass = Class.forName("org.jdesktop.jdic.tray.TrayIcon");

        Method displayMessageMethod = trayIconClass.getDeclaredMethod("displayMessage", String.class, String.class, int.class);
        displayMessageMethod.invoke(trayIcon, message, "Nailgun Server", 0);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Starting/stopping NG server operation.
   *
   * @return true if server started; false if server stopped
   */
  private boolean executeServerMenuItem() {
    if (server == null || !server.isRunning()) {
      InetAddress serverAddress = null;
      int port = NGConstants.DEFAULT_PORT;

      server = new NGServer(serverAddress, port);

      Thread thread = new Thread(server);
      thread.start();

      Runtime.getRuntime().addShutdownHook(new NGServerShutdowner(server));

      // if the port is 0, it will be automatically determined.
      // add this little wait so the ServerSocket can fully
      // initialize and we can see what port it chose.
      /*int runningPort = server.getPort();
      while (runningPort == 0) {
        try {
          Thread.sleep(50);
        }
        catch (Throwable toIgnore) {
          // ignore it
        }
        runningPort = server.getPort();
      }
      */

      while (!server.isRunning()) {
        try {
          Thread.sleep(50);
        }
        catch (Throwable toIgnore) {
          // ignore it
        }
      }

      displayMessage("Nailgun Server started on "
          + ((serverAddress == null)
          ? "all interfaces" : serverAddress.getHostAddress()) + ", port " + server.getPort() + ".");
      return true;
    } else {
      server.shutdown(false);
      displayMessage("Nailgun Server stopped.");

      return false;
    }
  }

  /**
   * Exit operation.
   */
  private void exit() {
    System.exit(0);
  }

  /**
   * Main entry point.
   *
   * @param args the list of arguments
   */
  public static void main(String[] args) {
    // Catch exceptions and exit, which prevents an orphaned tray icon
    // that might force the user to use Task Mgr to kill the process
    Thread.setDefaultUncaughtExceptionHandler(
        new Thread.UncaughtExceptionHandler() {
          public void uncaughtException(Thread t, Throwable e) {
            e.printStackTrace();
            System.exit(1);
          }
        });

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          NailgunTray nailgunTray = new NailgunTray();

          nailgunTray.addToSystemTray();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

}

