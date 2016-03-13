package com.l3info.fidelcards.ihm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.l3info.fidelcards.R;
import com.l3info.fidelcards.actvities.CardPageActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by User on 04/03/2016.
 */
class ViewCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RelativeLayout rl;
    private ImageButton ib_card;
    private LinearLayout ll;
    private RelativeLayout.LayoutParams paramsOptn = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    private TextView tv;
    private ImageButton ib_edit;
    private String enseigne;

    public ViewCardHolder (View view) {
        super(view);

        rl = (RelativeLayout) view.findViewById(R.id.carte);
        ib_card = (ImageButton) view.findViewById(R.id.image);
        ll = (LinearLayout) view.findViewById(R.id.option);
        tv = (TextView) view.findViewById(R.id.nomCarte);
        ib_edit = (ImageButton) view.findViewById(R.id.buttonEdit);
    }

    public void remplir (Carte carte) {
        rl.removeView(ll);
        int id = 10 * carte.getNumero();
        ib_card.setId(id + 1);
        paramsOptn.addRule(RelativeLayout.ALIGN_BOTTOM, id + 1);
        tv.setId(id + 2);
        ib_edit.setId(id + 3);

        enseigne = carte.getName();
        tv.setText(enseigne);
        File imgFile = new  File(carte.getImageUrl());
        if(imgFile.exists()) {
            //Bitmap myBitmap = android.graphics.BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ib_card.setImageDrawable(Drawable.createFromPath(carte.getImageUrl()));
        }
        ib_card.setOnClickListener(this);
        rl.addView(ll, paramsOptn);
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(v.getContext(), CardPageActivity.class);
        myIntent.putExtra("enseigne", enseigne);
        v.getContext().startActivity(myIntent);
    }
}
