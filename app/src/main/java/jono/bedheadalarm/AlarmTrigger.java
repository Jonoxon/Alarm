package jono.bedheadalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jono on 6/07/2016.
 * I GOT NO CLUE
 */

public class AlarmTrigger {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    // i think i need something like this but iz dunno
    //public CustomAdapter (final Context context, final int ItemLayout) {super(context, 0); this.ItemLayout = ItemLayout;}
/* this code is quite messed... help pls
    public void TimeCreate (int hour, int min, int daysofweek){
        //idk why these context and AlarmReceiver things are red :(
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        for (Integer day : GetDays(daysofweek)) {

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



    public int getNextDay(int days) {
        Calendar calendar = Calendar.getInstance();
        //goes to the day after the current one
        int checkday = calendar.get(Calendar.DAY_OF_WEEK)+1;

        //need something to check it with?
        int nextday = checkday;

        while ((nextday & Days.checkday) == 0) {
            // somehow this works
            checkday += 1;
        }
        return checkday;
    }
*/
    public List<Integer> GetDays (int days) {
        final List<Integer> daysofweek = new ArrayList<>();
        if ((days & Days.SUNDAY) != 0) {
            daysofweek.add(1);
        }
        if ((days & Days.MONDAY) != 0) {
            daysofweek.add(2);
        }
        if ((days & Days.TUESDAY) != 0) {
            daysofweek.add(3);
        }
        if ((days & Days.WEDNESDAY) != 0) {
            daysofweek.add(4);
        }
        if ((days & Days.THURSDAY) != 0) {
            daysofweek.add(5);
        }
        if ((days & Days.FRIDAY) != 0) {
            daysofweek.add(6);
        }
        if ((days & Days.SATURDAY) != 0) {
            daysofweek.add(7);
        }
            return daysofweek;
    }
}
