package com.example.idreams.dot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.idreams.dot.chat.ChatActivity;
import com.example.idreams.dot.chat.ChatLogin;
import com.example.idreams.dot.localtopics.BoardActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.example.idreams.dot.utils.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements FragmentSimpleLoginButton.MyInterface {

    TextView tv;
    public static final int INDEX_SIMPLE_LOGIN = 0;

    private static final String STATE_SELECTED_FRAGMENT_INDEX = "selected_fragment_index";
    public static final String FRAGMENT_TAG = "fragment_tag";
    private FragmentManager mFragmentManager;

    private static final String urllogin = "user/get_token";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String tokenstring = "api_doc_token";

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
        String mUsername="";
        tv.setText(msg);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //mUsername = prefs.getString("username", "traveller");
        prefs.edit().putString("username", msg).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getToken();
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

    public void getToken()
    {
        RequestParams params = new RequestParams();
        params.put("id","a1411f06f306e17dad9956dc6ba86cdb");
        params.put("secret_key", "1369ac51fd6fc95db2e9dde7b74cc3b8");

        RestClient.post(urllogin, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.e(LOG_TAG, "getToken()" + response.toString());
                    MainActivity.tokenstring = response.getJSONObject("result").getString("token");
//                    Log.e(LOG_TAG, "getToken()" + tokenstring);

                } catch (Exception err) {
                    Log.e(LOG_TAG, err.getMessage());
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(LOG_TAG, "Fail json! " + throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(LOG_TAG, "Fail! " + throwable.getMessage());
            }
        });
    }
}
