package com.example.idreams.dot.nearby;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.idreams.dot.R;
import com.example.idreams.dot.utils.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowNearbyFragment extends Fragment {

    private static final String url = "fb_checkin_search";
    private ListView nearbyListView;
    private static final String LOG_TAG = "ShowNearbyFragment";

    private ShowNearbyAdapter adapter;
    private ArrayList<CheckIn> checkInsArray;
    ProgressDialog progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_nearby, container, false);
        // Construct the data source
        ArrayList<CheckIn> arrayOfCheckins = new ArrayList<CheckIn>();
        // Create the adapter to convert the array to views

        adapter = new ShowNearbyAdapter(getActivity(), arrayOfCheckins);

        // Attach the adapter to a ListView
        nearbyListView = (ListView) view.findViewById(R.id.show_nearby_listview);
        nearbyListView.setAdapter(adapter);

        return view;
    }

    public void changeValue(String category, String keyword) {
        adapter.clear();
        progressbar = ProgressDialog.show(getActivity(), "下載資料", "請稍待片刻...", true);
        getData(category, keyword);
    }

    private void getData(String category, String keyword) {
        RequestParams params = new RequestParams();
        params.put("category", category);
        params.put("keyword", keyword);
        params.put("coordinates", "25.041399,121.554233");
        params.put("radius", 100);   // radius = 100km
        params.put("limit", 20);     // limit = 20
        params.put("period", "month");
        params.put("sort", "upcount");
        params.put("token", "api_doc_token");

        RestClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray resultJsonArray = response.getJSONArray("result");
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        JSONObject j = resultJsonArray.getJSONObject(i);
                        String name = j.getString("name");
                        String id = j.getString("id");
                        String checkins = j.getString("checkins");

                        adapter.add(new CheckIn(name, id, checkins));
                    }

                } catch (Exception err) {
                    Log.e(LOG_TAG, err.getMessage());
                }

                progressbar.dismiss();
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
