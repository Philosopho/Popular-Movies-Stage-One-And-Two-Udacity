package com.krinotech.popularmovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        final TextView authorNameTextView;
        final TextView contentTextView;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            authorNameTextView = itemView.findViewById(R.id.tv_reviewer_name);
            contentTextView = itemView.findViewById(R.id.tv_review_content);
        }

    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card_list_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);

        holder.authorNameTextView.setText(review.getAuthor());
        holder.contentTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if(reviews != null) {
            return reviews.size();
        }
        return 0;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

}
