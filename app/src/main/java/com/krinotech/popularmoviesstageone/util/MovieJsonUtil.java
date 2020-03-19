package com.krinotech.popularmoviesstageone.util;

import com.krinotech.popularmoviesstageone.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieJsonUtil {
    private final static String PAGE = "page";
    private final static String RESULTS_ARRAY = "results";
    private final static String POSTER_PATH = "poster_path";
    private final static String OVERVIEW = "overview";
    private final static String RELEASE_DATE = "release_date";
    private final static String GENRE_IDS_ARRAY = "genre_ids";
    private final static String ID = "id";
    private final static String TITLE = "title";
    private final static String ORIGINAL_TITLE = "original_title";
    private final static String ORIGINAL_LANGUAGE = "original_language";
    private final static String VOTE_AVERAGE = "vote_average";

    public static Movie[] parseJsonIntoMovies(String response) {
        Movie[] movies;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS_ARRAY);
            movies = new Movie[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);
                String posterPath = NetworkUtil.buildImageURL(jsonObjectResult.getString(POSTER_PATH).substring(1)).toString();
                String overview = jsonObjectResult.getString(OVERVIEW);
                String releaseDate = jsonObjectResult.getString(RELEASE_DATE);
                String title = jsonObjectResult.getString(TITLE);
                int Id = jsonObjectResult.getInt(ID);
                double voteAverage = jsonObjectResult.getDouble(VOTE_AVERAGE);

                Movie movie = new Movie(Id, title, posterPath, releaseDate, voteAverage, overview);
                movies[i] = movie;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return movies;
    }
}
