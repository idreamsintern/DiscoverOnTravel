package com.example.idreams.dot;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    public MainFragmentCallbacks myInterface;
    private CallbackManager      mCallbackManager;
    private AccessTokenTracker   mTokenTracker;
    private ProfileTracker       mProfileTracker;
    private FacebookCallback<LoginResult> mFacebookCallback;
    public MainFragment() {
    }

    public void onAttach(Activity activity) {
        myInterface = (MainFragmentCallbacks) activity;
        mFacebookCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("VIVZ", "onSuccess");
            }

            @Override
            public void onCancel() {
                Log.d("VIVZ", "onCancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("VIVZ", "onError " + e);
            }
        };
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        myInterface.onFacebookLogin();//transfer data to actAppCompatButton v = (AppCompatButton) findViewById(R.id.mybutton);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupLoginButton(view);
        myInterface.onFragmentViewCreated(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        myInterface.onFacebookLogin();
        LoginButton loginButton = (LoginButton) getActivity().findViewById(R.id.login_button);
        Animation darkenAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.back_alpha_lower);
        darkenAnim.setFillAfter(true);
        loginButton.startAnimation(darkenAnim);
    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("VIVZ", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("VIVZ", "" + currentProfile);
            }
        };
    }

    private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.login_button);
        mButtonLogin.setFragment(this);
        mButtonLogin.setReadPermissions("user_friends");
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
    }

    public static interface MainFragmentCallbacks {
        public void onFacebookLogin();
        public void onFragmentViewCreated(View view);
    }
}
