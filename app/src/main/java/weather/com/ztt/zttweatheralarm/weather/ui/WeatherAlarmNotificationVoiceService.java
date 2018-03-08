package weather.com.ztt.zttweatheralarm.weather.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import weather.com.ztt.zttweatheralarm.BuildConfig;
import weather.com.ztt.zttweatheralarm.R;
import weather.com.ztt.zttweatheralarm.WeatherAlarmApplication;
import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.WeatherPresenter;
import weather.com.ztt.zttweatheralarm.weather.callback.WeatherLocationCallback;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherComponent;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherConfigurationComponent;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherLocation;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherParamConfiguration;
import weather.com.ztt.zttweatheralarm.weather.impl.ZttTextToSpeach;
import weather.com.ztt.zttweatheralarm.weather.listener.WeatherLocationListener;
import weather.com.ztt.zttweatheralarm.weather.ui.UtilsWeather;
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherView;

public class WeatherAlarmNotificationVoiceService extends Service
        implements WeatherLocationCallback, WeatherView {

    // Listener específico para localizar la aplicación
    private WeatherLocationListener weatherLocationListener;
    // Contexto de la aplicación
    private WeatherAlarmApplication weatherAlarmApplication;

    // Permite recuperar la lógica de la aplicación
    private WeatherConfiguration weatherConfiguration;
    /**
     * Presentador para la recuperación del tiempo
     */
    private WeatherPresenter weatherPresenter;
    ZttTextToSpeach speach;

    public WeatherAlarmNotificationVoiceService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
       this.weatherAlarmApplication = (WeatherAlarmApplication) this.getApplication();
        this.setupInyection();
        this.speach = new ZttTextToSpeach(this);
        weatherLocationListener = new WeatherLocationListener(this, this.weatherConfiguration, this);
    }

    @Override
    public void onDestroy() {
        this.weatherLocationListener.stopSearchLocation();
        this.weatherPresenter.destroy();
    }

    /**
     * Configuración de la inyección de dependencias
     */
    private void setupInyection() {
        WeatherConfigurationComponent component = this.weatherAlarmApplication.getWeatherConfiguration();
        this.weatherConfiguration = component.getWeatherConfiguration();

        WeatherComponent weatherComponent = this.weatherAlarmApplication.getWeatherComponent(this);
        this.weatherPresenter = weatherComponent.getWeatherPresenter();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.weatherLocationListener.startSearchLocation();
        return START_STICKY;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showError(int stringCodeError) {

    }

    @Override
    public void renderWeather(WeatherDay weatherDay) {
        String weatherDescription = this.getString(UtilsWeather.getDescriptionWeather(weatherDay.getWeatherCode()));
        final String msg =  String.format(getString(R.string.weather_info_voice), new String[]{weatherDay.getCity(),String.valueOf(weatherDay.getTemp()),weatherDescription});
        speach.speak(msg);
        this.stopSelf();
    }

    @Override
    public void onSearchLocationComplete(WeatherLocation weatherLocation) {
        if (weatherLocation != null) {
            WeatherParamConfiguration weatherParamConfiguration = this.weatherAlarmApplication.getWeatherParamConfiguration();
            this.weatherPresenter.getCurrentWeatherByCoordinates(weatherLocation.getLatitude(), weatherLocation.getLongitude(),
                    weatherParamConfiguration.getUnits(), weatherParamConfiguration.getLang(), BuildConfig.WEATHER_API_KEY);

        }
    }

    @Override
    public void onProviderDisabledExecuteIntent(Intent intent) {

    }

    @Override
    public void onRequestPermission(String[] permmissions) {

    }
}
