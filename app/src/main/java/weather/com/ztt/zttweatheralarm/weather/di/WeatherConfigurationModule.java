package weather.com.ztt.zttweatheralarm.weather.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import weather.com.ztt.zttweatheralarm.weather.WeatherAlarmConfiguration;
import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.impl.WeatherAlarmConfigurationImpl;
import weather.com.ztt.zttweatheralarm.weather.impl.WeatherConfigurationSharedPreferences;
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherView;

/**
 * Created by vtcmer on 27/12/2017.
 */

@Module
public class WeatherConfigurationModule {

    private Context context;


    public WeatherConfigurationModule(Context context) {
      this.context = context;
    }


    @Provides
    @Singleton
    public Context provideContext(){
        return this.context;
    }

    @Provides
    @Singleton
    public WeatherConfiguration provideWeatherConfiguration(final Context context){
        return new WeatherConfigurationSharedPreferences(context);
    }


    @Provides
    @Singleton
    WeatherAlarmConfiguration provideWeatherAlarmConfiguration (final Context context, final WeatherConfiguration weatherConfiguration){
        return new WeatherAlarmConfigurationImpl(context,weatherConfiguration);
    }

}
