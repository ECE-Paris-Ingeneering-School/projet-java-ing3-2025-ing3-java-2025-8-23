//https://boostcamp.omneseducation.com/course/view.php?id=377193&section=5#tabs-tree-start
package Modele;

import java.sql.Date;

/**
 * Modèle représentant une commande client
 * Contient l'identifiant, l'utilisateur associé, la date, le total et l'état de validation
 */
public class Commande {
    private int id;
    private int utilisateurId;
    private Date dateCommande;
    private double total;
    private int valider;  // 0 = en cours, 1 = payée

    /**
     * Constructeur complet pour créer une commande
     *
     * @param id identifiant unique de la commande
     * @param utilisateurId identifiant de l'utilisateur ayant passé la commande
     * @param dateCommande date de la commande
     * @param total montant total de la commande
     * @param valider 0 si en cours, 1 si validée
     */
    public Commande(int id, int utilisateurId, Date dateCommande, double total, int valider) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.dateCommande = dateCommande;
        this.total = total;
        this.valider = valider;
    }

    /**
     * Constructeur vide
     */
    public Commande() {
    }

    public int getId() {
        return id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public double getTotal() {
        return total;
    }

    /**
     * Renvoie 0 si la commande est en cours, 1 si elle est payée
     */
    public int getValider() {
        return valider;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", utilisateurId=" + utilisateurId +
                ", dateCommande=" + dateCommande +
                ", total=" + total +
                ", valider=" + valider +
                '}';
    }
}