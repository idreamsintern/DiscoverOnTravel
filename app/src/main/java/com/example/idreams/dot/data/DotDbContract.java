package com.example.idreams.dot.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by schwannden on 2015/7/20.
 */
public class DotDbContract {
    public static final String CONTENT_AUTHORITY = "com.example.idreams.dot";
    public static final String REMOTE_CONTENT_AUTHORITY = "api.ser.ideas.iii.org.tw:80/api";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri REMOTE_BASE_CONTENT_URI = Uri.parse("http://" + CONTENT_AUTHORITY);
    public static final String PATH_FB_CHECKIN = "fb_checkin";
    public static final String PATH_TOP_ARTICLE = "top_article";

    public static final class FbCheckinEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FB_CHECKIN).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FB_CHECKIN;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FB_CHECKIN;
        public static final String TABLE_NAME = "fb_checkin";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_KEYWORD = "category";
        public static final String COLUMN_LAT = "latitude";
        public static final String COLUMN_LNG = "longitude";
        public static final String COLUMN_CHECKINS = "checkins";
        public static final String COLUMN_CHECKINS_UPCOUNT = "checkins_upcount";
        public static final String COLUMN_STARTDATE = "startdate";


        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri builCategorySearch(String category) {
            return CONTENT_URI.buildUpon().appendPath("category").appendPath(category).build();
        }

        public static Uri builKeywordSearch(String keyword) {
            return CONTENT_URI.buildUpon().appendPath("keyword").appendPath(keyword).build();
        }


        public static Uri buildCategoryKeyworkSearch(String category, String keyword) {
            return CONTENT_URI.buildUpon().appendPath(category).appendPath(keyword).build();
        }

        public static String getCategoryFromUri(Uri uri) {
            if (uri.getPathSegments().get(1) == "category")
                return uri.getPathSegments().get(2);
            else if (uri.getPathSegments().get(1) != "keyword")
                return uri.getPathSegments().get(1);
            return null;
        }

        public static String getKeywordFromUri(Uri uri) {
            if (uri.getPathSegments().get(1) == "category")
                return null;
            return uri.getPathSegments().get(2);
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class TopArticleEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_ARTICLE).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_ARTICLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_ARTICLE;

        public static final String TABLE_NAME    = "top_article";
        public static final String COLUMN_TIME   = "time";
        public static final String COLUMN_TITLE  = "title";
        public static final String COLUMN_URL    = "url";
        public static final String COLUMN_PUSH   = "push";
        public static final String COLUMN_SOURCE = "ptt";
        public static final String SOURCE_PTT      = "ptt";
        public static final String SOURCE_FACEBOOK = "facebook";
        public static final String SOURCE_FORUM    = "forum";
        public static final String SOURCE_news     = "news";

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTopArticleSource(String source) {
            return CONTENT_URI.buildUpon().appendPath(source).build();
        }

        public static Uri buildTopArticleWithTime(String source, String time) {
            return CONTENT_URI.buildUpon().appendPath(source)
                    .appendPath(time).build();
        }

        public static String getSourceSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getTimeFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }
    }
}
