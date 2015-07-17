package com.example.idreams.dot.init;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Windows on 26-03-2015.
 */
public class FacebookInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
