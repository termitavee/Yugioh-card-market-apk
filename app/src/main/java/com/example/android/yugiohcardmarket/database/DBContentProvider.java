
package com.example.android.yugiohcardmarket.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by termitavee on 25/01/17.
 */
//TODO maneja la base de datos
public class DBContentProvider extends ContentProvider {
    private DBOpenHelper database;

    // used for the UriMacher
    private static final int LISTS = 100;
    private static final int LIST = 110;
    private static final int CARD = 111;

    private static final String AUTHORITY = "com.example.android.yugiohcardmarket";

    private static final String LIST_PATH = "list";
    private static final String CARD_PATH = "card";
    private static final String REL_PATH = "rel";

    private static final Uri LIST_URI = Uri.parse("content://" + AUTHORITY + "/" + LIST_PATH);
    private static final Uri CARD_URI = Uri.parse("content://" + AUTHORITY + "/" + CARD_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, LIST_PATH, LISTS);
        sURIMatcher.addURI(AUTHORITY, LIST_PATH + "/#", LIST);
        sURIMatcher.addURI(AUTHORITY, CARD_PATH + "/#", CARD);
    }

    //TODO para el activity
    //el activity pasará esto y parametros
    public static final String CONTENT_LISTS_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/lists";
    public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/list";
    public static final String CONTENT_CARD_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/card";

    @Override
    public boolean onCreate() {
        database = new DBOpenHelper(getContext());
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        /*
         * (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
         * projection  list of columns to return
         * selection where clause without 'where'
         * selectionArgs wheres
         * sortOrder  How to order SQL ORDER BY clause
         * SELECT projection FROM todoTabla WHERE selection AND sortOrder;
         * sustituir selectionArgs
         */

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case LISTS:
                //get all list
                queryBuilder.setTables(ListEntry.TABLE_NAME);
                break;
            case LIST:

                queryBuilder.setTables(ListEntry.TABLE_NAME + "," + CardEntry.TABLE_NAME);
                queryBuilder.appendWhere(ListEntry._ID + "=" + uri.getLastPathSegment());
                queryBuilder.appendWhere(ListEntry._ID + "=" + RelationEntry.COLUMN_CARD_ID);
                queryBuilder.appendWhere(CardEntry._ID + "=" + RelationEntry.COLUMN_CARD_ID);
                break;
            case CARD:
                queryBuilder.setTables(CardEntry.TABLE_NAME);
                queryBuilder.appendWhere(CardEntry._ID + "="
                        + uri.getLastPathSegment());

                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }
    public void asdf(){

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /*url, params*/
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        String path;
        long id = 0;
        switch (uriType) {
            case LIST:
                id = sqlDB.insert(ListEntry.TABLE_NAME, null, values);
                path = LIST_PATH;
                break;
            case CARD:
                id = sqlDB.insert(CardEntry.TABLE_NAME, null, values);

                path = CARD_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(path + "/" + id);

        //return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        /*url, select?, params*/
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted;
        String[] id = {uri.getLastPathSegment()};
        switch (uriType) {
            case CARD:
                rowsDeleted = sqlDB.delete(CardEntry.TABLE_NAME, CardEntry._ID + "=?", id);
                break;
            case LIST:
                rowsDeleted = sqlDB.delete(ListEntry.TABLE_NAME, ListEntry._ID + "=?", id);
                sqlDB.delete(RelationEntry.TABLE_NAME, ListEntry._ID + "=?", id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        /*url,value insert, where?, params*/
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated;
        String[] id = {uri.getLastPathSegment()};
        switch (uriType) {
            case CARD:
                rowsUpdated = sqlDB.update(CardEntry.TABLE_NAME,
                        values,
                        CardEntry._ID + "=?",
                        id);
                break;
            case LIST:
                rowsUpdated = sqlDB.update(ListEntry.TABLE_NAME,
                        values,
                        ListEntry._ID + "=?",
                        id);

                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;

    }


}
