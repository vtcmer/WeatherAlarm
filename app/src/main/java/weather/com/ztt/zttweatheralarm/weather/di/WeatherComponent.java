package weather.com.ztt.zttweatheralarm.weather.di;

import javax.inject.Singleton;

import dagger.Component;

import weather.com.ztt.zttweatheralarm.weather.WeatherPresenter;
import weather.com.ztt.zttweatheralarm.weather.api.di.ApiRestWeatherModule;

/**
 * Created by vtcmer on 16/12/2017.
 */

@Singleton
@Component(modules={WeatherModule.class, ApiRestWeatherModule.class})
public interface WeatherComponent {

    WeatherPresenter getWeatherPresenter();

}
