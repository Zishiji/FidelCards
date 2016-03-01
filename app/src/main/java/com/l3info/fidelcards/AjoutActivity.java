package com.l3info.fidelcards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 21/02/2016.
 */
public class AjoutActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);

        final Button validerButton = (Button) findViewById(R.id.valider);
        validerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                long id = valider();
                String idString = String.valueOf(id);
                System.out.println(id);
                Intent intent = new Intent(AjoutActivity.this, MainActivity.class);
                intent.putExtra("id", idString);
                startActivity(intent);
            }
        });

        /*File imgFile = new  File("/sdcard/Download/background.jpeg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            // taille de l'image à régler dans le xml
            myImage.setImageBitmap(myBitmap);
        }*/
    }

    public long valider() {

        CardDBOpener cardDBOpener = new CardDBOpener(getApplicationContext());
        final CardDB dbCard = new CardDB(cardDBOpener);

        //Enregistrement dans la base
        EditText editText_nom = (EditText) findViewById(R.id.nom);
        EditText editText_num = (EditText) findViewById(R.id.num);

        String nom = String.valueOf(editText_nom.getText());
        String num = String.valueOf(editText_num.getText());

        if (dbCard.contient(nom)) {
            return dbCard.ajout_carte(nom, num);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Vous avez déjà une carte ayant ce nom", Toast.LENGTH_SHORT);
            toast.show();
            return -1;
        }
    }
}
