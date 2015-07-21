package com.example.idreams.dot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.idreams.dot.data.DotDbContract.FbCheckinEntry;
import com.example.idreams.dot.data.DotDbContract.TopArticleEntry;

public class DotDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;

    static final String DATABASE_NAME = "dot.db";

    public DotDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FB_CHECKIN_TABLE =
                "CREATE TABLE " + FbCheckinEntry.TABLE_NAME + " (" +
                        FbCheckinEntry._ID + " INTEGER PRIMARY KEY," +
                        FbCheckinEntry.COLUMN_ID + " TEXT UNIQUE NOT NULL, " +
                        FbCheckinEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        FbCheckinEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                        FbCheckinEntry.COLUMN_LAT + " TEXT NOT NULL, " +
                        FbCheckinEntry.COLUMN_LNG + " TEXT NOT NULL, " +
                        FbCheckinEntry.COLUMN_CHECKINS + " INT  NOT NULL, " +
                        FbCheckinEntry.COLUMN_CHECKINS_UPCOUNT + " INT NOT NULL, " +
                        FbCheckinEntry.COLUMN_STARTDATE + " TEXT NOT NULL" +
                        " );";
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + TopArticleEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                TopArticleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TopArticleEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                TopArticleEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                TopArticleEntry.COLUMN_URL + " TEXT NOT NULL, " +
                TopArticleEntry.COLUMN_PUSH + " INTEGER NOT NULL," +
                TopArticleEntry.COLUMN_SOURCE + " TEXT NOT NULL " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_FB_CHECKIN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FbCheckinEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TopArticleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
