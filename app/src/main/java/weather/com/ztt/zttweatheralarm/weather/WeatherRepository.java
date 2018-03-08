package weather.com.ztt.zttweatheralarm.weather;

import rx.Subscription;
import weather.com.ztt.zttweatheralarm.weather.callback.WeatherDayCallback;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 06/12/2017.
 */

public interface WeatherRepository {


    /**
     * Recupeación del tiempo  actual
     * @param lat
     * @param lon
     * @param units
     * @param lang
     * @param apiKey
     * @param weatherDayCallback
     */
    void getCurrentWeatherByCoordinates(final String lat, final String lon,
                                   final String units, final String lang,
                                   final String apiKey, final WeatherDayCallback weatherDayCallback);
}
