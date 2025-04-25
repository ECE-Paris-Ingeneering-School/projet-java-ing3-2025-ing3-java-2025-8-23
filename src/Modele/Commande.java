package Modele;

import java.sql.Date;

public class Commande {
    private int id;
    private int utilisateurId;
    private Date dateCommande;
    private double total;
    private int valider;           // 0 = en cours, 1 = payée

    public Commande(int id,
                    int utilisateurId,
                    Date dateCommande,
                    double total,
                    int valider) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.dateCommande = dateCommande;
        this.total = total;
        this.valider = valider;
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

    /** 0 = en cours, 1 = payée */
    public int getValider() {
        return valider;
    }
}
