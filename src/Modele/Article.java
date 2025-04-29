//https://boostcamp.omneseducation.com/course/view.php?id=377193&section=5#tabs-tree-start
package Modele;

/**
 * Modèle représentant un article disponible à l'achat
 * Contient les informations principales : nom, marque, description, prix, stock et image
 *
 * @author groupe 23 TD8
 */
public class Article {
    private int id;
    private String nom;
    private String marque;
    private String description;
    private double prix;
    private int stock;
    private byte[] image;

    /**
     * Constructeur complet.
     *
     * @param id l'identifiant de l'article
     * @param nom le nom de l'article
     * @param marque la marque de l'article
     * @param description la description de l'article
     * @param prix le prix de l'article
     * @param stock la quantité disponible en stock
     * @param image l'image associée sous forme de tableau d'octets
     */
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

    /**
     * Constructeur vide
     */
    public Article() {
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getMarque() { return marque; }
    public String getDescription() { return description; }
    public double getPrix() { return prix; }
    public int getStock() { return stock; }
    public byte[] getImage() { return image; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setDescription(String description) { this.description = description; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setStock(int stock) { this.stock = stock; }
    public void setImage(byte[] image) { this.image = image; }

    /**
     * Redéfinition de toString() pour faciliter le débogage
     */
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                '}';
    }
}