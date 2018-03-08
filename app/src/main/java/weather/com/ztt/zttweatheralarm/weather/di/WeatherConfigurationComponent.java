package weather.com.ztt.zttweatheralarm.weather.di;

import javax.inject.Singleton;

import dagger.Component;
import weather.com.ztt.zttweatheralarm.weather.WeatherAlarmConfiguration;
import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;

/**
 * Created by vtcmer on 27/12/2017.
 */

@Singleton
@Component(modules={WeatherConfigurationModule.class})
public interface WeatherConfigurationComponent {
    WeatherConfiguration getWeatherConfiguration();
    WeatherAlarmConfiguration getWeatherAlarmConfiguration();
}
