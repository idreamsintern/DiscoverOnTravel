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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.idreams.dot.R;
import com.example.idreams.dot.data.DotDbContract.FbCheckinEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class nearbyFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int FB_CHECKIN_LOADER = 0;
    private static final String ARG_POSITION = "arg_position";
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

    static final int COLUMN_FB_CHECKIN_ID_              = 0;
    static final int COLUMN_FB_CHECKIN_ID               = 1;
    static final int COLUMN_FB_CHECKIN_NAME             = 2;
    static final int COLUMN_FB_CHECKIN_CATEGORY         = 3;
    static final int COLUMN_FB_CHECKIN_LAT              = 4;
    static final int COLUMN_FB_CHECKIN_LNG              = 5;
    static final int COLUMN_FB_CHECKIN_CHECKINS         = 6;
    static final int COLUMN_FB_CHECKIN_CHECKINS_UPCOUNT = 7;
    static final int COLUMN_FB_CHECKIN_STARTDATE        = 8;


    public nearbyFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static nearbyFragment newInstance(int position) {
        nearbyFragment fragment = new nearbyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((NearbyActivity) activity).onSectionAttached(getArguments().getInt(ARG_POSITION));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mCurrentCategory = NearbyActivity.sCategories.get(
                getArguments().getInt(ARG_POSITION));
        mCurrentKeyword = null;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);
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
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri searchUri = buildQueryUri();
        String sortOrder = FbCheckinEntry.COLUMN_CHECKINS + " DESC";
        return new CursorLoader(getActivity(), searchUri, PROJECTION, null, null, sortOrder);
    }

    private Uri buildQueryUri() {
        if (mCurrentCategory == "all") {
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