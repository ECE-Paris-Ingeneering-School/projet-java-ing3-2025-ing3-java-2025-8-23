package Vue;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class PageAccueil extends JFrame {

    // Couleurs modernes
    private final Color PRIMARY_COLOR = new Color(30, 30, 45); // Noir bleuté
    private final Color SECONDARY_COLOR = new Color(220, 53, 69); // Rouge corail
    private final Color ACCENT_COLOR = new Color(255, 215, 0); // Or
    private final Color LIGHT_BG = new Color(250, 250, 252); // Blanc très légèrement grisé

    public PageAccueil() {
        setTitle("Styliz | Mode Tendances 2025");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(LIGHT_BG);

        // ------------------ HEADER MODERNISÉ ------------------
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1200, 90));
        header.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        // Logo stylisé
        JLabel logo = new JLabel("STYLIZ");
        logo.setFont(new Font("Montserrat", Font.BOLD, 36));
        logo.setForeground(PRIMARY_COLOR);

        // Sous-titre du logo
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

        String[] menuItems = { "Accueil", "Connexion", "Inscription", "Catalogue" };
        for (String item : menuItems) {
            JButton btn = new StyledButton(item);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));

            // Effet de survol
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

            // Actions
            btn.addActionListener(e -> {
                switch (item) {
                    case "Connexion":
                        new PageConnexion().setVisible(true);
                        dispose();
                        break;
                    case "Inscription":
                        new PageInscription().setVisible(true);
                        dispose();
                        break;
                    case "Catalogue":
                        new PagePrincipale().setVisible(true);
                        dispose();
                        break;
                }
            });

            nav.add(btn);
        }

        header.add(logoPanel, BorderLayout.WEST);
        header.add(nav, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ------------------ BANNIÈRE PRINCIPALE ANIMÉE ------------------
        JPanel banner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Dégradé de fond
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(20, 20, 35),
                        getWidth(), getHeight(), new Color(50, 50, 75));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Image superposée avec transparence
                ImageIcon img = new ImageIcon("src/assets/banner.jpg");
                Composite oldComp = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2d.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
                g2d.setComposite(oldComp);
            }
        };
        banner.setLayout(new GridBagLayout());
        banner.setPreferredSize(new Dimension(1200, 450));

        JPanel bannerContent = new JPanel();
        bannerContent.setOpaque(false);
        bannerContent.setLayout(new BoxLayout(bannerContent, BoxLayout.Y_AXIS));
        bannerContent.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titre = new JLabel("NOUVELLE COLLECTION 2025");
        titre.setFont(new Font("Montserrat", Font.BOLD, 42));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel sousTitre = new JLabel("Découvrez les dernières tendances de la mode");
        sousTitre.setFont(new Font("SansSerif", Font.PLAIN, 18));
        sousTitre.setForeground(new Color(220, 220, 220));
        sousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnExplorer = new JButton("EXPLORER LA COLLECTION") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Bouton avec bord arrondi et effet de survol
                if (getModel().isRollover()) {
                    g2.setColor(SECONDARY_COLOR.brighter());
                } else {
                    g2.setColor(SECONDARY_COLOR);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
            }
        };
        btnExplorer.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnExplorer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExplorer.setForeground(Color.WHITE);
        btnExplorer.setContentAreaFilled(false);
        btnExplorer.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        btnExplorer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExplorer.setOpaque(false);

        // Animation au survol
        btnExplorer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExplorer.setBorder(BorderFactory.createEmptyBorder(10, 38, 14, 38));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExplorer.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
            }
        });

        btnExplorer.addActionListener(e -> {
            new PagePrincipale().setVisible(true);
            dispose();
        });

        bannerContent.add(titre);
        bannerContent.add(sousTitre);
        bannerContent.add(Box.createRigidArea(new Dimension(0, 30)));
        bannerContent.add(btnExplorer);

        banner.add(bannerContent);
        add(banner, BorderLayout.CENTER);

        // ------------------ SECTION CATÉGORIES ------------------
        JPanel categoriesTitle = new JPanel();
        categoriesTitle.setOpaque(false);
        categoriesTitle.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JLabel titleLabel = new JLabel("NOS CATÉGORIES", JLabel.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        categoriesTitle.add(titleLabel);

        JPanel categories = new JPanel(new GridLayout(1, 3, 30, 0));
        categories.setBorder(BorderFactory.createEmptyBorder(20, 40, 50, 40));
        categories.setBackground(LIGHT_BG);

        categories.add(createCategoryCard("HOMME", "src/assets/homme.jpg", SECONDARY_COLOR));
        categories.add(createCategoryCard("FEMME", "src/assets/femme.jpg", PRIMARY_COLOR));
        categories.add(createCategoryCard("ACCESSOIRES", "src/assets/accessoires.jpg", ACCENT_COLOR));

        JPanel categoriesSection = new JPanel(new BorderLayout());
        categoriesSection.setBackground(LIGHT_BG);
        categoriesSection.add(categoriesTitle, BorderLayout.NORTH);
        categoriesSection.add(categories, BorderLayout.CENTER);

        add(categoriesSection, BorderLayout.SOUTH);
    }

    private JPanel createCategoryCard(String titre, String imgPath, Color overlayColor) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Image de fond
                ImageIcon img = new ImageIcon(imgPath);
                g2d.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);

                // Overlay coloré avec transparence
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                g2d.setColor(overlayColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                // Bordure au survol
                if (getMousePosition() != null) {
                    g2d.setColor(new Color(255, 255, 255, 150));
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawRoundRect(5, 5, getWidth()-10, getHeight()-10, 15, 15);
                }
            }
        };
        card.setPreferredSize(new Dimension(350, 220));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Label avec fond semi-transparent
        JLabel label = new JLabel(titre, JLabel.CENTER);
        label.setFont(new Font("Montserrat", Font.BOLD, 22));
        label.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel();
        labelPanel.setOpaque(false);
        labelPanel.setLayout(new GridBagLayout());
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel textBg = new JPanel();
        textBg.setBackground(new Color(0, 0, 0, 120));
        textBg.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        textBg.add(label);

        labelPanel.add(textBg);
        card.add(labelPanel, BorderLayout.SOUTH);

        // Animation au survol
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new PagePrincipale().setVisible(true);
                dispose();
            }
        });

        // Panel avec ombre portée
        JPanel shadowPanel = new JPanel(new BorderLayout());
        shadowPanel.setBorder(new ShadowBorder(15, 0.15f));
        shadowPanel.add(card, BorderLayout.CENTER);

        return shadowPanel;
    }

    // Classe interne pour un bouton stylisé
    class StyledButton extends JButton {
        public StyledButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(PRIMARY_COLOR);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isRollover()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }

    // Classe interne pour une ombre portée
    class ShadowBorder implements Border {
        private int shadowSize;
        private float shadowOpacity;
        private Color shadowColor;

        public ShadowBorder(int size, float opacity) {
            this.shadowSize = size;
            this.shadowOpacity = opacity;
            this.shadowColor = new Color(0, 0, 0, opacity);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dessiner l'ombre
            g2d.setColor(shadowColor);
            for (int i = 0; i < shadowSize; i++) {
                g2d.drawRoundRect(
                        x + i,
                        y + i,
                        width - 2 * i - 1,
                        height - 2 * i - 1,
                        15, 15
                );
            }
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize, shadowSize, shadowSize, shadowSize);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PageAccueil frame = new PageAccueil();
            frame.setVisible(true);
        });
    }
}