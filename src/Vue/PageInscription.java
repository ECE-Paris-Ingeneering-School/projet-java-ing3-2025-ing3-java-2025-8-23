package Vue;

import javax.swing.*;
import java.awt.*;
import Controleur.PageInscriptionControleur;

public class PageInscription extends JFrame {
    private JTextField champPrenom;
    private JTextField champNom;
    private JTextField champEmail;
    private JTextField champAdresse;
    private JPasswordField champMotDePasse;
    private JPasswordField champConfirmerMotDePasse;
    private JButton boutonInscription;

    public PageInscription() {
        setTitle("Inscription - Créer un compte");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 7 lignes x 2 colonnes (1 ligne par champ + 1 pour le bouton)
        JPanel panelFormulaire = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulaire.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelFormulaire.add(new JLabel("Prénom :"));
        champPrenom = new JTextField();
        panelFormulaire.add(champPrenom);

        panelFormulaire.add(new JLabel("Nom :"));
        champNom = new JTextField();
        panelFormulaire.add(champNom);

        panelFormulaire.add(new JLabel("Email :"));
        champEmail = new JTextField();
        panelFormulaire.add(champEmail);

        panelFormulaire.add(new JLabel("Adresse :"));
        champAdresse = new JTextField();
        panelFormulaire.add(champAdresse);

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

        // Contrôleur
        new PageInscriptionControleur(this);
    }

    // Getters
    public JTextField getChampPrenom() {
        return champPrenom;
    }
    public JTextField getChampNom() {
        return champNom;
    }
    public JTextField getChampEmail() {
        return champEmail;
    }
    public JTextField getChampAdresse() {
        return champAdresse;
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
