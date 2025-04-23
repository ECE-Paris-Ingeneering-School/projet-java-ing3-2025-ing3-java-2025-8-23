package Vue;

import DAO.ArticlesDAO;
import DAO.PanierDAO;
import Modele.Panier;
import Modele.Article;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {

    //private List<Panier> panier = new ArrayList<>();
    private JPanel panelArticles;

    public MainGUI() {
        setTitle("Choix des articles");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelArticles = new JPanel(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelArticles);
        add(scrollPane, BorderLayout.CENTER);

        JButton voirPanierButton = new JButton("Voir le panier");
        voirPanierButton.addActionListener(e -> {
            new Panierbis().setVisible(true);
        });


        add(voirPanierButton, BorderLayout.SOUTH);

        chargerArticles();

        setVisible(true);
    }

    private void chargerArticles() {
        ArticlesDAO dao = new ArticlesDAO();
        List<Article> articles = dao.getAllArticles();

        for (Article article : articles) {
            JPanel articlePanel = new JPanel(new BorderLayout(10, 10));
            articlePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            // Image
            JLabel imageLabel = new JLabel();
            ImageIcon icon = new ImageIcon(article.getImagePath());
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
            articlePanel.add(imageLabel, BorderLayout.WEST);

            // Infos article
            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            infoPanel.add(new JLabel("Nom : " + article.getNom()));
            infoPanel.add(new JLabel("Prix : " + article.getPrix() + " €"));

            // Spinner pour quantité
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            infoPanel.add(spinner);

            articlePanel.add(infoPanel, BorderLayout.CENTER);

            // Bouton Ajouter
            JButton ajouterBtn = new JButton("Ajouter");
            ajouterBtn.addActionListener(e -> {
                int quantite = (int) spinner.getValue();
                if (quantite > 0) {
                    PanierDAO panierDAO = new PanierDAO();
                    panierDAO.ajouterProduit(article.getId(), quantite);
                    JOptionPane.showMessageDialog(this,
                            quantite + " x " + article.getNom() + " ajouté au panier.");
                    spinner.setValue(0); // Réinitialise le spinner
                }
            });

            articlePanel.add(ajouterBtn, BorderLayout.EAST);

            panelArticles.add(articlePanel);
        }

        revalidate();
        repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
