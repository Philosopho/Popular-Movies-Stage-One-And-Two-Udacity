package com.krinotech.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.krinotech.popularmovies.helper.MovieInfoHelper;

import java.util.List;

public class Movie implements Parcelable {
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

    protected Movie(Parcel in) {
        _ID = in.readInt();
        title = in.readString();
        imageUrl = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        plotSynopsis = in.readString();
        originalTitle = in.readString();
        reviews = in.createTypedArray(Review.CREATOR);
        trailers = in.createTypedArrayList(Trailer.CREATOR);
        budget = in.readInt();
        runtime = in.readInt();
        revenue = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public String getBudgetStats() {
        return MovieInfoHelper.checkBudget(budget);
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getRuntimeStats() {
        return MovieInfoHelper.checkRuntime(runtime);
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getRevenueStats() {
        return MovieInfoHelper.checkRevenue(revenue);
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

    public String getReviewStats() {
        return MovieInfoHelper.checkReviews(reviews);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_ID);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeString(plotSynopsis);
        dest.writeString(originalTitle);
        dest.writeTypedArray(reviews, flags);
        dest.writeTypedList(trailers);
        dest.writeInt(budget);
        dest.writeInt(runtime);
        dest.writeInt(revenue);
    }
}
