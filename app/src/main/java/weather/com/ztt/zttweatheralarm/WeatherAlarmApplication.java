package weather.com.ztt.zttweatheralarm;

import android.app.Application;

import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.api.di.ApiRestWeatherModule;
import weather.com.ztt.zttweatheralarm.weather.di.DaggerWeatherComponent;
import weather.com.ztt.zttweatheralarm.weather.di.DaggerWeatherConfigurationComponent;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherComponent;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherConfigurationComponent;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherConfigurationModule;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherModule;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherParamConfiguration;
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherView;

/**
 * Created by vtcmer on 05/12/2017.
 */

public class WeatherAlarmApplication extends Application {


    private WeatherConfiguration weatherConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        this.setupInyection();

    }

    /**
     * Configuración de la inyección de dependencias
     */
    private void setupInyection() {
        WeatherConfigurationComponent component = this.getWeatherConfiguration();
        this.weatherConfiguration = component.getWeatherConfiguration();

    }

    /**
     * Recuperación de los parametros de configuración
     * @return
     */
    public WeatherParamConfiguration getWeatherParamConfiguration(){

        return this.weatherConfiguration.getWeatherParamConfiguration();
    }


    public WeatherComponent getWeatherComponent(final WeatherView view){
        return DaggerWeatherComponent.builder()
                .apiRestWeatherModule(new ApiRestWeatherModule(BuildConfig.WEATHER_API_HOST,this))
                .weatherModule(new WeatherModule(view)).build();
    }

    public WeatherConfigurationComponent getWeatherConfiguration(){
        return DaggerWeatherConfigurationComponent.builder()
                .weatherConfigurationModule(new WeatherConfigurationModule(this)).build();
    }


}
