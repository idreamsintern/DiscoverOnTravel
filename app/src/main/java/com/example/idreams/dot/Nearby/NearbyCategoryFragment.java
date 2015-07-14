package com.example.idreams.dot.Nearby;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idreams.dot.R;

/**
 * Created by chichunchen on 2015/7/14.
 */
public class NearbyCategoryFragment extends Fragment {

    private static final String LOG_TAG = "NearbyCategoryFragment";
    private CategoryListener activityCallBack;
    private Spinner categorySpinner;
    private TextView settingTextview;
    private ListView categoryList;

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
        settingTextview = (TextView) view.findViewById(R.id.setting_textview);
        settingTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallBack.sendCategory("test");
            }
        });
        categoryList = (ListView) view.findViewById(R.id.category_list);
    }

    public interface CategoryListener {
        void sendCategory(String text);
    }
}
