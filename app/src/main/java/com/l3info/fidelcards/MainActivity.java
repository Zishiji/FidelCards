package com.l3info.fidelcards;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static android.R.drawable.ic_menu_edit;
import static android.graphics.BitmapFactory.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaJ_TableView();

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

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        if (intent != null) {
            String msg = intent.getStringExtra("id");

            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.show();
        }
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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width_pix = metrics.widthPixels;
        int heigth_pix = width_pix / 3;

        RelativeLayout table = (RelativeLayout) findViewById(R.id.listeCarte);
        RelativeLayout rl;
        ImageButton ib_carte;
        LinearLayout ll;
        TextView tv;
        ImageButton ib_renommer;

        RelativeLayout.LayoutParams paramsTable;
        RelativeLayout.LayoutParams paramsIbCarte;
        RelativeLayout.LayoutParams paramsLinear;
        LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams paramsIbRenommer = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        for(int i=0;i<carte.length;i++) {
            paramsTable = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsIbCarte = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsLinear = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int base_id = (i+1)*10;

            rl = new RelativeLayout(this);
            rl.setId(base_id);
            paramsTable.setMargins(10, 10, 10, 10);

            ib_carte = new ImageButton(this);
            File imgFile = new  File("/sdcard/Download/background.jpeg");
            if(imgFile.exists()) {
                Bitmap myBitmap = decodeFile(imgFile.getAbsolutePath());
                ib_carte.setImageBitmap(myBitmap);
            }

            ib_carte.setId(base_id + 1);
            ib_carte.setBackgroundColor(Color.BLUE);
            //ib_carte.setPadding(500, 100, 0, 0);
            paramsIbCarte.addRule(RelativeLayout.ALIGN_PARENT_START, -1);
            paramsIbCarte.addRule(RelativeLayout.ALIGN_PARENT_TOP, -1);
            paramsIbCarte.width = (width_pix / 2) - 20;
            paramsIbCarte.height = heigth_pix - 20;

            ll = new LinearLayout(this);
            ll.setId(base_id + 2);
            paramsLinear.addRule(RelativeLayout.ALIGN_LEFT, base_id + 1);
            paramsLinear.addRule(RelativeLayout.ALIGN_RIGHT, base_id + 1);
            paramsLinear.addRule(RelativeLayout.ALIGN_BOTTOM, base_id + 1);

            tv = new TextView(this);
            tv.setId(base_id + 3);
            tv.setText(carte[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 5 * heigth_pix / 42);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.BLUE);
            tv.setGravity(Gravity.CENTER);
            paramsIbRenommer.width = 0;
            paramsIbRenommer.height =  2 * heigth_pix / 7;
            paramsTv.weight = 70;
            paramsTv.gravity = Gravity.CENTER;

            ib_renommer = new ImageButton(this);
            ib_renommer.setId(base_id + 4);
            ib_renommer.setImageResource(ic_menu_edit);
            ib_renommer.setBackgroundColor(Color.BLUE);
            paramsIbRenommer.width = 0;
            paramsIbRenommer.height = 2 * heigth_pix / 7;
            paramsIbRenommer.weight = 30;

            if (i%2 == 0) {
                paramsTable.addRule(RelativeLayout.ALIGN_PARENT_START);
            } else {
                paramsTable.addRule(RelativeLayout.ALIGN_PARENT_END);
            }
            if(i > 1) {
                paramsTable.addRule(RelativeLayout.BELOW, base_id - 20);
            }

            rl.addView(ib_carte, 0, paramsIbCarte);
            ll.addView(tv, 0, paramsTv);
            ll.addView(ib_renommer, 1, paramsIbRenommer);
            rl.addView(ll, 1, paramsLinear);
            table.addView(rl, paramsTable);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
