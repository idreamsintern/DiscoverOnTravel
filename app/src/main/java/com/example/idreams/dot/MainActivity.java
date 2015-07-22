package com.example.idreams.dot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.idreams.dot.chat.ChatListActivity;
import com.example.idreams.dot.localtopics.BoardActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.example.idreams.dot.utils.GetToken;


public class MainActivity extends BaseActivity implements ButtonActivity.MyInterface {

    public static final int INDEX_SIMPLE_LOGIN = 0;
    public static final String FRAGMENT_TAG = "fragment_tag";
    private static final String STATE_SELECTED_FRAGMENT_INDEX = "selected_fragment_index";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String tokenstring = "api_doc_token";
    TextView tv;
    private FragmentManager mFragmentManager;
    private Animation fadein,fadeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton man = (ImageButton) findViewById(R.id.man);
        final TextView d = (TextView) findViewById(R.id.d);
        final TextView o = (TextView) findViewById(R.id.o);
        final TextView t = (TextView) findViewById(R.id.t);
        //texttype
        Typeface tf = Typeface.createFromAsset(getAssets(),"logofont.ttf");
        d.setTypeface(tf);
        o.setTypeface(tf);
        t.setTypeface(tf);
        //text appear effect
        fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        d.startAnimation(fadein);
        o.startAnimation(fadein);
        t.startAnimation(fadein);
        man.startAnimation(fadein);
        /*
        //Ibutton
        //Animation path(x1,x2,y1,y2)
        final Animation fadein = new TranslateAnimation(300, 10, 10, 10);
        //Animation Duration
        fadein.setDuration(2000);
        // Animation Repeat times (-1:non stop，0:once)
        fadein.setRepeatCount(0);
        //Set anim of ImageButton
        man.setAnimation(fadein);
        //start anim
        fadein.startNow();
        */


        //ImageButton:man
        man.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view fade out and another start
                d.setText("Discover");
                d.startAnimation(fadeout);
                o.startAnimation(fadeout);
                t.startAnimation(fadeout);
                man.startAnimation(fadeout);
                fadeout.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        pg1();
                    }
                });

            }
        });
        GetToken.getToken();

    }
    public void pg1(){

        setContentView(R.layout.page1);

        Animation plane_in = new TranslateAnimation(300, 10, 10, 10);
        plane_in.setDuration(2000);
        plane_in.setRepeatCount(0);
        final ImageButton p1next = (ImageButton) findViewById(R.id.p1next);
        p1next.setAnimation(plane_in);
        plane_in.startNow();
        TextView q1 = (TextView) findViewById(R.id.q1);
        q1.setTextColor(Color.RED);
        final TextView a1 = (TextView) findViewById(R.id.a1);
        a1.setTextColor(Color.BLUE);
        q1.startAnimation(fadein);
        a1.startAnimation(fadein);
        p1next.setOnClickListener(new ImageButton.OnClickListener() {

            public void onClick(View v) {
                a1.setText("Cantact people around here!");
                Animation next = new TranslateAnimation(10, -600, 20, 20);
                next.setDuration(2000);
                next.setRepeatCount(0);
                next.setFillAfter(true);
                p1next.setAnimation(next);
                next.startNow();
                next.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        pg2();
                    }
                });



            }

        });

    }
    public void pg2(){
        setContentView(R.layout.page2);
        Animation plane_in = new TranslateAnimation(300, 10, 10, 10);
        plane_in.setDuration(2000);
        plane_in.setRepeatCount(0);
        final ImageButton p2next = (ImageButton) findViewById(R.id.p2next);
        p2next.setAnimation(plane_in);
        plane_in.startNow();
        TextView q2 = (TextView) findViewById(R.id.q2);
        q2.setTextColor(Color.RED);
        final TextView a2 = (TextView) findViewById(R.id.a2);
        a2.setTextColor(Color.BLUE);
        q2.startAnimation(fadein);
        a2.startAnimation(fadein);
        p2next.setOnClickListener(new ImageButton.OnClickListener() {

            public void onClick(View v) {
                a2.setText("All here!");
                Animation next = new TranslateAnimation(10, -600, 20, 20);
                next.setDuration(2000);
                next.setRepeatCount(0);
                next.setFillAfter(true);
                p2next.setAnimation(next);
                next.startNow();
                next.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        pg3();
                    }
                });



            }

        });

    }
    public void pg3(){
        setContentView(R.layout.page3);
        Animation plane_in = new TranslateAnimation(300, 10, 10, 10);
        plane_in.setDuration(2000);
        plane_in.setRepeatCount(0);
        final ImageButton p3next = (ImageButton) findViewById(R.id.p3next);
        p3next.setAnimation(plane_in);
        plane_in.startNow();
        TextView q3 = (TextView) findViewById(R.id.q3);
        q3.setTextColor(Color.RED);
        final TextView a3 = (TextView) findViewById(R.id.a3);
        a3.setTextColor(Color.BLUE);
        q3.startAnimation(fadein);
        a3.startAnimation(fadein);
        p3next.setOnClickListener(new ImageButton.OnClickListener() {

            public void onClick(View v) {
                a3.setText("See hot topics");
                Animation next = new TranslateAnimation(10, -600, 20, 20);
                next.setDuration(2000);
                next.setRepeatCount(0);
                next.setFillAfter(true);
                p3next.setAnimation(next);
                //開始動畫
                next.startNow();
                next.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        setContentView(R.layout.menucontainer);
                        ButtonActivity fragment = new ButtonActivity();
                        mFragmentManager = getSupportFragmentManager();
                        toggleFragment(INDEX_SIMPLE_LOGIN);
                    }
                });



            }

        });

    }

    public void getMessage(String msg) {
        String mUsername = "";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //mUsername = prefs.getString("username", "traveller");
        prefs.edit().putString("username", msg).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GetToken.getToken();
    }


    public void nearbyBtn(View view) {
        Intent intent = new Intent(this, NearbyActivity.class);
        startActivity(intent);
    }

    public void localBtn(View view) {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }

    public void chatBtn(View view) {
        Intent intent = new Intent(this, ChatListActivity.class);
        startActivity(intent);
    }

    private void toggleFragment(int index) {
        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index) {
            case INDEX_SIMPLE_LOGIN:
                transaction.replace(android.R.id.content, new ButtonActivity(), FRAGMENT_TAG);
                break;

        }
        transaction.commit();
    }
}
