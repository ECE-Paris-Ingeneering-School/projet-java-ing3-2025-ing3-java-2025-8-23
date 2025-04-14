package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utilitaires.ConnectionFactory;
import Modele.Utilisateur;

public class UtilisateurDAO {

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

    public boolean inscrireUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (email, mot_de_passe, nom, prenom, telephone, adresse) VALUES (?,?,?,?,?,?)";
        try (Connection connexion = ConnectionFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setString(1, utilisateur.getEmail());
            ps.setString(2, utilisateur.getMotDePasse());
            ps.setString(3, utilisateur.getNom());
            ps.setString(4, utilisateur.getPrenom());
            ps.setString(5, utilisateur.getTelephone());
            ps.setString(6, utilisateur.getAdresse());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
