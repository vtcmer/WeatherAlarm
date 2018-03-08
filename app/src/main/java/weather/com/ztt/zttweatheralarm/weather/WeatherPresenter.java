package weather.com.ztt.zttweatheralarm.weather;

/**
 * Created by vtcmer on 17/12/2017.
 */

public interface WeatherPresenter {


    /**
     * Recuperación de la temperatura actual
     * @param lat
     * @param lon
     */
    void getCurrentWeatherByCoordinates(String lat, String lon,
                           final String units, final String lang,
                           final String apiKey);

    /**
     * Destrucción del presentador
     */
    void destroy();

}
