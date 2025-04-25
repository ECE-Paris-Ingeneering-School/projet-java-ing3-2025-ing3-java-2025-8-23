package DAO;

import Utilitaires.ConnectionFactory;
import Modele.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {

    /** Récupère (ou crée) la commande en cours (valider = 0). */
    public int getOuCreateCommandeEnCours(int utilisateurId) {
        // 1) Cherche une commande non validée
        String selectSql =
                "SELECT id FROM commandes WHERE utilisateur_id = ? AND valider = 0";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setInt(1, utilisateurId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 2) Sinon, création d’une nouvelle commande (valider = 0 par défaut)
        String insertSql =
                "INSERT INTO commandes (utilisateur_id, date_commande, total) " +
                        "VALUES (?, NOW(), 0)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, utilisateurId);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Impossible de récupérer/créer la commande en cours");
    }

    /** Finalise une commande : met à jour total et passe valider=1. */
    public boolean finaliserCommande(int commandeId, double total) {
        String sql =
                "UPDATE commandes SET total = ?, valider = 1 WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, commandeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Récupère toutes les commandes (pour l’admin), y compris le flag valider. */
    public List<Commande> getAllCommandes() {
        List<Commande> liste = new ArrayList<>();
        String sql =
                "SELECT id, utilisateur_id, date_commande, total, valider " +
                        "FROM commandes";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Commande(
                        rs.getInt("id"),
                        rs.getInt("utilisateur_id"),
                        rs.getDate("date_commande"),
                        rs.getDouble("total"),
                        rs.getInt("valider")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /** Détail d’une commande : ses lignes de panier. */
    public List<Modele.Panier> getDetailsCommande(int commandeId) {
        List<Modele.Panier> liste = new ArrayList<>();
        String sql =
                "SELECT p.id, p.article_id, p.quantite, a.nom, a.prix " +
                        "FROM panier p JOIN articles a ON p.article_id = a.id " +
                        "WHERE p.commande_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Modele.Panier(
                            rs.getInt("id"),
                            rs.getInt("article_id"),
                            rs.getInt("quantite"),
                            rs.getString("nom"),
                            rs.getDouble("prix")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Commande> getCommandesByUtilisateur(int userId) {
        List<Commande> liste = new ArrayList<>();
        String sql =
                "SELECT id, utilisateur_id, date_commande, total, valider " +
                        "FROM commandes " +
                        "WHERE utilisateur_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Commande(
                            rs.getInt("id"),
                            rs.getInt("utilisateur_id"),
                            rs.getDate("date_commande"),
                            rs.getDouble("total"),
                            rs.getInt("valider")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
