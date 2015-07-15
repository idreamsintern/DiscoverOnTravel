package com.example.idreams.dot.localtopics;

/**
 * Created by chichunchen on 2015/7/15.
 */
public class Topic {
    public String board;
    public String title;
    public String url;
    public String push;
    public String time;
    // private String details;

    public Topic(String board, String title,String url, String push,String time) {
        this.board = board;
        this.title = title;
        this.url = url;
        this.push = push;
        this.time = time;
    }
}
