package com.l3info.fidelcards.bddCarteFidelite;

import android.provider.BaseColumns;

/**
 * Created by User on 23/02/2016.
 */
public abstract class Carte_Fidelite_FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "cardFidelite";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CARD_NAME = "name";
    public static final String COLUMN_CARD_IMG = "img";
}
