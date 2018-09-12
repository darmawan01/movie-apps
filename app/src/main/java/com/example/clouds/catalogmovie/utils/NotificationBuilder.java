package com.example.clouds.catalogmovie.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.clouds.catalogmovie.BuildConfig;
import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.models.Movie;
import com.example.clouds.catalogmovie.views.activies.BaseActivity;
import com.example.clouds.catalogmovie.views.activies.DetailMovieActivity;

public class NotificationBuilder {

    public static void sendNotification(Context context, String description, int id) {
        Intent intent = new Intent(context, BaseActivity.class);
        notification(context, intent, description, id);
    }

    public static void sendNotification(Context context, String description, int id, Movie movie) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra("movie", movie);
        notification(context, intent, description, id);
    }

    private static void notification(Context context, Intent intent, String description, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.movie_icon_27)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(BuildConfig.NOTIFICATION_ID,
                    "NOTIFY_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorPrimary);

            builder.setChannelId(BuildConfig.NOTIFICATION_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }

}
