package com.dicoding.clouds.movies.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dicoding.clouds.movies.db.DatabaseContract;

import static com.dicoding.clouds.movies.db.DatabaseContract.getColumnFloat;
import static com.dicoding.clouds.movies.db.DatabaseContract.getColumnInt;
import static com.dicoding.clouds.movies.db.DatabaseContract.getColumnString;

public class Movie implements Parcelable {

    public Integer id;
    public Float voteAverage;
    public String title;
    public Float popularity;
    public String posterPath;
    public String backdropPath;
    public String overview;
    public String releaseDate;

    public Movie(){

    }


    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.MovieColumns.MOVIE_ID);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.popularity = getColumnFloat(cursor, DatabaseContract.MovieColumns.POPULARITY);
        this.voteAverage = getColumnFloat(cursor, DatabaseContract.MovieColumns.RATEING);
        this.posterPath = getColumnString(cursor, DatabaseContract.MovieColumns.POSTER_PATH);
        this.backdropPath = getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP_PATH);
        this.releaseDate = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
    }

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readFloat();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readFloat();
        }
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(voteAverage);
        }
        dest.writeString(title);
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(popularity);
        }
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }
}
