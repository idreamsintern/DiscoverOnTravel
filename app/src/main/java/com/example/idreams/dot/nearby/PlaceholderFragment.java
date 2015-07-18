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
    private static final String ARG_KEYWORD = "arg_keyword";
    private static final String ARG_CATEGORY = "arg_category";
    ProgressDialog progressbar;
    ShowNearbyAdapter itemsAdapter;
    private ListView nearbyListView;
    private String currentCategory;
    private String currentKeyword;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(String mCategory, String mKey) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, mCategory);
        args.putString(ARG_KEYWORD, mKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(LOG_TAG, "onAttach");
        ((NearbyActivity) activity).onSectionAttached(
                getArguments().getString(ARG_CATEGORY), getArguments().getString(ARG_KEYWORD));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);
        Bundle arg = getArguments();
        currentCategory = arg.getString(ARG_CATEGORY);
        currentKeyword  = arg.getString(ARG_KEYWORD);
        ArrayList<CheckIn> arrayOfCheckins = new ArrayList<CheckIn>();
        itemsAdapter = new ShowNearbyAdapter(getActivity(), arrayOfCheckins);
        getdatafromapi();
        progressbar = ProgressDialog.show(getActivity(), "下載資料", "請稍待片刻...", true);
        nearbyListView = (ListView) rootView.findViewById(R.id.nearby_list);
        nearbyListView.setAdapter(itemsAdapter);

        return rootView;
    }


    public void getdatafromapi() {

        RequestParams params = new RequestParams();
        params.put("category", currentCategory);
        params.put("keyword", currentKeyword);
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