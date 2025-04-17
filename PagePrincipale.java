package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PagePrincipale extends JFrame {
    public PagePrincipale() {
        setTitle("Boutique Shopping - Accueil");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centré à l'écran

        // Disposition principale
        setLayout(new BorderLayout());


        // --- 1. Panel d'en-tête (Nord) ---
        JPanel panelEntete = new JPanel(new BorderLayout());
        panelEntete.setBackground(new Color(255, 153, 0)); // Teinte orange

        // --- Logo (à gauche) ---
        JLabel labelLogo = new JLabel("Ma Boutique");
        labelLogo.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelLogo.setForeground(Color.WHITE);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelEntete.add(labelLogo, BorderLayout.WEST);

        // --- Centre : Barre de recherche + bouton rechercher ---
        JPanel centerSearchPanel = new JPanel(new BorderLayout());
        centerSearchPanel.setOpaque(false); // pour garder l’arrière-plan orange

        JTextField barreRecherche = new JTextField();
        barreRecherche.setPreferredSize(new Dimension(300, 30));
        centerSearchPanel.add(barreRecherche, BorderLayout.CENTER);

        JButton boutonRecherche = new JButton("Rechercher");
        centerSearchPanel.add(boutonRecherche, BorderLayout.EAST);

        panelEntete.add(centerSearchPanel, BorderLayout.CENTER);

        // --- Droite : Boutons Panier + Déconnexion ---
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setOpaque(false);

        JButton btnPanier = new JButton("Mon Panier");
        JButton btnDeconnexion = new JButton("Déconnexion");

        rightButtonPanel.add(btnPanier);
        rightButtonPanel.add(btnDeconnexion);

        panelEntete.add(rightButtonPanel, BorderLayout.EAST);


        // Action du bouton déconnexion
        btnDeconnexion.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new PageConnexion().setVisible(true);
            });
        });
        btnPanier.addActionListener(e -> {
            dispose();  // ferme la page actuelle
            SwingUtilities.invokeLater(() -> new Panier().setVisible(true));  // ouvre la page Panier
        });


        add(panelEntete, BorderLayout.NORTH);


        // --- 2. Panneau de navigation (Ouest) ---
        JPanel panelNavigation = new JPanel();
        panelNavigation.setLayout(new BoxLayout(panelNavigation, BoxLayout.Y_AXIS));
        panelNavigation.setPreferredSize(new Dimension(150, 600));
        panelNavigation.setBackground(new Color(240, 240, 240));

        // Liste fictive de catégories
        String[] categories = {"Pantalons", "T-Shirt", "Sweat", "Chaussures", "Autres"};
        for (String categorie : categories) {
            JButton boutonCategorie = new JButton(categorie);
            boutonCategorie.setAlignmentX(Component.CENTER_ALIGNMENT);
            boutonCategorie.setMaximumSize(new Dimension(140, 30));
            panelNavigation.add(Box.createRigidArea(new Dimension(0, 10)));  // Espace vertical
            panelNavigation.add(boutonCategorie);
        }
        add(panelNavigation, BorderLayout.WEST);

        // --- 3. Panneau central (Centre) pour afficher les produits ---
        JPanel panelContenu = new JPanel(new GridLayout(2, 3, 10, 10));
        panelContenu.setBackground(Color.WHITE);
        // Ajout de quelques "produits" fictifs
        for (int i = 1; i <= 6; i++) {
            JPanel panelProduit = new JPanel(new BorderLayout());
            panelProduit.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            JLabel labelProduit = new JLabel("Produit " + i, SwingConstants.CENTER);
            panelProduit.add(labelProduit, BorderLayout.CENTER);
            panelContenu.add(panelProduit);
        }
        add(panelContenu, BorderLayout.CENTER);

        // --- 4. Pied de page (Sud) ---
        JPanel panelPied = new JPanel();
        panelPied.setBackground(new Color(200, 200, 200));
        panelPied.add(new JLabel("© 2025 Ma Boutique. Tous droits réservés."));
        add(panelPied, BorderLayout.SOUTH);
    }
}
