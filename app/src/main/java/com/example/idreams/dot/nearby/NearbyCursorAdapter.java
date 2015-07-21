package com.example.idreams.dot.nearby;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idreams.dot.R;
import com.example.idreams.dot.data.DotDbContract.FbCheckinEntry;

/**
 * Created by schwannden on 2015/7/21.
 */
public class NearbyCursorAdapter extends CursorAdapter {
    public NearbyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    // views are reused as needed.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_nearby_row, parent, false);
        return view;
    }


    // This is where we fill-in the views with the contents of the cursor.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CheckBox ckCheck = (CheckBox) view.findViewById(R.id.checkbox);
        TextView tvName = (TextView) view.findViewById(R.id.show_name);
        TextView tvCheckins = (TextView) view.findViewById(R.id.show_checkins);

        InputFilter[] namefilter = new InputFilter[1];
        namefilter[0] = new InputFilter.LengthFilter(11);
        tvName.setFilters(namefilter);

        //**change color with number of checkins. */
        tvName.setText(cursor.getString(PlaceholderFragment.COLUMN_FB_CHECKIN_NAME));
        tvCheckins.setText(cursor.getString(PlaceholderFragment.COLUMN_FB_CHECKIN_CHECKINS));
    }
}