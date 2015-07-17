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
            int tempk = Integer.valueOf(checkins)/1000;
            int tempn = (Integer.valueOf(checkins)%1000)/100;
            this.checkins = String.valueOf(tempk) + "," + tempn + "k";
        } else
            this.checkins = checkins;
    }
}
