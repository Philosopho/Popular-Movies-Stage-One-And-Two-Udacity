package com.krinotech.popularmovies.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public final class NetworkConnectionHelper {

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {return false;}

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    );
            }
        else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
}
