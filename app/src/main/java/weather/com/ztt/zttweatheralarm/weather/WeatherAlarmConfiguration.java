package weather.com.ztt.zttweatheralarm.weather;

import weather.com.ztt.zttweatheralarm.main.ui.AlarmEnum;

/**
 * Created by vtcmer on 02/01/2018.
 */

public interface WeatherAlarmConfiguration {


    /**
     * Registro de una  alarma
     * @param alarm
     */
    void registerAlarm(final AlarmEnum alarm);

    /**
     * Se desregistra la alarma
     * @param alarmEnum
     */
    void unregisterAlarm(final AlarmEnum alarmEnum);

    /**
     * Recuperación del estado de una alarma
     * @param alarmEnum
     * @return
     */
    boolean getStatusAlarm(final AlarmEnum alarmEnum);

    /**
     * Guardar la hora de ejecución de la alarma
     * @param alarmEnum
     * @param time
     */
    void saveTimeAlarm(final AlarmEnum alarmEnum, final String time);

    /**
     * Recuperar la hora de ejecución de la alarma
     * @param alarmEnum
     * @return
     */
    String getTimeAlarm(final AlarmEnum alarmEnum);
}
