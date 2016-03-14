package com.l3info.fidelcards.bdd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 21/02/2016.
 */
public class CardDB {

    SQLiteDatabase db;

    public CardDB(CardDBOpener cardDBOpener) {
        // Gets the data repository in write mode
        db = cardDBOpener.getWritableDatabase();
    }

    public long ajout_carte(String nom, String numero, String path) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_CARD_NAME, nom);
        values.put(FeedEntry.COLUMN_CARD_NUM, numero);
        values.put(FeedEntry.COLUMN_CARD_LOGO, path);
        values.put(FeedEntry.COLUMN_CARD_SHOP, "Micromania");

        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public void suppression_carte(String name) {

        String selection = FeedEntry.COLUMN_CARD_NAME + " LIKE ?";
        String[] selectionArgs = {name};
        db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public Cursor lecture_base() {
        return db.rawQuery("SELECT * FROM card", null);
    }

    public int taille_base() {
        Cursor c = lecture_base();
        return c.getCount();
    }

    public Cursor getCard(String name) {
        String[] projection = {FeedEntry.COLUMN_CARD_NAME};
        String selection = FeedEntry.COLUMN_CARD_NAME + " LIKE ?";
        String[] selectionArgs = {name};
        Cursor c = db.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, FeedEntry.COLUMN_ID + " DESC");
        return c;
    }

    public String getLogo(String name) {
        String[] projection = {FeedEntry.COLUMN_CARD_NAME};
        String selection = FeedEntry.COLUMN_CARD_NAME + " LIKE ?";
        String[] selectionArgs = {name};
        Cursor c = db.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, FeedEntry.COLUMN_ID + " DESC");
        return c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_LOGO));
    }

    public String getShop(String name) {
        String[] projection = {FeedEntry.COLUMN_CARD_NAME};
        String selection = FeedEntry.COLUMN_CARD_NAME + " LIKE ?";
        String[] selectionArgs = {name};
        Cursor c = db.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, FeedEntry.COLUMN_ID + " DESC");
        return c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_SHOP));
    }

    public boolean contient(String name) {
        String[] projection = {FeedEntry.COLUMN_CARD_NAME};
        String selection = FeedEntry.COLUMN_CARD_NAME + " LIKE ?";
        String[] selectionArgs = {name};
        Cursor c = db.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, FeedEntry.COLUMN_ID + " DESC");
        return c.moveToFirst();
    }

    public void setName(String old_name, String new_name) {

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_CARD_NAME, new_name);

        String selection = FeedEntry.COLUMN_CARD_NAME + " LIKE ?";
        String[] selectionArgs = {old_name};

        int count = db.update(FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public Cursor getLine(String name){

        String querry = "SELECT * FROM " + FeedEntry.TABLE_NAME + " WHERE " + FeedEntry.COLUMN_CARD_NAME + " = \"" + name + "\"";

        return db.rawQuery(querry, null);
    }
}
