package Modele;

import java.time.LocalDate;

/**
 * Représentation d'un utilisateur tel que stocké en base.
 */
public class Utilisateur {
    private int id;
    private String prenom;
    private String nom;
    private String email;
    private String motDePasse;
    private String adresse;
    private LocalDate dateInscription;
    private int rang;

    /** Constructeur pour la création (inscription) */
    public Utilisateur(String prenom, String nom, String email, String motDePasse, String adresse) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        // id, dateInscription et rang seront définis par la base
    }

    /** Constructeur complet pour la récupération depuis la base */
    public Utilisateur(int id,
                       String prenom,
                       String nom,
                       String email,
                       String motDePasse,
                       String adresse,
                       LocalDate dateInscription,
                       int rang) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        this.dateInscription = dateInscription;
        this.rang = rang;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getNom() {
        return nom;
    }
    public String getEmail() {
        return email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public String getAdresse() {
        return adresse;
    }
    public LocalDate getDateInscription() {
        return dateInscription;
    }
    public int getRang() {
        return rang;
    }
}
