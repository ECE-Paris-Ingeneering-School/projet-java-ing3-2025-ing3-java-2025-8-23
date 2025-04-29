//https://openclassrooms.com/fr/courses/6982461-utilisez-spring-data-pour-interagir-avec-vos-bases-de-donnees/7202741-identifiez-les-avantages-de-java-pour-communiquer-avec-une-bdd
package Utilitaires;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe fournit une méthode utilitaire pour établir une connexion à la BDD
 * Elle utilise le pattern Factory pour créer des connections JDBC.
 *
 * @author groupe 23 TD8
 */
public class ConnectionFactory {
    // Configuration de la connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/shopping?serverTimezone=UTC";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "root"; // À changer en production !

    /**
     * Établit et retourne une connexion à la BDD
     *
     * @return Un objet Connection valide
     * @throws SQLException Si la connexion échoue

     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }
}