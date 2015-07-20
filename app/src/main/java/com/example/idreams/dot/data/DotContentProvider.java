package com.example.idreams.dot.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.example.idreams.dot.data.DotDbContract.FbCheckinEntry;
import com.example.idreams.dot.data.DotDbContract.TopArticleEntry;

public class DotContentProvider  extends ContentProvider {
    // The URI Matcher used by this content provider.
    private DotDbHelper mDotDbHelper;
    private static final SQLiteQueryBuilder sQueryBuilder;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int FB_CHECKIN = 100;
    static final int FB_CHECKIN_SEARCH_CATEGORY = 101;
    static final int FB_CHECKIN_SEARCH_KEYWORD = 102;
    static final int FB_CHECKIN_SEARCH_CATEGORY_KEYWORD = 103;
    static final int TOP_ARTICLE = 200;
    static final int TOP_ARTICLE_SEARCH_SOURCE = 201;

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sQueryBuilder.setTables(FbCheckinEntry.TABLE_NAME);
    }

    //location.location_setting = ?
    private static final String sCategorySelection =
            FbCheckinEntry.TABLE_NAME + "." + FbCheckinEntry.COLUMN_CATEGORY + " = ? ";
    private static final String sKeywordSelection =
            FbCheckinEntry.TABLE_NAME + "." + FbCheckinEntry.COLUMN_NAME + " LIKE '%?%' ";
    private static final String sCategoryKeywordSelection =
            FbCheckinEntry.TABLE_NAME + "." + FbCheckinEntry.COLUMN_CATEGORY + " = ? AND " +
            FbCheckinEntry.COLUMN_NAME + " LIKE '%?%' ";
    private static final String sSourceSelection =
            TopArticleEntry.TABLE_NAME + "." + TopArticleEntry.COLUMN_SOURCE + " = ? ";

    private Cursor getFbCheckinByCategory(Uri uri, String[] projection, String sortOrder) {
        String categorySetting = FbCheckinEntry.getCategoryFromUri(uri);

        return sQueryBuilder.query(mDotDbHelper.getReadableDatabase(),
                projection,
                sCategorySelection,
                new String[]{categorySetting},
                null, null, sortOrder
        );
    }

    private Cursor getFbCheckinByKeyword(Uri uri, String[] projection, String sortOrder) {
        String keywordSetting  = FbCheckinEntry.getKeywordFromUri(uri);

        return sQueryBuilder.query(mDotDbHelper.getReadableDatabase(),
                projection,
                sKeywordSelection,
                new String[]{keywordSetting},
                null, null, sortOrder
        );
    }

    private Cursor getFbCheckinByCategoryKeyword(Uri uri, String[] projection, String sortOrder) {
        String categorySetting = FbCheckinEntry.getCategoryFromUri(uri);
        String keywordSetting  = FbCheckinEntry.getKeywordFromUri(uri);

        return sQueryBuilder.query(mDotDbHelper.getReadableDatabase(),
                projection,
                sCategoryKeywordSelection,
                new String[]{categorySetting, keywordSetting},
                null, null, sortOrder
        );
    }

    private Cursor getTopArticleByKeyword(Uri uri, String[] projection, String sortOrder) {
        String sourceSetting  = TopArticleEntry.getSourceSettingFromUri(uri);

        return sQueryBuilder.query(mDotDbHelper.getReadableDatabase(),
                projection,
                sSourceSelection,
                new String[]{sourceSetting},
                null, null, sortOrder
        );
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DotDbContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DotDbContract.PATH_FB_CHECKIN, FB_CHECKIN);
        matcher.addURI(authority, DotDbContract.PATH_FB_CHECKIN + "/category/*", FB_CHECKIN_SEARCH_CATEGORY);
        matcher.addURI(authority, DotDbContract.PATH_FB_CHECKIN + "/keyword/*", FB_CHECKIN_SEARCH_KEYWORD);
        matcher.addURI(authority, DotDbContract.PATH_FB_CHECKIN + "/*/*", FB_CHECKIN_SEARCH_CATEGORY_KEYWORD);
        matcher.addURI(authority, DotDbContract.PATH_TOP_ARTICLE, TOP_ARTICLE);
        matcher.addURI(authority, DotDbContract.PATH_TOP_ARTICLE + "/*", TOP_ARTICLE_SEARCH_SOURCE);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mDotDbHelper = new DotDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case FB_CHECKIN:
            case FB_CHECKIN_SEARCH_CATEGORY:
            case FB_CHECKIN_SEARCH_KEYWORD:
            case FB_CHECKIN_SEARCH_CATEGORY_KEYWORD:
                return FbCheckinEntry.CONTENT_TYPE;
            case TOP_ARTICLE:
            case TOP_ARTICLE_SEARCH_SOURCE:
                return TopArticleEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "fb_checkin"
            case FB_CHECKIN: {
                retCursor = mDotDbHelper.getReadableDatabase().query(
                        FbCheckinEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            }
            // "fb_checkin/category/*"
            case FB_CHECKIN_SEARCH_CATEGORY: {
                retCursor = getFbCheckinByCategory(uri, projection, sortOrder);        break;
            }
            // "fb_checkin/keyword/*"
            case FB_CHECKIN_SEARCH_KEYWORD: {
                retCursor = getFbCheckinByKeyword(uri, projection, sortOrder);         break;
            }
            // "fb_checkin/*/*"
            case FB_CHECKIN_SEARCH_CATEGORY_KEYWORD: {
                retCursor = getFbCheckinByCategoryKeyword(uri, projection, sortOrder); break;
            }
            // "top_article"
            case TOP_ARTICLE:  {
                retCursor = mDotDbHelper.getReadableDatabase().query(
                        FbCheckinEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            }
            // "top_article/*"
            case TOP_ARTICLE_SEARCH_SOURCE: {
                retCursor =  getTopArticleByKeyword(uri, projection, sortOrder);       break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDotDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FB_CHECKIN: {
                long _id = db.insert(FbCheckinEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FbCheckinEntry.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TOP_ARTICLE: {
                long _id = db.insert(TopArticleEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = TopArticleEntry.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDotDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if ( null == selection ) selection = "1";
        switch (match) {
            case FB_CHECKIN:
                rowsDeleted = db.delete(FbCheckinEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TOP_ARTICLE:
                rowsDeleted = db.delete(TopArticleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDotDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case FB_CHECKIN:
                rowsUpdated = db.update(FbCheckinEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TOP_ARTICLE:
                rowsUpdated = db.update(TopArticleEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mDotDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case FB_CHECKIN:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(FbCheckinEntry.TABLE_NAME, null, value);
                        if (_id != -1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case TOP_ARTICLE:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(TopArticleEntry.TABLE_NAME, null, value);
                        if (_id != -1)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mDotDbHelper.close();
        super.shutdown();
    }
}
