package Modele;

public class Article {
    private int id;
    private String nom;
    private String marque;
    private String description;
    private double prix;
    private int stock;
    private byte[] image;

    public Article(int id, String nom, String marque,
                   String description, double prix, int stock, byte[] image) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getMarque() { return marque; }
    public String getDescription() { return description; }
    public double getPrix() { return prix; }
    public int getStock() { return stock; }
    public byte[] getImage() { return image; }

    // Setters (pour l’édition)
    public void setNom(String nom) { this.nom = nom; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setDescription(String description) { this.description = description; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setStock(int stock) { this.stock = stock; }
    public void setImage(byte[] image) { this.image = image; }
}
