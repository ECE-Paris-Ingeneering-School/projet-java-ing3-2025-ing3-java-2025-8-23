package Modele;

public class Article {

    private int id;
    private String nom;
    private double prix;
    private String chemin;

    public Article(int id, String nom, double prix, String imagePath) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.chemin = imagePath;
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

    public String getImagePath() {
        return chemin;
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

    public void setImagePath(String chemin) {
        this.chemin = chemin;
    }

    @Override
    public String toString() {
        return nom + " - " + prix + "€";
    }
}
