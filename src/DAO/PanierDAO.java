package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utilitaires.ConnectionFactory;
import Modele.Panier;

/**
 * DAO pour la table `panier`.
 * Gère l’ajout, la mise à jour, la suppression et la lecture
 * des lignes de panier associées à une commande donnée.
 */
public class PanierDAO {

    /**
     * Ajoute ou met à jour la quantité d’un article dans le panier.
     *
     * @param commandeId L’ID de la commande “en cours”
     * @param articleId  L’ID de l’article à ajouter
     * @param quantite   La quantité à ajouter
     */
    public void ajouterProduit(int commandeId, int articleId, int quantite) {
        String checkSql  = "SELECT quantite FROM panier WHERE commande_id = ? AND article_id = ?";
        String updateSql = "UPDATE panier SET quantite = ? WHERE commande_id = ? AND article_id = ?";
        String insertSql = "INSERT INTO panier (commande_id, article_id, quantite) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            // 1) Vérifier si la ligne existe déjà
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, commandeId);
                ps.setInt(2, articleId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int nouvelleQuantite = rs.getInt("quantite") + quantite;
                        try (PreparedStatement psUpd = conn.prepareStatement(updateSql)) {
                            psUpd.setInt(1, nouvelleQuantite);
                            psUpd.setInt(2, commandeId);
                            psUpd.setInt(3, articleId);
                            psUpd.executeUpdate();
                        }
                        return;
                    }
                }
            }
            // 2) Sinon, insertion d’une nouvelle ligne
            try (PreparedStatement psIns = conn.prepareStatement(insertSql)) {
                psIns.setInt(1, commandeId);
                psIns.setInt(2, articleId);
                psIns.setInt(3, quantite);
                psIns.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Récupère toutes les lignes du panier pour une commande donnée.
     *
     * @param commandeId L’ID de la commande “en cours”
     * @return Liste des objets Panier (id, article_id, quantite, nom, prix)
     */
    public List<Panier> getPanier(int commandeId) {
        List<Panier> liste = new ArrayList<>();
        String sql =
                "SELECT p.id, p.article_id, p.quantite, a.nom, a.prix " +
                        "FROM panier p " +
                        "JOIN articles a ON p.article_id = a.id " +
                        "WHERE p.commande_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Panier p = new Panier(
                            rs.getInt("id"),
                            rs.getInt("article_id"),
                            rs.getInt("quantite"),
                            rs.getString("nom"),
                            rs.getDouble("prix")
                    );
                    liste.add(p);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    /**
     * Supprime une ligne du panier par son ID.
     *
     * @param id L’ID de la ligne panier à supprimer
     */
    public void supprimerArticle(int id) {
        String sql = "DELETE FROM panier WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Vide entièrement le panier (toutes commandes).
     * Si vous ne voulez vider que la commande en cours,
     * ajoutez un WHERE commande_id = ... dans la requête.
     */
    public void viderPanier() {
        String sql = "DELETE FROM panier";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
