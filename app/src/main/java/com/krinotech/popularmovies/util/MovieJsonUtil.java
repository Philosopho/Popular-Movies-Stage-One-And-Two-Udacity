package com.krinotech.popularmovies.util;

import android.net.Uri;
import android.provider.MediaStore;

import com.krinotech.popularmovies.model.Movie;
import com.krinotech.popularmovies.model.Review;
import com.krinotech.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    final static String BUDGET = "budget";

    private static final String REVIEWS = "reviews";
    private static final String RUNTIME = "runtime";
    private static final String REVENUE = "revenue";
    private static final String VIDEOS = "videos";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String TYPE = "type";
    private static final String TRAILER = "Trailer";
    private static final String YOUTUBE = "YouTube";
    private static final String SITE = "site";
    private static final String KEY = "key";
    private static final String NAME = "name";

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

    public static Movie parseJsonIntoReviewsAndTrailers(Movie movie, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            movie.setBudget(jsonObject.getInt(BUDGET));
            movie.setRuntime(jsonObject.optInt(RUNTIME));
            movie.setRevenue(jsonObject.getInt(REVENUE));

            JSONObject jsonObject1Reviews = jsonObject.getJSONObject(REVIEWS);
            JSONObject jsonObject2Videos = jsonObject.getJSONObject(VIDEOS);

            JSONArray jsonReviewsArray = jsonObject1Reviews.getJSONArray(RESULTS_ARRAY);
            JSONArray jsonVideosArray = jsonObject2Videos.getJSONArray(RESULTS_ARRAY);
            int reviewsLength = jsonReviewsArray.length();
            int videosLength = jsonVideosArray.length();
            Review[] reviews = new Review[reviewsLength];
            List<Trailer> trailers = new ArrayList<>();

            for(int i = 0; i < reviewsLength; i++) {
                JSONObject result = jsonReviewsArray.getJSONObject(i);
                Review review = new Review(
                        result.getString(AUTHOR),
                        result.getString(CONTENT)
                );

                reviews[i] = review;
            }

            movie.setReviews(reviews);

            for(int i = 0; i < videosLength; i++) {
                JSONObject result = jsonVideosArray.getJSONObject(i);
                String type = result.getString(TYPE);
                String platform = result.getString(SITE);
                if(type.equalsIgnoreCase(TRAILER) && platform.equalsIgnoreCase(YOUTUBE)) {
                    String watchQueryValue = result.getString(KEY);

                    String name = result.getString(NAME);
                    Uri link = NetworkUtil.getYouTubeLinkURL(watchQueryValue);

                    Trailer trailer = new Trailer(link, name);
                    trailers.add(trailer);
                }
            }

            movie.setTrailers(trailers);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
