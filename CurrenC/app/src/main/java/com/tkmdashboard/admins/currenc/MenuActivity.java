package com.tkmdashboard.admins.currenc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemDaily;

    private ResideMenuItem itemPassbook;
    private ResideMenuItem itemProfile;


int creditvalue=0;
    int debitvalue=0;
    int balance = 0;
TextView textView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.balances);

        SharedPreferences sp = getSharedPreferences("credits", Activity.MODE_PRIVATE);
        creditvalue = sp.getInt("credit", -1);
        SharedPreferences sp1 = getSharedPreferences("debits", Activity.MODE_PRIVATE);
        debitvalue = sp1.getInt("debit", -1);

        balance=creditvalue-debitvalue ;


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {


            // <---- run your one time code here
            Intent i = new Intent(MenuActivity.this, DefaultIntro.class);
            startActivity(i);

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new DailyFragment());
    }
    public void calc(View view) {


    String strI = String.valueOf(balance);
    textView = (TextView) findViewById(R.id.balances);



    textView.setText(strI);
        changeFragment(new PassbookFragment());
}
    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemDaily     = new ResideMenuItem(this, R.drawable.daily1,     "Daily");

        itemPassbook = new ResideMenuItem(this, R.drawable.passbook1, "Balance");

        itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "Profile");


        itemDaily.setOnClickListener(this);

        itemPassbook.setOnClickListener(this);

        itemProfile.setOnClickListener(this);

        resideMenu.addMenuItem(itemDaily, ResideMenu.DIRECTION_LEFT);

        resideMenu.addMenuItem(itemPassbook, ResideMenu.DIRECTION_LEFT);

        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_RIGHT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemDaily){
            changeFragment(new DailyFragment());
        }else if (view == itemPassbook){
            changeFragment(new PassbookFragment());
        } else if (view == itemProfile){
            changeFragment(new ProfileFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {

        }

        @Override
        public void closeMenu() {

        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        LoginManager.getInstance().logOut();
        goLoginScreen();

    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void upd(View view) {
        Intent intent = new Intent(this, credit.class);
        startActivity(intent);
    }
    public void upd2(final View view) {   Intent intent = new Intent(this, debit.class);
        startActivity(intent);
    }





}


