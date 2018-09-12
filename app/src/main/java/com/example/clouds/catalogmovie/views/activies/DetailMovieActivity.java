package com.example.clouds.catalogmovie.views.activies;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.clouds.catalogmovie.BuildConfig;
import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.db.Moviehelpers;
import com.example.clouds.catalogmovie.models.Movie;
import com.example.clouds.catalogmovie.utils.Ext;

import static com.example.clouds.catalogmovie.db.DatabaseContract.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String MOVIE_LIST= "movie_list";
    private Movie movie;
    private TextView overview, title, releaseDate, rating, popularity;
    private ImageView vPoster, vPoster2;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Moviehelpers moviehelpers;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        overview = findViewById(R.id.tv_overview);
        vPoster = findViewById(R.id.v_poster_dtl);
        title = findViewById(R.id.tv_title_dtl);
        releaseDate = findViewById(R.id.tv_release_dtl);
        rating = findViewById(R.id.tv_rating_dtl);
        vPoster2 = findViewById(R.id.v_dtl_poster);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        popularity = findViewById(R.id.tv_dtl_popularity);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        movie = getIntent().getParcelableExtra(MOVIE_LIST);
        overview.setText(movie.getOverview());
        title.setText(movie.getOriginalTitle());
        popularity.setText("Popularity: "+movie.getPopularity().toString());
        releaseDate.setText(Ext.getDateFormat("EEE, d MMM yyyy","yyyyy-mm-dd",movie.getReleaseDate()));
        rating.setText("Rateing: "+movie.getVoteAverage().toString());
        Glide.with(this).load(BuildConfig.IMG_URL_BACDROP+movie.getBackdropPath()).into(vPoster);
        Glide.with(this).load(BuildConfig.IMG_URL+movie.getPosterPath()).into(vPoster2);

        collapsingToolbarLayout.setTitle("");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(this);

        moviehelpers = new Moviehelpers(this);
        moviehelpers.open();
        setFavMark();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                Boolean isFav = moviehelpers.getMovie(movie.getId().toString());
                if (!isFav){
                    moviehelpers.insert(movie);
                    Toast.makeText(this, "Movie Added To Favorite", Toast.LENGTH_SHORT).show();
                    setFavMark();
                }else{
                    moviehelpers.delete(movie.getId());
                    Toast.makeText(this, "Movie Removed From Favorite", Toast.LENGTH_SHORT).show();
                    setFavMark();
                }
                break;
        }
    }

    private void setFavMark(){
        Boolean isFav = moviehelpers.getMovie(movie.getId().toString());
        if (isFav){
            fab.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp));
        }else{
            fab.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moviehelpers.close();
    }
}
