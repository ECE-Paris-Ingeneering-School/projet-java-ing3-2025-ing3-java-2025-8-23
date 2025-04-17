package Modele;

import javax.swing.*;
import java.util.List;

public class ProduitImage {
    private String nom;
    private byte[] image;
    private double prix;

    public ProduitImage(String nom, byte[] image, double prix) {
        this.nom = nom;
        this.image = image;
        this.prix = prix;
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



}
