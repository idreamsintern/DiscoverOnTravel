package com.example.idreams.dot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.example.idreams.dot.chat.ChatActivity;
import com.example.idreams.dot.chat.ChatListActivity;
import com.example.idreams.dot.localtopics.BoardActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.example.idreams.dot.utils.FetchContentTask;
import com.example.idreams.dot.utils.GetToken;


public class MainActivity extends BaseActivity implements FragmentSimpleLoginButton.MyInterface {

    public static final int INDEX_SIMPLE_LOGIN = 0;
    public static final String FRAGMENT_TAG = "fragment_tag";
    private static final String STATE_SELECTED_FRAGMENT_INDEX = "selected_fragment_index";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String tokenstring = "api_doc_token";
    TextView tv;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        FragmentSimpleLoginButton fragment = new FragmentSimpleLoginButton();
        mFragmentManager = getSupportFragmentManager();
        toggleFragment(INDEX_SIMPLE_LOGIN);
        //(new GetToken(this)).getToken();
    }

    public void getMessage(String msg) {
        String mUsername = "";
        tv.setText(msg);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //mUsername = prefs.getString("username", "traveller");
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

    public void localBtn(View view) {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }

    public void chatBtn(View view) {
        Intent intent = new Intent(this, ChatListActivity.class);
        startActivity(intent);
    }

    private void toggleFragment(int index) {
        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index) {
            case INDEX_SIMPLE_LOGIN:
                transaction.replace(android.R.id.content, new FragmentSimpleLoginButton(), FRAGMENT_TAG);
                break;
        }
        transaction.commit();
    }
}
