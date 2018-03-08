package weather.com.ztt.zttweatheralarm.weather.impl;


import rx.Subscription;
import weather.com.ztt.zttweatheralarm.weather.WeatherInteractor;
import weather.com.ztt.zttweatheralarm.weather.WeatherRepository;
import weather.com.ztt.zttweatheralarm.weather.callback.WeatherDayCallback;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 06/12/2017.
 */

public class WeatherInteractorImpl implements WeatherInteractor {

    private WeatherRepository weatherRepository;

    public WeatherInteractorImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void getCurrentWeatherByCoordinates(final String lat, final String lon,
                                          final String units, final String lang,
                                          final String apiKey,
                                          final WeatherDayCallback weatherDayCallback) {

         this.weatherRepository.getCurrentWeatherByCoordinates(lat,lon,units,lang, apiKey, weatherDayCallback);

    }
}
