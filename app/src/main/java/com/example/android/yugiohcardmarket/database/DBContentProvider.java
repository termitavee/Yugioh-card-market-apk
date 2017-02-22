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
    private static final int FORBIDDEN_LIST = 101;
    private static final int OWN_LIST = 102;
    private static final int LIST_ID = 110;
    private static final int CARD_ID = 111;

    private static final String AUTHORITY = "com.example.android.yugiohcardmarket";

    private static final String LIST_PATH = "list";
    private static final String FORBIDDEN_PATH = "for";
    private static final String OWN_PATH = "own";
    private static final String CARD_PATH = "card";

    private static final Uri LIST_URI = Uri.parse("content://" + AUTHORITY + "/" + LIST_PATH);
    private static final Uri CARD_URI = Uri.parse("content://" + AUTHORITY + "/" + CARD_PATH);
    //TODO sURIMatcher.match(url); se comprueba la uri pasada con un switch
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, LIST_PATH, LISTS);
        sURIMatcher.addURI(AUTHORITY, FORBIDDEN_PATH + "/#", FORBIDDEN_LIST);
        sURIMatcher.addURI(AUTHORITY, OWN_PATH + "/#", OWN_LIST);
        sURIMatcher.addURI(AUTHORITY, LIST_PATH + "/#", LIST_ID);
        sURIMatcher.addURI(AUTHORITY, CARD_PATH + "/#", CARD_ID);
    }

    //TODO para el activity
    //el activity pasará esto y parametros
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/lists";
    public static final String CONTENT_LIST_FORBIDDEN = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/for";
    public static final String CONTENT_LIST_OWN = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/own";
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
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        //checkColumns(projection);

        // Set tables

        //queryBuilder.setTables(TodoTable.TABLE_TODO);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case LISTS:
                //get all list
                break;
            case FORBIDDEN_LIST:
                // get forbidden list
                break;
            case OWN_LIST:
                // get own list
                break;
            case LIST_ID:
                // adding the ID to the original query
                //queryBuilder.appendWhere(TodoTable.COLUMN_ID + "="
                //       + uri.getLastPathSegment());
                break;
            case CARD_ID:
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
        //return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
      /*  int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case LISTS:
                id = sqlDB.insert(TodoTable.TABLE_TODO, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);*/
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
     /*   int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case LISTS:
                rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO, selection,
                        selectionArgs);
                break;
            case LIST_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            TodoTable.TABLE_TODO,
                            TodoTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            TodoTable.TABLE_TODO,
                            TodoTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;*/
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
/*
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case LISTS:
                rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO,
                        values,
                        selection,
                        selectionArgs);
                break;
            case LIST_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO,
                            values,
                            TodoTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO,
                            values,
                            TodoTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;*/
        return 0;
    }
/*
    private void checkColumns(String[] projection) {
        String[] available = { TodoTable.COLUMN_CATEGORY,
                TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_DESCRIPTION,
                TodoTable.COLUMN_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }*/

}
