package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import Modele.Article;
import DAO.ArticlesDAO;
import Utilitaires.Session;

/**
 * Classe représentant la page d'accueil de notre appli.
 * Cette interface affiche les principales sections : bannière, top ventes,
 * catégories et permet la navigation vers les autres pages.
 *
 * @author groupe 23 TD8
 */


public class PageAccueil extends JFrame {
    /**
     * Vérifie si un utilisateur est connecté.
     * @return true si un utilisateur est connecté et false sinon
     */
    private boolean utilisateurEstConnecte() {
        return Utilitaires.Session.getUtilisateur() != null;
    }

    private void afficherMessageConnexionRequise() {
        JOptionPane.showMessageDialog(
                this,
                "Veuillez vous connecter pour accéder au catalogue.",
                "Connexion requise",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private final Color PRIMARY_COLOR = new Color(30, 30, 45);
    private final Color SECONDARY_COLOR = new Color(220, 53, 69);
    private final Color ACCENT_COLOR = new Color(255, 215, 0);
    private final Color LIGHT_BG = new Color(250, 250, 252);
    private List<Article> tousLesProduits;

    /**
     * Constructeur principal initialisant la page d'accueil.
     */

    public PageAccueil() {
        setTitle("Java Shopping | Mode Tendances 2025");
        setSize(1200, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tousLesProduits = ArticlesDAO.getAllArticles();

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

    /**
     * Crée l'en tete de la page avec le logo et la navigation
     * @return JPanel contenant l'en tete
     */
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
                    case "Catalogue" -> {
                        if (!utilisateurEstConnecte()) {
                            afficherMessageConnexionRequise();
                            return;
                        }
                        PagePrincipale page = new PagePrincipale();
                        page.setVisible(true);
                        dispose();
                    }

                }
            });

            nav.add(btn);
        }

        header.add(logoPanel, BorderLayout.WEST);
        header.add(nav, BorderLayout.EAST);
        return header;
    }

    /**
     * Crée la bannière principale avec image de fond
     * @return JPanel contenant l'image
     */
    private JPanel createBanner() {
        JPanel banner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Chargement et affichage de l'image de fond
                    BufferedImage image = ImageIO.read(new File("src/assets/banner.jpg"));
                    Image scaled = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g.drawImage(scaled, 0, 0, this);

                    // Ajout d'un overlay sombre
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
            if (!utilisateurEstConnecte()) {
                afficherMessageConnexionRequise();
                return;
            }
            PagePrincipale page = new PagePrincipale();
            page.setVisible(true);
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

        Collections.shuffle(tousLesProduits);
        List<Article> produitsAleatoires = tousLesProduits.subList(0, Math.min(4, tousLesProduits.size()));

        for (Article produit : produitsAleatoires) {
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

    private JPanel createProductCard(Article produit) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!utilisateurEstConnecte()) {
                    afficherMessageConnexionRequise();
                    return;
                }
                PagePrincipale page = new PagePrincipale();
                page.setVisible(true);
                String categorie = trouverCategorie(produit.getNom());
                if (categorie != null) {
                    filtrerPagePrincipale(page, categorie);
                }
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

    /**
     * Détermine la catégorie d'un produit à partir de son nom
     * @param nomProduit Le nom du produit
     * @return La catégorie trouvée ou null
     */
    private String trouverCategorie(String nomProduit) {
        String nomLower = nomProduit.toLowerCase();
        if (nomLower.contains("pantalon")) return "Pantalon";
        if (nomLower.contains("t-shirt") || nomLower.contains("tshirt")) return "T-Shirt";
        if (nomLower.contains("sweat")) return "Sweat";
        if (nomLower.contains("chaussure")) return "Chaussure";
        if (nomLower.contains("veste")) return "Veste";
        return null;
    }

    /**
     * Filtre la page principale par catégorie
     * @param page La page principale
     * @param categorie La catégorie à filtrer
     */
    private void filtrerPagePrincipale(PagePrincipale page, String categorie) {
        try {
            // Utilisation de la réflexion pour accéder à la méthode privée
            java.lang.reflect.Method method = PagePrincipale.class.getDeclaredMethod("filtrerParCategorie", String.class);
            method.setAccessible(true);
            method.invoke(page, categorie);
        } catch (Exception e) {
            System.err.println("Erreur lors du filtrage: " + e.getMessage());
            simulerClicCategorie(page, categorie);
        }
    }

    /**
     * Simule un clic sur le bouton de catégorie si la méthode réflexion échoue
     * @param page La page principale
     * @param categorie La catégorie à sélectionner
     */
    private void simulerClicCategorie(PagePrincipale page, String categorie) {
        for (Component comp : page.getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                for (Component subComp : ((JPanel) comp).getComponents()) {
                    if (subComp instanceof JButton) {
                        JButton btn = (JButton) subComp;
                        if (btn.getText().equalsIgnoreCase(categorie)) {
                            btn.doClick();
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Créer la section des catégories
     * @return JPanel contenant la section
     */
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

        categories.add(createCategoryCard("PANTALON", "src/assets/pantalon.jpg", "Pantalon"));
        categories.add(createCategoryCard("T-SHIRT", "src/assets/tshirt.jpg", "T-Shirt"));
        categories.add(createCategoryCard("SWEAT", "src/assets/sweat.jpg", "Sweat"));
        categories.add(createCategoryCard("CHAUSSURES", "src/assets/chaussures.jpg", "Chaussure"));
        categories.add(createCategoryCard("VESTE", "src/assets/veste.jpg", "Veste"));

        section.add(categories, BorderLayout.CENTER);
        return section;
    }

    /**
     * Crée une carte de catégorie individuelle
     * @param titre Le titre de la catégorie
     * @param imgPath Le chemin de l'image
     * @param categorie Le nom de la catégorie
     * @return JPanel représentant la carte catégorie
     */
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
                if (!utilisateurEstConnecte()) {
                    afficherMessageConnexionRequise();
                    return;
                }
                PagePrincipale page = new PagePrincipale();
                page.setVisible(true);
                filtrerPagePrincipale(page, categorie);
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

    /**
     * Crée le footer de la page
     * @return JPanel contenant le footer
     */
    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setPreferredSize(new Dimension(1200, 80));
        footer.setBackground(new Color(240, 240, 240));
        footer.setLayout(new GridBagLayout());

        JPanel linksPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        linksPanel.setOpaque(false);

        JLabel copyright = new JLabel("© 2025 Java Shopping | Tous droits réservés | ");
        copyright.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyright.setForeground(Color.DARK_GRAY);

        JLabel mentionsLegales = createClickableLabel("Mentions légales", () -> {
            new PageMentionsLegales().setVisible(true);
            dispose();
        });

        JLabel contact = createClickableLabel("Contact", () -> {
            new PageContact().setVisible(true);
            dispose();
        });

        // Assemblage des éléments
        linksPanel.add(copyright);
        linksPanel.add(mentionsLegales);
        linksPanel.add(new JLabel("|"));
        linksPanel.add(contact);

        footer.add(linksPanel);
        return footer;
    }

    /**
     * Crée un label cliquable
     * @param text Le texte du label
     * @param action L'action à exécuter au clic
     * @return Le label créé
     */
    private JLabel createClickableLabel(String text, Runnable action) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(PRIMARY_COLOR);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(SECONDARY_COLOR);
                label.setText("<html><u>" + text + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(PRIMARY_COLOR);
                label.setText(text);
            }
        });

        return label;
    }

    /**
     * Classe interne pour les boutons stylisés de l'interface.
     */
    private static class StyledButton extends JButton {
        /**
         * Crée un bouton avec le style spécifique de l'application.
         * @param text Le texte à afficher sur le bouton
         */
        public StyledButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * Méthode principale pour lancer l'appli.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageAccueil().setVisible(true));
    }

}
