package com.l3info.fidelcards.ihm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.l3info.fidelcards.R;

import java.util.List;

/**
 * Created by User on 04/03/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<ViewCardHolder> {

    List<Carte> list;

    public CardAdapter (List<Carte> list) {
        this.list = list;
    }

    @Override
    public ViewCardHolder onCreateViewHolder (ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cartes, viewGroup, false);
        return new ViewCardHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewCardHolder viewCardHolder, int position) {
        Carte carte = list.get(position);
        viewCardHolder.remplir(carte);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
