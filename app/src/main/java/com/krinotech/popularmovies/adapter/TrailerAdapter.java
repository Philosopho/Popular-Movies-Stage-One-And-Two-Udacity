package com.krinotech.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.krinotech.popularmovies.R;
import com.krinotech.popularmovies.model.Trailer;

import java.util.List;

public class TrailerAdapter extends ArrayAdapter<Trailer> {

    public TrailerAdapter(Context context, List<Trailer> trailers) {
        super(context, 0, trailers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.trailers_list_item, parent, false);
        }

        Trailer trailer = getItem(position);

        TextView textView = listItemView.findViewById(R.id.tv_trailer_name);
        if(trailer != null){

            textView.setText(trailer.getName());

        }
        return listItemView;
    }
}
