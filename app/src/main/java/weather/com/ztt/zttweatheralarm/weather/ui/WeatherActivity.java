package weather.com.ztt.zttweatheralarm.weather.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import weather.com.ztt.zttweatheralarm.weather.listener.WeatherLocationListener;

public class WeatherActivity extends AppCompatActivity
        implements WeatherLocationCallback, WeatherView, SwipeRefreshLayout.OnRefreshListener
        , OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private final static int CODE_REQUEST_PERMISSION_LOCATION = 1000;
    private final static int CODE_REQUEST_PERMISSION_LOCATION_MAPS = 2000;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.minTemp)
    TextView minTemp;
    @BindView(R.id.maxTemp)
    TextView maxTemp;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.preassure)
    TextView preassure;
    @BindView(R.id.weather)
    TextView weather;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.latitude)
    TextView latitude;
    @BindView(R.id.longitude)
    TextView longitude;
    @BindView(R.id.lastUpdate)
    TextView lastUpdate;


    // Contexto de la aplicación
    private WeatherAlarmApplication weatherAlarmApplication;
    // Listener específico para localizar la aplicación
    private WeatherLocationListener weatherLocationListener;
    // Permite recuperar la lógica de la aplicación
    private WeatherConfiguration weatherConfiguration;
    /**
     * Presentador para la recuperación del tiempo
     */
    WeatherPresenter weatherPresenter;

    // ZttTextToSpeach speach;


    // Renderización de Map
    private GoogleMap map;
    // Marcas del Map
    //private HashMap<Marker, WeatherDay> markers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        setupToolbar();
        setupInyection();
        setupMap();

        this.swiperefresh.setOnRefreshListener(this);




    }

    @Override
    protected void onResume() {
        super.onResume();
        this.showProgressBar();
        this.weatherLocationListener.startSearchLocation();
    }

    /**
     * Inyección de dependencias
     */
    private void setupInyection() {

        this.weatherAlarmApplication = (WeatherAlarmApplication) this.getApplication();

        WeatherComponent component = this.weatherAlarmApplication.getWeatherComponent(this);
        this.weatherPresenter = component.getWeatherPresenter();

        WeatherConfigurationComponent weatherConfiguration = this.weatherAlarmApplication.getWeatherConfiguration();
        this.weatherConfiguration = weatherConfiguration.getWeatherConfiguration();

        // this.speach = new ZttTextToSpeach(this);

        this.weatherLocationListener = new WeatherLocationListener(this, this.weatherConfiguration, this);

    }

    /**
     * Configuración del Maps
     */
    private void setupMap(){
       // markers = new HashMap<Marker, WeatherDay>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean result = false;
        switch (item.getItemId()) {
            case R.id.action_location:
                this.showProgressBar();
                this.weatherLocationListener.searchPositionByGPS();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }

        return result;
    }

    /**
     * Inicialización del toolbar
     */
    private void setupToolbar() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onDestroy() {
        this.weatherLocationListener.stopSearchLocation();
        this.weatherPresenter.destroy();
        super.onDestroy();
    }


    @Override
    public void showProgressBar() {
        this.swiperefresh.setRefreshing(true);

    }

    @Override
    public void hideProgressBar() {
        this.swiperefresh.setRefreshing(false);
    }

    @Override
    public void showError(int stringCodeError) {
        Snackbar.make(this.container, getString(stringCodeError), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void renderWeather(final WeatherDay weatherDay) {

        this.city.setText(weatherDay.getCity());
        this.latitude.setText(String.valueOf(weatherDay.getLatitude()));
        this.longitude.setText(String.valueOf(weatherDay.getLongitude()));
        this.temp.setText(String.valueOf(weatherDay.getTemp()));
        this.minTemp.setText(String.valueOf(weatherDay.getMinTemp()));
        this.maxTemp.setText(String.valueOf(weatherDay.getMaxTemp()));
        this.humidity.setText(String.valueOf(weatherDay.getHumidity()));
        this.preassure.setText(String.valueOf(weatherDay.getPressure()));
        String weatherDescription = this.getString(UtilsWeather.getDescriptionWeather(weatherDay.getWeatherCode()));
        this.weather.setText(weatherDescription);

        if (weatherDay.getLastUpdateDate() != null) {
            DateFormat gmt = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
            gmt.setTimeZone(TimeZone.getTimeZone("GMT"));
            this.lastUpdate.setText(gmt.format(weatherDay.getLastUpdateDate()));
        }


        LatLng location = new LatLng(weatherDay.getLatitude(), weatherDay.getLongitude());
        map.addMarker(new MarkerOptions().position(location));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 6));
        //markers.put(marker, weatherDay);

        //final String msg =  String.format(getString(R.string.weather_info_voice), new String[]{weatherDay.getCity(),String.valueOf(weatherDay.getTemp()),weatherDescription});
        //String txt = "La temperatura en " + weatherDay.getCity() + " es de " + weatherDay.getTemp() + " grados.";

        //speach.speak(msg);


    }


    @Override
    public void onRefresh() {
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
    public void onProviderDisabledExecuteIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onRequestPermission(String[] permmissions) {
        ActivityCompat.requestPermissions(this, permmissions, CODE_REQUEST_PERMISSION_LOCATION);
        return;
    }

    /**
     * Validación de los permmisos
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.showProgressBar();
                this.weatherLocationListener.startSearchLocation();
                return;
            }
        } else  if (requestCode == CODE_REQUEST_PERMISSION_LOCATION_MAPS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            return;
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setInfoWindowAdapter(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, CODE_REQUEST_PERMISSION_LOCATION_MAPS);

        }else {
            map.setMyLocationEnabled(true);
        }
    }
}
