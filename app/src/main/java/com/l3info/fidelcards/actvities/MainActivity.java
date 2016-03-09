package com.l3info.fidelcards.actvities;

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

        Cursor c = dbCard.lecture_base();

        int j = 0;
        while (c.moveToNext()) {
            carte[j] = c.getString(c.getColumnIndex(FeedEntry.COLUMN_CARD_NAME));
            j++;
        }

        for (int i = 0; i < carte.length; i++) {
            String urlImage = "/sdcard/Pictures/"+carte[i]+".png";
            cartes.add(new Carte(i+1, carte[i], urlImage));
        }
    }

    public void edit_name (View v) {
        int id = v.getId();
        EditText et = (EditText) findViewById(id-1);
        String old_name = String.valueOf(et.getText());
        System.out.println(old_name);
        et.setEnabled(true);
        et.setFocusable(true);
        et.setClickable(true);
        String new_name = String.valueOf(et.getText());
        CardDBOpener cardDBOpener = new CardDBOpener(getApplicationContext());
        final CardDB dbCard = new CardDB(cardDBOpener);
        dbCard.setName(old_name, new_name);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
