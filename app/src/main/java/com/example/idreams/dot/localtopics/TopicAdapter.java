package com.example.idreams.dot.localtopics;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.idreams.dot.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chichunchen on 2015/7/15.
 */
public class TopicAdapter extends ArrayAdapter<Topic> {
    private static final String LOG_TAG = "TopicAdapter";

    public TopicAdapter(Context context, ArrayList<Topic> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Log.e(LOG_TAG, "getView()");
        Topic topic = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_item, parent, false);
        }
        TextView tvTopicCategory = (TextView) convertView.findViewById(R.id.tv_topic_category);
        String line = topic.title;
        tvTopicCategory.setText(line);
        return convertView;
    }
}