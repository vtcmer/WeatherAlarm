package weather.com.ztt.zttweatheralarm.main.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import weather.com.ztt.zttweatheralarm.BuildConfig;

import weather.com.ztt.zttweatheralarm.R;
import weather.com.ztt.zttweatheralarm.WeatherAlarmApplication;
import weather.com.ztt.zttweatheralarm.weather.WeatherAlarmConfiguration;
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
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherActivity;
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherView;

public class MainActivity extends AppCompatActivity implements WeatherLocationCallback, WeatherView {


    private final static int CODE_REQUEST_PERMISSION_LOCATION = 1000;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.alarma1)
    Switch alarma1;
    @BindView(R.id.timeAlarm1)
    EditText timeAlarm1;
    @BindView(R.id.saveTimeAlarm1)
    ImageView saveTimeAlarm1;

    // Contexto de la aplicación
    private WeatherAlarmApplication weatherAlarmApplication;
    // Listener específico para localizar la aplicación
    private WeatherLocationListener weatherLocationListener;
    // Permite recuperar la lógica de la aplicación
    private WeatherConfiguration weatherConfiguration;
    /**
     * Presentador para la recuperación del tiempo
     */
    private WeatherPresenter weatherPresenter;


    private WeatherAlarmConfiguration weatherAlarmConfiguration;
    private ZttTextToSpeach speach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.setupInyection();
        this.initDefaultConfig();


        // this.registerAlamReceiver();

        // this.getApplicationContext().unregisterReceiver();
    }

    /**
     * Configuración de la inyección de dependencias
     */
    private void setupInyection() {

        this.weatherAlarmApplication = (WeatherAlarmApplication) this.getApplication();

        WeatherConfigurationComponent componentConfiguration = this.weatherAlarmApplication.getWeatherConfiguration();
        this.weatherConfiguration = componentConfiguration.getWeatherConfiguration();
        this.weatherAlarmConfiguration = componentConfiguration.getWeatherAlarmConfiguration();

        WeatherComponent weatherComponent = this.weatherAlarmApplication.getWeatherComponent(this);
        this.weatherPresenter = weatherComponent.getWeatherPresenter();

        this.speach = new ZttTextToSpeach(this);


    }

    /**
     * Inicialización de la configuración por defecto
     */
    private void initDefaultConfig() {
        weatherLocationListener = new WeatherLocationListener(this, this.weatherConfiguration, this);

        boolean activeAlarm1 = this.weatherConfiguration.getStatusAlarm(AlarmEnum.ALARM1);
        this.alarma1.setChecked(activeAlarm1);
        this.updateStatusAlarm1ViewItem(!activeAlarm1);
        this.timeAlarm1.setText(this.weatherAlarmConfiguration.getTimeAlarm(AlarmEnum.ALARM1));
    }

    @Override
    protected void onDestroy() {
        this.weatherLocationListener.stopSearchLocation();
        this.weatherPresenter.destroy();
        super.onDestroy();
    }

    @OnClick(R.id.btnStartStop)
    public void onViewClicked() {
        Intent intent = new Intent(this, WeatherActivity.class);
        this.startActivity(intent);
    }


    @OnClick(R.id.btnVoice)
    public void onVoiceViewClicked() {
        this.showProgressBar();
        this.weatherLocationListener.startSearchLocation();
    }

    @Override
    public void onSearchLocationComplete(WeatherLocation weatherLocation) {
        if (!this.weatherLocationListener.isEnabledGPS()) {
            Snackbar.make(this.container, getString(R.string.weather_info_gps_active), Snackbar.LENGTH_LONG).show();
        }

        if (weatherLocation != null) {
            WeatherParamConfiguration weatherParamConfiguration = this.weatherAlarmApplication.getWeatherParamConfiguration();
            this.weatherPresenter.getCurrentWeatherByCoordinates(weatherLocation.getLatitude(), weatherLocation.getLongitude(),
                    weatherParamConfiguration.getUnits(), weatherParamConfiguration.getLang(), BuildConfig.WEATHER_API_KEY);

        }
    }

    @Override
    public void renderWeather(WeatherDay weatherDay) {
        this.hideProgressBar();

        String weatherDescription = this.getString(UtilsWeather.getDescriptionWeather(weatherDay.getWeatherCode()));
        final String msg = String.format(getString(R.string.weather_info_voice), new String[]{weatherDay.getCity(), String.valueOf(weatherDay.getTemp()), weatherDescription});
        speach.speak(msg);
    }


    @Override
    public void onProviderDisabledExecuteIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onRequestPermission(String[] permmissions) {
        ActivityCompat.requestPermissions(this, permmissions, CODE_REQUEST_PERMISSION_LOCATION);
        return;
    }

    /**
     * Validación de permisos
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // -- TODO PODRÍA REINVOCARSE DESDE AQUÍ LA BÚSQUEDA DEL TIEMPO
                return;
            }
        }
    }


    @Override
    public void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int stringCodeError) {
        Snackbar.make(this.container, getString(stringCodeError), Snackbar.LENGTH_LONG).show();
    }


    @OnClick(R.id.alarma1)
    public void onViewAlarmClicked(View view) {
        if (this.alarma1.isChecked()) {
            this.updateStatusAlarm1ViewItem(false);
            this.weatherAlarmConfiguration.registerAlarm(AlarmEnum.ALARM1);
        } else {
            this.updateStatusAlarm1ViewItem(true);
            this.weatherAlarmConfiguration.unregisterAlarm(AlarmEnum.ALARM1);
        }
    }

    /**
     * Actualiza el estado de los componenentes de la alarma 1
     *
     * @param enabled
     */
    private void updateStatusAlarm1ViewItem(final boolean enabled) {
        if (enabled) {
            this.timeAlarm1.setEnabled(true);
            this.saveTimeAlarm1.setVisibility(View.VISIBLE);
        } else {
            this.timeAlarm1.setEnabled(false);
            this.saveTimeAlarm1.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.saveTimeAlarm1)
    public void onSaveTimeAlarm1ViewClicked() {
        if (!this.alarma1.isChecked()) {
            this.weatherAlarmConfiguration.saveTimeAlarm(AlarmEnum.ALARM1, this.timeAlarm1.getText().toString());
            Snackbar.make(this.container, getString(R.string.weather_alarm_time_save_ok), Snackbar.LENGTH_LONG).show();
            this.timeAlarm1.clearFocus();
        } else {
            Snackbar.make(this.container, getString(R.string.weather_alarm_time_error_save_disabled_required), Snackbar.LENGTH_LONG).show();
        }

    }

/*
    @OnClick(R.id.alarma1)
    public void onViewAlarmClicked(View view) {

        this.weatherConfiguration.updateStatusAlarm(AlarmEnum.ALARM1,this.alarma1.isChecked());


        Intent alarm = new Intent(this.getApplicationContext(), MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1001, alarm, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (this.alarma1.isChecked()){
            boolean alarmRunning = (PendingIntent.getBroadcast(this.getApplicationContext(), 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
            if (!alarmRunning) {
                // Set the alarm to start at 8:30 a.m.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 55);



                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 1800000, pendingIntent);
            }

        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }


    }*/

}
