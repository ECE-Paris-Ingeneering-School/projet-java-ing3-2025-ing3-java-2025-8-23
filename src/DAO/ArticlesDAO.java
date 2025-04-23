package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modele.Article;
import Utilitaires.ConnectionFactory;

public class ArticlesDAO {

    public static List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT id, nom, prix, image, marque FROM articles";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                byte[] imageData = rs.getBytes("image");
                String marque = rs.getString("marque");
                articles.add(new Article(id, nom,prix,imageData, marque));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }
}