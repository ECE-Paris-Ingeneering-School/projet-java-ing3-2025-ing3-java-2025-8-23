//https://boostcamp.omneseducation.com/course/view.php?id=377193&section=6#tabs-tree-start
//https://boostcamp.omneseducation.com/course/view.php?id=377193&section=5#tabs-tree-start
package DAO;

import Modele.Article;
import Utilitaires.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) pour la gestion des articles
 * <p>
 * Permet d'effectuer des opérations (création, lecture, modification, suppression) sur les articles
 * stockés en BDD
 * </p>
 *
 * @author groupe 23 TD8
 */
public class ArticlesDAO {

    /**
     * Récupère tous les articles de la table {@code articles}
     *
     * @return une liste d'objets {@link Article} représentant tous les articles dispos
     */
    public static List<Article> getAllArticles() {
        List<Article> liste = new ArrayList<>();
        String sql = "SELECT id, nom, marque, description, prix, stock, image FROM articles";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                liste.add(new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("marque"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        rs.getBytes("image")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    /**
     * Ajoute un nouvel article dans la BDD
     *
     * @param art l'article à insérer
     * @return {@code true} si l'insertion a marchée et {@code false} sinon
     */
    public boolean createArticle(Article art) {
        String sql = "INSERT INTO articles (nom, marque, description, prix, stock, image) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, art.getNom());
            ps.setString(2, art.getMarque());
            ps.setString(3, art.getDescription());
            ps.setDouble(4, art.getPrix());
            ps.setInt(5, art.getStock());
            ps.setBytes(6, art.getImage());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour les informations d'un article existant
     *
     * @param art l'article contenant les nouvelles informations
     * @return {@code true} si la mise à jour a fonctionnée et {@code false} sinon
     */
    public boolean updateArticle(Article art) {
        String sql = "UPDATE articles SET nom = ?, marque = ?, description = ?, "
                + "prix = ?, stock = ?, image = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, art.getNom());
            ps.setString(2, art.getMarque());
            ps.setString(3, art.getDescription());
            ps.setDouble(4, art.getPrix());
            ps.setInt(5, art.getStock());
            ps.setBytes(6, art.getImage());
            ps.setInt(7, art.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un article de la BDD en fonction de son identifiant
     *
     * @param id l'identifiant de l'article à supprimer
     * @return {@code true} si la suppression a réussi et {@code false} sinon
     */
    public boolean supprimerArticle(int id) {
        String sql = "DELETE FROM articles WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
