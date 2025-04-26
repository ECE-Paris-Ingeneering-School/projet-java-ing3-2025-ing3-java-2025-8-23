package Modele;

import javax.swing.*;
import java.util.List;

public class ProduitImage {
    private String nom;
    private byte[] image;
    private double prix;
    private String marque;

    public ProduitImage(String nom, byte[] image, double prix, String marque) {
        this.nom = nom;
        this.image = image;
        this.prix = prix;
        this.marque = marque;
    }

    public String getNom() {
        return nom;
    }

    public byte[] getImage() {
        return image;
    }

    public double getPrix() {
        return prix;
    }
    public String getMarque() {
        return marque;
    }


}
