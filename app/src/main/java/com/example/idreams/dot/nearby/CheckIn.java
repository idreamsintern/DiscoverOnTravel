package com.example.idreams.dot.nearby;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by chichunchen on 2015/7/15.
 */
public class CheckIn {
    public String name;
    public String id;
    public String like;
    public LatLng location;
    public int tag;

    public CheckIn(String name, String id, String like, String lat, String lng) {
        this.name = name;
        this.id = id;
        if (Integer.valueOf(like) >= 1000) {
            int tempk = Integer.valueOf(like)/1000;
            int tempn = (Integer.valueOf(like)%1000)/100;
            this.like = String.valueOf(tempk) + "," + tempn + "k";
        } else
            this.like = like;
        location = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
    }
}
