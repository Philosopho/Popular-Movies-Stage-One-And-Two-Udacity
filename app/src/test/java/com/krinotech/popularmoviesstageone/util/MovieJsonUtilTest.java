package com.krinotech.popularmoviesstageone.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovieJsonUtilTest {

    @Test
    public void PAGE() {
        assertEquals(MovieJsonUtil.PAGE, "page");
    }

    @Test
    public void RESULTS_ARRAY() {
        assertEquals(MovieJsonUtil.RESULTS_ARRAY, "results");
    }

    @Test
    public void POSTER_PATH() {
        assertEquals(MovieJsonUtil.POSTER_PATH, "poster_path");
    }

    @Test
    public void OVERVIEW() {
        assertEquals(MovieJsonUtil.OVERVIEW, "overview");
    }

    @Test
    public void RELEASE_DATE() {
        assertEquals(MovieJsonUtil.RELEASE_DATE, "release_date");
    }

    @Test
    public void GENRE_ID_ARRAY() {
        assertEquals(MovieJsonUtil.GENRE_IDS_ARRAY, "genre_ids");
    }

    @Test
    public void ID() {
        assertEquals(MovieJsonUtil.ID, "id");
    }

    @Test
    public void TITLE() {
        assertEquals(MovieJsonUtil.TITLE, "title");
    }

    @Test
    public void ORIGINAL_TITLE() {
        assertEquals(MovieJsonUtil.ORIGINAL_TITLE, "original_title");
    }

    @Test
    public void ORIGINAL_LANGUAGE() {
        assertEquals(MovieJsonUtil.ORIGINAL_LANGUAGE, "original_language");
    }

    @Test
    public void VOTE_AVERAGE() {
        assertEquals(MovieJsonUtil.VOTE_AVERAGE, "vote_average");
    }

}