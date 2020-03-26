package com.krinotech.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class Preferences {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";
    public static final String SELECTED = "Selected";
    private static final String POPULAR_MOVIES = "Popular_Movies";
    private static final String HIGHEST_RATED_MOVIES = "Highest_Rated_Movies";
    private static final String FAVORITE_MOVIES = "Favorite_Movies";

    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        this.sharedPreferences = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void saveToFavorites(Set<String> favorites) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putStringSet(FAVORITES, favorites).apply();
    }

    public void addFavorite(int id) {
        Set<String> favorites = getFavorites();

        String idString = String.valueOf(id);

        favorites.add(idString);

        saveToFavorites(favorites);
    }

    public boolean containsFavorite(int id) {
        String idString = String.valueOf(id);

        Set<String> favorites = getFavorites();

        return !favorites.isEmpty() && favorites.contains(idString);
    }

    public Set<String> getFavorites() {
        return sharedPreferences.getStringSet(FAVORITES, new HashSet<String>());
    }

    public void removeFavorite(int id) {
        Set<String> favorites = getFavorites();

        String idString = String.valueOf(id);

        favorites.remove(idString);

        saveToFavorites(favorites);
    }

    public void savePopularSelected() {
        saveSelected(POPULAR_MOVIES);
    }

    public void saveHighestRatedSelected() {
        saveSelected(HIGHEST_RATED_MOVIES);
    }

    public void saveFavoritesSelected() {
        saveSelected(FAVORITE_MOVIES);
    }

    public boolean isPopular() {
        String selected = getSelected();

        return selected.equals(POPULAR_MOVIES);
    }

    public boolean isHighestRated() {
        String selected = getSelected();

        return selected.equals(HIGHEST_RATED_MOVIES);
    }

    public boolean isFavorites() {
        String selected = getSelected();

        return selected.equals(FAVORITE_MOVIES);
    }

    private String getSelected() {
        return sharedPreferences.getString(SELECTED, POPULAR_MOVIES);
    }

    private void saveSelected(String selected) {
        sharedPreferences.edit().putString(SELECTED, selected).apply();
    }
}
