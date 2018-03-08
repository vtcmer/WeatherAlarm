package weather.com.ztt.zttweatheralarm.weather;

import rx.Subscription;
import weather.com.ztt.zttweatheralarm.weather.callback.WeatherDayCallback;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 05/12/2017.
 */

public interface WeatherInteractor {

    /**
     * Recupera informaición del tiempo actutal
     * @param lat
     * @param lon
     */
    void getCurrentWeatherByCoordinates(final String lat, final String lon,
                                   final String units, final String lang,
                                   final String apiKey,
                                   final WeatherDayCallback weatherDayCallback);


}
