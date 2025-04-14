package Modele;

public class Utilisateur {
    // L'ID est généré automatiquement par la base, ici nous ne le gérons pas dans le constructeur
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;

    public Utilisateur(String email, String motDePasse, String nom, String prenom, String telephone, String adresse) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    // Getters
    public String getEmail() {
        return email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getAdresse() {
        return adresse;
    }
}
