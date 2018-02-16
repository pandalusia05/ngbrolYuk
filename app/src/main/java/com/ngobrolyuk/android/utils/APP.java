package com.ngobrolyuk.android.utils;

/**
 * Created by MIP on 1/6/2018.
 */


import android.app.Application;

import io.realm.Realm;


public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

    }
}
