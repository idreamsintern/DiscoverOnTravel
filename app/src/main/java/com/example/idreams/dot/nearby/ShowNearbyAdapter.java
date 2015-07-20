package com.example.idreams.dot.nearby;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idreams.dot.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class ShowNearbyAdapter extends ArrayAdapter<CheckIn> {
    private CheckIn currentCheckIn;

    public ShowNearbyAdapter(Context context, ArrayList<CheckIn> checkins) {
        super(context, 0, checkins);
    }

    public HashMap<String, LatLng> getSelectedLocations () {
        return NearbyActivity.mSelectedLocations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        currentCheckIn = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.show_nearby_row, parent, false);
        }
        CheckBox ckCheck = (CheckBox) convertView.findViewById(R.id.checkbox);
        ckCheck.setTag(position);
        TextView tvName = (TextView) convertView.findViewById(R.id.show_name);

        InputFilter[] namefilter = new InputFilter[1];
        namefilter[0] = new InputFilter.LengthFilter(11);
        tvName.setFilters(namefilter);

        TextView tvCheckins = (TextView) convertView.findViewById(R.id.show_checkins);
        ckCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //is chkIos checked?
                currentCheckIn = getItem((Integer) view.getTag());
                if (((CheckBox) view).isChecked()) {
                    // Toast.makeText(getContext(), "Check", Toast.LENGTH_LONG).show();
                    NearbyActivity.mSelectedLocations.put(currentCheckIn.id, currentCheckIn.location);
                    NearbyActivity.mSelectedLocationsName.add(currentCheckIn.name);
                } else {
                    NearbyActivity.mSelectedLocations.remove(currentCheckIn.id);
                    NearbyActivity.mSelectedLocationsName.remove(currentCheckIn.name);
                }
            }
        });

        //**change color with number of checkins. */
        if(Integer.valueOf(currentCheckIn.likenum) >= 50000)
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color3));
        else if(Integer.valueOf(currentCheckIn.likenum) >= 10000)
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color2));
        //**change color with number of checkins. */

        tvName.setText(currentCheckIn.name);
        tvCheckins.setText(currentCheckIn.like);

        return convertView;
    }
}