package Vue;

import javax.swing.*;
import java.awt.*;
import Controleur.PageConnexionControleur;

/**
 * Classe représentant l'interface de connexion à notre appli.
 * Permet aux utilisateurs de se connecter ou de créer un compte.
 *
 * @author groupe 23 TD8
 */
public class PageConnexion extends JFrame {
    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;
    private JButton boutonInscription;

    /**
     * Constructeur de la page de connexion.
     * Initialise l'interface graphique et associe le contrôleur.
     */
    public PageConnexion() {
        setTitle("Connexion - Boutique Shopping");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        setupInterface();

        new PageConnexionControleur(this);
    }

    /**
     * Initialise les composants graphiques.
     */
    private void initComponents() {
        champEmail = new JTextField(20);
        champMotDePasse = new JPasswordField(20);
        boutonConnexion = new JButton("Se connecter");
        boutonInscription = new JButton("S'inscrire");
    }

    /**
     * Organise les composants dans l'interface.
     */
    private void setupInterface() {
        JPanel panelChamps = new JPanel(new GridLayout(2, 2, 5, 5));
        panelChamps.add(new JLabel("Adresse Email :"));
        panelChamps.add(champEmail);
        panelChamps.add(new JLabel("Mot de passe :"));
        panelChamps.add(champMotDePasse);

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBoutons.add(boutonConnexion);
        panelBoutons.add(boutonInscription);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(panelChamps, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    /**
     * @return Le champ de saisie pour le mail
     */
    public JTextField getChampEmail() {
        return champEmail;
    }

    /**
     * @return Le champ de saisie pour le mot de passe
     */
    public JPasswordField getChampMotDePasse() {
        return champMotDePasse;
    }

    /**
     * @return Le bouton de connexion
     */
    public JButton getBoutonConnexion() {
        return boutonConnexion;
    }

    /**
     * @return Le bouton d'inscription
     */
    public JButton getBoutonInscription() {
        return boutonInscription;
    }

    /**
     * Méthode main pour tester l'interface.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PageConnexion().setVisible(true);
        });
    }
}