package com.example.android.yugiohcardmarket.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yugiohcardmarket.item.Card;
import com.example.android.yugiohcardmarket.item.CardList;

import java.util.ArrayList;

/**
 * Created by termitavee on 15/01/17.
 */
public class DBQuery extends AsyncTask<String, Void, Boolean> {
    private DBOpenHelper DbHelper;
    SQLiteDatabase database;
    Activity parent;
    Context context;
    ContentValues values = new ContentValues();
    private final String INSERT = "1";
    private final String DELETE = "2";
    static boolean success;

    public DBQuery(Context context, Activity activity) {
        DbHelper = new DBOpenHelper(context);
        this.context = context;
        parent = activity;
    }


    public void open() throws SQLException {
        database = DbHelper.getWritableDatabase();
    }

    public void close() {
        if (DbHelper != null)
            DbHelper.close();
    }

    public boolean insertCard(Card mcard, int midList) {
        final Card card = mcard;
        final int idlist = midList;
        Log.i("insertCard", "insertCard ");
        success = false;

        open();

        values.clear();
        values.put(RelationEntry.COLUMN_CARD_ID, card.getId());
        values.put(RelationEntry.COLUMN_LIST_ID, idlist);

        database.insert(RelationEntry.TABLE_NAME, null, values);
        //if previous fails, this one is unnecesary
        values.clear();
        values.put(CardEntry.COLUMN_CARD_ID, card.getId());
        values.put(CardEntry.COLUMN_CARD_NAME, card.getName());
        values.put(CardEntry.COLUMN_CARD_IMAGE_URL, card.getImage());
        values.put(CardEntry.COLUMN_CARD_RARITY, card.getRarity());
        values.put(CardEntry.COLUMN_CARD_EXPANSION, card.getExpansion());
        values.put(CardEntry.COLUMN_CARD_PRICE_LOW, card.getPriceLow());
        values.put(CardEntry.COLUMN_CARD_PRICE_TREND, card.getPriceTrend());
        values.put(CardEntry.COLUMN_CARD_URL, card.getWeb());

        database.insert(CardEntry.TABLE_NAME, null, values);

        //insert relation in relation LOL


        success = true;
        Log.i("insertCard", "relation insertion success apparently ");


        return success;
    }

    //NO TOCAR
    public CardList createList(String name) {
        Log.i("DBQuery", "createList ");
        open();

        ContentValues values = new ContentValues();
        values.put(ListEntry.COLUMN_LIST_NAME, name);
        Log.i("createList", "values to insert: " + values);
        long ins = database.insert(ListEntry.TABLE_NAME, ListEntry.COLUMN_LIST_NAME, values);
        Log.i("createList", "rows inserted: " + ins);
        String[] column = {ListEntry._ID};
        String[] param = {ListEntry.COLUMN_LIST_NAME};
        Cursor cursor = database.query(ListEntry.TABLE_NAME, column, "_ID=?", param, null, null, ListEntry._ID, "1");

        int id = cursor.moveToFirst() ? cursor.getInt(1) : 0;
        close();

        CardList list = new CardList(name, id, 0);

        return list;

    }

    public int deleteList(int id) {
        Log.i("DBQuery", "deleteList ");
        open();

        String[] param = {id + ""};
        //delete a list
        int i = database.delete(ListEntry.TABLE_NAME, ListEntry._ID + "=?", param);
        //delete from relation
        database.delete(RelationEntry.TABLE_NAME, RelationEntry.COLUMN_LIST_ID + "=?", param);
        //TODO delete cards with no list
        //delete outer join

        close();


        return i;
    }

    public int deleteCard(int cardid, int listid) {
        open();
        String[] param = {cardid + "", listid + ""};


        //int i = database.delete(CardEntry.TABLE_NAME, CardEntry._ID + "=?", param);
        //TODO delete it from relation
        int i = database.delete(RelationEntry.TABLE_NAME, RelationEntry.COLUMN_CARD_ID + "=? AND " + RelationEntry.COLUMN_LIST_ID + "=?", param);

        return i;
    }

    public ArrayList<CardList> getAllLists() {
        Log.i("DBQuery", "getAllLists ");
        open();


        Cursor cursor = database.query(ListEntry.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<CardList> lists = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            int id;
            String name;
            int q;
            while (!cursor.isAfterLast()) {

                id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ListEntry._ID)));
                name = cursor.getString(cursor.getColumnIndex(ListEntry.COLUMN_LIST_NAME));
                q = cursor.getInt(cursor.getColumnIndex(ListEntry.COLUMN_LIST_NUMBER));


                lists.add(new CardList(name, id, q));
                cursor.moveToNext();
            }
        }

        close();
        return lists;
    }

    public ArrayList<Card> getAllCards(int listID) {

        Log.i("DBQuery", "getAllCards ");
        open();
        String[] param = {"" + listID};
        ArrayList<Card> lists = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_ID + ", " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_NAME + ", " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_IMAGE_URL + ", " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_RARITY + ", " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_EXPANSION + ", " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_PRICE_LOW + ", " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_PRICE_TREND + ", " +
                    CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_URL +
                    " FROM " + CardEntry.TABLE_NAME +
                    " INNER JOIN " + RelationEntry.TABLE_NAME + " ON " + CardEntry.TABLE_NAME + "." + CardEntry.COLUMN_CARD_ID + " = " + RelationEntry.TABLE_NAME + "." + RelationEntry.COLUMN_CARD_ID +
                    " INNER JOIN " + ListEntry.TABLE_NAME + " ON " + ListEntry.TABLE_NAME + "." + ListEntry._ID + " = " + RelationEntry.TABLE_NAME + "." + RelationEntry.COLUMN_LIST_ID +
                    " WHERE " + ListEntry.TABLE_NAME + "." + ListEntry._ID + " ='" + listID + "' ;";
            //Log.d(TAG, "DB: query = \n" + query.replace(", ",",\n  "));
            Cursor cursor = database.rawQuery(query, null);

            if (cursor != null) {
                cursor.moveToFirst();
                Log.i("getAllCards", "cursor.getCount=" + cursor.getCount());
                int id;
                String name;
                String image;
                String rarity;
                String expansion;
                double priceLow;
                double priceTrend;
                String web;
                for (int i = 0; i < cursor.getCount(); i++) {

                    //get data from cursor and manage it
                    id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_CARD_ID)));
                    name = cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_CARD_NAME));
                    image = cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_CARD_IMAGE_URL));
                    rarity = cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_CARD_RARITY));
                    expansion = cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_CARD_EXPANSION));
                    priceLow = cursor.getDouble(cursor.getColumnIndex(CardEntry.COLUMN_CARD_PRICE_LOW));
                    priceTrend = cursor.getDouble(cursor.getColumnIndex(CardEntry.COLUMN_CARD_PRICE_TREND));
                    web = cursor.getString(cursor.getColumnIndex(CardEntry.COLUMN_CARD_URL));

                    lists.add(new Card(id, name, image, rarity, expansion, priceLow, priceTrend, web));
                    cursor.moveToNext();
                }
            }


            return lists;
        } catch (Exception e) {
            Log.e("DBQuery.getAllCards", "error gettimg all cards for id=" + listID);
        } finally {
            close();
        }
        return lists;
    }

    protected Boolean doInBackground(String... action) {
        try {
            switch (action[0]) {
                case INSERT:
                    long a = database.insert(CardEntry.TABLE_NAME, null, values);

                    Log.i("doInBackground", "inserted " + a);
                    break;
                case DELETE:

                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}


