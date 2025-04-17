package Modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeleImage {
    public static List<ProduitImage> getProduitsDepuisBDD() {
        List<ProduitImage> produits = new ArrayList<>();
        try {
            // Connexion à la base de données
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/shopping", "root", "root");

            String sql = "SELECT nom, image, prix FROM articles";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                byte[] imageData = rs.getBytes("image");
                double prix = rs.getDouble("prix");
                produits.add(new ProduitImage(nom, imageData, prix));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return produits;
    }
}
