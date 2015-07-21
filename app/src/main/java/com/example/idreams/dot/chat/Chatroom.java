package com.example.idreams.dot.chat;

/**
 * Created by chichunchen on 2015/7/21.
 */
public class Chatroom {

    private String mTitle;
    private String mUsers;

    public Chatroom(String title, String users) {
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
