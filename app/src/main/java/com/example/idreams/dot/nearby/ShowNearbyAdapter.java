package com.example.idreams.dot.nearby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.idreams.dot.R;

import java.util.ArrayList;

/**
 * Created by chichunchen on 2015/7/15.
 */
public class ShowNearbyAdapter extends ArrayAdapter<CheckIn> {
    public ShowNearbyAdapter(Context context, ArrayList<CheckIn> checkins) {
        super(context, 0, checkins);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CheckIn checkins = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.show_nearby_row, parent, false);
        }
        // Lookup view for data population
        TextView tvId = (TextView) convertView.findViewById(R.id.show_id);
        TextView tvName = (TextView) convertView.findViewById(R.id.show_name);
        TextView tvCheckins = (TextView) convertView.findViewById(R.id.show_checkins);
        // Populate the data into the template view using the data object
        tvId.setText(checkins.id);
        tvName.setText(checkins.name);
        tvCheckins.setText(checkins.checkins);
        // Return the completed view to render on screen
        return convertView;
    }
}