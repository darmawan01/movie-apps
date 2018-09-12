package com.example.clouds.catalogmovie.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.models.Movie;
import com.example.clouds.catalogmovie.utils.AlrmNotification;
import com.example.clouds.catalogmovie.utils.NotificationBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class MovieNowPlayingNotif extends BroadcastReceiver{

    private static int notificationId = 2045;
    private static int UPCOMING_HOUR = 8;

    @Override public void onReceive(Context context, Intent intent) {
        Movie movie = new Gson().fromJson(intent.getStringExtra("movie"), Movie.class);
        NotificationBuilder.sendNotification(context, context.getString(R.string.notif_upcomeing, movie.getTitle()), notificationId, movie);
    }

    public void setUpcomingAlarm(Context context, ArrayList<Movie> movies) {
        int notificationDelay = 0;
        Calendar timeAlarm = hour(UPCOMING_HOUR);
        for (Movie movie: movies) {
            cancelUpcomingAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieNowPlayingNotif.class);
            intent.putExtra("movie", new Gson().toJson(movie));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlrmNotification.set(alarmManager, timeAlarm, notificationDelay, pendingIntent);
            notificationId++;
            notificationDelay += 5000;
        }
    }

    public void cancelUpcomingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieNowPlayingNotif.class);
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
