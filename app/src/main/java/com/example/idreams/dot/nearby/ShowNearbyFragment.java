package com.example.idreams.dot.nearby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.idreams.dot.R;

import java.util.ArrayList;

public class ShowNearbyFragment extends Fragment {

    private ListView nearbyListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_nearby, container, false);
        // Construct the data source
        ArrayList<CheckIn> arrayOfCheckins = new ArrayList<CheckIn>();
        // Create the adapter to convert the array to views

        ShowNearbyAdapter adapter = new ShowNearbyAdapter(getActivity(), arrayOfCheckins);

        // Add item to adapter
        CheckIn newUser = new CheckIn("Nathan", "San Diego", "sss");
        CheckIn newUser2 = new CheckIn("Nathan", "San Diego", "sss");
        adapter.add(newUser);
        adapter.add(newUser2);

        // Attach the adapter to a ListView
        nearbyListView = (ListView) view.findViewById(R.id.show_nearby_listview);
        nearbyListView.setAdapter(adapter);



        return view;
    }

    public void changeValue(String text) {

    }
}
