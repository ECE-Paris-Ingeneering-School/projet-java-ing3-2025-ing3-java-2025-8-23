package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modele.StatistiqueArticle;
import Utilitaires.ConnectionFactory;

/**
 * DAO permettant de récupérer les statistiques de ventes des articles.
 * Permet de calculer le nombre total d'articles vendus et le chiffre d'affaires généré par article.
 *
 * Utilisé dans la page administrateur pour l'affichage des statistiques.
 */
public class StatistiquesDAO {

    /**
     * Récupère les statistiques de ventes pour chaque article.
     *
     * @return Une liste d'objets {@code StatistiqueArticle} contenant le nom de l'article,
     *         la quantité totale vendue et le total généré en euros.
     */
    public List<StatistiqueArticle> getStatistiquesArticles() {
        List<StatistiqueArticle> stats = new ArrayList<>();

        String sql = """
            SELECT a.nom, SUM(p.quantite) AS total_qte, SUM(p.quantite * a.prix) AS total_prix
            FROM panier p
            JOIN articles a ON p.article_id = a.id
            GROUP BY a.nom
            ORDER BY total_qte DESC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                int qte = rs.getInt("total_qte");
                double total = rs.getDouble("total_prix");
                stats.add(new StatistiqueArticle(nom, qte, total));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
}
