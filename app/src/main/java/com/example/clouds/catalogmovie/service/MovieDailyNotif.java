package com.example.clouds.catalogmovie.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.clouds.catalogmovie.BuildConfig;
import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.utils.AlrmNotification;
import com.example.clouds.catalogmovie.utils.NotificationBuilder;

import java.util.Calendar;

public class MovieDailyNotif extends BroadcastReceiver {
    private static int notificationId = Integer.valueOf(BuildConfig.NOTIFICATION_ID);
    private static int DAILY_HOUR = 7;

    @Override public void onReceive(Context context, Intent intent) {
        NotificationBuilder.sendNotification(context, context.getString(R.string.say_hello), notificationId);
    }

    public void setDailyAlarm(Context context) {
        cancelDailyAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar timeAlarm = hour(DAILY_HOUR);
        AlrmNotification.set(alarmManager, timeAlarm, 1, getPendingIntent(context));
    }

    public void cancelDailyAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieDailyNotif.class);
        return PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private Calendar hour(int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }
}
