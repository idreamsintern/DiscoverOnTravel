package com.example.idreams.dot.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;

import java.util.ArrayList;

public class ChatListActivity extends BaseActivity {

    private RecyclerView chatListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListRecyclerView = (RecyclerView) findViewById(R.id.rv_chat_list);

        ChatListRecyclerAdapter adapter = new ChatListRecyclerAdapter(this, getChatroomLists());
        chatListRecyclerView.setAdapter(adapter);
        chatListRecyclerView.setHasFixedSize(true);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Chatroom> getChatroomLists() {
        ArrayList<Chatroom> chatroomArrayList = new ArrayList<>();
        chatroomArrayList.add(new Chatroom("華山1914文化創意產業園區", "desp1", getDrawable(R.drawable.card_background_1)));
        chatroomArrayList.add(new Chatroom("信義威秀影城", "desp1", getDrawable(R.drawable.card_background_2)));
        chatroomArrayList.add(new Chatroom("西門町", "desp1", getDrawable(R.drawable.card_background_3)));

        return chatroomArrayList;
    }
}
