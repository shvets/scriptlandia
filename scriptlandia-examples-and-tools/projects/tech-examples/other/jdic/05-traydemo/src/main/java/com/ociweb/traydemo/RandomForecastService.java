package com.ociweb.traydemo;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;

/**
 * A dummy implementation that produces random weather forecasts.
 *
 * @author Eric M. Burke
 */
public class RandomForecastService implements ForecastService {
    private Random rand = new Random();

    public List<ForecastItem> getForecast(int numDays) {
        Calendar cal = Calendar.getInstance();
        List<ForecastItem> forecast = new ArrayList<ForecastItem>(numDays);
        for (int i=0; i<numDays; i++) {
            forecast.add(i, createRandomForecastItem(cal));
        }
        return forecast;
    }

    private ForecastItem createRandomForecastItem(Calendar cal) {
        WeatherCondition[] conditions = WeatherCondition.values();
        WeatherCondition condition = conditions[
                rand.nextInt(conditions.length)];

        int projectedMin;
        if (condition == WeatherCondition.SNOW) {
            projectedMin = rand.nextInt(32);
        } else {
            projectedMin = rand.nextInt(60) + 32;
        }
        return new ForecastItem(
                cal.getTime(),
                condition,
                projectedMin,
                projectedMin + (rand.nextInt(20)));
    }
}
