package weather.com.ztt.zttweatheralarm.weather.callback;

import android.content.Intent;

import weather.com.ztt.zttweatheralarm.weather.entities.WeatherLocation;

/**
 * Created by vtcmer on 30/12/2017.
 */

public interface WeatherLocationCallback {

    /**
     * Función  que se ejecuta cuando finaliza la localización
     * @param location
     */
    void onSearchLocationComplete(final WeatherLocation location);

    /**
     * Ejecición del intent cuando el proveedor esta desactivo
     * @param intent
     */
    void onProviderDisabledExecuteIntent(final Intent intent);

    /**
     * Solicitud de los permisos
     * @param permmissions
     */
    void onRequestPermission(final String[] permmissions);
}
