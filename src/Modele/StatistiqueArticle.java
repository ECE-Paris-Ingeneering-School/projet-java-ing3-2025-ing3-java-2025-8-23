package Modele;

/**
 * Modèle pour les statistiques de ventes pour un article :
 * - nom de l'article
 * - quantité totale vendue
 * - chiffre d'affaires généré
 */
public class StatistiqueArticle {
    private String nom;
    private int quantiteTotale;
    private double totalGenere;

    public StatistiqueArticle(String nom, int quantiteTotale, double totalGenere) {
        this.nom = nom;
        this.quantiteTotale = quantiteTotale;
        this.totalGenere = totalGenere;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantiteTotale() {
        return quantiteTotale;
    }

    public double getTotalGenere() {
        return totalGenere;
    }
}
