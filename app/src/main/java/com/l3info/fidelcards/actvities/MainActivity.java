package com.l3info.fidelcards.actvities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.l3info.fidelcards.ihm.CardAdapter;
import com.l3info.fidelcards.ihm.Carte;
import com.l3info.fidelcards.R;
import com.l3info.fidelcards.bdd.CardDB;
import com.l3info.fidelcards.bdd.CardDBOpener;
import com.l3info.fidelcards.bdd.FeedEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Carte> cartes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaJ_TableView();

        int orientation = 2;
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        switch(display.getOrientation()) {
            /*case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                orientation = 2;
                break;*/
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                orientation = 4;
                break;
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, orientation));
        recyclerView.setAdapter(new CardAdapter(cartes));

        final Button ajoutButton = (Button) findViewById(R.id.ajouter);
        ajoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AjoutActivity.class);
                startActivity(intent);
            }
        });

        final Button suppButton = (Button) findViewById(R.id.supprimer);
        suppButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuppressionActivity.class);
                startActivity(intent);
            }
        });
    }

    public void MaJ_TableView() {
        CardDBOpener cardDBOpener = new CardDBOpener(getApplicationContext());
        final CardDB dbCard = new CardDB(cardDBOpener);

        int taille = dbCard.taille_base();
        final String [] carte = new String[taille];

        dbCard.suppression_carte("hh");

        Cursor c = dbCard.lecture_base();

        int j = 0;
        while (c.moveToNext()) {
            carte[j] = c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_NAME));
            j++;
        }

        cartes.clear();
        for (int i = 0; i < carte.length; i++) {
            String urlImage = "/sdcard/Pictures/"+carte[i]+".png";
            cartes.add(new Carte(i+1, carte[i], urlImage));
        }
    }

    public void edit_name (View v) {
        int id = v.getId();
        TextView tv = (TextView) findViewById(id-1);
        String old_name = String.valueOf(tv.getText());
        final String[] new_name = new String[1];

        final Dialog dialog_renommer = new Dialog(MainActivity.this);
        dialog_renommer.setContentView(R.layout.renommer);
        dialog_renommer.setTitle("Renommer");

        TextView text = (TextView) dialog_renommer.findViewById(R.id.renommer);
        text.setText("Entrer le nouveau nom de la carte");

        Button validerButton = (Button) dialog_renommer.findViewById(R.id.ok);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) dialog_renommer.findViewById(R.id.newNom);
                new_name[0] = text.toString();
                dialog_renommer.dismiss();
            }
        });

        Button annulerButton = (Button) dialog_renommer.findViewById(R.id.ok);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_renommer.dismiss();
            }
        });
        dialog_renommer.show();

        /*Carte_Fidelite_DBOpener cardDBOpener = new Carte_Fidelite_DBOpener(getApplicationContext());
        final Carte_Fidelite_DB dbCard = new Carte_Fidelite_DB(cardDBOpener);
        dbCard.setName(old_name, "toto");*/
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
