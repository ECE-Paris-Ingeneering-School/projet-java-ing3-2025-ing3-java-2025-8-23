package Modele;

import java.sql.Date;

public class Commande {
    private int id;
    private int utilisateurId;
    private Date dateCommande;
    private double total;

    public Commande(int id, int utilisateurId, Date dateCommande, double total) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.dateCommande = dateCommande;
        this.total = total;
    }
    public int getId() { return id; }
    public int getUtilisateurId() { return utilisateurId; }
    public Date getDateCommande() { return dateCommande; }
    public double getTotal() { return total; }
}
