package com.krinotech.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.krinotech.popularmovies.databinding.ActivityDetailsBinding;
import com.krinotech.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailsBinding activityDetailsBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_details);

        setTitle(getString(R.string.details_activity_title));

        Intent intent = getIntent();

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

        Movie movie = new Movie(title, imageUrl, releaseDate, voteAverage, plotSynopsis, originalTitle);

        activityDetailsBinding.setMovie(movie);

        ImageView posterImageView = findViewById(R.id.iv_movie_details_image);

        Picasso.get().load(imageUrl).into(posterImageView);
    }
}
