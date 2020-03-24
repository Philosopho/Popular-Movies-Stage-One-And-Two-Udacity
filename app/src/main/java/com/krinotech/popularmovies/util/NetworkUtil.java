package com.krinotech.popularmovies.util;

import android.net.Uri;
import android.util.Log;

import com.krinotech.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    private static final String TAG = NetworkUtil.class.getSimpleName();

    public static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie";

    public static final String POPULAR_URL = MOVIE_BASE_URL + "/popular";

    public static final String TOP_RATED_URL = MOVIE_BASE_URL + "/top_rated";

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    public static final String API_KEY_PARAM = "api_key";

    public static final String APPEND_PARAM = "append_to_response";

    public static final String REVIEWS_TRAILERS_QUERY = "reviews,videos";

    public static final String YOUTUBE_BASE = "https://www.youtube.com/watch";

    public static final String YOUTUBE_V_PARAM = "v";

    public static Uri getYouTubeLinkURL(String query) {

        return Uri.parse(YOUTUBE_BASE)
                .buildUpon()
                .appendQueryParameter(YOUTUBE_V_PARAM, query)
                .build();
    }

    public static URL getMovieDetailsTrailersReviews(int id) {
        String id_string = Integer.toString(id);

//        Uri uri = Uri.parse(MOVIE_BASE_URL)
//                .buildUpon()
//                .appendPath(id_string)
//                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_V3_API_KEY)
//                .appendQueryParameter(APPEND_PARAM, REVIEWS_TRAILERS_QUERY)
//                .build();

        String stringBuilder = MOVIE_BASE_URL +
                "/" +
                id_string +
                "?" +
                API_KEY_PARAM +
                "=" + BuildConfig.MOVIE_DB_V3_API_KEY +
                "&" + APPEND_PARAM + "=" +
                REVIEWS_TRAILERS_QUERY;
        Uri uri = Uri.parse(stringBuilder);
        return instantiateURL(uri);
    }
    public static URL getPopularMoviesURL() {
        Uri uri = buildBaseUri(POPULAR_URL);

        return instantiateURL(uri);
    }

    public static URL getTopRatedMoviesURL() {
        Uri uri = buildBaseUri(TOP_RATED_URL);

        return instantiateURL(uri);
    }

    public static URL buildImageURL(String endpoint) {
        Uri uri = buildImageUri(endpoint, ImageSize.DEFAULT.size);

        return instantiateURL(uri);
    }

    public static URL buildImageURL(String size, String endpoint) {
        Uri uri = buildImageUri(endpoint, size);

        return instantiateURL(uri);
    }

    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static Uri buildBaseUri(String baseUri) {
        return Uri.parse(baseUri)
                .buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_V3_API_KEY)
                .build();
    }

    private static Uri buildImageUri(String endpoint, String size) {
        return Uri.parse(IMAGE_BASE_URL)
                .buildUpon()
                .appendPath(size)
                .appendPath(endpoint)
                .build();
    }

    private static URL instantiateURL(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            String message = "Uri malformed: ";
            Log.e(TAG, message + uri);
            e.printStackTrace();
        }
        return url;
    }

    public enum ImageSize {
        SMALL("w92"),
        MEDIUM("w154"),
        DEFAULT("w185"),
        LARGE("w342"),
        XLARGE("w500"),
        XXLARGE("w780");

        final String size;

        ImageSize(String size){
            this.size = size;
        }
    }
}
