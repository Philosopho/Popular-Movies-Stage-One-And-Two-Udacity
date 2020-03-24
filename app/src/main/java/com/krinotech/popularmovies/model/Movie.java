package com.krinotech.popularmovies.model;

import com.krinotech.popularmovies.helper.CurrencyFormatHelper;

import java.util.List;

public class Movie {
    private static final String MINUTES = "Minutes";
    private static final String NO_INFO = "No info";

    private int _ID;
    private String title;
    private String imageUrl;
    private String releaseDate;
    private double voteAverage;
    private String plotSynopsis;
    private String originalTitle;

    private Review[] reviews;
    private List<Trailer> trailers;
    private int budget;
    private int runtime;
    private int revenue;

    // Default constructor for serialization
    public Movie() {}

    public Movie(int _ID, String title, String imageUrl,
                 String releaseDate, double voteAverage, String plotSynopsis,
                 String originalTitle) {
        this._ID = _ID;
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
        this.originalTitle = originalTitle;
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

    public String getVoteAverageText() { return voteAverage + "/10"; }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getBudget() {
        if(budget == 0){
            return NO_INFO;
        }
        return CurrencyFormatHelper.convertToUSCurrency(budget);
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getRuntime() {
        if(runtime == 0){
            return NO_INFO;
        }
        return runtime + " " + MINUTES;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getRevenue() {
        if(revenue == 0){
            return NO_INFO;
        }
        return CurrencyFormatHelper.convertToUSCurrency(revenue);
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
