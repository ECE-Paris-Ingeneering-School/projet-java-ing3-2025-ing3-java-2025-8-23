package Vue;

import javax.swing.*;
import java.awt.*;
import Controleur.PageConnexionControleur;

public class PageConnexion extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;
    private JButton boutonInscription;

    public PageConnexion() {
        setTitle("Connexion - Boutique Shopping");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création des composants
        champEmail = new JTextField(20);
        champMotDePasse = new JPasswordField(20);
        boutonConnexion = new JButton("Se connecter");
        boutonInscription = new JButton("S'inscrire");

        // Panel pour les champs de saisie
        JPanel panelChamps = new JPanel(new GridLayout(2, 2, 5, 5));
        panelChamps.add(new JLabel("Adresse Email :"));
        panelChamps.add(champEmail);
        panelChamps.add(new JLabel("Mot de passe :"));
        panelChamps.add(champMotDePasse);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBoutons.add(boutonConnexion);
        panelBoutons.add(boutonInscription);

        // Organisation globale dans un panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(panelChamps, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // Instanciation du contrôleur de la page de connexion
        new PageConnexionControleur(this);
    }

    // Getters pour le contrôleur
    public JTextField getChampEmail() {
        return champEmail;
    }

    public JPasswordField getChampMotDePasse() {
        return champMotDePasse;
    }

    public JButton getBoutonConnexion() {
        return boutonConnexion;
    }

    public JButton getBoutonInscription() {
        return boutonInscription;
    }

    // Méthode main de test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PageConnexion().setVisible(true);
        });
    }
}
