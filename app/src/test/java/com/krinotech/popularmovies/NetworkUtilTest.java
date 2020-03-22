package com.krinotech.popularmovies;

import com.krinotech.popularmovies.util.NetworkUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetworkUtilTest {

    @Test
    public void MOVIE_BASE_URL() {
        assertEquals(NetworkUtil.MOVIE_BASE_URL, "https://api.themoviedb.org/3/movie");
    }

    @Test
    public void POPULAR_URL() {
        assertEquals(NetworkUtil.POPULAR_URL, "https://api.themoviedb.org/3/movie/popular");
    }

    @Test
    public void TOP_RATED_URL() {
        assertEquals(NetworkUtil.TOP_RATED_URL, "https://api.themoviedb.org/3/movie/top_rated");
    }

    @Test
    public void IMAGE_BASE_URL() {
        assertEquals(NetworkUtil.IMAGE_BASE_URL, "http://image.tmdb.org/t/p/");
    }

    @Test
    public void API_KEY_PARAM() {
        assertEquals(NetworkUtil.API_KEY_PARAM, "api_key");
    }
}
