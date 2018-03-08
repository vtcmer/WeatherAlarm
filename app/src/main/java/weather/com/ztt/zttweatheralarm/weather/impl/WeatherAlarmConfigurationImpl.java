package weather.com.ztt.zttweatheralarm.weather.impl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

import weather.com.ztt.zttweatheralarm.main.ui.AlarmEnum;
import weather.com.ztt.zttweatheralarm.weather.WeatherAlarmConfiguration;
import weather.com.ztt.zttweatheralarm.weather.WeatherConfiguration;
import weather.com.ztt.zttweatheralarm.weather.ui.WeatherAlarmNotificationVoiceService;

/**
 * Created by vtcmer on 02/01/2018.
 */

public class WeatherAlarmConfigurationImpl implements WeatherAlarmConfiguration {

    private int requestCode = 80000;

    private Context context;
    // Permite recuperar la lógica de la aplicación
    private WeatherConfiguration weatherConfiguration;

    private AlarmManager alarmManager;
    private Intent alarmIntent;

    public WeatherAlarmConfigurationImpl(final Context context, final WeatherConfiguration weatherConfiguration) {
        this.context = context;
        this.weatherConfiguration = weatherConfiguration;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.alarmIntent =  new Intent(this.context, WeatherAlarmNotificationVoiceService.class);
    }

    @Override
    public void registerAlarm(final AlarmEnum alarm){
        this.weatherConfiguration.updateStatusAlarm(AlarmEnum.ALARM1,true);
        int code = this.requestCode + alarm.getValue();
        boolean alarmRunning = (PendingIntent.getService(this.context, code, this.alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {

            String time = this.getTimeAlarm(alarm);
            String[] timeInfo = time.split(":");
            int alarmHour = Integer.valueOf(timeInfo[0]);
            int alarmMinute = Integer.valueOf(timeInfo[1]);

            Calendar currentDay = Calendar.getInstance();
            int currentHour = currentDay.get(Calendar.HOUR_OF_DAY);
            int currentMinute = currentDay.get(Calendar.MINUTE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            if (currentHour > alarmHour ){
                calendar.add(Calendar.DAY_OF_MONTH,1);
            } else if (currentMinute >= alarmMinute ){
                calendar.add(Calendar.DAY_OF_MONTH,1);
            }
            calendar.set(Calendar.HOUR_OF_DAY,alarmHour);
            calendar.set(Calendar.MINUTE, alarmMinute);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(alarm));
            //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 50000, getPendingIntent(alarm));
        }

    }

    @Override
    public void unregisterAlarm(final AlarmEnum alarmEnum){
        this.weatherConfiguration.updateStatusAlarm(AlarmEnum.ALARM1,false);
        PendingIntent pendingIntent = this.getPendingIntent(alarmEnum);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

    }

    @Override
    public boolean getStatusAlarm(AlarmEnum alarmEnum) {
        return this.weatherConfiguration.getStatusAlarm(alarmEnum);
    }

    @Override
    public void saveTimeAlarm(AlarmEnum alarmEnum, String time) {
        this.weatherConfiguration.updateTimeAlarm(alarmEnum,time);
    }

    @Override
    public String getTimeAlarm(AlarmEnum alarmEnum) {
        return this.weatherConfiguration.getTimeAlarm(alarmEnum);
    }

    /**
     * Recuperacion del  pending Intent
     * @param alarm
     * @return
     */
    private PendingIntent getPendingIntent(AlarmEnum alarm){
        int code = this.requestCode + alarm.getValue();
        PendingIntent pendingIntent = PendingIntent.getService(this.context, code, this.alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;

    }
}
