package com.example.android.sec_movieapp.App;

import android.app.Application;

/**
 * Created by ahmedb on 11/1/16.
 */

public class FlickerApplicationClass extends Application {

    public static FlickerApplicationClass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static FlickerApplicationClass getFlickerApp(){
        return instance;
    }
}
