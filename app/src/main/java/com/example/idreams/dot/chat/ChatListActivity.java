package com.example.idreams.dot.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;

import java.util.ArrayList;

public class ChatListActivity extends BaseActivity {

    private RecyclerView chatListRecyclerView;
    private ViewGroup sceneRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        setupWindowAnimations();
        sceneRoot = (LinearLayout) findViewById(R.id.chat_list_root);

        chatListRecyclerView = (RecyclerView) findViewById(R.id.rv_chat_list);

        ChatListRecyclerAdapter adapter = new ChatListRecyclerAdapter(this, getChatroomLists());
        chatListRecyclerView.setAdapter(adapter);
        chatListRecyclerView.setHasFixedSize(true);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Chatroom> getChatroomLists() {
        ArrayList<Chatroom> chatroomArrayList = new ArrayList<>();
        chatroomArrayList.add(new Chatroom("華山1914文化創意產業園區", "", R.drawable.chatroom1small));
        chatroomArrayList.add(new Chatroom("信義威秀影城", "", R.drawable.chatroom2small));
        chatroomArrayList.add(new Chatroom("西門町", "", R.drawable.chatroom3small));

        return chatroomArrayList;
    }

    private void setupWindowAnimations() {
        Explode explode = new Explode();
        Fade fade = new Fade();
        explode.setDuration(1000);
        getWindow().setExitTransition(fade);
        getWindow().setReenterTransition(fade);
    }
}
