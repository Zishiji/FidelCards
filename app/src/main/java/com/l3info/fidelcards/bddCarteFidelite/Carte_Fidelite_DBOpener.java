package com.l3info.fidelcards.bddCarteFidelite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 21/02/2016.
 */
public final class Carte_Fidelite_DBOpener extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FiCards.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Carte_Fidelite_FeedEntry.TABLE_NAME + " (" +
                    Carte_Fidelite_FeedEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Carte_Fidelite_FeedEntry.COLUMN_CARD_NAME + " TEXT," +
                    Carte_Fidelite_FeedEntry.COLUMN_CARD_IMG + " TEXT" + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Carte_Fidelite_FeedEntry.TABLE_NAME;

    public Carte_Fidelite_DBOpener(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ENTRIES); }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
