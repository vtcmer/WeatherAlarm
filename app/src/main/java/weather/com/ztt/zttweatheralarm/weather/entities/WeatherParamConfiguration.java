package weather.com.ztt.zttweatheralarm.weather.entities;

/**
 * Created by vtcmer on 29/12/2017.
 */

public class WeatherParamConfiguration {

    private String units = "metric";
    private String lang = "ES_ES";

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
