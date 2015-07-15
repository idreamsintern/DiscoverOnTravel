package com.example.idreams.dot.nearby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.idreams.dot.MainActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.utils.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


public class NearbyActivity extends AppCompatActivity implements NearbyCategoryFragment.CategoryListener {


    private static final String urllogin = "user/get_token";
    private static final String LOG_TAG = "NearbyActivity";
    private String tokenstring = MainActivity.tokenstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nearby, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendCategory(String category, String keyword) {
        FragmentManager fm = getSupportFragmentManager();
        ShowNearbyFragment showNearbyFragment = (ShowNearbyFragment) fm.findFragmentById(R.id.show_nearby_fragment);
        showNearbyFragment.changeValue(category, keyword);
    }

}
