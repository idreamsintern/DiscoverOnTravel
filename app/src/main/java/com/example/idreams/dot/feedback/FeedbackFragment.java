package com.example.idreams.dot.feedback;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.idreams.dot.R;
import com.facebook.CallbackManager;

public class FeedbackFragment extends Fragment {


    public FeedbackFragmentCallbacks myInterface;
    private CallbackManager mCallbackManager;
    public FeedbackFragment() {
    }

    public void onAttach(Activity activity) {
        myInterface = (FeedbackFragmentCallbacks) activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setBackgroundColor(Color.WHITE);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static interface FeedbackFragmentCallbacks {
    }
}
