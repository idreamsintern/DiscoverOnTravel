package com.example.idreams.dot.localtopics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.idreams.dot.R;
import com.example.idreams.dot.SettingsActivity;

public class TopicContentActivity extends AppCompatActivity {

    private final static String LOG_TAG = "TopicContentActivity";
    private String translateHead = "http://translate.google.com.tw/translate?hl=en&sl=zh-TW&u=";
    private String targetLanguage;
    private String contentUrl;
    private WebView pttWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_content);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        targetLanguage = prefs.getString(getResources().getString(R.string.pref_language_key), null);

        // Get board name from BoardActivity.
        Intent intent = getIntent();
        contentUrl = intent.getStringExtra("ContentURL");
        Log.e(LOG_TAG, contentUrl);

        viewSetting();
    }

    private void viewSetting() {
        pttWebView = (WebView) findViewById(R.id.ptt_webview);

        pttWebView.getSettings().setJavaScriptEnabled(true);
        pttWebView.getSettings().setLoadWithOverviewMode(true);
        pttWebView.getSettings().setAllowFileAccess(true);
        pttWebView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are open in new browser not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(TopicContentActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        if(!targetLanguage.equals("zh-TW")) {
            contentUrl = "http://translate.google.com.tw/translate?hl=" + targetLanguage +
                    "&sl=zh-TW&u=" + contentUrl;
            Log.e(LOG_TAG, contentUrl);
        }
        pttWebView.loadUrl(contentUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_content, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
