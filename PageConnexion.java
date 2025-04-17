package Vue;

import javax.swing.*;
import java.awt.*;
import Controleur.PageConnexionControleur;

public class PageConnexion extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;
    private JButton boutonInscription;
    // Bouton pour s'inscrire

    // Déclaration des boutons radio (à mettre en haut avec les autres attributs)
    private JRadioButton radioOui;
    private JRadioButton radioNon;


    public PageConnexion() {
        setTitle("Connexion - Boutique Shopping");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création des composants
        champEmail = new JTextField(20);
        champMotDePasse = new JPasswordField(20);
        boutonConnexion = new JButton("Se connecter");
        boutonInscription = new JButton("S'inscrire");

        // Utilisation d'un panel pour les champs de saisie
        JPanel panelChamps = new JPanel(new GridLayout(2, 2, 5, 5));
        panelChamps.add(new JLabel("Adresse Email :"));
        panelChamps.add(champEmail);
        panelChamps.add(new JLabel("Mot de passe :"));
        panelChamps.add(champMotDePasse);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBoutons.add(boutonConnexion);
        panelBoutons.add(boutonInscription);

        // Panel pour la question "Êtes-vous administrateur ?"
        JPanel panelAdmin = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelAdmin.setBorder(BorderFactory.createTitledBorder("Êtes-vous administrateur ?"));

        radioOui = new JRadioButton("Oui");
        radioNon = new JRadioButton("Non");
        radioNon.setSelected(true); // Option par défaut

        ButtonGroup groupeAdmin = new ButtonGroup();
        groupeAdmin.add(radioOui);
        groupeAdmin.add(radioNon);

        panelAdmin.add(radioOui);
        panelAdmin.add(radioNon);

// Création d’un panel secondaire pour contenir les boutons + choix admin
        JPanel panelSud = new JPanel(new BorderLayout());
        panelSud.add(panelBoutons, BorderLayout.NORTH);
        panelSud.add(panelAdmin, BorderLayout.SOUTH);

        // Organisation globale dans un panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(panelChamps, BorderLayout.CENTER);
        panelPrincipal.add(panelSud, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Instanciation du contrôleur de la page d'accueil
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
    public boolean estAdminCoche() {return radioOui.isSelected();}


    // Méthode main de test (si vous souhaitez lancer directement la page de connexion)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PageConnexion().setVisible(true);
        });
    }
}
