package com.l3info.fidelcards.ihm;

/**
 * Created by User on 04/03/2016.
 */
public class Carte {
    private int numero;
    private String name;
    private String imageUrl;

    public Carte (int numero, String name, String imageUrl) {
        this.numero = numero;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getNumero() {
        return numero;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
