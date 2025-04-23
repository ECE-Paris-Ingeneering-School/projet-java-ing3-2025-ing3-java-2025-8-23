package Modele;

public class Article {

    private int id;
    private String nom;
    private double prix;
    private byte[] image;
    private String marque;


    public Article(int id, String nom, double prix, byte[] image, String marque) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.image = image;
        this.marque = marque;

    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public byte[] getImage() {
        return image;
    }

    public String getMarque() {
        return marque;
    }
    // Setters (optionnel, si tu veux rendre les objets modifiables)
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return nom + " - " + prix + "€";
    }
}
