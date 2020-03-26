package com.krinotech.popularmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.krinotech.popularmovies.model.Movie;

@Database( entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String TAG = "MovieDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movie database";
    private static MovieDatabase instance;

    public static MovieDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Database");
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "getInstance: Database");
        return instance;
    }

    public abstract MovieDao movieDao();
}
