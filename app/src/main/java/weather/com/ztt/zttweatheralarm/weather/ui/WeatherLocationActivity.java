package weather.com.ztt.zttweatheralarm.weather.ui;

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import weather.com.ztt.zttweatheralarm.WeatherAlarmApplication;
import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherComponent;
import weather.com.ztt.zttweatheralarm.weather.di.WeatherConfigurationComponent;
import weather.com.ztt.zttweatheralarm.weather.entities.WeatherLocation;


/**
 * Created by vtcmer on 25/12/2017.
 */

public abstract class WeatherLocationActivity extends AppCompatActivity implements LocationListener{

    private static final String TAG = "WeatherLocationActivity";

    private final static int CODE_REQUEST_PERMISSION_LOCATION = 1000;

    private  LocationManager locationManager;

    protected WeatherAlarmApplication weatherAlarmApplication;

    /**
     * Encapsula la lógica de configuración
    */
    WeatherConfiguration weatherConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.weatherAlarmApplication = (WeatherAlarmApplication) this.getApplication();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        this.setupInyection();

    }

    /**
     * Configuración de la inyección de dependencias
     */
    private void setupInyection() {
         WeatherConfigurationComponent component = this.weatherAlarmApplication.getWeatherConfiguration();
        this.weatherConfiguration = component.getWeatherConfiguration();

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.startFindLocation();
    }


    /**
     * Inicia la carga de la ubicación
     */
    protected void startFindLocation() {

        this.showFindLocationProgressBar();
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gpsEnabled){
            this.findPositionByGPS();
        } else {
            WeatherLocation location = this.weatherConfiguration.getLocation();
            if (location != null){
                this.findWeatherByCoordinates(location);
            } else {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(settingsIntent);
            }
        }

    }

    /**
     * Búsqueda de la posición a partir del GPS
     */
    protected void findPositionByGPS(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, CODE_REQUEST_PERMISSION_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    /**
     * Para el listener de la ubicación
     */
    protected void stopFindLocation(){
        locationManager.removeUpdates(this);
    }

    /**
     * Validación de permisos
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startFindLocation();
                return;
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        this.hideFindLocationProgressBar();
        this.stopFindLocation();
        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
            WeatherLocation weatherLocation = new WeatherLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            this.weatherConfiguration.updateLocation(weatherLocation);
            this.findWeatherByCoordinates(weatherLocation);
        }

    }

    @Override
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
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "onProviderEnabled --> "+provider);
    }



    /**
     * Realiza la búsqueda por localización
     * @param location
     */
    protected abstract void findWeatherByCoordinates(WeatherLocation location);

    /**
     * Carga el spinner de búsqueda
     */
    protected abstract void showFindLocationProgressBar();

    /**
     * Oculta el spinner de búsqueda
     */
    protected abstract void hideFindLocationProgressBar();


}
