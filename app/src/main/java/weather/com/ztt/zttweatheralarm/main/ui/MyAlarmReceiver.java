package weather.com.ztt.zttweatheralarm.main.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import weather.com.ztt.zttweatheralarm.weather.ui.WeatherAlarmNotificationVoiceService;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        Intent background = new Intent(context, WeatherAlarmNotificationVoiceService.class);
        context.startService(background);
        */
    }
}
