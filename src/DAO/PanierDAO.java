package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utilitaires.ConnectionFactory;
import Modele.Panier;

import javax.swing.*;

/**
 * DAO (Data Access Object) pour la gestion du panier
 * <p>
 * Permet d'ajouter, mettre à jour, lire et supprimer des articles
 * associés à une commande en base de données.
 * </p>
 *
 * @author groupe 23 TD8
 */
public class PanierDAO {

    /**
     * Ajoute un produit au panier ou met à jour sa quantité si il est déja présent
     *
     * @param commandeId l'identifiant de la commande en cours
     * @param articleId l'identifiant de l'article à ajouter ou mettre à jour
     * @param quantite la quantité à ajouter
     */

    public int ajouterProduit(int commandeId, int articleId, int quantite) {
        String checkSql  = "SELECT quantite FROM panier WHERE commande_id = ? AND article_id = ?";
        String updateSql = "UPDATE panier SET quantite = ? WHERE commande_id = ? AND article_id = ?";
        String insertSql = "INSERT INTO panier (commande_id, article_id, quantite) VALUES (?, ?, ?)";
        String stockSql  = "SELECT stock FROM articles WHERE id = ?";
        String decrementStockSql = "UPDATE articles SET stock = stock - ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {

            int stockDisponible = 0;
            try (PreparedStatement psStock = conn.prepareStatement(stockSql)) {
                psStock.setInt(1, articleId);
                try (ResultSet rsStock = psStock.executeQuery()) {
                    if (rsStock.next()) {
                        stockDisponible = rsStock.getInt("stock");
                    }
                }
            }

            if (stockDisponible < quantite) {
                JOptionPane.showMessageDialog(null,
                        "Stock insuffisant pour l'article sélectionné (disponible : " + stockDisponible + ")",
                        "Erreur de stock",
                        JOptionPane.ERROR_MESSAGE);
                return 1;
            }

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
                    } else {
                        try (PreparedStatement psIns = conn.prepareStatement(insertSql)) {
                            psIns.setInt(1, commandeId);
                            psIns.setInt(2, articleId);
                            psIns.setInt(3, quantite);
                            psIns.executeUpdate();
                        }
                    }
                }
            }

            try (PreparedStatement psDec = conn.prepareStatement(decrementStockSql)) {
                psDec.setInt(1, quantite);
                psDec.setInt(2, articleId);
                psDec.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Une erreur s'est produite : " + ex.getMessage(),
                    "Erreur SQL",
                    JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }



    /**
     * Récupère tous les articles du panier pour une commande spécifique.
     *
     * @param commandeId l'identifiant de la commande
     * @return une liste de {@link Panier} contenant les produits et leurs infos
     */
    public List<Panier> getPanier(int commandeId) {
        List<Panier> liste = new ArrayList<>();
        String sql = "SELECT p.id, p.article_id, p.quantite, a.nom, a.prix " +
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
     * Supprime un article du panier en fonction de son identifiant.
     *
     * @param id l'identifiant de la ligne dans le panier
     */
    public void supprimerArticle(int id) {
        String selectSql = "SELECT article_id, quantite FROM panier WHERE id = ?";
        String deleteSql = "DELETE FROM panier WHERE id = ?";
        String updateStockSql = "UPDATE articles SET stock = stock + ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            try (PreparedStatement psSelect = conn.prepareStatement(selectSql)) {
                psSelect.setInt(1, id);
                try (ResultSet rs = psSelect.executeQuery()) {
                    if (rs.next()) {
                        int articleId = rs.getInt("article_id");
                        int quantite = rs.getInt("quantite");

                        try (PreparedStatement psUpdateStock = conn.prepareStatement(updateStockSql)) {
                            psUpdateStock.setInt(1, quantite);
                            psUpdateStock.setInt(2, articleId);
                            psUpdateStock.executeUpdate();
                        }
                    }
                }
            }

            try (PreparedStatement psDelete = conn.prepareStatement(deleteSql)) {
                psDelete.setInt(1, id);
                psDelete.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Vide la totalité du panier d'une commande donnée
     *
     * @param commandeId l'identifiant de la commande
     */
    public void viderPanierCommande(int commandeId) {
        String selectSql = "SELECT article_id, quantite FROM panier WHERE commande_id = ?";
        String deleteSql = "DELETE FROM panier WHERE commande_id = ?";
        String updateStockSql = "UPDATE articles SET stock = stock + ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            try (PreparedStatement psSelect = conn.prepareStatement(selectSql)) {
                psSelect.setInt(1, commandeId);
                try (ResultSet rs = psSelect.executeQuery()) {
                    while (rs.next()) {
                        int articleId = rs.getInt("article_id");
                        int quantite = rs.getInt("quantite");

                        try (PreparedStatement psUpdateStock = conn.prepareStatement(updateStockSql)) {
                            psUpdateStock.setInt(1, quantite);
                            psUpdateStock.setInt(2, articleId);
                            psUpdateStock.executeUpdate();
                        }
                    }
                }
            }

            try (PreparedStatement psDelete = conn.prepareStatement(deleteSql)) {
                psDelete.setInt(1, commandeId);
                psDelete.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
