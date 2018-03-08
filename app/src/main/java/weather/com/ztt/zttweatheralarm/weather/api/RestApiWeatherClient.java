package weather.com.ztt.zttweatheralarm.weather.api;

import retrofit2.Retrofit;

/**
 * Created by vtcmer on 01/01/2018.
 */

public class RestApiWeatherClient {

    Retrofit retrofit;

    public RestApiWeatherClient(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public RestApiWeather getRestApiWeather(){
        RestApiWeather apiWeather = this.retrofit.create(RestApiWeather.class);
        return apiWeather;
    }
}
