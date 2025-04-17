package Utilitaires;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Modifiez l'URL, l'utilisateur et le mot de passe en fonction de votre configuration
    private static final String URL = "jdbc:mysql://localhost:3306/db_shopping?serverTimezone=UTC";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "root"; // Remplacez par votre mot de passe si nécessaire

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }
}
