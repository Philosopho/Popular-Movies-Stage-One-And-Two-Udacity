package com.krinotech.popularmovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Movie[] movies;
    private OnClickMovieHandler clickHandler;

    @Override
    public int getItemCount() {
        if(movies == null) return 0;
        return movies.length;
    }

    public interface OnClickMovieHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(OnClickMovieHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView movieListItemImageView;

        MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            movieListItemImageView = itemView.findViewById(R.id.iv_movie_list_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie movie = movies[getAdapterPosition()];

            clickHandler.onClick(movie);
        }

    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Movie movie = movies[position];

        Picasso.get()
                .load(movie.getImageUrl())
                .error(R.drawable.ic_launcher_background)
                .into(holder.movieListItemImageView);
    }

    public void setMovies(Movie[] movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public Movie[] getMovies() {
        return movies;
    }
}
