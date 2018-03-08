package weather.com.ztt.zttweatheralarm.weather.impl;

import weather.com.ztt.zttweatheralarm.R;
import weather.com.ztt.zttweatheralarm.weather.WeatherInteractor;
import weather.com.ztt.zttweatheralarm.weather.WeatherPresenter;
import weather.com.ztt.zttweatheralarm.weather.callback.WeatherDayCallback;
import weather.com.ztt.zttweatheralarm.weather.entities.EnumErrorWeather;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherView;

/**
 * Created by vtcmer on 17/12/2017.
 */

public class WeatherPresenterImpl  implements WeatherPresenter {

    private WeatherView weatherView;
    private WeatherInteractor weatherInteractor;



    public WeatherPresenterImpl(WeatherView weatherView, WeatherInteractor weatherInteractor) {
        this.weatherView = weatherView;
        this.weatherInteractor = weatherInteractor;
    }


    @Override
    public void getCurrentWeatherByCoordinates(String lat, String lon,
                                  final String units, final String lang,
                                  final String apiKey) {
        if  (this.weatherView != null){
            this.weatherView.showProgressBar();
        }


        this.weatherInteractor.getCurrentWeatherByCoordinates(lat, lon, units, lang, apiKey, new WeatherDayCallback() {
            @Override
            public void onSuccess(WeatherDay weatherDay) {
                weatherView.hideProgressBar();
                weatherView.renderWeather(weatherDay);
            }

            @Override
            public void onError(final EnumErrorWeather enumError, final String message) {
                weatherView.hideProgressBar();
                switch  (enumError){
                    case UNKNOWN_HOST_EXCEPTION:
                        weatherView.showError(R.string.error_unknown_host_exception);
                        break;
                    default:
                        weatherView.showError(R.string.error_internal);
                }
            }
        });

    }

    @Override
    public void destroy() {

        this.weatherView = null;
    }
}
