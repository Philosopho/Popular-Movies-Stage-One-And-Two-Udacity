package com.krinotech.popularmovies;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.krinotech.popularmovies.database.MovieDatabase;
import com.krinotech.popularmovies.viewmodel.AddMovieViewModel;

public class AddMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieDatabase movieDatabase;
    private final int movieId;

    public AddMovieViewModelFactory(MovieDatabase movieDatabase, int movieId) {
        this.movieDatabase = movieDatabase;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddMovieViewModel(movieDatabase, movieId);
    }
}
