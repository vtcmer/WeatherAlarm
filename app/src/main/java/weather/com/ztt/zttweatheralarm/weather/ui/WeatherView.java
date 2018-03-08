package weather.com.ztt.zttweatheralarm.weather.ui;

import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 17/12/2017.
 */

public interface WeatherView {

    /**
     * Muestra el spin de progreso
     */
    void showProgressBar();

    /**
     * Oculta el spin de progreso
     */
    void hideProgressBar();

    /**
     * Muestra el error
     * @param stringCodeError
     */
    void showError(final int stringCodeError);

    /**
     * Renderíza la  temperatura recuperada
     * @param weatherDay
     */
    void renderWeather(final WeatherDay weatherDay);
}
