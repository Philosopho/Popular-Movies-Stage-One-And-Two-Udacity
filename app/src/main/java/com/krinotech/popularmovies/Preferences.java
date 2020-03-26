package com.krinotech.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class Preferences {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

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
}
