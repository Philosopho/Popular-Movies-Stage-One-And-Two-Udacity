package com.krinotech.popularmovies.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.adapter.TrailerAdapter;
import com.krinotech.popularmovies.databinding.ActivityDetailsBinding;
import com.krinotech.popularmovies.model.Movie;
import com.krinotech.popularmovies.model.Trailer;
import com.krinotech.popularmovies.util.MovieJsonUtil;
import com.krinotech.popularmovies.util.NetworkUtil;

import java.io.IOException;
import java.net.URL;

import static com.krinotech.popularmovies.helper.NetworkConnectionHelper.isConnected;

public class DetailsActivity extends AppCompatActivity {
    private Movie movie;
    private ActivityDetailsBinding activityDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        activityDetailsBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_details);

        setTitle(getString(R.string.details_activity_title));

        Intent intent = getIntent();

        int id = intent
                .getIntExtra(getString(R.string.ID_EXTRA), 0);
        String title = intent
                .getStringExtra(getString(R.string.TITLE_EXTRA));
        String originalTitle = intent
                .getStringExtra(getString(R.string.ORIGINAL_TITLE_EXTRA));
        String plotSynopsis = intent
                .getStringExtra(getString(R.string.PLOT_SYNOPSIS_EXTRA));
        String imageUrl = intent
                .getStringExtra(getString(R.string.IMAGE_URL_EXTRA));
        String releaseDate = intent
                .getStringExtra(getString(R.string.RELEASE_DATE_EXTRA));
        double voteAverage = intent
                .getDoubleExtra(getString(R.string.VOTE_AVERAGE_EXTRA), 0.0);

        movie = new Movie(id, title, imageUrl, releaseDate, voteAverage, plotSynopsis, originalTitle);
        new MovieTask().execute(NetworkUtil.getMovieDetailsTrailersReviews(movie.getID()));
    }

    public class MovieTask extends AsyncTask<URL, Void, Movie> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected Movie doInBackground(URL... urls) {
            Movie detailsMovie = null;
            try {
                String response = NetworkUtil.getHttpResponse(urls[0]);
                detailsMovie = MovieJsonUtil.parseJsonIntoReviewsAndTrailers(movie, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return detailsMovie;
        }

        @Override
        protected void onPostExecute(Movie detailsMovie) {
            super.onPostExecute(movie);
            hideProgressBar();
            if(isConnected(DetailsActivity.this)){
                if(detailsMovie != null) {
                    movie = detailsMovie;

                    TrailerAdapter trailerAdapter = new TrailerAdapter(DetailsActivity.this, movie.getTrailers());

                    activityDetailsBinding.listViewTrailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Trailer trailerClicked = (Trailer) parent.getItemAtPosition(position);

                            Intent intent = new Intent(Intent.ACTION_VIEW, trailerClicked.getLink());

                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    activityDetailsBinding.listViewTrailers.setAdapter(trailerAdapter);
                }
                else {
                    showErrorToast(getString(R.string.failed_to_load_details));
                }
            }
            else {
                showErrorToast(getString(R.string.network_error));
            }
            activityDetailsBinding.setMovie(movie);
            activityDetailsBinding.svDetails.setVisibility(View.VISIBLE);
        }
    }

    private void showErrorToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    private void hideProgressBar() {
        activityDetailsBinding.pbDetails.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        activityDetailsBinding.pbDetails.setVisibility(View.VISIBLE);
    }
}
