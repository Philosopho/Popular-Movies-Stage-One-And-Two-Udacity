package com.krinotech.popularmovies.adapter;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.model.Review;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private static String TAG = ReviewAdapter.class.getSimpleName();
    private List<Review> reviews;
    private ReviewOnClickHandler clickListener;
    private static SparseBooleanArray selectedItems;


    public interface ReviewOnClickHandler {
        void onClick(int position);
    }

    public ReviewAdapter(List<Review> reviews, ReviewOnClickHandler clickHandler) {
        this.reviews = reviews;
        this.clickListener = clickHandler;
        selectedItems = new SparseBooleanArray();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView authorNameTextView;
        final TextView contentTextView;
        final int height_dps;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            authorNameTextView = itemView.findViewById(R.id.tv_reviewer_name);
            contentTextView = itemView.findViewById(R.id.tv_review_content);
            height_dps = (int) (itemView.getResources().getDimension(R.dimen.reviews_content_height));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, height_dps);
                contentTextView.setLayoutParams(layoutParams);
            } else {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                contentTextView.setLayoutParams(layoutParams);
                selectedItems.put(getAdapterPosition(), true);
            }
            notifyDataSetChanged();
            clickListener.onClick(getAdapterPosition());
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

        Log.d(TAG, selectedItems.toString());
        if (selectedItems.get(position, false)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            holder.contentTextView.setLayoutParams(layoutParams);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, holder.height_dps);
            holder.contentTextView.setLayoutParams(layoutParams);
        }

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
