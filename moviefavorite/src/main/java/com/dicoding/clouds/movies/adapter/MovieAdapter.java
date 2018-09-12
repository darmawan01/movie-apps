package com.dicoding.clouds.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.clouds.movies.BuildConfig;
import com.dicoding.clouds.movies.R;
import com.dicoding.clouds.movies.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> list;

    public MovieAdapter( Context context, ArrayList<Movie> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_fav_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvRateing.setText(list.get(position).getVoteAverage().toString());
        Glide.with(context).load(BuildConfig.IMG_URL+list.get(position).getPosterPath()).into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRateing, tvTitle;
        ImageView ivPoster;

        public ViewHolder(View itemView) {
            super(itemView);

            tvRateing = itemView.findViewById(R.id.tv_ratings);
            tvTitle = itemView.findViewById(R.id.v_titles);
            ivPoster = itemView.findViewById(R.id.v_posters);
        }
    }
}
