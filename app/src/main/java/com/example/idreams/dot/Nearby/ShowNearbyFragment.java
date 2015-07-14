package com.example.idreams.dot.Nearby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.idreams.dot.R;

public class ShowNearbyFragment extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_nearby, container, false);
        textView = (TextView) view.findViewById(R.id.textView);

        return view;
    }

    public void changeValue(String text) {
        textView.setText(text);
    }
}
