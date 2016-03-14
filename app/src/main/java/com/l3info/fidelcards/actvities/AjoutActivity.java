package com.l3info.fidelcards.actvities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.l3info.fidelcards.R;
import com.l3info.fidelcards.bdd.CardDB;
import com.l3info.fidelcards.bdd.CardDBOpener;

import java.io.File;

/**
 * Created by User on 21/02/2016.
 */
public class AjoutActivity extends Activity {

    static final int CAM_REQUEST = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
    }

    public void valider(View v) {

        CardDBOpener cardDBOpener = new CardDBOpener(getApplicationContext());
        final CardDB dbCard = new CardDB(cardDBOpener);

        //Enregistrement dans la base
        EditText editText_nom = (EditText) findViewById(R.id.nom);
        EditText editText_num = (EditText) findViewById(R.id.num);

        String nom = String.valueOf(editText_nom.getText());
        String num = String.valueOf(editText_num.getText());

        if (nom.equals("")) {
            Toast.makeText(getApplicationContext(), "Veuillez renseigner le nom de la carte", Toast.LENGTH_SHORT).show();
        } else if (num.equals("")) {
            Toast.makeText(getApplicationContext(), "Veuillez renseigner le numéro de la carte", Toast.LENGTH_SHORT).show();
        } else if (dbCard.contient(nom)) {
            Toast.makeText(getApplicationContext(), "Vous avez déjà une carte ayant ce nom", Toast.LENGTH_SHORT).show();
        } else {
            String urlImg = "/sdcard/Pictures/" + nom + ".png";
            dbCard.ajout_carte(nom, num, urlImg);

            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = getFile();
            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            if (camera_intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(camera_intent,CAM_REQUEST);
            }
        }
    }

    private File getFile(){

        EditText name = (EditText) findViewById(R.id.nom);
        String namePhoto = String.valueOf(name.getText());
        File folder = new File("sdcard/Pictures");

        if(!folder.exists()) {
            folder.mkdirs();
        }

        File image_file = new File(folder, namePhoto + ".png");
        namePhoto = namePhoto + ".png";

        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent(AjoutActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
