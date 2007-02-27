package com.ociweb.traydemo;

import java.util.List;

/**
 * This interface defines how a weather service can provide forecasts.
 *
 * @author Eric M. Burke
 */
public interface ForecastService {

    /**
     * @param numDays the maximum number of days to retrieve.
     * @return a forecast where element 0 is the current date.
     */
    List<ForecastItem> getForecast(int numDays);
}
