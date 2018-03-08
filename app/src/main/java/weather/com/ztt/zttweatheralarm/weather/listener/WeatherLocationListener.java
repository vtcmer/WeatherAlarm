package weather.com.ztt.zttweatheralarm.weather.listener;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.callback.WeatherLocationCallback;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherLocation;

/**
 * Created by vtcmer on 25/12/2017.
 */

public class WeatherLocationListener implements LocationListener {


    private static final String TAG = "WeatherLocationListener";
    private Context context;
    private WeatherLocationCallback weatherLocationCallback;
    private LocationManager locationManager;
    private WeatherConfiguration weatherConfiguration;

    public WeatherLocationListener(final Context context, final WeatherConfiguration weatherConfiguration,
                                   final WeatherLocationCallback weatherLocationCallback) {
        this.context = context;
        this.weatherConfiguration = weatherConfiguration;
        this.weatherLocationCallback = weatherLocationCallback;
        this.locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
    }


    /**
     * Búsqueda de la posición a partir del GPS
     */
    public void searchPositionByGPS(){

        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODE_REQUEST_PERMISSION_LOCATION);
            this.weatherLocationCallback.onRequestPermission( new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            return;
        }
        if (!this.isEnabledGPS()){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.weatherLocationCallback.onProviderDisabledExecuteIntent(intent);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }



    /**
     * Validación si está activo el GPS
     * @return
     */
    public boolean isEnabledGPS(){
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Inicia la carga de la ubicación
     */
    public void startSearchLocation() {


        final boolean gpsEnabled = this.isEnabledGPS();

        if (gpsEnabled){
            this.searchPositionByGPS();
        } else {
            WeatherLocation location = this.weatherConfiguration.getLocation();
            if (location != null){
                this.weatherLocationCallback.onSearchLocationComplete(location);
            } else {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                this.weatherLocationCallback.onProviderDisabledExecuteIntent(settingsIntent);
            }
        }

    }

    /**
     * Para el listener de la ubicación
     */
    public void stopSearchLocation(){
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.stopSearchLocation();
        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
            WeatherLocation weatherLocation = new WeatherLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            this.weatherConfiguration.updateLocation(weatherLocation);
            this.weatherLocationCallback.onSearchLocationComplete(weatherLocation);
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.i(TAG, "onStatusChanged --> debug LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.i(TAG, "onStatusChanged --> debug LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.i(TAG, "onStatusChanged --> debug LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado
        Log.i(TAG, "onProviderDisabled --> "+provider);
        //Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //this.weatherLocationCallback.onProviderDisabledExecuteIntent(settingsIntent);
        // startActivity(settingsIntent);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "onProviderEnabled --> "+provider);
    }


}
