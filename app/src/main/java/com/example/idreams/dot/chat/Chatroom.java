package com.example.idreams.dot.chat;

import android.graphics.drawable.Drawable;

/**
 * Created by chichunchen on 2015/7/21.
 */
public class Chatroom {

    private String mTitle;
    private String mUsers;
    private int backgroundId;

    public Chatroom(String title, String users, int backgroundId) {
        mTitle = title;
        mUsers = users;
        this.backgroundId = backgroundId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUsers() {
        return mUsers;
    }

    public void setUsers(String users) {
        mUsers = users;
    }

    public int getBackground() {
        return backgroundId;
    }

    public void setBackground(int background) {
        this.backgroundId = background;
    }
}
