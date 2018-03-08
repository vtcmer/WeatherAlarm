package weather.com.ztt.zttweatheralarm.weather.ui;

import weather.com.ztt.zttweatheralarm.R;

/**
 * Created by vtcmer on 31/12/2017.
 */

public class UtilsWeather {

    public static int getDescriptionWeather(final String weatherCodeString){
        int code = R.string.weather_icon_code_none;
        switch (weatherCodeString) {
            case "01n":
            case "01d":
                code = R.string.weather_icon_code_01d;
                break;
            case "02n":
            case "02d":
                code = R.string.weather_icon_code_02d;
                break;
            case "03n":
            case "03d":
                code = R.string.weather_icon_code_03d;
                break;
            case "04n":
            case "04d":
                code = R.string.weather_icon_code_04d;
                break;
            case "09n":
            case "09d":
                code = R.string.weather_icon_code_09d;
                break;
            case "10n":
            case "10d":
                code = R.string.weather_icon_code_10d;
                break;
            case "11n":
            case "11d":
                code = R.string.weather_icon_code_11d;
                break;
            case "13n":
            case "13d":
                code = R.string.weather_icon_code_13d;
                break;
            case "50n":
            case "50d":
                code = R.string.weather_icon_code_50d;
                break;
            default:
                break;

        }

        return code;

    }
}
