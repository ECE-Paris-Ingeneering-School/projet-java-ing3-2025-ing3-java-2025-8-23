package Modele;

/**
 * Cette classe représente un panier d'achat
 * Elle permet de stocker les infos d'un produit ajouté au panier,
 * comme son identifiant, sa quantité, son nom et son prix.
 *
 * @author groupe 23 TD8
 */
public class Panier {
    private int id;
    private int produitId;
    private int quantite;
    private String nom;
    private double prix;

    /**
     * Constructeur pour créer un nouvel élément dans le panier
     *
     * @param id L'identifiant unique du panier
     * @param produitId L'identifiant du produit
     * @param quantite La quantité du produit dans le panier
     * @param nom Le nom du produit
     * @param prix Le prix unitaire du produit
     */
    public Panier(int id, int produitId, int quantite, String nom, double prix) {
        this.id = id;
        this.produitId = produitId;
        this.quantite = quantite;
        this.nom = nom;
        this.prix = prix;
    }

    // === Getters ===

    /**
     * Récupère l'identifiant du panier
     * @return L'identifiant du panier
     */
    public int getId() { return id; }

    /**
     * Récupère l'identifiant du produit
     * @return l'identifiant du produit
     */
    public int getProduitId() { return produitId; }

    /**
     * Récupère la quantité du produit dans le panier
     * @return la quantité du produit
     */
    public int getQuantite() { return quantite; }

    /**
     * Récupère le nom du produit
     * @return le nom du produit
     */
    public String getNom() { return nom; }

    /**
     * Récupère le prix unitaire du produit
     * @return le prix du produit
     */
    public double getPrix() { return prix; }

    // === Setters ===

    /**
     * Modifie la quantité du produit dans le panier
     * @param quantite la nouvelle quantité
     */
    public void setQuantite(int quantite) { this.quantite = quantite; }
}