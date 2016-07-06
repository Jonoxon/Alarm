package jono.bedheadalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

/**
 * Created by Jono on 6/07/2016.
 * I GOT NO CLUE
 */

public class AlarmTrigger {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    // i think i need something like this but iz dunno
    //public CustomAdapter (final Context context, final int ItemLayout) {super(context, 0); this.ItemLayout = ItemLayout;}

    public void TimeCreate (int hour, int min, int day){
        //idk why these context and AlarmReceiver things are red :(
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // custom alarm params.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);

        // set inexact repeating alarm with interval of 7 days
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY*7, alarmIntent);
    }


}
