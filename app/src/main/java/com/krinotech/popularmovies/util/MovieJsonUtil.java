package com.krinotech.popularmovies.util;

import com.krinotech.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieJsonUtil {
    final static String PAGE = "page";
    final static String RESULTS_ARRAY = "results";
    final static String POSTER_PATH = "poster_path";
    final static String OVERVIEW = "overview";
    final static String RELEASE_DATE = "release_date";
    final static String GENRE_IDS_ARRAY = "genre_ids";
    final static String ID = "id";
    final static String TITLE = "title";
    final static String ORIGINAL_TITLE = "original_title";
    final static String ORIGINAL_LANGUAGE = "original_language";
    final static String VOTE_AVERAGE = "vote_average";

    public static Movie[] parseJsonIntoMovies(String response) {
        Movie[] movies;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_ARRAY);
            movies = new Movie[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);
                String posterPathEndpoint = jsonObjectResult
                        .optString(POSTER_PATH, " ")
                        .substring(1);
                String posterPath = NetworkUtil
                        .buildImageURL(posterPathEndpoint).toString();
                String overview = jsonObjectResult.getString(OVERVIEW);
                String releaseDate = jsonObjectResult.getString(RELEASE_DATE);
                String title = jsonObjectResult.getString(TITLE);
                String originalTitle = jsonObjectResult.getString(ORIGINAL_TITLE);
                int Id = jsonObjectResult.getInt(ID);
                double voteAverage = jsonObjectResult.getDouble(VOTE_AVERAGE);

                Movie movie = new Movie(Id, title, posterPath,
                        releaseDate, voteAverage, overview, originalTitle);
                movies[i] = movie;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return movies;
    }
}
