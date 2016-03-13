package com.l3info.fidelcards.bddCarteFidelite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 21/02/2016.
 */
public class Carte_Fidelite_DB {

    SQLiteDatabase db;

    public Carte_Fidelite_DB(Carte_Fidelite_DBOpener carteFideliteDBOpener) {
        // Gets the data repository in write mode
        db = carteFideliteDBOpener.getWritableDatabase();

        this.ajout_carte();
    }

    public void ajout_carte () {

        String[] tabStr = new String[] { "U", "Carrefour", "Fnac", "Error404"};

        for(int i=0;i<tabStr.length;i++) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Carte_Fidelite_FeedEntry.COLUMN_CARD_NAME, tabStr[i]);
            values.put(Carte_Fidelite_FeedEntry.COLUMN_CARD_IMG, "/sdcard/FidelCards/img/" + tabStr[i] + ".jpg");

            db.insert(Carte_Fidelite_FeedEntry.TABLE_NAME, null, values);
        }
    }

    public Cursor lecture_base () {
        return db.rawQuery("SELECT * FROM card", null);
    }

    public int taille_base() {
        Cursor c = lecture_base();
        return c.getCount();
    }

    public Cursor getImg (String name) {

        String querry = "SELECT * FROM " + Carte_Fidelite_FeedEntry.TABLE_NAME
                                  + " WHERE " + Carte_Fidelite_FeedEntry.COLUMN_CARD_NAME + " = \"" + name + "\"";

        return db.rawQuery(querry, null);
    }
}
