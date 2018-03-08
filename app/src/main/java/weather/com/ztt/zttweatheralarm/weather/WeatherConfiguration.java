package weather.com.ztt.zttweatheralarm.weather;

import weather.com.ztt.zttweatheralarm.main.ui.AlarmEnum;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherLocation;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherParamConfiguration;

/**
 * Created by vtcmer on 27/12/2017.
 */

public interface WeatherConfiguration {

    /**
     * Recuperarción de las coordenadas
     * @return
     */
    WeatherLocation getLocation();

    /**
     * Actualización de las coordenadas
     * @param location
     */
    void updateLocation(WeatherLocation location);

    /**
     * Recuperación de los parametros de configuración
     * @return
     */
    WeatherParamConfiguration getWeatherParamConfiguration();

    /**
     * Actualización de los parametros de configuración
     * @param weatherParamConfiguration
     */
    void updateWeatherParamConfiguration(final WeatherParamConfiguration weatherParamConfiguration);

    /**
     * Actualiza el estado de la alarma
     * @param alarm
     * @param value
     */
    void updateStatusAlarm(final AlarmEnum alarm, final boolean value);

    /**
     * Recuperación del estado de la alarma
     * @param alarm
     * @return
     */
    boolean getStatusAlarm(final AlarmEnum alarm);

    /**
     * Actualizar la hora de la alarma
     * @param alarm
     * @param time
     */
    void updateTimeAlarm(final AlarmEnum alarm,final String time);

    /**
     * Recuperar la hora de la alarma
     * @param alarm
     * @return
     */
    String getTimeAlarm(final AlarmEnum alarm);
}
