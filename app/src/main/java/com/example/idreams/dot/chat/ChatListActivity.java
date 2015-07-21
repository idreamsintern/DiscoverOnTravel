package com.example.idreams.dot.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.utils.TemplateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
        chatroomArrayList.add(new Chatroom("title1", "desp1"));
        chatroomArrayList.add(new Chatroom("title2", "desp1"));
        chatroomArrayList.add(new Chatroom("title3", "desp1"));

        return chatroomArrayList;
    }
}
