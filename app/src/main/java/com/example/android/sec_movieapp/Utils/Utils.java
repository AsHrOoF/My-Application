package com.example.android.sec_movieapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ahmedb on 11/1/16.
 */

public class Utils {

    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
