package Vue;

import javax.swing.*;
import java.awt.*;
import Controleur.PageInscriptionControleur;

public class PageInscription extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JPasswordField champConfirmerMotDePasse;
    private JButton boutonInscription;

    public PageInscription() {
        setTitle("Inscription - Créer un compte");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du formulaire avec GridLayout
        JPanel panelFormulaire = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFormulaire.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelFormulaire.add(new JLabel("Adresse Email :"));
        champEmail = new JTextField();
        panelFormulaire.add(champEmail);

        panelFormulaire.add(new JLabel("Mot de passe :"));
        champMotDePasse = new JPasswordField();
        panelFormulaire.add(champMotDePasse);

        panelFormulaire.add(new JLabel("Confirmer mot de passe :"));
        champConfirmerMotDePasse = new JPasswordField();
        panelFormulaire.add(champConfirmerMotDePasse);

        // Bouton d'inscription
        boutonInscription = new JButton("S'inscrire");
        panelFormulaire.add(new JLabel("")); // Pour aligner le bouton
        panelFormulaire.add(boutonInscription);

        add(panelFormulaire);

        // Instanciation du contrôleur de la page d'inscription
        new PageInscriptionControleur(this);
    }

    // Getters pour les contrôleurs
    public JTextField getChampEmail() {
        return champEmail;
    }
    public JPasswordField getChampMotDePasse() {
        return champMotDePasse;
    }
    public JPasswordField getChampConfirmerMotDePasse() {
        return champConfirmerMotDePasse;
    }
    public JButton getBoutonInscription() {
        return boutonInscription;
    }
}
