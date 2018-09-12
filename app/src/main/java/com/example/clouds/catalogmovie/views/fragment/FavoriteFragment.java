package com.example.clouds.catalogmovie.views.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.adapter.MovieAdapter;
import com.example.clouds.catalogmovie.db.DatabaseHelper;
import com.example.clouds.catalogmovie.db.Moviehelpers;
import com.example.clouds.catalogmovie.models.Movie;
import com.example.clouds.catalogmovie.views.activies.DetailMovieActivity;
import com.example.clouds.catalogmovie.views.interfaces.ClickInterface;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements ClickInterface, SwipeRefreshLayout.OnRefreshListener{

    private MovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView rvMovieFav;
    private Moviehelpers helper;
    private SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovieFav = view.findViewById(R.id.rc_movie_fav);
        swipe = view.findViewById(R.id.swipe);
        adapter = new MovieAdapter(movies, getContext(), this);
        rvMovieFav.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieFav.setAdapter(adapter);

        helper = new Moviehelpers(getContext());
        helper.open();

        swipe.setOnRefreshListener(this);
        getData();
    }

    private void getData(){
        movies.clear();
        movies.addAll(helper.query());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void click(int posisi) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.MOVIE_LIST, movies.get(posisi));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (swipe.isRefreshing()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData();
                    swipe.setRefreshing(false);
                }
            }, 5000);
        }
    }
}
