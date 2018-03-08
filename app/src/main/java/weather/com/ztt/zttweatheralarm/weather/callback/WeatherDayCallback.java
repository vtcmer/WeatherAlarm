package weather.com.ztt.zttweatheralarm.weather.callback;

import weather.com.ztt.zttweatheralarm.weather.entities.EnumErrorWeather;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 17/12/2017.
 */

public interface WeatherDayCallback {

    /**
     * Resultado correcto, devuelve la temperatura
     * @param weatherDay
     */
    void onSuccess(final WeatherDay weatherDay);

    /**
     * Error en la recuperación de la temperatura
     * @param enumError
     * @param message
     */
    void onError (final EnumErrorWeather enumError, final String message);
}
