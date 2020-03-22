package com.krinotech.popularmovies.helper;

import android.content.Context;
import android.net.ConnectivityManager;

public final class NetworkConnectionHelper {

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager != null &&
                connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();

    }
}
