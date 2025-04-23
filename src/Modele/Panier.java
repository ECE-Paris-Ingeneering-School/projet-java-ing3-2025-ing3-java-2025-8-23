package Modele;

public class Panier {
    private int id;
    private int produitId;
    private int quantite;
    private String nom;
    private double prix;

    public Panier(int id, int produitId, int quantite, String nom, double prix) {
        this.id = id;
        this.produitId = produitId;
        this.quantite = quantite;
        this.nom = nom;
        this.prix = prix;
    }

    // Getters et Setters
    public int getId() { return id; }
    public int getProduitId() { return produitId; }
    public int getQuantite() { return quantite; }
    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}