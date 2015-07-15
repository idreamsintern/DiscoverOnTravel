package com.example.idreams.dot.localtopics;

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
public class TopicAdapter extends ArrayAdapter<Topic> {
    public TopicAdapter(Context context, ArrayList<Topic> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Topic topic = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_board);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tv_topic_name);
        // Populate the data into the template view using the data object
        tvName.setText(topic.board);
        tvHome.setText(topic.topicname);
        // Return the completed view to render on screen
        return convertView;
    }
}