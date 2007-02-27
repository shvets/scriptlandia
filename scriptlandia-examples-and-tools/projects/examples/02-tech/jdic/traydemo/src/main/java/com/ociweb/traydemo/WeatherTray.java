package com.ociweb.traydemo;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;

import javax.swing.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class manages the icon shown in the system tray, as well as the
 * tray icon's popup menu and action listener.
 * @author Eric M. Burke
 */
public class WeatherTray {
    private SystemTray systemTray;
    private TrayIcon trayIcon;
    private ForecastService forecastService;
    private ForecastFrame forecastFrame;
    private List<ForecastItem> forecast;

    public WeatherTray(SystemTray systemTray,
                       ForecastService forecastService) {
        this.systemTray = systemTray;
        this.forecastService = forecastService;

        // initialize with a null icon and text because the
        // getNewForecast() method will provide those
        trayIcon = new TrayIcon(
                        null,
                        null,
                        createPopupMenu());

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showForecastClicked();
            }
        });

        // update the forecast every 15 seconds
        Timer timer = new Timer(15000, new ActionListener() {
            // this occurs on the event thread so is OK
            public void actionPerformed(ActionEvent e) {
                getNewForecast();
            }
        });
        timer.start();
        getNewForecast();
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(new AbstractAction("Show Forecast") {
            public void actionPerformed(ActionEvent e) {
                showForecastClicked();
            }
        }));
        popupMenu.addSeparator();
        popupMenu.add(new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));
        return popupMenu;
    }

    private void getNewForecast() {
        forecast = getForecast();

        // update the system tray icon
        ForecastItem todaysForecast = forecast.get(0);
        trayIcon.setIcon(todaysForecast.getCondition().getImageIcon());

        // NOTE: The tool tip can have \n characters, but not HTML
        trayIcon.setToolTip(todaysForecast.toString());

        if (forecastFrame != null) {
            forecastFrame.setForecast(forecast);
        }

        // bark at the user with a popup bubble
        // NOTE: Windows only shows the icon if the caption is non-null
        // NOTE: \n works for the text, but not HTML 
        trayIcon.displayMessage(
                "Important Information",
                "New Forecast Available",
                TrayIcon.INFO_MESSAGE_TYPE);
    }

    public void addToSystemTray() {
        // add to the system systemTray
        systemTray.addTrayIcon(trayIcon);
    }

    private List<ForecastItem> getForecast() {
        return forecastService.getForecast(5);
    }

    private void showForecastClicked() {
        if (forecastFrame == null) {
            forecastFrame = new ForecastFrame(forecast);
        }
        forecastFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // catch exceptions and exit, which prevents an orphaned tray icon
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
                WeatherTray wt = new WeatherTray(
                        SystemTray.getDefaultSystemTray(),
                        new RandomForecastService());
                wt.addToSystemTray();
            }
        });
    }
}
