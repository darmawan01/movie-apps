package com.example.clouds.catalogmovie.views.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.clouds.catalogmovie.BuildConfig;
import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.db.Moviehelpers;
import com.example.clouds.catalogmovie.models.Movie;

import java.util.ArrayList;

public class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Movie> movies = new ArrayList<>();
    private Context mContext;
    private Moviehelpers moviehelpers;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {

        moviehelpers = new Moviehelpers(mContext);
        moviehelpers.open();

        movies.addAll(moviehelpers.query());

    }


    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
//        Log.i("Data Widget", "Data: "+movies);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.IMG_URL+movies.get(position).getPosterPath())
                    .submit()
                    .get();

            rv.setImageViewBitmap(R.id.imageViewWidget, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        extras.putParcelableArrayList(FavoriteWidget.EXTRA_ITEM, movies);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
