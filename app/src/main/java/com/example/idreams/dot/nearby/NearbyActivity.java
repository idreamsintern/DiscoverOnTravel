package com.example.idreams.dot.nearby;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;

public class NearbyActivity extends BaseActivity implements NearbyCategoryFragment.CategoryListener {

    private static final String LOG_TAG = NearbyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
    }

    @Override
    public void sendCategory(String category, String keyword) {
        FragmentManager fm = getSupportFragmentManager();
        ShowNearbyFragment showNearbyFragment = (ShowNearbyFragment) fm.findFragmentById(R.id.show_nearby_fragment);
        showNearbyFragment.changeValue(category, keyword);
    }
}
