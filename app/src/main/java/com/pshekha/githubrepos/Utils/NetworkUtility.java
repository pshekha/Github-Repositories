package com.pshekha.githubrepos.Utils;

/**
 * Created by krrathore on 11/16/18.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class NetworkUtility {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isKnownException(Throwable t){
        return (t instanceof ConnectException
                || t instanceof UnknownHostException
                || t instanceof SocketTimeoutException
                || t instanceof IOException);
    }

}