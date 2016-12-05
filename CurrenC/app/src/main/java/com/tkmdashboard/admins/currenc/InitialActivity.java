package com.tkmdashboard.admins.currenc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;


public class InitialActivity extends Activity {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);


        Intent intent = null;


        if (preferences.contains(KEY_CREDENTIALS)|| AccessToken.getCurrentAccessToken() !=  null) {

            intent = new Intent(InitialActivity.this, MenuActivity.class);

        } else {                                                 //if user is not yet logged in;
            intent = new Intent(InitialActivity.this, LoginActivity.class);
        }
        startActivity(intent);




    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        Intent intent = null;
        if (preferences.contains(KEY_CREDENTIALS)|| AccessToken.getCurrentAccessToken() !=  null) {              //if user is currently logged in;
            intent = new Intent(InitialActivity.this, MenuActivity.class);
        } else {                                                 //if user is not yet logged in;
            intent = new Intent(InitialActivity.this, LoginActivity.class);
        }
        startActivity(intent);

    }




}

