package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import Modele.ProduitImage;
import Modele.ModeleImage;

public class PageAccueil extends JFrame {

    private final Color PRIMARY_COLOR = new Color(30, 30, 45);
    private final Color SECONDARY_COLOR = new Color(220, 53, 69);
    private final Color ACCENT_COLOR = new Color(255, 215, 0);
    private final Color LIGHT_BG = new Color(250, 250, 252);
    private List<ProduitImage> tousLesProduits;

    public PageAccueil() {
        setTitle("Java Shopping | Mode Tendances 2025");
        setSize(1200, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Charger les produits depuis la BDD
        tousLesProduits = ModeleImage.getProduitsDepuisBDD();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(LIGHT_BG);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);

        mainPanel.add(createHeader());
        mainPanel.add(createBanner());
        mainPanel.add(createTopVentesSection());
        mainPanel.add(createCategoriesSection());
        mainPanel.add(createFooter());
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(1200, 90));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel logo = new JLabel("JAVA SHOPPING");
        logo.setFont(new Font("Montserrat", Font.BOLD, 36));
        logo.setForeground(PRIMARY_COLOR);

        JLabel subLogo = new JLabel("Mode & Élégance");
        subLogo.setFont(new Font("Montserrat", Font.PLAIN, 12));
        subLogo.setForeground(new Color(150, 150, 150));

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.add(logo);
        logoPanel.add(subLogo);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        nav.setOpaque(false);

        String[] menuItems = {"Accueil", "Connexion", "Inscription", "Catalogue"};
        for (String item : menuItems) {
            JButton btn = new StyledButton(item);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setForeground(PRIMARY_COLOR);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setForeground(SECONDARY_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setForeground(PRIMARY_COLOR);
                }
            });

            btn.addActionListener(e -> {
                switch (item) {
                    case "Connexion" -> new PageConnexion().setVisible(true);
                    case "Inscription" -> new PageInscription().setVisible(true);
                    case "Catalogue" -> new PagePrincipale().setVisible(true);
                }
                dispose();
            });

            nav.add(btn);
        }

        header.add(logoPanel, BorderLayout.WEST);
        header.add(nav, BorderLayout.EAST);
        return header;
    }

    private JPanel createBanner() {
        JPanel banner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage image = ImageIO.read(new File("src/assets/banner.jpg"));
                    Image scaled = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g.drawImage(scaled, 0, 0, this);

                    g.setColor(new Color(0, 0, 0, 120));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } catch (Exception ex) {
                    System.err.println("Erreur chargement banner: " + ex.getMessage());
                }
            }
        };
        banner.setPreferredSize(new Dimension(1200, 400));
        banner.setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel titre = new JLabel("NOUVELLE COLLECTION 2025");
        titre.setFont(new Font("Montserrat", Font.BOLD, 42));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sousTitre = new JLabel("Découvrez les dernières tendances de la mode");
        sousTitre.setFont(new Font("SansSerif", Font.PLAIN, 18));
        sousTitre.setForeground(Color.LIGHT_GRAY);
        sousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exploreBtn = new JButton("EXPLORER LA COLLECTION");
        exploreBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        exploreBtn.setForeground(Color.WHITE);
        exploreBtn.setBackground(SECONDARY_COLOR);
        exploreBtn.setFocusPainted(false);
        exploreBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exploreBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        exploreBtn.addActionListener(e -> {
            new PagePrincipale().setVisible(true);
            dispose();
        });

        content.add(titre);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(sousTitre);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        content.add(exploreBtn);

        banner.add(content);
        return banner;
    }

    private JPanel createTopVentesSection() {
        JPanel section = new JPanel();
        section.setBackground(LIGHT_BG);
        section.setLayout(new BorderLayout());
        section.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("NOS TOP VENTES", JLabel.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 28));
        title.setForeground(PRIMARY_COLOR);
        section.add(title, BorderLayout.NORTH);

        JPanel produitsPanel = new JPanel();
        produitsPanel.setLayout(new BoxLayout(produitsPanel, BoxLayout.X_AXIS));
        produitsPanel.setBackground(LIGHT_BG);
        produitsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Sélectionner 4 produits aléatoires
        Collections.shuffle(tousLesProduits);
        List<ProduitImage> produitsAleatoires = tousLesProduits.subList(0, Math.min(4, tousLesProduits.size()));

        for (ProduitImage produit : produitsAleatoires) {
            produitsPanel.add(Box.createHorizontalStrut(20));
            produitsPanel.add(createProductCard(produit));
        }
        produitsPanel.add(Box.createHorizontalStrut(20));

        JScrollPane scrollPane = new JScrollPane(produitsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        section.add(scrollPane, BorderLayout.CENTER);
        return section;
    }

    private JPanel createProductCard(ProduitImage produit) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Image du produit
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            byte[] imageData = produit.getImage();
            if (imageData != null && imageData.length > 0) {
                ImageIcon icon = new ImageIcon(imageData);
                Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                imageLabel.setText("Pas d'image disponible");
                imageLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            }
        } catch (Exception e) {
            imageLabel.setText("Erreur de chargement");
            imageLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        }
        card.add(imageLabel, BorderLayout.CENTER);

        // Info du produit
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel nameLabel = new JLabel(produit.getNom());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel(String.format("%.2f €", produit.getPrix()));
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLabel.setForeground(SECONDARY_COLOR);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);

        card.add(infoPanel, BorderLayout.SOUTH);

        // Action lors du clic
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PagePrincipale page = new PagePrincipale();
                page.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
        });

        return card;
    }

    private JPanel createCategoriesSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(LIGHT_BG);
        section.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel title = new JLabel("NOS CATÉGORIES", JLabel.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 28));
        title.setForeground(PRIMARY_COLOR);
        section.add(title, BorderLayout.NORTH);

        JPanel categories = new JPanel(new GridLayout(1, 5, 15, 0));
        categories.setBackground(LIGHT_BG);
        categories.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // Les 5 catégories principales
        categories.add(createCategoryCard("PANTALON", "src/assets/pantalon.jpg", "Pantalon"));
        categories.add(createCategoryCard("T-SHIRT", "src/assets/tshirt.jpg", "T-Shirt"));
        categories.add(createCategoryCard("SWEAT", "src/assets/sweat.jpg", "Sweat"));
        categories.add(createCategoryCard("CHAUSSURES", "src/assets/chaussures.jpg", "Chaussure"));
        categories.add(createCategoryCard("VESTE", "src/assets/veste.jpg", "Veste"));

        section.add(categories, BorderLayout.CENTER);
        return section;
    }

    private JPanel createCategoryCard(String titre, String imgPath, String categorie) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage img = ImageIO.read(new File(imgPath));
                    Image scaled = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g.drawImage(scaled, 0, 0, this);

                    g.setColor(new Color(0, 0, 0, 70));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } catch (Exception ex) {
                    System.err.println("Erreur chargement image " + imgPath + " : " + ex.getMessage());
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        card.setPreferredSize(new Dimension(200, 220));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(titre);
        label.setFont(new Font("Montserrat", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        card.add(label, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PagePrincipale page = new PagePrincipale();
                page.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(null);
            }
        });

        return card;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setPreferredSize(new Dimension(1200, 80));
        footer.setBackground(new Color(240, 240, 240));
        footer.setLayout(new GridBagLayout());

        JLabel label = new JLabel("© 2025 Java Shopping | Tous droits réservés | Mentions légales | Contact");
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(Color.DARK_GRAY);
        footer.add(label);

        return footer;
    }

    private static class StyledButton extends JButton {
        public StyledButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageAccueil().setVisible(true));
    }
}