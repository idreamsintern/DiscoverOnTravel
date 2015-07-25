package com.example.idreams.dot.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deng-li on 2015/7/25.
 */
public class FetchBoardTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private Context mContext;

    public static List<String> sBoardName = new ArrayList<String>();
/*
    public FetchBoardTask(Context context) {
        //this 代表MainActivity
        mContext = context;boardName
    }
*/
    @Override
    protected Void doInBackground(String... params) {
        Document mDoc = null;
        Elements metaElems = null;

        try {
            mDoc  = Jsoup.connect("http://api.ser.ideas.iii.org.tw/docs/ptt_board_list.html").get();
            metaElems = mDoc.getElementsByTag("span");
            int boardSize = metaElems.size();
            //塞進boardName這個vector裡面
            for (int i = 1; i < boardSize; i++){
                sBoardName.add(metaElems.get(i).childNode(0).toString());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
        return null;
    }
}
