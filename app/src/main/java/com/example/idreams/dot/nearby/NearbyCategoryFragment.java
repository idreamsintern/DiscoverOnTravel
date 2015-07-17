package com.example.idreams.dot.nearby;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.idreams.dot.R;

/**
 * Created by chichunchen on 2015/7/14.
 */
public class NearbyCategoryFragment extends Fragment {

    private static final String LOG_TAG = NearbyCategoryFragment.class.getSimpleName();
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
        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        final String[] categories = getResources().getStringArray(R.array.category_arrays);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories) {

            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                ((TextView) v).setTextSize(20);
                ((TextView) v).setTextColor(
                        getResources()
                                .getColorStateList(R.color.color1));
                return v;
            }

            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,
                        parent);
                v.setBackgroundResource(R.color.color4);

                ((TextView) v).setTextColor(getResources().getColorStateList(
                        R.color.color1));
                ((TextView) v).setGravity(Gravity.CENTER);

                return v;
            }
        };


        final String[] food = getResources().getStringArray(R.array.Food);
        final String[] travel = getResources().getStringArray(R.array.Travel);
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
        final String[] stringDefualt = {};
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(getActivity(), R.layout.fragment_nearby_category_listview, stringDefualt);
        categoryList = (ListView) view.findViewById(R.id.category_list);
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


        settingTextview = (TextView) view.findViewById(R.id.setting_textview);
        settingTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallBack.sendCategory("test", "");
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public interface CategoryListener {
        void sendCategory(String category, String keyword);
    }
}
