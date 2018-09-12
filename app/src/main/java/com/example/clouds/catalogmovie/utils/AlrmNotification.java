package com.example.clouds.catalogmovie.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;

import java.util.Calendar;

public class AlrmNotification {

    public static void set(AlarmManager alarmManager, Calendar calendar, int timeIncrement, PendingIntent intent) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + timeIncrement,
                    AlarmManager.INTERVAL_DAY,
                    intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + timeIncrement, intent);
        }
    }

}