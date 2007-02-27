package com.ociweb.traydemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Shows a weather forecast in a JFrame.
 *
 * @author Eric M. Burke
 */
public class ForecastFrame extends JFrame {
    private JPanel forecastShell;

    public ForecastFrame(Iterable<ForecastItem> forecast) {
        super("Weather Forecast");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        forecastShell = new JPanel(new GridLayout(0, 1));
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(forecastShell, BorderLayout.NORTH);
        add(new JScrollPane(centerPanel), BorderLayout.CENTER);
        add(createButtonPane(), BorderLayout.SOUTH);

        setForecast(forecast);
        pack();
    }

    private Component createButtonPane() {
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.add(new JButton(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));
        return buttonPane;
    }

    public void setForecast(Iterable<ForecastItem> forecast) {
        forecastShell.removeAll();
        if (forecast != null) {
            for (ForecastItem fi : forecast) {
                forecastShell.add(new JLabel(fi.toString(),
                                fi.getCondition().getImageIcon(),
                                JLabel.LEFT));
            }
        }
        forecastShell.validate();
    }
}
