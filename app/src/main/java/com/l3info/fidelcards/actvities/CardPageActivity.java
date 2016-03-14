package com.l3info.fidelcards.actvities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.l3info.fidelcards.R;
import com.l3info.fidelcards.bdd.CardDB;
import com.l3info.fidelcards.bdd.CardDBOpener;
import com.l3info.fidelcards.bdd.FeedEntry;
import com.l3info.fidelcards.bddCarteFidelite.Carte_Fidelite_DB;
import com.l3info.fidelcards.bddCarteFidelite.Carte_Fidelite_DBOpener;
import com.l3info.fidelcards.bddCarteFidelite.Carte_Fidelite_FeedEntry;
import com.l3info.fidelcards.ihm.Carte;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alexis on 12/03/2016.
 */
public class CardPageActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_page);

        Intent intent = getIntent();
        String enseigne = intent.getStringExtra("enseigne");

        /* Recuperer image carte de fidelite */
        Carte_Fidelite_DBOpener carte_Fidelite_DBOpener = new Carte_Fidelite_DBOpener(getApplicationContext());
        final Carte_Fidelite_DB dbCard = new Carte_Fidelite_DB(carte_Fidelite_DBOpener);

        String[] tabStr = new String[] { "U", "Carrefour", "Fnac"};

        Cursor c = null;
        for(int i=0;i<tabStr.length;i++)
            if(tabStr[i].equals(enseigne)) c = dbCard.getImg(enseigne);

        if(c==null) c = dbCard.getImg("Error404");

        c.moveToNext();
        String imgStr = c.getString(c.getColumnIndex(Carte_Fidelite_FeedEntry.COLUMN_CARD_IMG));

        ImageView img = (ImageView) findViewById(R.id.imgCard);
        img.setImageBitmap(this.decodeFileFromPath(imgStr));

        /* recuperer num & nom de carte */
        CardDBOpener cardDBOpener = new CardDBOpener(getApplicationContext());
        final CardDB dbCard2 = new CardDB(cardDBOpener);

        c = null;
        TextView tv = (TextView) findViewById(R.id.tvCard);
        for(int i=0;i<tabStr.length;i++)
            if(tabStr[i].equals(enseigne)) c = dbCard2.getLine(enseigne);

        if(c!=null) {
            c.moveToNext();
            imgStr = c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_LOGO));
            img = (ImageView) findViewById(R.id.imgCard2);
            img.setImageBitmap(this.decodeFileFromPath(imgStr));
        }

        c = dbCard2.getLine(enseigne);
        c.moveToNext();
        String num = c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_NUM));
        tv.setText("Votre carte est " + enseigne + "\nVotre numéro de client associé est " + num);

    }

    private Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private Bitmap decodeFileFromPath(String path){
        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = getContentResolver().openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            int inSampleSize = 1024;
            if (o.outHeight > inSampleSize || o.outWidth > inSampleSize) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(inSampleSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
