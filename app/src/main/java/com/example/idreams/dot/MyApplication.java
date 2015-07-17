package com.example.idreams.dot;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Windows on 26-03-2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }


}
