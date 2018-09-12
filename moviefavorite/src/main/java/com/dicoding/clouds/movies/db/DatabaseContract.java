package com.dicoding.clouds.movies.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.clouds.catalogmovie";
    public static String TABLE_NAME = "movie";
    public static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {

        public static String MOVIE_ID = "movie_id";
        public static String TITLE = "title";
        public static String POPULARITY = "popularity";
        public static String POSTER_PATH = "poster_path";
        public static String BACKDROP_PATH = "backdrop_path";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String RATEING = "vote_average";
    }
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
