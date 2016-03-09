package com.l3info.fidelcards.ihm;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.l3info.fidelcards.R;

import java.io.File;

/**
 * Created by User on 04/03/2016.
 */
public class ViewCardHolder extends RecyclerView.ViewHolder {

    private RelativeLayout rl;
    private ImageButton ib_card;
    private LinearLayout ll;
    private RelativeLayout.LayoutParams paramsOptn = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    private EditText et;
    private ImageButton ib_edit;

    public ViewCardHolder (View view) {
        super(view);

        rl = (RelativeLayout) view.findViewById(R.id.carte);
        ib_card = (ImageButton) view.findViewById(R.id.image);
        ll = (LinearLayout) view.findViewById(R.id.option);
        et = (EditText) view.findViewById(R.id.nomCarte);
        ib_edit = (ImageButton) view.findViewById(R.id.buttonEdit);
    }

    public void remplir (Carte carte) {
        rl.removeView(ll);
        int id = 10 * carte.getNumero();
        ib_card.setId(id + 1);
        paramsOptn.addRule(RelativeLayout.ALIGN_BOTTOM, id + 1);
        et.setId(id + 2);
        ib_edit.setId(id + 3);

        et.setText(carte.getName());
        et.setFocusable(false);
        File imgFile = new  File(carte.getImageUrl());
        if(imgFile.exists()) {
            //Bitmap myBitmap = android.graphics.BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ib_card.setImageDrawable(Drawable.createFromPath(carte.getImageUrl()));
        }
        rl.addView(ll, paramsOptn);
    }
}
