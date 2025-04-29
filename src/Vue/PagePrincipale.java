package Vue;

import DAO.ArticlesDAO;
import DAO.CommandeDAO;
import DAO.PanierDAO;
import Modele.Article;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Cette classe représente la page principale du site.
 * On peut y voir tous les articles disponibles, chercher un produit, filtrer par marque
 * ou par catégorie, et aussi ajouter des articles dans son panier.
 *
 * @author groupe 23 TD8
 */
public class PagePrincipale extends JFrame {

    private JTextField barreRecherche;
    private JComboBox<String> comboMarques;
    private JPanel panelContenu;
    private List<Article> tousLesArticles;

    /**
     * Constructeur : Initialise toute la fenêtre principale.
     */
    public PagePrincipale() {
        Utilisateur user = Session.getUtilisateur();

        setTitle("JAVA SHOPPING");
        setSize(1000, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelEntete = new JPanel(new BorderLayout());
        panelEntete.setBackground(new Color(255, 153, 0));

        JLabel labelLogo = new JLabel("JAVA SHOPPING");
        labelLogo.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelLogo.setForeground(Color.WHITE);
        labelLogo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PageAccueil().setVisible(true);
            }
        });
        panelEntete.add(labelLogo, BorderLayout.WEST);
        JPanel centerSearchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        barreRecherche = new JTextField(20);
        comboMarques = new JComboBox<>();
        barreRecherche.setPreferredSize(new Dimension(150, 30));
        comboMarques.setPreferredSize(new Dimension(100, 30));
        centerSearchPanel.setOpaque(false);
        centerSearchPanel.add(new JLabel("Recherche : "));
        centerSearchPanel.add(barreRecherche);
        centerSearchPanel.add(new JLabel("Marque : "));
        centerSearchPanel.add(comboMarques);
        panelEntete.add(centerSearchPanel, BorderLayout.CENTER);

        // Droite : boutons "Mon panier" et "Profil"
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setOpaque(false);
        JButton btnPanier = new JButton("Mon Panier");
        JButton btnProfil = new JButton("Profil");
        btnPanier.setBackground(Color.WHITE);
        btnProfil.setBackground(Color.WHITE);
        rightButtonPanel.add(btnPanier);
        rightButtonPanel.add(btnProfil);
        panelEntete.add(rightButtonPanel, BorderLayout.EAST);

        btnPanier.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new Panierbis().setVisible(true));
        });
        btnProfil.addActionListener(e -> new PageProfil().setVisible(true));

        add(panelEntete, BorderLayout.NORTH);

        JPanel panelNavigation = new JPanel();
        panelNavigation.setLayout(new BoxLayout(panelNavigation, BoxLayout.Y_AXIS));
        panelNavigation.setPreferredSize(new Dimension(150, 600));
        panelNavigation.setBackground(new Color(240, 240, 240));
        String[] categories = {"Pantalon", "T-Shirt", "Sweat", "Chaussure", "Veste"};
        for (String cat : categories) {
            JButton btnCat = new JButton(cat);
            btnCat.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnCat.setMaximumSize(new Dimension(140, 30));
            btnCat.addActionListener(e -> filtrerParCategorie(cat));
            panelNavigation.add(Box.createRigidArea(new Dimension(0, 10)));
            panelNavigation.add(btnCat);
        }
        add(panelNavigation, BorderLayout.WEST);

        panelContenu = new JPanel(new GridLayout(0, 4, 20, 20));
        panelContenu.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(panelContenu);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelPied = new JPanel();
        panelPied.setBackground(new Color(200, 200, 200));
        panelPied.add(new JLabel("© 2025 Ma Boutique. Tous droits réservés."));
        add(panelPied, BorderLayout.SOUTH);

        // Chargement des articles depuis la BDD et affichage
        tousLesArticles = ArticlesDAO.getAllArticles();
        chargerMarques();
        afficherProduits(tousLesArticles);

        barreRecherche.addActionListener(e -> filtrerProduits());
        comboMarques.addActionListener(e -> filtrerProduits());
    }

    /**
     * Filtrer les produits selon le texte tapé dans la barre de recherche et la marque choisie.
     */
    private void filtrerProduits() {
        String txt = barreRecherche.getText().trim().toLowerCase();
        String marque = comboMarques.getSelectedItem().toString();
        List<Article> filtres = tousLesArticles.stream()
                .filter(a -> a.getNom().toLowerCase().contains(txt))
                .filter(a -> marque.equals("Toutes") || a.getMarque().equalsIgnoreCase(marque))
                .collect(Collectors.toList());
        afficherProduits(filtres);
    }

    /**
     * Filtrer les produits quand on clique sur une catégorie dans le menu de gauche.
     * @param cat La catégorie choisie
     */
    private void filtrerParCategorie(String cat) {
        List<Article> filtres = tousLesArticles.stream()
                .filter(a -> a.getNom().toLowerCase().contains(cat.toLowerCase()))
                .collect(Collectors.toList());
        afficherProduits(filtres);
    }

    /**
     * Charge toutes les marques disponibles à partir des articles.
     */
    private void chargerMarques() {
        Set<String> marques = tousLesArticles.stream()
                .map(Article::getMarque)
                .filter(m -> m != null && !m.isEmpty())
                .collect(Collectors.toSet());
        comboMarques.addItem("Toutes");
        marques.stream().sorted().forEach(comboMarques::addItem);
    }

    /**
     * Affiche les produits dans la grille du panel principal.
     * @param produits La liste d'articles à afficher
     */
    private void afficherProduits(List<Article> produits) {
        panelContenu.removeAll();

        Utilisateur user = Session.getUtilisateur();
        CommandeDAO commandeDAO = new CommandeDAO();
        int commandeId = commandeDAO.getOuCreateCommandeEnCours(user.getId());
        PanierDAO panierDAO = new PanierDAO();

        for (Article article : produits) {
            JPanel panelArticle = new JPanel(new BorderLayout());
            panelArticle.setPreferredSize(new Dimension(200, 220));
            panelArticle.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            panelArticle.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel();
            byte[] data = article.getImage();
            if (data != null && data.length > 0) {
                ImageIcon icon = new ImageIcon(data);
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setText("Pas d'image");
            }
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
                    JPanel popup = new JPanel(new GridLayout(0, 1, 5, 5));
                    popup.add(new JLabel("Article : " + article.getNom()));
                    popup.add(new JLabel("Prix unitaire : " + article.getPrix() + " €"));
                    popup.add(new JLabel("Quantité :"));
                    popup.add(spinner);

                    int choix = JOptionPane.showConfirmDialog(
                            null, popup, "Ajouter au panier", JOptionPane.OK_CANCEL_OPTION);

                    if (choix == JOptionPane.OK_OPTION) {
                        int qte = (int) spinner.getValue();
                        int disponible = panierDAO.ajouterProduit(commandeId, article.getId(), qte);
                        if (disponible == 0) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    article.getNom() + " x" + qte + " ajouté(s) à votre panier.",
                                    "Panier mis à jour",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    }
                }
            });

            panelArticle.add(imageLabel, BorderLayout.CENTER);

            JLabel lblNom = new JLabel(article.getNom(), SwingConstants.CENTER);
            JLabel lblPrix = new JLabel(article.getPrix() + " €", SwingConstants.CENTER);
            lblPrix.setFont(new Font("SansSerif", Font.BOLD, 13));

            JPanel bas = new JPanel(new GridLayout(2, 1));
            bas.add(lblNom);
            bas.add(lblPrix);
            panelArticle.add(bas, BorderLayout.SOUTH);

            panelContenu.add(panelArticle);
        }

        panelContenu.revalidate();
        panelContenu.repaint();
    }
}
