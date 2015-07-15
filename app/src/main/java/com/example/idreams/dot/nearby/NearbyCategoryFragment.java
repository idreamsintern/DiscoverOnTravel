package com.example.idreams.dot.nearby;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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

    private int categoryIndex;
    private String currentCategory = "Category";
    private String currentKeyword = "Keyword";

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
        final String[] stringDefualt = {};
        final String[] food = getResources().getStringArray(R.array.Food);
        final String[] travel = getResources().getStringArray(R.array.Travel);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringDefualt);
        categoryList.setAdapter(itemsAdapter);

        categoryList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int index = position;
                        Log.e(LOG_TAG, categoryIndex + "");
                        if (categoryIndex == 1) {
                            currentKeyword = getResources().getStringArray(R.array.Food)[index];
                            Log.e(LOG_TAG, currentCategory + " 1: " + currentKeyword);
                            activityCallBack.sendCategory(currentCategory, currentKeyword);
                        } else if (categoryIndex == 2) {
                            currentKeyword = getResources().getStringArray(R.array.Travel)[index];
                            activityCallBack.sendCategory(currentCategory, currentKeyword);
                            Log.e(LOG_TAG, currentCategory + " 2: " + currentKeyword);
                        }
                    }
                }
        );
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        categoryIndex = parent.getSelectedItemPosition();

                        if (categoryIndex == 1) {
                            currentCategory = categories[categoryIndex];
                            ArrayAdapter<String> itemsAdapter =
                                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, food);
                            categoryList.setAdapter(itemsAdapter);
                        } else if (categoryIndex == 2) {
                            currentCategory = categories[categoryIndex];
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
                activityCallBack.sendCategory("test", "");
            }
        });
    }

    public interface CategoryListener {
        void sendCategory(String category, String keyword);
    }
}
