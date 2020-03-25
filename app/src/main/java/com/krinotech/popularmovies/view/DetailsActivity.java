package com.krinotech.popularmovies.view;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.adapter.TrailerAdapter;
import com.krinotech.popularmovies.databinding.ActivityDetailsBinding;
import com.krinotech.popularmovies.model.Movie;
import com.krinotech.popularmovies.model.Review;
import com.krinotech.popularmovies.model.Trailer;
import com.krinotech.popularmovies.util.MovieJsonUtil;
import com.krinotech.popularmovies.util.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static com.krinotech.popularmovies.helper.NetworkConnectionHelper.isConnected;

public class DetailsActivity extends AppCompatActivity {
    public static final String TAG = DetailsActivity.class.getSimpleName();
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

        movie = intent.getParcelableExtra(getString(R.string.MOVIE_PARCEL_EXTRA));
        if(savedInstanceState != null) {
            Log.d(TAG, "onCreate: SavedIntanceState");
            Bundle savedBundle = savedInstanceState.getBundle(getString(R.string.SAVED_MOVIE_BUNDLE));
            if (savedBundle != null){
                Movie savedMovie = savedBundle.getParcelable(getString(R.string.MOVIE_PARCEL_EXTRA));
                if( savedMovie != null && movie.getID() == savedMovie.getID()) {
                    movie = savedMovie;
                }
            }
        }
        else {
            Log.d(TAG, "MovieTask");
            new MovieTask().execute(NetworkUtil.getMovieDetailsTrailersReviews(movie.getID()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.MOVIE_PARCEL_EXTRA), movie);

        outState.putBundle(getString(R.string.SAVED_MOVIE_BUNDLE), bundle);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public class MovieTask extends AsyncTask<URL, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected Void doInBackground(URL... urls) {
            try {
                String response = NetworkUtil.getHttpResponse(urls[0]);
                MovieJsonUtil.parseJsonIntoReviewsAndTrailers(movie, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void nulLStuff) {
            super.onPostExecute(nulLStuff);
            hideProgressBar();
            if(isConnected(DetailsActivity.this)){
                initAdapter();

                setReviewsOnClickListener();
            }
            else {
                showErrorToast(getString(R.string.network_error));
            }
            attachMovieAttributes();
        }
    }

    private void attachMovieAttributes() {
        activityDetailsBinding.setMovie(movie);
        activityDetailsBinding.svDetails.setVisibility(View.VISIBLE);
    }

    private void setReviewsOnClickListener() {
        if(movie.getReviews() != null && movie.getReviews().length != 0) {
            activityDetailsBinding.tvReviewAmountDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startReviewsActivity();
                }
            });
        }
    }

    private void initAdapter() {
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

    private void showErrorToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    private void hideProgressBar() {
        activityDetailsBinding.pbDetails.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        activityDetailsBinding.pbDetails.setVisibility(View.VISIBLE);
    }

    private void startReviewsActivity() {
        Intent intent = new Intent(this, ReviewsActivity.class);
        ArrayList<Review> reviews = new ArrayList<>(Arrays.asList(movie.getReviews()));

        intent.putParcelableArrayListExtra(getString(R.string.REVIEWS_EXTRA), reviews);

        startActivity(intent);
    }
}
