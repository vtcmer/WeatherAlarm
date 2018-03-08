package weather.com.ztt.zttweatheralarm.weather.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.inject.Inject;

import weather.com.ztt.zttweatheralarm.weather.entities.WeatherDay;

/**
 * Created by vtcmer on 16/12/2017.
 */

public class WeatherDayJsonDeserializer implements JsonDeserializer<WeatherDay> {


    @Override
    public WeatherDay deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        WeatherDay weatherDay = new WeatherDay();
        weatherDay.setCity(json.getAsJsonObject().get("name").getAsString());
        weatherDay.setTemp(json.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsInt());
        weatherDay.setMinTemp(json.getAsJsonObject().get("main").getAsJsonObject().get("temp_min").getAsInt());
        weatherDay.setMaxTemp(json.getAsJsonObject().get("main").getAsJsonObject().get("temp_max").getAsInt());
        weatherDay.setHumidity(json.getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt());
        weatherDay.setPressure(json.getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsInt());
        JsonArray weather = json.getAsJsonObject().get("weather").getAsJsonArray();
        if (weather.size() > 0){
            JsonElement item = weather.get(0);
            weatherDay.setWeather(item.getAsJsonObject().get("main").getAsString());
            weatherDay.setWeatherCode(item.getAsJsonObject().get("icon").getAsString());
        }

        long dt = json.getAsJsonObject().get("dt").getAsLong();
        Date date = new Date(dt * 1000L);
        weatherDay.setLastUpdateDate(date);

        return weatherDay;
    }
}
