package com.example.android.yugiohcardmarket.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by termitavee on 24/01/17.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ygo.db";
    private static final int DATABASE_VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        Log.i("DBOpenHelper", "onCreate database");
        ListEntry.onCreate(database);
        Log.i("DBOpenHelper", "ListEntry created");
        CardEntry.onCreate(database);
        Log.i("DBOpenHelper", "CardEntry created");
        RelationEntry.onCreate(database);
        Log.i("DBOpenHelper", "RelationEntry created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        ListEntry.onUpgrade(database, oldVersion, newVersion);
        CardEntry.onUpgrade(database, oldVersion, newVersion);
        RelationEntry.onUpgrade(database, oldVersion, newVersion);

    }

}


class ListEntry {

    static final String TABLE_NAME = "list";

    static final String _ID = BaseColumns._ID;

    static final String COLUMN_LIST_NAME = "name";
    static final String COLUMN_LIST_NUMBER = "quantity";


    private static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LIST_NAME + " TEXT NOT NULL, "
            + COLUMN_LIST_NUMBER + " INTEGER NOT NULL DEFAULT 0);";

    static private String SQL_DEFAULT_TABLE = "INSERT INTO "
            + TABLE_NAME + "(" + COLUMN_LIST_NAME + ", " + COLUMN_LIST_NUMBER + ")"
            +" VALUES('default',0);";

    static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE);
        database.execSQL(SQL_DEFAULT_TABLE);
    }

    static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }
}

class CardEntry {

    static final String TABLE_NAME = "card";

    static final String _ID = "id";
    static final String COLUMN_CARD_ID = "cardID";

    static final String COLUMN_CARD_NAME = "name";

    static final String COLUMN_CARD_IMAGE_URL = "imgURL";

    static final String COLUMN_CARD_RARITY = "rarity";

    static final String COLUMN_CARD_EXPANSION = "expansion";

    static final String COLUMN_CARD_PRICE_LOW = "priceLow";
    static final String COLUMN_CARD_PRICE_TREND = "priceTrend";

    static final String COLUMN_CARD_URL = "web";


    static private String SQL_CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CARD_ID + " INTEGER, "
            + COLUMN_CARD_NAME + " TEXT NOT NULL, "
            + COLUMN_CARD_IMAGE_URL + " TEXT NOT NULL, "
            + COLUMN_CARD_RARITY + " TEXT NOT NULL, "
            + COLUMN_CARD_EXPANSION + " TEXT NOT NULL, "
            + COLUMN_CARD_PRICE_LOW + " TEXT NOT NULL, "
            + COLUMN_CARD_PRICE_TREND + " TEXT NOT NULL, "
            + COLUMN_CARD_URL + " TEXT NOT NULL);";


    static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE);
    }

    static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }
}

class RelationEntry {

    static final String TABLE_NAME = "cards_per_list";

    static final String _ID = BaseColumns._ID;

    static final String COLUMN_CARD_ID = "card_id";

    static final String COLUMN_LIST_ID = "list_id";

    static private String SQL_CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CARD_ID + " TEXT NOT NULL, "
            + COLUMN_LIST_ID + " TEXT NOT NULL);";

    static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE);
    }

    static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }
}
