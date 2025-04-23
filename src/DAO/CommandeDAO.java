package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Utilitaires.ConnectionFactory;

/**
 * DAO pour la table `commandes`.
 * Récupère ou crée la commande "en cours" (total=0) pour un utilisateur donné.
 */
public class CommandeDAO {

    /**
     * Récupère l’ID de la commande dont total=0 pour l’utilisateur.
     * Si aucune n’existe, insère une nouvelle ligne et renvoie son ID.
     *
     * @param utilisateurId l’ID de l’utilisateur connecté
     * @return l’ID de la commande "en cours"
     */
    public int getOuCreateCommandeEnCours(int utilisateurId) {
        int commandeId = 0;
        String selectSql =
                "SELECT id FROM commandes WHERE utilisateur_id = ? AND total = 0";
        String insertSql =
                "INSERT INTO commandes (utilisateur_id, date_commande, total) VALUES (?, ?, 0)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            // 1) On cherche une commande existante dont total=0
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setInt(1, utilisateurId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            }

            // 2) Pas de commande "en cours", on en crée une nouvelle
            try (PreparedStatement ps = conn.prepareStatement(
                    insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, utilisateurId);
                ps.setDate(2, new Date(System.currentTimeMillis()));
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        commandeId = keys.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandeId;
    }
}
