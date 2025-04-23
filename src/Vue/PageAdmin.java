package Vue;

import Utilitaires.Session;
import Modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * Interface pour les administrateurs :
 * - Gestion de l'inventaire
 * - Création d'offres / rabais
 * - Maintenance des dossiers clients
 */
public class PageAdmin extends JFrame {

    public PageAdmin() {
        initUI();
    }

    private void initUI() {
        Utilisateur admin = Session.getUtilisateur();

        setTitle("Admin Dashboard - " + admin.getPrenom() + " " + admin.getNom());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Barre d'onglets pour séparer les fonctionnalités
        JTabbedPane onglets = new JTabbedPane();

        // Onglet inventaire
        JPanel panelInventaire = new JPanel(new BorderLayout());
        panelInventaire.add(new JLabel("Gestion de l'inventaire"), BorderLayout.NORTH);
        // TODO: ajouter table, formulaires d'ajout/suppression, etc.
        onglets.addTab("Inventaire", panelInventaire);

        // Onglet offres
        JPanel panelOffres = new JPanel(new BorderLayout());
        panelOffres.add(new JLabel("Création / gestion des offres"), BorderLayout.NORTH);
        // TODO: ajouter formulaire de rabais, liste des promotions...
        onglets.addTab("Offres & Rabais", panelOffres);

        // Onglet clients
        JPanel panelClients = new JPanel(new BorderLayout());
        panelClients.add(new JLabel("Maintenance des dossiers clients"), BorderLayout.NORTH);
        // TODO: liste des utilisateurs, détails, actions…
        onglets.addTab("Clients", panelClients);

        // Onglet statistiques (optionnel)
        JPanel panelStats = new JPanel(new BorderLayout());
        panelStats.add(new JLabel("Statistiques & Reporting"), BorderLayout.NORTH);
        // TODO: graphiques, exports...
        onglets.addTab("Statistiques", panelStats);

        add(onglets, BorderLayout.CENTER);

        // Bouton Déconnexion en bas
        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.addActionListener(e -> {
            Session.clear();
            for (Window w : Window.getWindows()) w.dispose();
            new PageConnexion().setVisible(true);
        });
        JPanel panelSud = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSud.add(btnDeconnexion);
        add(panelSud, BorderLayout.SOUTH);
    }
}
