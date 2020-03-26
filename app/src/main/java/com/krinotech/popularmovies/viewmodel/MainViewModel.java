package com.krinotech.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.krinotech.popularmovies.database.MovieDatabase;
import com.krinotech.popularmovies.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
        Log.d(TAG, "MainViewModel: Getting movies");
        movies = movieDatabase.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
