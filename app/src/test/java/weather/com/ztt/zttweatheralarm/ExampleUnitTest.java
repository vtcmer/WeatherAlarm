package weather.com.ztt.zttweatheralarm;

import android.util.Log;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);


        long milliSeconds = 1514823896 ;
        Date date = new Date(milliSeconds * 1000L);
        DateFormat gmt = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        gmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(gmt.format(date));


    }
}