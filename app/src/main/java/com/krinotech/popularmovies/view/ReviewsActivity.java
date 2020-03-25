package com.krinotech.popularmovies.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.adapter.ReviewAdapter;
import com.krinotech.popularmovies.model.Review;

import java.util.List;

public class ReviewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reviews);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        List<Review> reviews = intent.getParcelableArrayListExtra(getString(R.string.REVIEWS_EXTRA));

        initRecyclerView(reviews);

        setTitle(getString(R.string.review_activity_title));

    }

    private void initRecyclerView(List<Review> reviews) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false);

        recyclerView = findViewById(R.id.rv_reviews);

        recyclerView.setLayoutManager(layoutManager);

        ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);

        recyclerView.setAdapter(reviewAdapter);
    }
}
