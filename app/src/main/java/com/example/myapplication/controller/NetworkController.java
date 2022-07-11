package com.example.myapplication.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.DialogoRedDisponible;

public class NetworkController extends BroadcastReceiver {
    private static NetworkController instancia;
    private boolean opcion_red;

    // initialize listener
    public static ReceiverListener Listener;

    private static final String DEBUG_TAG = "NetworkStatusTPO";

    public static NetworkController getInstancia() {
        if (instancia==null) {
            instancia=new NetworkController();
        }
        return instancia;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String verificarTipoRed(Context context){
        String respuesta="";
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                respuesta="WIFI";
                isWifiConn |= networkInfo.isConnected();
                Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
                return respuesta;
            }else{
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn |= networkInfo.isConnected();
                    respuesta="MOBILE";
                }else{
                    respuesta="NO_RED";
                }
            }
        }

        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
        return respuesta;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // initialize connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        // check condition
        if (Listener != null) {

            // when connectivity receiver
            // listener  not null
            // get connection status
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

            // call listener method
            Listener.onNetworkChange(isConnected);
        }
    }

    public void setNavegarSinWifi(boolean eleccion_user) {
        opcion_red=eleccion_user;
    }

    public boolean getOpcionNavegarSinWifi (){
        return opcion_red;
    }


    public interface ReceiverListener {
        // create method
        void onNetworkChange(boolean isConnected);
    }

}

