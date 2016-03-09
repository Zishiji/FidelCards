package com.l3info.fidelcards;

import android.provider.BaseColumns;

/**
 * Created by User on 23/02/2016.
 */
public abstract class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "card";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CARD_NAME = "name";
    public static final String COLUMN_CARD_NUM = "num";
    public static final String COLUMN_CARD_LOGO = "logo";
    public static final String COLUMN_CARD_SHOP = "shop";
}
