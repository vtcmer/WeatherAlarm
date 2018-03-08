package weather.com.ztt.zttweatheralarm.weather.api.di;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import weather.com.ztt.zttweatheralarm.weather.api.RestApiWeatherClient;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;
import weather.com.ztt.zttweatheralarm.weather.api.WeatherDayJsonDeserializer;

/**
 * Created by vtcmer on 10/12/2017.
 */

@Module
public class ApiRestWeatherModule {

    private String urlBase;
    private Application application;

    public ApiRestWeatherModule(String urlBase,Application application) {
        this.urlBase = urlBase;
        this.application = application;
    }



    @Provides
    @Singleton
    Cache provideHttpCache() {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(this.application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson(final WeatherDayJsonDeserializer weatherDayJsonDeserializer) {
        GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(WeatherDay.class, weatherDayJsonDeserializer);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    public WeatherDayJsonDeserializer provideWeatherDayJsonDeserializer(){
        return new WeatherDayJsonDeserializer();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(this.urlBase)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    RestApiWeatherClient provideRestApiWeatherClient(final Retrofit retrofit){
        return new RestApiWeatherClient(retrofit);
    }


}
