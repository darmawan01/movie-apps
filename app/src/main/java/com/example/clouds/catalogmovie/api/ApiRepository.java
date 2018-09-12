package com.example.clouds.catalogmovie.api;

import com.example.clouds.catalogmovie.models.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRepository {
    @GET("3/search/movie?api_key=a8370bf2cba7a6ff3c81580ce6bb0257&language=en-US")
    Call<MovieResponse> searchMovie(@Query("query") String query);

    @GET("3/movie/now_playing?api_key=a8370bf2cba7a6ff3c81580ce6bb0257&language=en-US")
    Call<MovieResponse> getOnPlayMovie();

    @GET("3/movie/upcoming?api_key=a8370bf2cba7a6ff3c81580ce6bb0257&language=en-US")
    Call<MovieResponse> getUpComMovie();
}
