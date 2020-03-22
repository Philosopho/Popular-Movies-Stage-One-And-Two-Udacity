package com.krinotech.popularmovies.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageSizeTest {

    @Test
    public void SMALL() {
        assertEquals(NetworkUtil.ImageSize.SMALL.size, "w92");
    }

    @Test
    public void MEDIUM() {
        assertEquals(NetworkUtil.ImageSize.MEDIUM.size, "w154");
    }

    @Test
    public void DEFAULT() {
        assertEquals(NetworkUtil.ImageSize.DEFAULT.size, "w185");
    }

    @Test
    public void LARGE() {
        assertEquals(NetworkUtil.ImageSize.LARGE.size, "w342");
    }

    @Test
    public void XLARGE() {
        assertEquals(NetworkUtil.ImageSize.XLARGE.size, "w500");
    }

    @Test
    public void XXLARGE() {
        assertEquals(NetworkUtil.ImageSize.XXLARGE.size, "w780");
    }
}
