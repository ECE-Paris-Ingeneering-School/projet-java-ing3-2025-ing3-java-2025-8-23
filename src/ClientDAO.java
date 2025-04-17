import java.sql.*;

public class ClientDAO {
    private Connection conn;

    public ClientDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/shopping", "root", "root");
    }

    public Client seConnecter(String email, String mdp) throws SQLException {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, mdp);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Client(rs.getInt("id"), email, mdp);
        }
        return null;
    }

    public boolean inscrire(String email, String mdp, String rang) throws SQLException {
        String query = "INSERT INTO utilisateurs (email, mot_de_passe, rang) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, mdp);
        stmt.setString(3, rang);
        return stmt.executeUpdate() > 0;
    }
}
