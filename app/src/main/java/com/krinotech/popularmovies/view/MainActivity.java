package com.krinotech.popularmovies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.krinotech.popularmovies.Preferences;
import com.krinotech.popularmovies.adapter.MovieAdapter;
import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.model.Movie;
import com.krinotech.popularmovies.util.MovieJsonUtil;
import com.krinotech.popularmovies.util.NetworkUtil;
import com.krinotech.popularmovies.viewmodel.MainViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.krinotech.popularmovies.helper.NetworkConnectionHelper.isConnected;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnClickMovieHandler {
    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView mErrorMessageTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;
    private Preferences preferences;
    private boolean mSortedPopular = false;
    private boolean mSortedRatings = false;
    private boolean mSortedFavorites = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        mProgressBar = findViewById(R.id.pb_movie_loader);
        mRecyclerView = findViewById(R.id.rv_movies);

        initAdapter();

        if(!isConnected(this)){
            mSortedPopular = false;
        }

        preferences = new Preferences(getApplicationContext());

        if(preferences.isFavorites()) {
            loadAllFavoriteMovies();
        }
        else if(preferences.isHighestRated()) {
            getMoviesRated();
        }
        else {
            getMoviesPopular();
        }

        setTitle(getString(R.string.main_activity_title));
    }

    public void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.mi_popular:
                getMoviesPopular();
                break;
            case R.id.mi_rating:
                getMoviesRated();
                break;
            case R.id.mi_favorites:
                loadAllFavoriteMovies();
                break;
        }
        return true;
    }

    private void getMoviesPopular() {
        if(isConnected(this)){

            if(!mSortedPopular){
                new MovieTask().execute(NetworkUtil.getPopularMoviesURL());
                mSortedPopular = true;
                mSortedRatings = false;
                mSortedFavorites = false;
                preferences.savePopularSelected();
            }
            else {
                Toast.makeText(this, getString(R.string.popular_sorted_true), Toast.LENGTH_SHORT).show();
            }

        }
        else {
            showToastNetworkError();
        }
    }

    private void getMoviesRated() {
        if(isConnected(this)){

            if(!mSortedRatings){
                new MovieTask().execute(NetworkUtil.getTopRatedMoviesURL());
                mSortedRatings = true;
                mSortedPopular = false;
                mSortedFavorites = false;
                preferences.saveHighestRatedSelected();
            }
            else {
                Toast.makeText(this, getString(R.string.rated_sorted_true), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            showToastNetworkError();
        }
    }

    @Override
    public void onClick(Movie movie) {
        launchDetailsActivity(movie);
    }

    private class MovieTask extends AsyncTask<URL, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }
        @Override
        protected Movie[] doInBackground(URL... urls) {
            URL url = urls[0];
            Movie[] movies = null;
            try {
                String response = NetworkUtil.getHttpResponse(url);
                movies = MovieJsonUtil.parseJsonIntoMovies(response);
            } catch (IOException e) {
                e.getMessage();
            }
            return movies;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            hideProgressBar();
            if(movies != null){
                hideErrorMessage();
                mMovieAdapter.setMovies(movies);
            }
            else {
                if(isConnected(MainActivity.this)){
                    showErrorMessage(getString(R.string.an_error_occurred));
                }
                else {
                    showErrorMessage(getString(R.string.network_error));
                }
            }
        }

    }
    private void showErrorMessage(String errorText) {
        mErrorMessageTextView.setText(errorText);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void hideErrorMessage() {
        mErrorMessageTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


    private void showToastNetworkError() {
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    private void launchDetailsActivity(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.MOVIE_PARCEL_EXTRA), movie);

        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void loadAllFavoriteMovies() {
        mSortedFavorites = true;
        mSortedPopular = false;
        mSortedRatings = false;
        preferences.saveFavoritesSelected();
        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mainViewModel.getMovies().removeObserver(this);
                Movie[] favoriteMovies = new Movie[movies.size()];
                mMovieAdapter.setMovies(movies.toArray(favoriteMovies));
            }
        });
    }
}
