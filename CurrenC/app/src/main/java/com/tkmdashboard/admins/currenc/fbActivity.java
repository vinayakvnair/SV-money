package com.tkmdashboard.admins.currenc;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Daniel Alvarez on 21/6/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class fbActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}