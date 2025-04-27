package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utilitaires.ConnectionFactory;
import Modele.Utilisateur;

/**
 * DAO (Data Access Object) pour la table des utilisateurs
 * <p>
 * Permet la gestion des connexions, inscriptions et mises à jour de profils/rangs
 * </p>
 *
 * @author groupe 23 TD8
 */
public class UtilisateurDAO {

    /**
     * Vérifie la connexion et retourne l'utilisateur complet si les identifiants sont bons
     *
     * @param email l'adresse mail de l'utilisateur
     * @param motDePasse le mot de passe de l'utilisateur
     * @return l'objet {@link Utilisateur} ou {@code null} si la connexion ne marche pas
     */
    public Utilisateur seConnecter(String email, String motDePasse) {
        String sql = "SELECT id, prenom, nom, email, mot_de_passe, adresse, date_inscription, rang " +
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
     * Inscrit un nouvel utilisateur dans la BDD
     *
     * @param utilisateur l'objet {@link Utilisateur} à insérer
     * @return {@code true} si l'insertion a réussi et {@code false} sinon
     */
    public boolean inscrireUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (prenom, nom, email, mot_de_passe, adresse, rang) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        final int RANG_CLIENT = 1; // Rang par défaut pour un nouvel utilisateur

        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getPrenom());
            ps.setString(2, utilisateur.getNom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getAdresse());
            ps.setInt(6, RANG_CLIENT);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour le rang d'un utilisateur existant
     *
     * @param id l'identifiant de l'utilisateur
     * @param nouveauRang la nouvelle valeur du rang
     * @return {@code true} si la mise à jour a affecté au moins une ligne
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

    /**
     * Met à jour les infos de profil d'un utilisateur
     *
     * @param userUpdated l'objet {@link Utilisateur} avec les nouvelles infos
     * @return {@code true} si la mise à jour a réussi et {@code false} sinon
     */
    public boolean mettreAJourProfil(Utilisateur userUpdated) {
        String sql = "UPDATE utilisateurs SET prenom = ?, nom = ?, email = ?, mot_de_passe = ?, adresse = ? " +
                "WHERE id = ?";
        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {

            ps.setString(1, userUpdated.getPrenom());
            ps.setString(2, userUpdated.getNom());
            ps.setString(3, userUpdated.getEmail());
            ps.setString(4, userUpdated.getMotDePasse());
            ps.setString(5, userUpdated.getAdresse());
            ps.setInt(6, userUpdated.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
