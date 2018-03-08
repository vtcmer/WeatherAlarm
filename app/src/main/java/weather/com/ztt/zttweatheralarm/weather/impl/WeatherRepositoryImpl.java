package weather.com.ztt.zttweatheralarm.weather.impl;

import java.net.UnknownHostException;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import weather.com.ztt.zttweatheralarm.weather.api.RestApiWeather;
import weather.com.ztt.zttweatheralarm.weather.WeatherRepository;
import weather.com.ztt.zttweatheralarm.weather.api.RestApiWeatherClient;
import weather.com.ztt.zttweatheralarm.weather.callback.WeatherDayCallback;
import weather.com.ztt.zttweatheralarm.weather.entities.EnumErrorWeather;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 06/12/2017.
 */

public class WeatherRepositoryImpl implements WeatherRepository {


    private RestApiWeatherClient restApiWeatherClient;

    public WeatherRepositoryImpl(final RestApiWeatherClient restApiWeatherClient) {
        this.restApiWeatherClient = restApiWeatherClient;
    }


    @Override
    public void  getCurrentWeatherByCoordinates(final String lat, final String lon,
                                           final String units, final String lang,
                                           final String apiKey,
                                           final WeatherDayCallback weatherDayCallback) {

        RestApiWeather apiWeather = this.restApiWeatherClient.getRestApiWeather();

        apiWeather.getWeather(lat,lon,units,apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends WeatherDay>>() {
                    @Override
                    public Observable<? extends WeatherDay> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WeatherDay>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException){
                            weatherDayCallback.onError(EnumErrorWeather.UNKNOWN_HOST_EXCEPTION, "Error: " + e.getMessage());
                        } else {
                            weatherDayCallback.onError(EnumErrorWeather.INTERNAL_ERROR, "Error: " + e.getMessage());
                        }

                    }

                    @Override
                    public void onNext(WeatherDay weatherDay) {
                        //JsonObject coord =  json.getAsJsonObject().get("coord").getAsJsonObject();
                        weatherDay.setLatitude(Double.valueOf(lat));
                        weatherDay.setLongitude(Double.valueOf(lon));
                        weatherDayCallback.onSuccess(weatherDay);

                    }
                });

    }
}
