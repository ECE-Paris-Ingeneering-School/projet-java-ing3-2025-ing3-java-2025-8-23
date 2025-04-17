package Modele;

public class Utilisateur {
    private String prenom;
    private String nom;
    private String email;
    private String motDePasse;
    private String adresse;

    public Utilisateur(String prenom, String nom, String email, String motDePasse, String adresse) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
    }

    // Getters
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
}
