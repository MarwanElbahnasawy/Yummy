package com.example.yummy.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;


public class NetworkChecker {

    private static Context context;
    public static NetworkChecker instance = null;

    private NetworkChecker() {

    }

    public static NetworkChecker getInstance(Context contextInput){
        if (instance == null){
            context = contextInput;
            instance = new NetworkChecker();
        }
        return instance;
    }

    public static NetworkChecker getInstance(){
        if (instance == null){
            instance = new NetworkChecker();
        }
        return instance;
    }



    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    public Boolean checkIfInternetIsConnected(){
        return ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) ;
    }

}
