package com.krinotech.popularmoviesstageone.util;

import android.net.Uri;
import android.util.Log;

import com.krinotech.popularmoviesstageone.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtil {
    public static final String TAG = NetworkUtil.class.getSimpleName();

    public static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie";

    public static final String POPULAR_URL = MOVIE_BASE_URL + "/popular";

    public static final String TOP_RATED_URL = MOVIE_BASE_URL + "/top_rated";

    public static final String API_KEY_PARAM = "api_key";

    public static URL getPopularMoviesURL() {
        Uri uri = buildBaseUri(POPULAR_URL);

        return instantiateURL(uri);
    }

    public static URL getTopRatedMoviesURL() {
        Uri uri = buildBaseUri(TOP_RATED_URL);

        return instantiateURL(uri);
    }

    private static Uri buildBaseUri(String baseUri) {
        return Uri.parse(baseUri)
                .buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_V3_API_KEY)
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
}
