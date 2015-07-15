package com.example.idreams.dot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.idreams.dot.chat.ChatActivity;
import com.example.idreams.dot.localtopics.LocalTopicsActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.example.idreams.dot.utils.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String urllogin = "user/get_token";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String tokenstring = "api_doc_token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToken();
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
                    Log.e(LOG_TAG, "getToken()" + tokenstring);

                } catch (Exception err) {
                    Log.e(LOG_TAG, err.getMessage());
                }

                //progressbar.dismiss();
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(LOG_TAG, "Fail json! " + throwable.getMessage());
                //progressbar.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(LOG_TAG, "Fail! " + throwable.getMessage());
                //progressbar.dismiss();
            }
        });
    }
}
