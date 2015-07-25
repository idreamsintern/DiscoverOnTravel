package com.example.idreams.dot.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.idreams.dot.data.DotDbContract;
import com.example.idreams.dot.data.DotDbContract.FbCheckinEntry;
import com.example.idreams.dot.data.DotDbContract.TopArticleEntry;
import com.example.idreams.dot.nearby.NearbyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by schwannden on 2015/7/20.
 */
public class FetchContentTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private Context mContext;

    public FetchContentTask(Context context) {
        mContext = context;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private void getContentFromJson(String contentJsonStr)
            throws JSONException {

        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.

        // Location information
        final String OWM_MESSAGE          = "message";
        final String OWM_TOTAL            = "total";
        final String OWM_RESULT           = "result";
        final String OWM_ID               = "id";
        final String OWM_NAME             = "name";
        final String OWM_CATEGORY         = "category";
        final String OWM_LATITUDE         = "latitude";
        final String OWM_LONGITUDE        = "longitude";
        final String OWM_CHECKINS         = "checkins";
        final String OWM_CHECKINS_UPCOUNT = "checkins_upcount";
        final String OWM_STARTDATE        = "startdate";

        try {
            JSONObject contentJson = new JSONObject(contentJsonStr);
            JSONArray checkinArray = contentJson.getJSONArray(OWM_RESULT);

            String message = contentJson.getString(OWM_MESSAGE);
            int total      = contentJson.getInt(OWM_TOTAL);

            Vector<ContentValues> cVVector = new Vector<ContentValues>(total);

            for (int i = 0; i < total; i++) {
                // These are the values that will be collected.
                double latitude, longitude;
                long id;
                int checkins, checkins_upcount;
                String name, category, startdate;

                // Get the JSON object representing the day
                JSONObject checkinItem = checkinArray.getJSONObject(i);

                // Cheating to convert this to UTC time, which is what we want anyhow
                latitude  = checkinItem.getDouble(OWM_LATITUDE);
                longitude = checkinItem.getDouble(OWM_LONGITUDE);
                id        = checkinItem.getLong(OWM_ID);
                checkins  = checkinItem.getInt(OWM_CHECKINS);
                checkins_upcount = checkinItem.getInt(OWM_CHECKINS_UPCOUNT);
                name      = checkinItem.getString(OWM_NAME);
                category  = checkinItem.getString(OWM_CATEGORY);
                startdate = checkinItem.getString(OWM_STARTDATE);
                if (NearbyActivity.sCategoryStatistics == null)
                    NearbyActivity.sCategoryStatistics = new HashMap<>();
                Integer count = NearbyActivity.sCategoryStatistics.get(category);
                if (count == null)
                    NearbyActivity.sCategoryStatistics.put(category, 1);
                else
                    NearbyActivity.sCategoryStatistics.put(category, count + 1);

                ContentValues checkinItemValue = new ContentValues();

                checkinItemValue.put(FbCheckinEntry.COLUMN_ID       , id);
                checkinItemValue.put(FbCheckinEntry.COLUMN_NAME     , name);
                checkinItemValue.put(FbCheckinEntry.COLUMN_CATEGORY , category);
                checkinItemValue.put(FbCheckinEntry.COLUMN_LAT      , latitude);
                checkinItemValue.put(FbCheckinEntry.COLUMN_LNG      , longitude);
                checkinItemValue.put(FbCheckinEntry.COLUMN_CHECKINS , checkins);
                checkinItemValue.put(FbCheckinEntry.COLUMN_CHECKINS_UPCOUNT, checkins_upcount);
                checkinItemValue.put(FbCheckinEntry.COLUMN_STARTDATE, startdate);

                cVVector.add(checkinItemValue);
            }

            // add to database
            int inserted = 0;
            if (total > 0) {
                ContentValues[] cvArray = new ContentValues[total];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(FbCheckinEntry.CONTENT_URI, cvArray);
            }
            Log.d(LOG_TAG, "Sync from S.E.R Complete. " + inserted + " Inserted");
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String contentJsonStr = null;

        String format = "json";

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "http://api.ser.ideas.iii.org.tw:80/api/fb_checkin_search";
            final String QTOKEN_PARAM = "token";
            final String COORDI_PARAM = "coordinates";
            final String RADIUS_PARAM = "radius";
            final String LIMITS_PARAM = "limit";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon().build();
            Uri postParams = Uri.parse("").buildUpon()
                    .appendQueryParameter(QTOKEN_PARAM, params[0])
                    .appendQueryParameter(COORDI_PARAM, "25.031827, 121.575170")
                    .appendQueryParameter(RADIUS_PARAM, "5")
                    .appendQueryParameter(LIMITS_PARAM, "100")
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setFixedLengthStreamingMode(
                    postParams.toString().substring(1).getBytes().length);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(postParams.toString().substring(1));
            out.close();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            contentJsonStr = buffer.toString();
            getContentFromJson(contentJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
