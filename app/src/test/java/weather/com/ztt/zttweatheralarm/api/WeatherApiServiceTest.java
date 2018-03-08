package weather.com.ztt.zttweatheralarm.api;

import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.observers.TestSubscriber;
import weather.com.ztt.zttweatheralarm.BaseTest;
import weather.com.ztt.zttweatheralarm.BuildConfig;
import weather.com.ztt.zttweatheralarm.weather.api.RestApiWeather;
import weather.com.ztt.zttweatheralarm.weather.api.RestApiWeatherClient;
import weather.com.ztt.zttweatheralarm.weather.api.WeatherDayJsonDeserializer;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 01/01/2018.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class WeatherApiServiceTest extends BaseTest {
    RestApiWeather service = null;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WeatherDay.class, new WeatherDayJsonDeserializer());
        OkHttpClient.Builder client2 = new OkHttpClient.Builder();


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BuildConfig.WEATHER_API_HOST)
                .client( client2.build())
                .build();;

        RestApiWeatherClient client = new RestApiWeatherClient(retrofit);
        service = client.getRestApiWeather();
    }


    @Test
    public void executeTest(){

        Observable<WeatherDay> obj = service.getWeather("lat","long","metric",BuildConfig.WEATHER_API_KEY);

        TestSubscriber<WeatherDay> testSubscriber = new TestSubscriber<WeatherDay>();

        obj.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();



    }


}
