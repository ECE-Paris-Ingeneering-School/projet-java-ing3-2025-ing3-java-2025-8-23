package DAO;

import Modele.Article;
import Utilitaires.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticlesDAO {

    /**
     * Récupère tous les articles de la table `articles`.
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
     * Crée un nouvel article.
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
     * Met à jour un article existant.
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
     * Supprime un article par son ID.
     */
    public boolean deleteArticle(int id) {
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
