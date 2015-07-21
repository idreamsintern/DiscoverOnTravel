package com.example.idreams.dot.chat;

import android.graphics.drawable.Drawable;

/**
 * Created by chichunchen on 2015/7/21.
 */
public class Chatroom {

    private String mTitle;
    private String mUsers;
    private Drawable background;

    public Chatroom(String title, String users, Drawable background) {
        mTitle = title;
        mUsers = users;
        this.background = background;
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

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }
}
