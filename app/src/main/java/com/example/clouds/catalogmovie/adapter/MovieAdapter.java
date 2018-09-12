package com.example.clouds.catalogmovie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.clouds.catalogmovie.BuildConfig;
import com.example.clouds.catalogmovie.R;
import com.example.clouds.catalogmovie.models.Movie;
import com.example.clouds.catalogmovie.views.interfaces.ClickInterface;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Movie> listMovie;
    private Context context;
    private ClickInterface clickInterface;

    public MovieAdapter(ArrayList<Movie> listMovie, Context context, ClickInterface clickInterface) {
        this.listMovie = listMovie;
        this.context = context;
        this.clickInterface = clickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(listMovie.get(position), clickInterface);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvRating, tvPopularity;
        public ImageView vImage;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.v_title);
            vImage = itemView.findViewById(R.id.v_poster);
            tvRating = itemView.findViewById(R.id.tv_rating);
            view = itemView;
        }

        public void bindItem(Movie movie, final ClickInterface clickInterface){

            tvTitle.setText(movie.getTitle());
            tvRating.setText(movie.getVoteAverage().toString());
            Glide.with(context)
                    .load(BuildConfig.IMG_URL+movie.getPosterPath()).into(vImage);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.click(getPosition());
                }
            });
        }

    }
}
