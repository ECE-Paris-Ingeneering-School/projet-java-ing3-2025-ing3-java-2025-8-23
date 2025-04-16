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
    private JButton boutonRetour;  // Nouveau bouton pour revenir à la page de connexion

    public PageInscription() {
        setTitle("Inscription - Créer un compte");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du formulaire avec GridLayout pour les champs
        JPanel panelFormulaire = new JPanel(new GridLayout(6, 2, 5, 5));
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

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        boutonRetour = new JButton("Retour");
        boutonInscription = new JButton("S'inscrire");
        panelBoutons.add(boutonRetour);
        panelBoutons.add(boutonInscription);

        // Organisation globale : formulaire en haut, boutons en bas
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panelFormulaire, BorderLayout.CENTER);
        container.add(panelBoutons, BorderLayout.SOUTH);

        // Instanciation du contrôleur associé
        new PageInscriptionControleur(this);
    }

    // Getters pour les contrôleurs
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
    public JButton getBoutonRetour() {
        return boutonRetour;
    }
    public JButton getBoutonInscription() {
        return boutonInscription;
    }
}
