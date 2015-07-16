package com.example.idreams.dot.localtopics;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.idreams.dot.R;

public class TopicContentActivity extends Activity {

    private final static String LOG_TAG = "TopicContentActivity";
    private String contentUrl;
    private WebView pttWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_content);

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
        pttWebView.loadUrl(contentUrl);
    }
}
