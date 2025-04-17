package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import Utilitaires.ConnectionFactory;
import Modele.Utilisateur;

/**
 * DAO pour la table utilisateurs.
 */
public class UtilisateurDAO {

    /**
     * Vérifie la connexion et renvoie l'utilisateur complet si OK.
     *
     * @param email       l'adresse email
     * @param motDePasse  le mot de passe
     * @return l'objet Utilisateur complet (id, prénom, nom, email, mot_de_passe, adresse, date_inscription, rang) ou null
     */
    public Utilisateur seConnecter(String email, String motDePasse) {
        String sql =
                "SELECT id, prenom, nom, email, mot_de_passe, adresse, date_inscription, rang " +
                        "FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, motDePasse);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("prenom"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("adresse"),
                            rs.getDate("date_inscription").toLocalDate(),
                            rs.getInt("rang")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Inscrit un nouvel utilisateur dans la base.
     *
     * @param utilisateur l'objet Utilisateur à insérer (prenom, nom, email, mot_de_passe, adresse)
     * @return true si l'insertion a réussi, false sinon
     */
    public boolean inscrireUtilisateur(Utilisateur utilisateur) {
        String sql =
                "INSERT INTO utilisateurs (prenom, nom, email, mot_de_passe, adresse) " +
                        "VALUES (?, ?, ?, ?, ?)";
        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getPrenom());
            ps.setString(2, utilisateur.getNom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getAdresse());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour le rang d'un utilisateur en base.
     *
     * @param id           l'identifiant de l'utilisateur
     * @param nouveauRang  le nouveau rang (1→2)
     * @return true si la mise à jour a affecté au moins une ligne
     */
    public boolean mettreAJourRang(int id, int nouveauRang) {
        String sql = "UPDATE utilisateurs SET rang = ? WHERE id = ?";
        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {

            ps.setInt(1, nouveauRang);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
