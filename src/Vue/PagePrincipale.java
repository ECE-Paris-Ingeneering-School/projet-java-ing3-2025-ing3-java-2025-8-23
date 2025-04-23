package Vue;

import Modele.ModeleImage;
import Modele.ProduitImage;

import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PagePrincipale extends JFrame {
    private JTextField barreRecherche;
    private JComboBox<String> comboMarques;
    private JPanel panelContenu;
    private List<ProduitImage> tousLesProduits;

    public PagePrincipale() {
        Utilisateur user = Session.getUtilisateur();

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

        JPanel centerSearchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        barreRecherche = new JTextField(20);
        comboMarques = new JComboBox<>();
        comboMarques.setPreferredSize(new Dimension(100, 30));
        barreRecherche.setPreferredSize(new Dimension(150, 30));

        centerSearchPanel.setOpaque(false);
        centerSearchPanel.add(new JLabel("Recherche : "));
        centerSearchPanel.add(barreRecherche);
        centerSearchPanel.add(new JLabel("Marque : "));
        centerSearchPanel.add(comboMarques);

        panelEntete.add(centerSearchPanel, BorderLayout.CENTER);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setOpaque(false);
        JButton btnPanier = new JButton("Mon Panier");
        JButton btnProfil = new JButton("Profil");
        btnPanier.setBackground(Color.WHITE);
        btnProfil.setBackground(Color.WHITE);
        rightButtonPanel.add(btnPanier);
        rightButtonPanel.add(btnProfil);
        panelEntete.add(rightButtonPanel, BorderLayout.EAST);

        btnProfil.addActionListener(e -> {

             new PageProfil().setVisible(true);;
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
        String[] categories = {"Pantalon", "T-Shirt", "Sweat", "Chaussure", "Veste"};
        for (String categorie : categories) {
            JButton boutonCategorie = new JButton(categorie);
            boutonCategorie.setAlignmentX(Component.CENTER_ALIGNMENT);
            boutonCategorie.setMaximumSize(new Dimension(140, 30));
            boutonCategorie.addActionListener(e -> filtrerParCategorie(categorie));
            panelNavigation.add(Box.createRigidArea(new Dimension(0, 10)));
            panelNavigation.add(boutonCategorie);
        }
        add(panelNavigation, BorderLayout.WEST);

        // --- Centre : panneau de contenu ---
        panelContenu = new JPanel(new GridLayout(0, 4, 20, 20));
        panelContenu.setBackground(Color.WHITE);
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

        // --- Données et actions ---
        tousLesProduits = ModeleImage.getProduitsDepuisBDD();
        chargerMarques();
        afficherProduits(tousLesProduits);

        barreRecherche.addActionListener(e -> filtrerProduits());
        comboMarques.addActionListener(e -> filtrerProduits());
    }

    private void filtrerProduits() {
        String texte = barreRecherche.getText().trim().toLowerCase();
        String marqueSelectionnee = comboMarques.getSelectedItem().toString();

        List<ProduitImage> filtres = tousLesProduits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(texte))
                .filter(p -> marqueSelectionnee.equals("Toutes") || p.getMarque().equalsIgnoreCase(marqueSelectionnee))
                .collect(Collectors.toList());

        afficherProduits(filtres);
    }

    private void filtrerParCategorie(String categorie) {
        List<ProduitImage> filtres = tousLesProduits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(categorie.toLowerCase()))
                .collect(Collectors.toList());
        afficherProduits(filtres);
    }

    private void chargerMarques() {
        Set<String> marques = tousLesProduits.stream()
                .map(ProduitImage::getMarque)
                .filter(m -> m != null && !m.isEmpty())
                .collect(Collectors.toSet());

        comboMarques.addItem("Toutes");
        marques.stream().sorted().forEach(comboMarques::addItem);
    }

    private void afficherProduits(List<ProduitImage> produits) {
        panelContenu.removeAll();

        for (ProduitImage produit : produits) {
            JPanel panelProduit = new JPanel(new BorderLayout());
            panelProduit.setPreferredSize(new Dimension(200, 220));
            panelProduit.setBackground(Color.WHITE);
            panelProduit.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(200, 200, 200)),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)
                    )
            ));
            panelProduit.setOpaque(true);
            panelProduit.setBackground(Color.WHITE);
            panelProduit.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            panelProduit.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));

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
                            reduction = 0.10 * total;
                            total -= reduction;
                        }

                        String message = "Produit : " + produit.getNom() + "\n"
                                + "Quantité : " + quantite + "\n"
                                + "Prix unitaire : " + prixUnitaire + " €\n";

                        if (reduction > 0) {
                            message += "Réduction : -" + (int)(reduction) + " €\n";
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

        panelContenu.revalidate();
        panelContenu.repaint();
    }


}
