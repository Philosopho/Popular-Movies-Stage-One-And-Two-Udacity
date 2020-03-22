package com.krinotech.popularmovies;

import com.krinotech.popularmovies.model.Movie;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MovieTest {
    private int _ID = 1;
    private String titleParam = "Jaws";
    private String imageUrlParam = "movieDatabase";
    private String releaseDateParam = "1965";
    private double voteAverage = 4.9;
    @SuppressWarnings("CanBeFinal")
    private String plotSynopsis = "A deadly shark, a deadly game.";
    private String originalTitle = "Original Title";
    private Movie testSubject;

    @Before
    public void setUp() {
        testSubject = new Movie(
                _ID,
                titleParam,
                imageUrlParam,
                releaseDateParam,
                voteAverage,
                plotSynopsis,
                originalTitle);
    }

    @Test
    public void movie_should_have_empty_constructor(){
        new Movie();
    }

    @Test
    public void movie_getID_should_be_1() {
        assertEquals(testSubject.getID(), _ID);
    }

    @Test
    public void movie_getTitle_should_return_title() {
        assertEquals(testSubject.getTitle(), titleParam);
    }

    @Test
    public void movie_getImageUrl_should_return_imageUrl() {
        assertEquals(testSubject.getImageUrl(), imageUrlParam);
    }

    @Test
    public void movie_getReleaseDate_should_return_releaseDate() {
        assertEquals(testSubject.getReleaseDate(), releaseDateParam);
    }

    @Test
    public void movie_getVoteAverage_should_return_voteAverage() {
        assertEquals(testSubject.getVoteAverage(), voteAverage, 0.01);
    }

    @Test
    public void movie_getPlotSynopsis_should_return_plotSynopsis() {
        assertEquals(testSubject.getPlotSynopsis(), plotSynopsis);
    }

    @Test
    public void movie_getOriginalTitle_should_return_originalTitle() {
        assertEquals(testSubject.getOriginalTitle(), originalTitle);
    }
}
