package com.example.idreams.dot.nearby;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.idreams.dot.R;
import com.example.idreams.dot.navigate.NavigateActivity;

public class NearbyScheduleBar extends Fragment implements View.OnClickListener {

    private Button navigateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_schedule_bar, container, false);
        navigateButton = (Button) view.findViewById(R.id.navigate_btn);
        navigateButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NavigateActivity.class);
        startActivity(intent);
    }
}
