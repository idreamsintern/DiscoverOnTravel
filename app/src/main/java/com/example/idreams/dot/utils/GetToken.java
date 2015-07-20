package com.example.idreams.dot.utils;

import android.app.Activity;
import android.util.Log;

import com.example.idreams.dot.MainActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by idreams on 2015/7/18.
 */
public class GetToken {

    private static String LOG_TAG = "GetToken";
    private static final String urllogin = "user/get_token";
    private static Activity mContext;

    public GetToken(Activity context) {
        mContext = context;
    }


    public static void getToken() {
        RequestParams params = new RequestParams();
        params.put("id", "a1411f06f306e17dad9956dc6ba86cdb");
        params.put("secret_key", "1369ac51fd6fc95db2e9dde7b74cc3b8");

        RestClient.post(urllogin, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.e(LOG_TAG, "getToken()" + response.toString());
                    MainActivity.tokenstring = response.getJSONObject("result").getString("token");
                    Log.e(LOG_TAG, "getToken(): " + MainActivity.tokenstring);
                    FetchContentTask fetchContentTask = new FetchContentTask(GetToken.mContext);
                    fetchContentTask.execute(MainActivity.tokenstring);

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
