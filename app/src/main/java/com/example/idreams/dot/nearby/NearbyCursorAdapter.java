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

import com.example.idreams.dot.R;
import com.google.android.gms.maps.model.LatLng;

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
    public void bindView(View view, Context context, final Cursor cursor) {
        CheckBox ckCheck = (CheckBox) view.findViewById(R.id.checkbox);
        TextView tvName = (TextView) view.findViewById(R.id.show_name);
        TextView tvCheckins = (TextView) view.findViewById(R.id.show_checkins);
        int likes = Integer.valueOf(cursor.getString(nearbyFragment.COLUMN_FB_CHECKIN_CHECKINS));

        InputFilter[] namefilter = new InputFilter[] {new InputFilter.LengthFilter(11)};
        tvName.setFilters(namefilter);
        ckCheck.setTag(getName(cursor) + "," + getLat(cursor) + "," + getLng(cursor));
        if (NearbyActivity.sSelectedLocations.containsKey(getName(cursor))) {
            ckCheck.setChecked(true);
        } else {
            ckCheck.setChecked(false);
        }

        tvName.setText(cursor.getString(nearbyFragment.COLUMN_FB_CHECKIN_NAME));
        if (Integer.valueOf(likes) >= 1000 && Integer.valueOf(likes) <= 1000000) {
            int tempk = Integer.valueOf(likes)/1000;
            int tempn = (Integer.valueOf(likes)%1000)/100;
            tvCheckins.setText(tempk + "." + tempn + "k");

            //**change color with number of checkins. */
            if(likes >= 500000)
                view.setBackgroundColor(context.getResources().getColor(R.color.color4));
            else if(likes >= 100000)
                view.setBackgroundColor(context.getResources().getColor(R.color.color3));
        }
        else if (Integer.valueOf(likes) > 1000000) {
            int tempm = Integer.valueOf(likes)/1000000;
            int tempn = (Integer.valueOf(likes)%1000000)/100000;
            tvCheckins.setText(tempm + "." + tempn + "m");

            //**change color with number of checkins. */
            if(likes >= 50000000)
                view.setBackgroundColor(context.getResources().getColor(R.color.color1));
            else if(likes >= 10000000)
                view.setBackgroundColor(context.getResources().getColor(R.color.color2));
        }
        else {
            tvCheckins.setText(likes);
        }

        ckCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] tags = view.getTag().toString().split(",");
                final LatLng latLng = new LatLng(Double.valueOf(tags[1]), Double.valueOf(tags[2]));
                if (((CheckBox) view).isChecked()) {
                    NearbyActivity.sSelectedLocations.put(tags[0], latLng) ;
                    NearbyActivity.sSelectedLocationsName.add(tags[0]);
                } else {
                    NearbyActivity.sSelectedLocations.remove(tags[0]);
                    NearbyActivity.sSelectedLocationsName.remove(tags[0]);
                }
            }
        });
    }

    private String getLat(Cursor cursor) {
        return cursor.getString(nearbyFragment.COLUMN_FB_CHECKIN_LAT);
    }

    private String getLng(Cursor cursor) {
        return cursor.getString(nearbyFragment.COLUMN_FB_CHECKIN_LNG);
    }

    private String getName(Cursor cursor) {
        return cursor.getString(nearbyFragment.COLUMN_FB_CHECKIN_NAME);
    }
}