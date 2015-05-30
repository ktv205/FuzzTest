package com.example.krishnateja.fuzztest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class CommonFunctions {

    public static final String TAG = CommonFunctions.class.getSimpleName();

    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isInternetReachable() {
        java.net.InetAddress address = null;
        boolean canConnect = false;
        try {
            address = InetAddress.getByName("www.google.com");
            if (!address.equals("")) {
                canConnect = true;
                return canConnect;
            } else {
                canConnect = false;
                return canConnect;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            canConnect = false;
            return canConnect;

        }
    }
}
