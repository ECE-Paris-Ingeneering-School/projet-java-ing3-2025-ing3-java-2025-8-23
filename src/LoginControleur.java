import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginControleur {
    private LoginView view;
    private ClientDAO userDAO;

    public LoginControleur(LoginView view, ClientDAO dao) {
        this.view = view;
        this.userDAO = dao;

        view.loginBtn.addActionListener(e -> seConnecter());
        view.registerBtn.addActionListener(e -> sInscrire());
    }

    private void seConnecter() {
        try {
            String email = view.emailField.getText();
            String mdp = new String(view.mdpField.getPassword());
            Client user = userDAO.seConnecter(email, mdp);
            if (user != null) {
                JOptionPane.showMessageDialog(view, "Bienvenue ");
                // ici on pourrait rediriger vers une interface client/admin
            } else {
                JOptionPane.showMessageDialog(view, "Identifiants incorrects.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sInscrire() {
        try {
            String email = view.emailField.getText();
            String mdp = new String(view.mdpField.getPassword());
            String rang = JOptionPane.showInputDialog(view, "Rang (admin ou client):");

            if (rang.equals("admin") || rang.equals("client")) {
                if (userDAO.inscrire(email, mdp, rang)) {
                    JOptionPane.showMessageDialog(view, "Compte créé !");
                } else {
                    JOptionPane.showMessageDialog(view, "Erreur d'inscription.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Rang invalide.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
