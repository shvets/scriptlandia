package org.sf.scriptlandia.nailgun.jdic;

import com.martiansoftware.nailgun.NGConstants;
import com.martiansoftware.nailgun.NGServer;
import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;
import org.sf.scriptlandia.nailgun.NGServerShutdowner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

/**
 * This class system tray for NG server.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public class NailgunTray extends TrayIcon {
  /** The popup frame that displays available menu choice. */
  //private JFrame popupFrame;

  /** The popup menu. */
  private JPopupMenu popupMenu;

  /** The Nailgun server instance. */
  private NGServer server;

  /**
   * Creates new system tray. /
   */
  public NailgunTray() {
    super(new ImageIcon(NailgunTray.class.getResource("/images/scriptlandia.gif")));

    this.setCaption("Scriptlandia Launching Pad");

    createPopupMenu();

    this.setPopupMenu(popupMenu);

    /*this.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showFloatingPane();
      }
    });
*/
  }

  /**
   * Creates popup menu.
   */
  private void createPopupMenu() {
    popupMenu = new JPopupMenu("Menu");

    JMenuItem serverMenuItem = new JMenuItem();

    //setServerMenuItemTitle(serverMenuItem);
    serverMenuItem.setText("Start Nailgun Server");    

    serverMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        executeServerMenuItem((JMenuItem) e.getSource());
      }
    });

    JMenuItem exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exit();
      }
    });

    popupMenu.add(serverMenuItem);
    popupMenu.add(exitMenuItem);
  }

/*
  private void showFloatingPane() {
    if (popupFrame == null) {
      popupFrame = new JFrame("Scriptlandia Launching Pad");
      popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      JPanel panel = new JPanel();

      JLabel label = new JLabel("scriptlandia");
      panel.add(label);

      popupFrame.add(panel);

      popupFrame.pack();

      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

      popupFrame.setLocation((int) (size.getWidth() - popupFrame.getWidth() - 30),
              (int) (size.getHeight() - popupFrame.getHeight() - 30));
    }

    if (!popupFrame.isVisible()) {
      popupFrame.setVisible(true);
    }
    else {
      popupFrame.setVisible(false);
    }
  }
*/

  /**
   * Sets the menu label based on inner state.
   *
   * @param menuItem the menu item
   */
/*  private void setServerMenuItemTitle(JMenuItem menuItem) {
    if (server != null && server.isRunning()) {
      menuItem.setText("Stop Nailgun Server");
    }
    else {
      menuItem.setText("Start Nailgun Server");
    }
  }
*/
  /**
   * Starting/stopping NG server operation.
   *
   * @param menuItem the menu item
   */
  private void executeServerMenuItem(JMenuItem menuItem) {
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
                  ? "all interfaces" : serverAddress.getHostAddress()) + ", port " + server.getPort() + ".",
                   "Nailgun Server", 0);
      menuItem.setText("Stop Nailgun Server");
    }
    else {
      server.shutdown(false);
      displayMessage("Nailgun Server stopped.", "Nailgun Server", 0);

      menuItem.setText("Start Nailgun Server");
    }

    //setServerMenuItemTitle(menuItem);
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
        SystemTray systemTray = SystemTray.getDefaultSystemTray();

        systemTray.addTrayIcon(new NailgunTray());
      }
    });
  }

}

