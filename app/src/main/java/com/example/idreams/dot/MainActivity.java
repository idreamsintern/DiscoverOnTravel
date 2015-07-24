package com.example.idreams.dot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.idreams.dot.chat.ChatListActivity;
import com.example.idreams.dot.localtopics.BoardActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.example.idreams.dot.utils.GetToken;


public class MainActivity extends BaseActivity
        implements MainFragment.MainFragmentCallbacks {

    public static final int INDEX_SIMPLE_LOGIN = 0;
    public static final String FRAGMENT_TAG = "fragment_tag";
    private static final int STATE_TOUR_1 = 1;
    private static final int STATE_TOUR_2 = 2;
    private static final int STATE_TOUR_3 = 3;
    private static final int STATE_TOUR_4 = 4;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String sSerToken = "api_doc_token";
    public static int sState;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (true) {
            sState = STATE_TOUR_1;
        }
        setContentView(R.layout.activity_main);
        (new GetToken(this)).getToken();
    }

    @Override
    public void onFragmentViewCreated(View view) {
        ImageView backgroundPicture = (ImageView) findViewById(R.id.back_image);
        switch (sState) {
            case STATE_TOUR_1:
                final ImageView imageView = (ImageView) findViewById(R.id.dot_logo);
                Animation fadeoutAnim = AnimationUtils.loadAnimation(this, R.anim.fadeout);
                fadeoutAnim.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationEnd(Animation animation) {
                        imageView.setVisibility(View.INVISIBLE);
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }
                });

                imageView.startAnimation(fadeoutAnim);
                mFragmentManager = getSupportFragmentManager();
                toggleFragment(INDEX_SIMPLE_LOGIN);
                //set imageview alpha lower
                final Animation darkenAnim =AnimationUtils.loadAnimation(this,R.anim.back_alpha_lower);
                backgroundPicture.startAnimation(darkenAnim);

                sState++;
                break;
            case STATE_TOUR_2:
                sState++;
                break;
            case STATE_TOUR_3:
                sState++;
                break;
            case STATE_TOUR_4:
                Animation lightenAnim=AnimationUtils.loadAnimation(this,R.anim.back_alpha_higher);
                //backgroundPicture.startAnimation(lightenAnim);
                break;

        }
    }

    @Override
    public void onFacebookLogin(String msg) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString("username", msg).commit();
    }
    public void getFragmentStatus(){

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void nearbyBtn(View view) {
        Intent intent = new Intent(this, NearbyActivity.class);
        startActivity(intent);
    }

    public void localTopicBtn(View view) {
        Intent intent = new Intent(this, BoardActivity.class);
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    public void chatBtn(View view) {
        Intent intent = new Intent(this, ChatListActivity.class);
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    private void toggleFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index) {
            case INDEX_SIMPLE_LOGIN:
                transaction.replace(android.R.id.content, new MainFragment(), FRAGMENT_TAG);
                break;
        }
        transaction.commit();
    }
}
