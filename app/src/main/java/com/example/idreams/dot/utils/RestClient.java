package com.example.idreams.dot.utils;

/**
 * Created by chichunchen on 2015/7/15.
 */
import com.loopj.android.http.*;

public class RestClient {
    private static final String BASE_URL = "http://api.ser.ideas.iii.org.tw:80/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}