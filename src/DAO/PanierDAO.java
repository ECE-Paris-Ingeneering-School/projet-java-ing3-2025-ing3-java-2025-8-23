package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modele.Panier;
import Utilitaires.ConnectionFactory;

public class PanierDAO {

    public void ajouterProduit(int produitId, int quantite) {
        // Vérifie d'abord si le produit est déjà dans le panier
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Vérifier si l'article existe déjà
            String checkSql = "SELECT quantite FROM panier WHERE article_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, produitId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Mise à jour de la quantité si l'article existe déjà
                int nouvelleQuantite = rs.getInt("quantite") + quantite;
                String updateSql = "UPDATE panier SET quantite = ? WHERE article_id = ?";
                PreparedStatement updatePs = conn.prepareStatement(updateSql);
                updatePs.setInt(1, nouvelleQuantite);
                updatePs.setInt(2, produitId);
                updatePs.executeUpdate();
            } else {
                // Ajout d'un nouvel article
                String insertSql = "INSERT INTO panier (article_id, quantite) VALUES (?, ?)";
                PreparedStatement insertPs = conn.prepareStatement(insertSql);
                insertPs.setInt(1, produitId);
                insertPs.setInt(2, quantite);
                insertPs.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Panier> getPanier() {
        List<Panier> liste = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT p.id, p.article_id, p.quantite, a.nom, a.prix " +
                    "FROM panier p JOIN articles a ON p.article_id = a.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public void supprimerArticle(int id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM panier WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viderPanier() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM panier";
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}