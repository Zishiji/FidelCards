package com.l3info.fidelcards.actvities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.l3info.fidelcards.R;
import com.l3info.fidelcards.bdd.CardDB;
import com.l3info.fidelcards.bdd.CardDBOpener;
import com.l3info.fidelcards.bdd.FeedEntry;

import java.util.ArrayList;

/**
 * Created by User on 23/02/2016.
 */
public class SuppressionActivity extends Activity {

    ListView listViewCard;
    String[] descriptionCartes;
    Object card;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suppression);

        listViewCard = (ListView) findViewById(R.id.listViewCard);
        affichage_carte();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuppressionActivity.this, android.R.layout.simple_list_item_1, descriptionCartes);
        listViewCard.setAdapter(adapter);

        final Button supprimerButton = (Button) findViewById(R.id.supprimer);
        supprimerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                supprimer();
                Intent intent = new Intent(SuppressionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listViewCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Object obj = parent.getItemAtPosition(pos);
                card = obj;
            }
        });
    }

    public void supprimer () {

        CardDBOpener cardDBOpener = new CardDBOpener(getApplicationContext());
        final CardDB dbCard = new CardDB(cardDBOpener);

        String carte = card.toString();
        String[] nom = carte.split(" ");

        dbCard.suppression_carte(nom[0]);
    }

    public void affichage_carte() {

        CardDBOpener cardDBOpener = new CardDBOpener(getApplicationContext());
        final CardDB dbCard = new CardDB(cardDBOpener);

        ArrayList<String> descCartes = new ArrayList<String>();
        Cursor c = dbCard.lecture_base();
        String nom, num, shop;

        /*if (c.moveToFirst()) {
            nom = c.getString(c.getColumnIndex(Carte_Fidelite_FeedEntry.COLUMN_CARD_NAME));
            num = String.valueOf(c.getInt(c.getColumnIndex(Carte_Fidelite_FeedEntry.COLUMN_CARD_NUM)));
            descCartes.add(nom + " " + num);
        }*/

        while (c.moveToNext()) {
            nom = c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_NAME));
            num = c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_NUM));
            shop = c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_SHOP));
            descCartes.add(nom + " " + num + " " + shop);
        }

        descriptionCartes = new String[descCartes.size()];
        for (int i = 0; i < descCartes.size(); i++) {
            descriptionCartes[i] = descCartes.get(i);
        }
    }
}
