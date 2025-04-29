//https://boostcamp.omneseducation.com/course/view.php?id=377193&section=6#tabs-tree-start
//https://boostcamp.omneseducation.com/course/view.php?id=377193&section=5#tabs-tree-start
package DAO;

import Utilitaires.ConnectionFactory;
import Modele.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) pour la gestion des commandes.
 * <p>
 * Permet la création, la récupération, la finalisation et l'affichage du détail des commandes
 * </p>
 *
 * @author groupe 23 TD8
 */
public class CommandeDAO {

    /**
     * Récupère une commande non validée pour un utilisateur donné,
     * ou en crée une nouvelle si aucune existe
     *
     * @param utilisateurId l'identifiant de l'utilisateur
     * @return l'identifiant de la commande en cours
     * @throws RuntimeException si aucune commande ne peut être récupérée ou créée
     */
    public int getOuCreateCommandeEnCours(int utilisateurId) {
        String selectSql = "SELECT id FROM commandes WHERE utilisateur_id = ? AND valider = 0";
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

        String insertSql = "INSERT INTO commandes (utilisateur_id, date_commande, total) VALUES (?, NOW(), 0)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
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

    /**
     * Finalise une commande en mettant à jour son total et en la marquant comme validée
     *
     * @param commandeId l'identifiant de la commande à finaliser
     * @param total le montant total de la commande
     * @return {@code true} si la mise à jour a fonctionnée et {@code false} sinon
     */
    public boolean finaliserCommande(int commandeId, double total) {
        String sql = "UPDATE commandes SET total = ?, valider = 1 WHERE id = ?";
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

    /**
     * Récupère toutes les commandes enregistrées (pratique pour la vue de l'admin)
     *
     * @return une liste de {@link Commande} représentant toutes les commandes passées
     */
    public List<Commande> getAllCommandes() {
        List<Commande> liste = new ArrayList<>();
        String sql = "SELECT id, utilisateur_id, date_commande, total, valider FROM commandes";
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

    /**
     * Récupère les détails d'une commande spécifique : liste des articles achetés
     *
     * @param commandeId l'identifiant de la commande
     * @return une liste de {@link Modele.Panier} en lien avec les articles de la commande
     */
    public List<Modele.Panier> getDetailsCommande(int commandeId) {
        List<Modele.Panier> liste = new ArrayList<>();
        String sql = "SELECT p.id, p.article_id, p.quantite, a.nom, a.prix " +
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

    /**
     * Récupère toutes les commandes d'un utilisateur spécifique
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une liste de {@link Commande} appartenant à l'utilisateur
     */
    public List<Commande> getCommandesByUtilisateur(int userId) {
        List<Commande> liste = new ArrayList<>();
        String sql = "SELECT id, utilisateur_id, date_commande, total, valider FROM commandes WHERE utilisateur_id = ?";
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
