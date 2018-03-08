package weather.com.ztt.zttweatheralarm.main.ui;

/**
 * Created by vtcmer on 02/01/2018.
 */

public enum AlarmEnum {

    ALARM1(1);

     AlarmEnum(int numberAlarm){
        this.numberAlarm = numberAlarm;
    }

    int numberAlarm;

    public int getValue() {
        return numberAlarm;
    }
}
