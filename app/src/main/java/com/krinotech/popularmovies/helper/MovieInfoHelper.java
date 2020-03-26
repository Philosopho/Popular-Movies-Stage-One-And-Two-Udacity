package com.krinotech.popularmovies.helper;

import com.krinotech.popularmovies.model.Review;

public class MovieInfoHelper {

    private static final String MINUTES = "Minutes";
    private static final String NO_INFO = "No info";
    private static final String NO_REVIEWS = "No reviews available.";
    private static final String REVIEWS = " reviews available. Tap to read!";
    private static final String REVIEW = " review available. Tap to read!";

    public static String checkRuntime(int runtime) {
        if(runtime == 0){
            return NO_INFO;
        }
        return runtime + " " + MINUTES;
    }

    public static String checkRevenue(int revenue) {
        if(revenue == 0){
            return NO_INFO;
        }
        return CurrencyFormatHelper.convertToUSCurrency(revenue);
    }

    public static String checkBudget(int budget) {
        if(budget == 0){
            return NO_INFO;
        }
        return CurrencyFormatHelper.convertToUSCurrency(budget);
    }

    public static String checkReviews(Review[] reviews) {
        if(reviews == null || reviews.length == 0){
            return NO_REVIEWS;
        }
        String value = String.valueOf(reviews.length);
        if(value.equals("1")) {
            return value + REVIEW;
        }
        return value + REVIEWS;
    }
}
