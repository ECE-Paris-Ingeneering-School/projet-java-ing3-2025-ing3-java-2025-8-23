package Vue;

import Modele.ModeleImage;
import Modele.ProduitImage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PagePrincipale extends JFrame {
    public PagePrincipale() {
        setTitle("Boutique Shopping - Accueil");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- En-tête ---
        JPanel panelEntete = new JPanel(new BorderLayout());
        panelEntete.setBackground(new Color(255, 153, 0));

        JLabel labelLogo = new JLabel("Ma Boutique");
        labelLogo.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelLogo.setForeground(Color.WHITE);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelEntete.add(labelLogo, BorderLayout.WEST);

        JPanel centerSearchPanel = new JPanel(new BorderLayout());
        centerSearchPanel.setOpaque(false);
        JTextField barreRecherche = new JTextField();
        barreRecherche.setPreferredSize(new Dimension(300, 30));
        centerSearchPanel.add(barreRecherche, BorderLayout.CENTER);
        JButton boutonRecherche = new JButton("Rechercher");
        centerSearchPanel.add(boutonRecherche, BorderLayout.EAST);
        panelEntete.add(centerSearchPanel, BorderLayout.CENTER);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setOpaque(false);
        JButton btnPanier = new JButton("Mon Panier");
        JButton btnDeconnexion = new JButton("Déconnexion");
        rightButtonPanel.add(btnPanier);
        rightButtonPanel.add(btnDeconnexion);
        panelEntete.add(rightButtonPanel, BorderLayout.EAST);

        btnDeconnexion.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new PageConnexion().setVisible(true));
        });

        btnPanier.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new Panier().setVisible(true));
        });

        add(panelEntete, BorderLayout.NORTH);

        // --- Navigation à gauche ---
        JPanel panelNavigation = new JPanel();
        panelNavigation.setLayout(new BoxLayout(panelNavigation, BoxLayout.Y_AXIS));
        panelNavigation.setPreferredSize(new Dimension(150, 600));
        panelNavigation.setBackground(new Color(240, 240, 240));
        String[] categories = {"Pantalons", "T-Shirt", "Sweat", "Chaussures", "Autres"};
        for (String categorie : categories) {
            JButton boutonCategorie = new JButton(categorie);
            boutonCategorie.setAlignmentX(Component.CENTER_ALIGNMENT);
            boutonCategorie.setMaximumSize(new Dimension(140, 30));
            panelNavigation.add(Box.createRigidArea(new Dimension(0, 10)));
            panelNavigation.add(boutonCategorie);
        }
        add(panelNavigation, BorderLayout.WEST);

        // --- Panneau central : affichage des produits ---
        JPanel panelContenu = new JPanel(new GridLayout(0, 4, 20, 20));
        panelContenu.setBackground(Color.WHITE);

        List<ProduitImage> produits = ModeleImage.getProduitsDepuisBDD();

        for (ProduitImage produit : produits) {
            JPanel panelProduit = new JPanel(new BorderLayout());
            panelProduit.setPreferredSize(new Dimension(200, 220));
            panelProduit.setBackground(Color.WHITE);
            panelProduit.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel imageLabel;
            byte[] data = produit.getImage();

            if (data != null && data.length > 0) {
                ImageIcon icon = new ImageIcon(data);

                if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel = new JLabel(new ImageIcon(image));
                } else {
                    imageLabel = new JLabel("Image corrompue");
                }
            } else {
                imageLabel = new JLabel("Pas d'image");
            }

            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.TOP);

            // --- CLICK : ouvrir popup d’ajout au panier ---
            imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
                    JPanel popupPanel = new JPanel(new GridLayout(0, 1, 5, 5));
                    popupPanel.add(new JLabel("Produit : " + produit.getNom()));
                    popupPanel.add(new JLabel("Prix unitaire : " + produit.getPrix() + " €"));
                    popupPanel.add(new JLabel("Quantité :"));
                    popupPanel.add(spinner);

                    int result = JOptionPane.showConfirmDialog(
                            null, popupPanel, "Ajouter au panier", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        int quantite = (int) spinner.getValue();
                        double prixUnitaire = produit.getPrix();
                        double total = prixUnitaire * quantite;
                        double reduction = 0;

                        if (quantite >= 10) {
                            reduction = 0.90;
                            total -= produit.getPrix() * reduction;
                        }

                        String message = "Produit : " + produit.getNom() + "\n"
                                + "Quantité : " + quantite + "\n"
                                + "Prix unitaire : " + prixUnitaire + " €\n";

                        if (reduction > 0) {
                            message += "Réduction : -10 %\n";
                        }

                        message += "Total à payer : " + total + " €";

                        JOptionPane.showMessageDialog(null, message, "Ajout au panier", JOptionPane.INFORMATION_MESSAGE);

                        System.out.println("Ajouté au panier : " + produit.getNom()
                                + " x" + quantite + " = " + total + "€ (réduction : " + reduction + "€)");
                    }
                }
            });

            panelProduit.add(imageLabel, BorderLayout.CENTER);

            JLabel labelNom = new JLabel(produit.getNom(), SwingConstants.CENTER);
            labelNom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

            JLabel labelPrix = new JLabel(produit.getPrix() + " €", SwingConstants.CENTER);
            labelPrix.setForeground(new Color(30, 144, 255));
            labelPrix.setFont(new Font("SansSerif", Font.BOLD, 13));

            JPanel blocBas = new JPanel(new GridLayout(2, 1));
            blocBas.setOpaque(false);
            blocBas.add(labelNom);
            blocBas.add(labelPrix);

            panelProduit.add(blocBas, BorderLayout.SOUTH);
            panelContenu.add(panelProduit);
        }

        JScrollPane scrollPane = new JScrollPane(panelContenu);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);

        // --- Pied de page ---
        JPanel panelPied = new JPanel();
        panelPied.setBackground(new Color(200, 200, 200));
        panelPied.add(new JLabel("© 2025 Ma Boutique. Tous droits réservés."));
        add(panelPied, BorderLayout.SOUTH);
    }
}
