package com.ociweb.traydemo;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.Format;

/**
 * This class holds a single date's weather forecast.
 * @author Eric M. Burke
 */
public class ForecastItem {
    private WeatherCondition condition;
    private Date date;
    private int projectedHighTemp;
    private int projectedLowTemp;
    private String displayText;

    public ForecastItem(Date date,
                        WeatherCondition condition,
                        int projectedLowTemp,
                        int projectedHighTemp) {
        this.date = date;
        this.condition = condition;
        this.projectedLowTemp = projectedLowTemp;
        this.projectedHighTemp = projectedHighTemp;
        Format dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        displayText = dateFormat.format(date) + " - " +
                condition.getDescription() + " (" +
                projectedLowTemp + "..." + projectedHighTemp + ")";
    }

    public String toString() {
        return displayText;
    }

    public Date getDate() {
        return date;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public int getProjectedLowTemp() {
        return projectedLowTemp;
    }

    public int getProjectedHighTemp() {
        return projectedHighTemp;
    }
}
