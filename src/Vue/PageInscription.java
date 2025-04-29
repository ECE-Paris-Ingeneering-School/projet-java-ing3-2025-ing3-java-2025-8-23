package Vue;

import javax.swing.*;
import java.awt.*;
import Controleur.PageInscriptionControleur;

/**
 * Cette classe représente la fenêtre d'inscription où un nouvel utilisateur peut créer un compte.
 * Elle contient un formulaire avec les champs nécessaires et deux boutons : s'inscrire et retour.
 */
public class PageInscription extends JFrame {

    private JTextField champPrenom;
    private JTextField champNom;
    private JTextField champEmail;
    private JTextField champAdresse;
    private JPasswordField champMotDePasse;
    private JPasswordField champConfirmerMotDePasse;

    private JButton boutonInscription;
    private JButton boutonRetour;

    /**
     * Constructeur de la page d'inscription.
     * Initialise tous les composants graphiques et place les éléments dans la fenetre.
     */
    public PageInscription() {
        setTitle("Inscription - Créer un compte");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        boutonRetour = new JButton("Retour");
        boutonInscription = new JButton("S'inscrire");
        panelBoutons.add(boutonRetour);
        panelBoutons.add(boutonInscription);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panelFormulaire, BorderLayout.CENTER);
        container.add(panelBoutons, BorderLayout.SOUTH);

        // Instancie et associe le contrôleur à cette page
        new PageInscriptionControleur(this);
    }


    /** @return le champ de saisie du prénom */
    public JTextField getChampPrenom() {
        return champPrenom;
    }

    /** @return le champ de saisie du nom */
    public JTextField getChampNom() {
        return champNom;
    }

    /** @return le champ de saisie du mail */
    public JTextField getChampEmail() {
        return champEmail;
    }

    /** @return le champ de saisie de l'adresse */
    public JTextField getChampAdresse() {
        return champAdresse;
    }

    /** @return le champ de saisie du mot de passe */
    public JPasswordField getChampMotDePasse() {
        return champMotDePasse;
    }

    /** @return le champ de saisie pour confirmer le mot de passe */
    public JPasswordField getChampConfirmerMotDePasse() {
        return champConfirmerMotDePasse;
    }

    /** @return le bouton pour revenir à la page précédente */
    public JButton getBoutonRetour() {
        return boutonRetour;
    }

    /** @return le bouton pour valider l'inscription */
    public JButton getBoutonInscription() {
        return boutonInscription;
    }
}
