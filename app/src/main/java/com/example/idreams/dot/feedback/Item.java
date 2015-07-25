package com.example.idreams.dot.feedback;

/**
 * Created by sucianpei on 2015/7/25.
 */
public class Item {
    private String title,discription;
    public Item(String mtitle,String mdiscription){
        title=mtitle;
        discription=mdiscription;
    }
    public String getTitle(){
        String mTitle;
        mTitle=this.title;
        return mTitle;
    }
    public String getDiscription(){
        String mDiscription;
        mDiscription=this.discription;
        return mDiscription;
    }
}
