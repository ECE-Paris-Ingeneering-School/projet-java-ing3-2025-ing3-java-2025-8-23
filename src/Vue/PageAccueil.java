package Vue;

import javax.swing.*;
import java.awt.*;

import Controleur.PageAccueilControleur;

public class PageAccueil extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;

    public PageAccueil() {
        setTitle("Page d'accueil - Shopping");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        champEmail = new JTextField(20);
        champMotDePasse = new JPasswordField(20);
        boutonConnexion = new JButton("Se connecter");

        JPanel panneauPrincipal = new JPanel(new GridLayout(3, 2, 5, 5));
        panneauPrincipal.add(new JLabel("Adresse Email :"));
        panneauPrincipal.add(champEmail);
        panneauPrincipal.add(new JLabel("Mot de passe :"));
        panneauPrincipal.add(champMotDePasse);
        panneauPrincipal.add(new JLabel(""));
        panneauPrincipal.add(boutonConnexion);

        add(panneauPrincipal);

        new PageAccueilControleur(this);
    }

    public JTextField getChampEmail() {
        return champEmail;
    }

    public JPasswordField getChampMotDePasse() {
        return champMotDePasse;
    }

    public JButton getBoutonConnexion() {
        return boutonConnexion;
    }
}
