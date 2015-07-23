package com.example.idreams.dot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.idreams.dot.chat.ChatListActivity;
import com.example.idreams.dot.localtopics.BoardActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.example.idreams.dot.utils.GetToken;


public class MainActivity extends BaseActivity implements MainFragment.MainFragmentCallbacks {

    public static final int INDEX_SIMPLE_LOGIN = 0;
    public static final String FRAGMENT_TAG = "fragment_tag";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String tokenstring = "api_doc_token";
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.menucontainer);
        mFragmentManager = getSupportFragmentManager();
        toggleFragment(INDEX_SIMPLE_LOGIN);
        (new GetToken(this)).getToken();

    }

    @Override
    public void onFacebookLogin(String msg) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString("username", msg).commit();
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
