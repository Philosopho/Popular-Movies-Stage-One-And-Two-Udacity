package com.krinotech.popularmoviesstageone.model;

public class Movie {

    public int _ID;
    private String title;
    private String imageUrl;
    private String releaseDate;
    private double voteAverage;
    private String plotSynopsis;

    // Default constructor for serialization
    public Movie() {}

    public Movie(int _ID, String title, String imageUrl,
                 String releaseDate, double voteAverage, String plotSynopsis) {
        this._ID = _ID;
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    public int getID() { return _ID; }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }
}
