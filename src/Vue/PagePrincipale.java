package Vue;

import Utilitaires.Session;
import Modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class PagePrincipale extends JFrame {
    private JButton boutonProfil;

    public PagePrincipale() {
        initUI();
    }

    private void initUI() {
        Utilisateur user = Session.getUtilisateur();

        setTitle("Boutique Shopping - Accueil");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- En-tête + bouton Profil ---
        JPanel panelEntete = new JPanel(new BorderLayout());
        panelEntete.setBackground(new Color(255, 153, 0));

        // Logo à gauche
        JLabel labelLogo = new JLabel("Ma Boutique");
        labelLogo.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelLogo.setForeground(Color.WHITE);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelEntete.add(labelLogo, BorderLayout.WEST);

        // Barre de recherche au centre
        JTextField barreRecherche = new JTextField();
        barreRecherche.setPreferredSize(new Dimension(400, 30));
        panelEntete.add(barreRecherche, BorderLayout.CENTER);

        // Bouton Profil à droite
        JPanel panelProfil = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelProfil.setOpaque(false);
        boutonProfil = new JButton("Profil");
        panelProfil.add(boutonProfil);
        panelEntete.add(panelProfil, BorderLayout.EAST);

        add(panelEntete, BorderLayout.NORTH);

        // --- Ici ton code existant pour navigation, contenu, footer… ---

        // Action du bouton Profil
        boutonProfil.addActionListener(e -> {
            new PageProfil().setVisible(true);
        });
    }
}
