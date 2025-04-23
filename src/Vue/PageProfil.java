package Vue;

import Utilitaires.Session;
import Modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * Affiche le profil complet de l'utilisateur connecté,
 * avec ID, prénom, nom, email, adresse, date d'inscription et rang.
 */
public class PageProfil extends JFrame {
    private JButton boutonFermer;
    private JButton boutonDeconnexion;

    public PageProfil() {
        initUI();
    }

    private void initUI() {
        Utilisateur user = Session.getUtilisateur();

        setTitle("Profil Utilisateur");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel d’informations (7 lignes)
        JPanel panelInfo = new JPanel(new GridLayout(7, 1, 5, 5));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelInfo.add(new JLabel("ID : " + user.getId()));
        panelInfo.add(new JLabel("Prénom : " + user.getPrenom()));
        panelInfo.add(new JLabel("Nom : "    + user.getNom()));
        panelInfo.add(new JLabel("Email : "  + user.getEmail()));
        panelInfo.add(new JLabel("Adresse : "+ user.getAdresse()));
        panelInfo.add(new JLabel("Inscrit le : " + user.getDateInscription()));
        // traduction du rang
        String libelleRang;
        switch (user.getRang()) {
            case 0: libelleRang = "Administrateur"; break;
            case 1: libelleRang = "Nouveau client"; break;
            case 2: libelleRang = "Ancien client"; break;
            default: libelleRang = "Inconnu";
        }
        panelInfo.add(new JLabel("Statut : " + libelleRang));

        add(panelInfo, BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        boutonFermer = new JButton("Fermer");
        boutonDeconnexion = new JButton("Déconnexion");
        panelBoutons.add(boutonFermer);
        panelBoutons.add(boutonDeconnexion);
        add(panelBoutons, BorderLayout.SOUTH);

        // Fermer : uniquement la fenêtre Profil
        boutonFermer.addActionListener(e -> dispose());

        // Déconnexion : vide session, ferme tout, revient à la connexion
        boutonDeconnexion.addActionListener(e -> {
            Session.clear();
            for (Window w : Window.getWindows()) w.dispose();
            new PageConnexion().setVisible(true);
        });
    }
}
