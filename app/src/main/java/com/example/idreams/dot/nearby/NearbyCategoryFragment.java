package com.example.idreams.dot.nearby;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.idreams.dot.R;
import com.example.idreams.dot.utils.RestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by chichunchen on 2015/7/14.
 */
public class NearbyCategoryFragment extends Fragment {

    private static final String LOG_TAG = "NearbyCategoryFragment";
    private CategoryListener activityCallBack;
    private Spinner categorySpinner;
    private TextView settingTextview;
    private ListView categoryList;

    private static final String url = "fb_checkin_search";
    private String currentCategory = "Category";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCallBack = (CategoryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_category, container, false);

        setView(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void setView(View view) {
        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        final String[] categories = getResources().getStringArray(R.array.category_arrays);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);

        categoryList = (ListView) view.findViewById(R.id.category_list);
        final String[] food = getResources().getStringArray(R.array.Food);
        final String[] travel = getResources().getStringArray(R.array.Travel);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, food);
        categoryList.setAdapter(itemsAdapter);

        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int index = parent.getSelectedItemPosition();
                        currentCategory = categories[index];

                        if (index == 1) {
                            ArrayAdapter<String> itemsAdapter =
                                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, food);
                            categoryList.setAdapter(itemsAdapter);
                        } else if(index == 2) {
                            ArrayAdapter<String> itemsAdapter =
                                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, travel);
                            categoryList.setAdapter(itemsAdapter);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        settingTextview = (TextView) view.findViewById(R.id.setting_textview);
        settingTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallBack.sendCategory("test");
            }
        });
    }

    public interface CategoryListener {
        void sendCategory(String text);
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("category", currentCategory);
        params.put("keyword", "肉");
        params.put("coordinates", "25.041399,121.554233");
        params.put("radius", 100);   // radius = 100km
        params.put("limit", 20);     // limit = 20
        params.put("period", "month");
        params.put("sort", "upcount");
        params.put("token", "api_doc_token");


        RestClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
