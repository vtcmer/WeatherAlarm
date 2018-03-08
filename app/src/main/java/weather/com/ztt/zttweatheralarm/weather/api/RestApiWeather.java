package weather.com.ztt.zttweatheralarm.weather.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 10/12/2017.
 */

public interface RestApiWeather {

    @GET("/data/2.5/weather")
    Observable<WeatherDay> getWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("units") String units, @Query("APPID") String apiKey);
}
