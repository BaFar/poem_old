package com.example.dell.kobitaapps;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by DELL on 2/25/2018.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
      //  Fabric.with(this,new Crashlytics());
    }
}
