package com.greenapp.kobitaapps;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by DELL on 2/25/2018.
 */

public class MyApp extends Application {
    public static MyApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
       // Fabric.with(this,new Crashlytics());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mInstance = this;
    }
    public static synchronized MyApp getInstance(){
        return mInstance;
    }

    public void setConnectivityReceiverListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.connectivityReceiverListener = listener;
        Log.d("receiver","receiver listener instance  initialized");
    }
}
