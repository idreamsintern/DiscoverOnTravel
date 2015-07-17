package com.example.idreams.dot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.idreams.dot.chat.ChatActivity;
import com.example.idreams.dot.localtopics.LocalTopicsActivity;
import com.example.idreams.dot.nearby.NearbyActivity;


public class MainActivity extends ActionBarActivity implements FragmentSimpleLoginButton.MyInterface {

    TextView tv;
    public static final int INDEX_SIMPLE_LOGIN = 0;

    private static final String STATE_SELECTED_FRAGMENT_INDEX = "selected_fragment_index";
    public static final String FRAGMENT_TAG = "fragment_tag";
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        FragmentSimpleLoginButton fragment=new FragmentSimpleLoginButton();
        mFragmentManager = getSupportFragmentManager();
        toggleFragment(INDEX_SIMPLE_LOGIN);

    }
    public void getMessage(String msg) {
        tv.setText(msg);
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
        Intent intent = new Intent(this, LocalTopicsActivity.class);
        startActivity(intent);
    }

    public void chatBtn(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void toggleFragment(int index) {
        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index){
            case INDEX_SIMPLE_LOGIN:
                transaction.replace(android.R.id.content, new FragmentSimpleLoginButton(),FRAGMENT_TAG);
                break;

        }
        transaction.commit();
    }
}
