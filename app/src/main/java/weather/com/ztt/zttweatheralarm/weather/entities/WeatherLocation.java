package weather.com.ztt.zttweatheralarm.weather.entities;

/**
 * Created by vtcmer on 27/12/2017.
 */

public class WeatherLocation {

    private String latitude;
    private String longitude;

    public WeatherLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
