package com.krinotech.popularmoviesstageone;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.popularmoviesstageone.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public List<Movie> movies;
    private OnClickMovieHandler clickHandler;

    @Override
    public int getItemCount() {
        if(movies == null) return 0;
        return movies.size();
    }

    public interface OnClickMovieHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movies, OnClickMovieHandler clickHandler) {
        this.movies = movies;
        this.clickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView movieListItemImageView;

        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            movieListItemImageView = itemView.findViewById(R.id.iv_movie_list_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie movie = movies.get(getAdapterPosition());

            clickHandler.onClick(movie);
        }

    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Movie movie = movies.get(position);

        Picasso.get()
                .load(movie.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.movieListItemImageView);
    }
}
