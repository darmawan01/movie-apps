package com.example.clouds.catalogmovie.views.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.adapter.MovieAdapter;
import com.example.clouds.catalogmovie.api.ApiBuilder;
import com.example.clouds.catalogmovie.api.ApiRepository;
import com.example.clouds.catalogmovie.models.Movie;
import com.example.clouds.catalogmovie.models.MovieResponse;
import com.example.clouds.catalogmovie.views.activies.DetailMovieActivity;
import com.example.clouds.catalogmovie.views.interfaces.ClickInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComeingClickFragment extends Fragment implements ClickInterface {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private ArrayList<Movie> movieList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_up_comeing_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.rc_movie_up);
        progressBar =  view.findViewById(R.id.progress_up);

        movieAdapter = new MovieAdapter(movieList, getContext(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);

        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.base, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String title) {
                Log.e("Halo", "Halo Guys");
                loadSearchData(title);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String title) {
                if (title.isEmpty()){
                    loadData();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(){
        progressBar.setVisibility(View.VISIBLE);
        ApiRepository api= ApiBuilder.getRetrofitIntance().create(ApiRepository.class);
        Call<MovieResponse> call = api.getUpComMovie();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList.clear();
                movieList.addAll(response.body().movies);
                movieAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Movie Request", "Error: "+ t.getMessage());
            }
        });
    }

    private void loadSearchData(String title){
        progressBar.setVisibility(View.VISIBLE);
        ApiRepository api= ApiBuilder.getRetrofitIntance().create(ApiRepository.class);
        Call<MovieResponse> call = api.searchMovie(title);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList.clear();
                movieList.addAll(response.body().movies);
                movieAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Movie Request", "Error: "+ t.getMessage());
            }
        });
    }

    @Override
    public void click(int posisi) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.MOVIE_LIST, movieList.get(posisi));
        startActivity(intent);
    }

}
