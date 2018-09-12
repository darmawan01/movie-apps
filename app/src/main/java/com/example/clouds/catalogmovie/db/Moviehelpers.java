package com.example.clouds.catalogmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.clouds.catalogmovie.models.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.clouds.catalogmovie.db.DatabaseContract.MovieColumns.*;
import static com.example.clouds.catalogmovie.db.DatabaseContract.TABLE_NAME;

public class Moviehelpers {
    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public Moviehelpers(Context context){
        this.context = context;
    }

    public Moviehelpers open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Movie> query(){
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null,MOVIE_ID +" DESC"
                ,null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount()>0) {
            do {

                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setPopularity(cursor.getFloat(cursor.getColumnIndexOrThrow(POPULARITY)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
                movie.setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(RATEING)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Boolean getMovie(String id){
        Cursor cursor = database
                .query(DATABASE_TABLE,null,
                        MOVIE_ID+" LIKE ?",
                        new String[]{id},
                        null,
                        null,
                        MOVIE_ID + " ASC",
                        null);

        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.close();

            return true;
        }
        return false;
    }

    public long insert(Movie movie){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(POPULARITY, movie.getPopularity());
        initialValues.put(RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(POSTER_PATH, movie.getPosterPath());
        initialValues.put(BACKDROP_PATH, movie.getBackdropPath());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(MOVIE_ID, movie.getId());
        initialValues.put(RATEING, movie.getVoteAverage());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Movie movie){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(POPULARITY, movie.getPopularity());
        initialValues.put(RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(POSTER_PATH, movie.getPosterPath());
        initialValues.put(BACKDROP_PATH, movie.getBackdropPath());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(MOVIE_ID, movie.getId());
        initialValues.put(RATEING, movie.getVoteAverage());
        return database.update(DATABASE_TABLE, initialValues, MOVIE_ID + "= '" + movie.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_NAME, MOVIE_ID + " = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,MOVIE_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,MOVIE_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,MOVIE_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,MOVIE_ID + " = ?", new String[]{id});
    }
}
