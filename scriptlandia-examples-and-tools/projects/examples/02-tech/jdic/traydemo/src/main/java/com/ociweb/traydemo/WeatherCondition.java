package com.ociweb.traydemo;

import javax.swing.*;

/**
 * @author Eric M. Burke
 */
public enum WeatherCondition {
    CLOUDY("Cloudy"),
    PARTLY_CLOUDY("Partly Cloudy"),
    SNOW("Snow"),
    SUNNY("Sunny"),
    THUNDERSTORMS("Thunderstorms"),
    RAINY("Rainy");

    private ImageIcon imageIcon;
    private String description;

    WeatherCondition(String description) {
        this.description = description;
        String iconPath = "/images/" + toString() + ".gif";
        try {
            imageIcon = new ImageIcon(WeatherCondition.class.getResource(iconPath));
        } catch (Exception e) {
            throw new RuntimeException("Unable to locate '" + iconPath + "'. " +
                    "Ensure the 'resources' directory is in your classpath.");
        }
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public String getDescription() {
        return description;
    }
}
