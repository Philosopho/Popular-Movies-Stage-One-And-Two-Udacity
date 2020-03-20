package com.krinotech.popularmoviesstageone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.krinotech.popularmoviesstageone.model.Movie;
import com.krinotech.popularmoviesstageone.util.MovieJsonUtil;
import com.krinotech.popularmoviesstageone.util.NetworkUtil;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnClickMovieHandler {
    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView mErrorMessageTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;
    private boolean mSortedPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        mProgressBar = findViewById(R.id.pb_movie_loader);
        mRecyclerView = findViewById(R.id.rv_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);
        new MovieTask().execute(NetworkUtil.getPopularMoviesURL());

        setTitle(getString(R.string.main_activity_title));
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
        }
        return true;
    }

    private void getMoviesPopular() {
        if(!mSortedPopular){
            new MovieTask().execute(NetworkUtil.getPopularMoviesURL());
            mSortedPopular = true;
        }
        else {
            Toast.makeText(this, getString(R.string.popular_sorted_true), Toast.LENGTH_SHORT).show();
        }
    }

    private void getMoviesRated() {
        if(mSortedPopular){
            new MovieTask().execute(NetworkUtil.getTopRatedMoviesURL());
            mSortedPopular = false;
        }
        else {
            Toast.makeText(this, getString(R.string.rated_sorted_true), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(Movie movie) {
        String text = movie.getTitle();
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
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
                showErrorMessage();
            }
        }
    }

    private void showErrorMessage() {
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
}
