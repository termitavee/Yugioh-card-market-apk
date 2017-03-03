package com.example.android.yugiohcardmarket.database;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.android.yugiohcardmarket.item.Card;
import com.example.android.yugiohcardmarket.item.CardList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by termitavee on 15/01/17.
 * http://www.vogella.com/tutorials/AndroidSQLite/article.html
 * https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
 */
//TODO usar el content provider para meter y sacar los datos necesarios CommentsDataSource
public class DBQuery {
    private DBOpenHelper DbHelper;
    private SQLiteDatabase database;
    private ContentResolver contentResolver;
    //private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public DBQuery(Context context, Activity activity) {
        DbHelper = new DBOpenHelper(context);
        contentResolver = activity.getContentResolver();
    }


    public void open() throws SQLException {
        database = DbHelper.getWritableDatabase();
    }

    public void close() {
        if (DbHelper != null)
            DbHelper.close();
    }

    public void insertCard(String arg) {
        //TODO insert a card in a list
        //TODO if exist ommit cart insertion butt add to the relation

    }

    public void createList(String arg) {
        //TODO create a empty list

    }

    public void deleteList(String arg) {
        //TODO delete a list

        //TODO delete from relation

        //TODO check all cards in the list
    }

    public void deleteCard(String arg) {
        //TODO delete card from a list

        //TODO delete it from relation

        //TODO check if that card exist in any relation, if not, delete from table list


    }

    //TODO getters api
    /*
     * (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
     * projection  list of columns to return
     * selection where clause without 'where'
     * selectionArgs wheres
     * sortOrder  How to order SQL ORDER BY clause
     *
     * SELECT projection FROM todoTabla WHERE selection AND sortOrder;
     * sustituir selectionArgs
     */

    public ArrayList<CardList> getLists() {

        Uri uri = Uri.parse(DBContentProvider.CONTENT_LISTS_TYPE);
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        ArrayList<CardList> lists = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            int id;
            String name;
            while (!cursor.isAfterLast()) {

                //TODO get data from cursor and manage it
                id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ListEntry._ID)));
                name = cursor.getString(cursor.getColumnIndex(ListEntry.COLUMN_LIST_NAME));


                lists.add(new CardList(name,id));
                cursor.moveToNext();
            }
        }


        return lists;
    }


    public List<Card> getAllCards(int listID) {
        String[] columnas = {"String[]", ""};
        //TODO get all cards of a list
        Cursor cursor = database.query("tabla", columnas, "Condicion", null, null, null, null);

        while (!cursor.isAfterLast()) {

            //TODO get data from cursor and manage it
            //Comment comment = cursorToComment(cursor);
            // comments.add(comment);
            cursor.moveToNext();
        }


        return null;
    }

}
