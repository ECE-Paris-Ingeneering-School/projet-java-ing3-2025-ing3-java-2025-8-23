package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utilitaires.ConnectionFactory;
import Modele.Utilisateur;

public class UtilisateurDAO {

    // Méthode pour vérifier la connexion
    public boolean verifierIdentifiants(String email, String motDePasse) {
        boolean valide = false;
        String sql = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, motDePasse);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    valide = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valide;
    }

    // Méthode pour inscrire un nouvel utilisateur
    public boolean inscrireUtilisateur(Utilisateur utilisateur) {
        // Adaptez si l'ordre des colonnes est différent dans votre table
        String sql = "INSERT INTO utilisateurs (prenom, nom, email, mot_de_passe, adresse, rang) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, utilisateur.getPrenom());
            ps.setString(2, utilisateur.getNom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setString(5, utilisateur.getAdresse());
            ps.setString(6, "1");

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
