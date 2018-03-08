package weather.com.ztt.zttweatheralarm.weather.impl;

import android.content.Context;
import android.content.SharedPreferences;

import weather.com.ztt.zttweatheralarm.main.ui.AlarmEnum;
import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherLocation;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherParamConfiguration;

/**
 * Created by vtcmer on 27/12/2017.
 */

public class WeatherConfigurationSharedPreferences implements WeatherConfiguration {

    private static final String PREFERENCES_WEATHER_CONFIG_KEY = "prefWeatherConfig";
    private static final String PARAM_LOCATION_LATITUDE = "latitude";
    private static final String PARAM_LOCATION_LONGITUDE = "longitude";
    private static final String PARAM_UNITS = "units";
    private static final String PARAM_LANGUAGE = "lang";
    private static final String PARAM_ALARM_STATUS_PREFIX = "statusAlarm";
    private static final String PARAM_ALARM_TIME_PREFIX = "timeAlarm";

    private Context context;
    private SharedPreferences sharedPreferences;


    public WeatherConfigurationSharedPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_WEATHER_CONFIG_KEY,Context.MODE_PRIVATE);
    }

    @Override
    public WeatherLocation getLocation() {

        String lat = this.sharedPreferences.getString(PARAM_LOCATION_LATITUDE,null);
        String lon = this.sharedPreferences.getString(PARAM_LOCATION_LONGITUDE,null);
        WeatherLocation location = null;
        if ((lat != null) && (lon != null)){
            location = new WeatherLocation(lat,lon);
        }
        return location;
    }

    @Override
    public void updateLocation(WeatherLocation location){

        if ((location != null)&&(location.getLatitude() != null) && (location.getLongitude() != null)){
            SharedPreferences.Editor edit = this.sharedPreferences.edit();
            edit.putString(PARAM_LOCATION_LATITUDE,location.getLatitude());
            edit.putString(PARAM_LOCATION_LONGITUDE,location.getLongitude());
            edit.commit();
        }

    }

    @Override
    public WeatherParamConfiguration getWeatherParamConfiguration() {
        WeatherParamConfiguration weatherParamConfiguration = new WeatherParamConfiguration();

        String units = this.sharedPreferences.getString(PARAM_UNITS,null);
        String lang = this.sharedPreferences.getString(PARAM_LANGUAGE,null);
        if ((units != null) && (lang != null)){
            weatherParamConfiguration.setUnits(units);
            weatherParamConfiguration.setLang(lang);
        } else{
            // -- Se actualiza con los valores por defecto
            this.updateWeatherParamConfiguration(weatherParamConfiguration);
        }

        return weatherParamConfiguration;
    }

    @Override
    public void updateWeatherParamConfiguration(final WeatherParamConfiguration weatherParamConfiguration){
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putString(PARAM_UNITS,weatherParamConfiguration.getUnits());
        edit.putString(PARAM_LANGUAGE,weatherParamConfiguration.getLang());
        edit.commit();
    }

    @Override
    public void updateStatusAlarm(AlarmEnum alarm, boolean value) {
        String key = PARAM_ALARM_STATUS_PREFIX+alarm.getValue();
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    @Override
    public boolean getStatusAlarm(AlarmEnum alarm) {
        String key = PARAM_ALARM_STATUS_PREFIX+alarm.getValue();
        return this.sharedPreferences.getBoolean(key,false);
    }

    @Override
    public void updateTimeAlarm(AlarmEnum alarm, final String time) {
        String key = PARAM_ALARM_TIME_PREFIX+alarm.getValue();
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putString(key,time);
        edit.commit();
    }

    @Override
    public String getTimeAlarm(AlarmEnum alarm) {
        String key = PARAM_ALARM_TIME_PREFIX+alarm.getValue();
        return this.sharedPreferences.getString(key,null);
    }
}
