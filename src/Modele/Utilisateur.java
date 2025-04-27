package Modele;

import java.time.LocalDate;

/**
 * Cette classe représente un utilisateur du système
 * Elle contient toutes les infos persos et de connexion
 * d'un utilisateur, ainsi que sa date d'inscription et son rang
 *
 * @author groupe 23 TD8
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

    /**
     * Constructeur utilisé lors de l'inscription d'un nouvel utilisateur
     * Les champs id, dateInscription et rang seront initialisés par la BDD
     *
     * @param prenom Le prénom de l'utilisateur
     * @param nom Le nom de famille de l'utilisateur
     * @param email L'adresse email
     * @param motDePasse Le mot de passe chiffré
     * @param adresse L'adresse postale complète
     */
    public Utilisateur(String prenom, String nom, String email, String motDePasse, String adresse) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
    }

    /**
     * Constructeur complet utilisé pour récupérer un utilisateur depuis la BDD
     *
     * @param id L'identifiant unique
     * @param prenom Le prénom de l'utilisateur
     * @param nom Le nom de famille de l'utilisateur
     * @param email L'adresse mail
     * @param motDePasse Le mot de passe
     * @param adresse L'adresse postale
     * @param dateInscription La date d'inscription
     * @param rang Le niveau de privilèges (0=utilisateur standard, 1=admin)
     */
    public Utilisateur(int id, String prenom, String nom, String email,
                       String motDePasse, String adresse,
                       LocalDate dateInscription, int rang) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        this.dateInscription = dateInscription;
        this.rang = rang;
    }

    // ============ GETTERS ============

    /**
     * @return L'identifiant unique de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * @return Le prénom de l'utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @return Le nom de famille de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return L'adresse mail de connexion
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Le mot de passe chiffré
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * @return L'adresse postale
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @return La date d'inscription
     */
    public LocalDate getDateInscription() {
        return dateInscription;
    }

    /**
     * @return Le rang/niveau de privilèges (0=standard, 1=admin)
     */
    public int getRang() {
        return rang;
    }
}