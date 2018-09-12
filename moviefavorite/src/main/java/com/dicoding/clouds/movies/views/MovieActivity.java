package com.dicoding.clouds.movies.views;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.dicoding.clouds.movies.R;
import com.dicoding.clouds.movies.adapter.MovieAdapter;
import com.dicoding.clouds.movies.models.Movie;

import java.util.ArrayList;

import static com.dicoding.clouds.movies.db.DatabaseContract.CONTENT_URI;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>{

    private ProgressBar progressBar;
    private RecyclerView rvFavMovie;
    private MovieAdapter favAdapter;
    protected ArrayList<Movie> listMovies = new ArrayList<>();

    private final int LOAD_MOVIE_FAV = 110;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progress);
        rvFavMovie = findViewById(R.id.rc_favorite);

        favAdapter = new MovieAdapter(this, listMovies);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(this));
        rvFavMovie.setAdapter(favAdapter);

        getSupportLoaderManager().initLoader(LOAD_MOVIE_FAV, null, this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIE_FAV, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<Movie> getmovies = new ArrayList<>();

        if (data != null && data.getCount() > 0){
            while (data.moveToNext()){
                Movie movie = new Movie(data);
                getmovies.add(movie);
            }
        }

        listMovies.addAll(getmovies);
        progressBar.setVisibility(View.INVISIBLE);
        favAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
