package weather.com.ztt.zttweatheralarm.weather.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import weather.com.ztt.zttweatheralarm.weather.WeatherAlarmConfiguration;
import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.WeatherInteractor;
import weather.com.ztt.zttweatheralarm.weather.WeatherPresenter;
import weather.com.ztt.zttweatheralarm.weather.WeatherRepository;
import weather.com.ztt.zttweatheralarm.weather.api.RestApiWeatherClient;
import weather.com.ztt.zttweatheralarm.weather.impl.WeatherAlarmConfigurationImpl;
import weather.com.ztt.zttweatheralarm.weather.impl.WeatherConfigurationSharedPreferences;
import weather.com.ztt.zttweatheralarm.weather.impl.WeatherInteractorImpl;
import weather.com.ztt.zttweatheralarm.weather.impl.WeatherPresenterImpl;
import weather.com.ztt.zttweatheralarm.weather.impl.WeatherRepositoryImpl;
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherView;

/**
 * Created by vtcmer on 16/12/2017.
 */

@Module
public class WeatherModule {

    private WeatherView weatherView;


    public WeatherModule(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    @Provides
    @Singleton
    WeatherView provideWeatherView(){
        return this.weatherView;
    }



    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository (final RestApiWeatherClient client){
        return new WeatherRepositoryImpl(client);
    }
    @Provides
    @Singleton
    WeatherInteractor provideWeatherInteractor (final WeatherRepository repository){
        return new WeatherInteractorImpl(repository);
    }

    @Provides
    @Singleton
    WeatherPresenter provideWeatherPresenter (final WeatherView view, final WeatherInteractor interactor){
        return new WeatherPresenterImpl(view,interactor);
    }


}
