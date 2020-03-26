package com.krinotech.popularmovies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.krinotech.popularmovies.MovieExecutors;
import com.krinotech.popularmovies.Preferences;
import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.adapter.TrailerAdapter;
import com.krinotech.popularmovies.database.MovieDatabase;
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
    private MovieDatabase movieDatabase;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieDatabase = MovieDatabase.getInstance(this);
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

        preferences = new Preferences(getApplicationContext());

        movie = intent.getParcelableExtra(getString(R.string.MOVIE_PARCEL_EXTRA));

        if (movie != null && preferences.containsFavorite(movie.getID())) {
            saveFavoritedSettings(false);
        }
        else {
            saveUnFavoritedSettings(false);
        }
        if(savedInstanceState != null) {
            loadInstanceState(savedInstanceState);
        }
        else {
            new MovieTask().execute(NetworkUtil.getMovieDetailsTrailersReviews(movie.getID()));
        }
    }

    private void loadInstanceState(Bundle savedInstanceState) {
        Bundle savedBundle = savedInstanceState.getBundle(getString(R.string.SAVED_MOVIE_BUNDLE));

        if (savedBundle != null ){
            movie = savedBundle.getParcelable(getString(R.string.MOVIE_PARCEL_EXTRA));
            initAdapter();
            setReviewsOnClickListener();
            attachMovieAttributes();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.MOVIE_PARCEL_EXTRA), movie);

        outState.putBundle(getString(R.string.SAVED_MOVIE_BUNDLE), bundle);
        super.onSaveInstanceState(outState);
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

    public void addFavoriteMovie() {
        preferences.addFavorite(movie.getID());

        MovieExecutors.getInstance().diskIO().execute(new Runnable() {

            @Override
            public void run() {
                movieDatabase.movieDao().insertMovie(movie);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        saveFavoritedSettings(true);
                    }
                });
            }
        });
    }

    public void removeFavoriteMovie() {
        preferences.removeFavorite(movie.getID());

        MovieExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDatabase.movieDao().deleteMovie(movie);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        saveUnFavoritedSettings(true);
                    }
                });
            }
        });
    }

    private void saveUnFavoritedSettings(boolean transform) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavoriteMovie();
            }
        };
        setFavoriteButton(R.color.colorAccent,
                R.color.black,
                R.string.favorite,
                listener, transform);
    }

    private void saveFavoritedSettings(boolean transform) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFavoriteMovie();
            }
        };

        setFavoriteButton(R.color.colorPrimary,
                R.color.white,
                R.string.favorited,
                listener, transform);
    }

    private void setFavoriteButton(int RBackgroundColor, int RTextColor, int RText, View.OnClickListener listener, boolean transform) {
        Resources resources = getResources();

        if(transform){
            final Button favoritesButton = activityDetailsBinding.favoritesButton;
            int currentTextColor = favoritesButton.getCurrentTextColor();

            ObjectAnimator.ofObject(favoritesButton,
                    "textColor", new ArgbEvaluator(),
                    currentTextColor, resources.getColor(RTextColor)).setDuration(250).start();

            int colorFrom = ((ColorDrawable) favoritesButton.getBackground()).getColor();
            int colorTo = resources.getColor(RBackgroundColor);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(250); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    favoritesButton.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();

        }
        else {
            activityDetailsBinding.favoritesButton.setBackgroundColor(resources
                    .getColor(RBackgroundColor));
            activityDetailsBinding.favoritesButton.setTextColor(resources
                    .getColor(RTextColor));
        }
        activityDetailsBinding.favoritesButton.setText(RText);
        activityDetailsBinding.favoritesButton.setOnClickListener(listener);
    }
}
