package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utilitaires.ConnectionFactory;

public class UtilisateurDAO {

    /**
     * Vérifie si l'utilisateur existe avec l'email et le mot de passe donnés.
     *
     * @param email l'adresse email de l'utilisateur
     * @param motDePasse le mot de passe de l'utilisateur
     * @return true si l'utilisateur existe, false sinon
     */
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
}
