package com.example.idreams.dot.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.idreams.dot.R;
import com.example.idreams.dot.utils.TemplateAdapter;

import java.util.List;

public class ChatListActivity extends AppCompatActivity
        implements TemplateAdapter.CreateViewFromCallback{

    public TemplateAdapter<ChatroomItem> mAdapter;
    public ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        mAdapter = new TemplateAdapter(this, R.layout.chat_list_item);
        mAdapter.add (new ChatroomItem("title1", "desp1"));
        mAdapter.add (new ChatroomItem("title2", "desp2"));
        mListView = (ListView) findViewById(R.id.lv_chatroom);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        View view;
        ChatroomItem chatroomItem = mAdapter.getItem(position);

        if(convertView == null) {
            view = mAdapter.getLayoutInflater().inflate(resource, parent, false);
        }
        else {
            view = convertView;
        }
        ((TextView)view.findViewById(R.id.tv_chatroomtitle)).setText(chatroomItem.getTitle());
        ((TextView)view.findViewById(R.id.tv_chatroomusers)).setText(chatroomItem.getUsers());
        return view;
    }




    private class ChatroomItem{
        private String mTitle;
        private String mUsers;

        public ChatroomItem(String title, String users){
            mTitle = title;
            mUsers = users;
        }

        public String getTitle(){
            return mTitle;
        }
        public void setTitle(String title){
            mTitle = title;
        }
        public String getUsers(){
            return mUsers;
        }
        public void setUsers(String users){
            mUsers = users;
        }


    }


}
