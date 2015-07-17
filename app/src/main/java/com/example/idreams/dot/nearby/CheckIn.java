package com.example.idreams.dot.nearby;

/**
 * Created by chichunchen on 2015/7/15.
 */
public class CheckIn {
    public String name;
    public String id;
    public String checkins;

    public CheckIn(String name, String id, String checkins) {
        this.name = name;
        this.id = id;
        if (Integer.valueOf(checkins) >= 1000) {
            int temp = Integer.valueOf(checkins) / 1000;
            this.checkins = String.valueOf(temp) + "k";
        } else
            this.checkins = checkins;
    }
}
