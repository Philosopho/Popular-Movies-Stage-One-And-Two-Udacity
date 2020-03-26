package com.krinotech.popularmovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krinotech.popularmovies.database.MovieDatabase;
import com.krinotech.popularmovies.model.Movie;

public class AddMovieViewModel extends ViewModel {

    private LiveData<Movie> movie;

    public AddMovieViewModel(MovieDatabase movieDatabase, int movieId) {
        movie = movieDatabase.movieDao().loadMovieById(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
