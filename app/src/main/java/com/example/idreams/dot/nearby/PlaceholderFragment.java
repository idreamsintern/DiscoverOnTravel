package com.example.idreams.dot.nearby;

/**
 * Created by chichunchen on 2015/7/17.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.idreams.dot.MainActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.utils.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    private final static String LOG_TAG = "PlacehloderFragment";
    private final static String url = "fb_checkin_search";
    private static final String ARG_SECTION_NUMBER = "section_number";
    ProgressDialog progressbar;
    ShowNearbyAdapter itemsAdapter;
    private ListView nearbyListView;
    private String currentCategory = "food";
    private String currentKeyword = "燒肉";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int position) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(LOG_TAG, "onAttach");
        ((NearbyActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);

        ArrayList<CheckIn> arrayOfCheckins = new ArrayList<CheckIn>();
        itemsAdapter = new ShowNearbyAdapter(getActivity(), arrayOfCheckins);
        getdatafromapi(currentCategory, currentKeyword);
        progressbar = ProgressDialog.show(getActivity(), "下載資料", "請稍待片刻...", true);
        nearbyListView = (ListView) rootView.findViewById(R.id.nearby_list);
        nearbyListView.setAdapter(itemsAdapter);

        return rootView;
    }


    public void getdatafromapi(String category, String keyword) {
        RequestParams params = new RequestParams();
        params.put("category", category);
        params.put("keyword", keyword);
        params.put("coordinates", "25.041399,121.554233");
        params.put("radius", 100);   // radius = 100km
        params.put("limit", 20);     // limit = 20
        params.put("period", "month");
        params.put("sort", "upcount");
        params.put("token", MainActivity.tokenstring);
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
                        Log.e("LOG_TAG", "onSuccess\n" + "name : " + name + "\nid" + id);
                        itemsAdapter.add(new CheckIn(name, id, checkins));
                    }

                } catch (Exception err) {
                    Log.e(LOG_TAG,"onFail :" + err.getMessage());
                }

                progressbar.dismiss();
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(LOG_TAG, "Fail json! " + throwable.getMessage());
                progressbar.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(LOG_TAG, "Fail! " + throwable.getMessage());
                progressbar.dismiss();
            }
        });

    }
}