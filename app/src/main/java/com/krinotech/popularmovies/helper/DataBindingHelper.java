package com.krinotech.popularmovies.helper;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class DataBindingHelper {

    @BindingAdapter("app:imageUrl")
    public static void imageUrl(ImageView imageView, String url){
        Picasso.get()
                .load(url)
                .into(imageView);
    }
}
