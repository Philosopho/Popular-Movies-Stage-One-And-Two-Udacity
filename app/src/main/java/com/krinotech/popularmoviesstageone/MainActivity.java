package com.krinotech.popularmoviesstageone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

    public final static String TITLE = "Pop Movies";

    private TextView mErrorMessageTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;

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

        setTitle(TITLE);
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
