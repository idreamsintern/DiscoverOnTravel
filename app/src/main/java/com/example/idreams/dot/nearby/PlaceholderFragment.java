package com.example.idreams.dot.nearby;

/**
 * Created by chichunchen on 2015/7/17.
 */

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.idreams.dot.R;
import com.example.idreams.dot.data.DotDbContract.FbCheckinEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int FB_CHECKIN_LOADER = 0;
    private static final String ARG_KEYWORD = "arg_keyword";
    private static final String ARG_CATEGORY = "arg_category";
    private NearbyCursorAdapter mNearbyAdapter;
    private String mCurrentCategory;
    private String mCurrentKeyword;
    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final String[] PROJECTION = {
            FbCheckinEntry.TABLE_NAME + "." + FbCheckinEntry._ID,
            FbCheckinEntry.COLUMN_ID,
            FbCheckinEntry.COLUMN_NAME,
            FbCheckinEntry.COLUMN_CATEGORY,
            FbCheckinEntry.COLUMN_LAT,
            FbCheckinEntry.COLUMN_LNG,
            FbCheckinEntry.COLUMN_CHECKINS,
            FbCheckinEntry.COLUMN_CHECKINS_UPCOUNT
    };

    static final int COLUMN_FB_CHECKIN_ID_ = 0;
    static final int COLUMN_FB_CHECKIN_ID = 1;
    static final int COLUMN_FB_CHECKIN_NAME = 2;
    static final int COLUMN_FB_CHECKIN_CATEGORY = 3;
    static final int COLUMN_FB_CHECKIN_LAT = 4;
    static final int COLUMN_FB_CHECKIN_LNG = 5;
    static final int COLUMN_FB_CHECKIN_CHECKINS = 6;
    static final int COLUMN_FB_CHECKIN_CHECKINS_UPCOUNT = 7;
    static final int COLUMN_FB_CHECKIN_STARTDATE = 8;


    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(String category, String key) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        args.putString(ARG_KEYWORD, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((NearbyActivity) activity).onSectionAttached(
                getArguments().getString(ARG_CATEGORY), getArguments().getString(ARG_KEYWORD));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentCategory = getArguments().getString(ARG_CATEGORY);
        mCurrentKeyword  = getArguments().getString(ARG_KEYWORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);

        String sortOrder = FbCheckinEntry.COLUMN_CHECKINS + " ASC";
        mNearbyAdapter = new NearbyCursorAdapter(getActivity(), null, 0);
        ListView listView = (ListView) rootView.findViewById(R.id.nearby_list);
        listView.setAdapter(mNearbyAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FB_CHECKIN_LOADER, null, this);
    }

    @Override
    public void onStart () {
        super.onStart();
        Log.v(LOG_TAG, "onStart");
    }

    @Override
    public void onResume () {
        super.onResume();
        Log.v(LOG_TAG, "onResume");
    }

    @Override
    public void onPause () {
        super.onPause();
        Log.v(LOG_TAG, "onPause");
    }

    @Override
    public void onStop () {
        super.onStop();
        Log.v(LOG_TAG, "onStop");
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        Log.v(LOG_TAG, "onDestroy");
    }

    @Override
    public void onDetach () {
        super.onDetach();
        Log.v(LOG_TAG, "onDetach");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri searchUri = buildQueryUri();
        String sortOrder = FbCheckinEntry.COLUMN_CHECKINS + " ASC";
        return new CursorLoader(getActivity(), searchUri, PROJECTION, null, null, sortOrder);
    }

    private Uri buildQueryUri() {
        if (mCurrentCategory == null) {
            if(mCurrentKeyword == null) {
                return FbCheckinEntry.builSearch();
            } else {
                return FbCheckinEntry.builKeywordSearch(mCurrentKeyword);
            }
        } else {
            if(mCurrentKeyword == null) {
                return FbCheckinEntry.builCategorySearch(mCurrentCategory);
            } else {
                return FbCheckinEntry.buildCategoryKeyworkSearch(mCurrentCategory, mCurrentKeyword);
            }
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mNearbyAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mNearbyAdapter.swapCursor(null);
    }
}