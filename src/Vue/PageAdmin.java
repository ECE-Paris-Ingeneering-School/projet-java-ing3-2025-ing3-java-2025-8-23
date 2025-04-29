//https://docs.oracle.com/javase/tutorial/uiswing/
package Vue;

import DAO.ArticlesDAO;
import DAO.CommandeDAO;
import DAO.PanierDAO;
import Modele.Article;
import Modele.Commande;
import Modele.Panier;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Interface d'administration pour la gestion de notre site.
 * Permet de gérer l'inventaire des produits et de visualiser les commandes clients.
 *
 * @author groupe 23 TD8
 */
public class PageAdmin extends JFrame {
    private JTabbedPane onglets;

    private JTable tabArticles;
    private DefaultTableModel modelArticles;
    private ArticlesDAO articlesDAO = new ArticlesDAO();

    private JTable tabCommandes, tabDetails;
    private DefaultTableModel modelCommandes, modelDetails;
    private CommandeDAO commandeDAO = new CommandeDAO();
    private PanierDAO panierDAO = new PanierDAO();

    /**
     * Constructeur de la page d'administration.
     * Initialise l'interface avec les onglets de gestion.
     */
    public PageAdmin() {
        Utilisateur admin = Session.getUtilisateur();
        setTitle("Admin Dashboard - " + admin.getPrenom() + " " + admin.getNom());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        onglets = new JTabbedPane();
        initInventaireTab();
        initClientsTab();
        onglets.addTab("Inventaire", null, buildInventairePanel());
        onglets.addTab("Clients", null, buildClientsPanel());

        add(onglets, BorderLayout.CENTER);

        JButton btnDeco = new JButton("Déconnexion");
        btnDeco.addActionListener(e -> {
            Session.clear();
            for (Window w : Window.getWindows()) w.dispose();
            new PageAccueil().setVisible(true);
        });
        JPanel sud = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sud.add(btnDeco);
        add(sud, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Initialise le tableau des articles.
     */
    private void initInventaireTab() {
        String[] cols = {"ID","Nom","Marque","Description","Prix","Stock"};
        modelArticles = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabArticles = new JTable(modelArticles);
        reloadArticles();
    }

    /**
     * Construit le panel de gestion des articles.
     *
     * @return JPanel configuré avec les composants nécessaires
     */
    private JPanel buildInventairePanel() {
        JPanel pnl = new JPanel(new BorderLayout(10,10));
        pnl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        pnl.add(new JScrollPane(tabArticles), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton btnAdd = new JButton("Ajouter");
        JButton btnEdit = new JButton("Modifier");
        JButton btnDel = new JButton("Supprimer");
        btns.add(btnAdd);
        btns.add(btnEdit);
        btns.add(btnDel);
        pnl.add(btns, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> openArticleDialog(null));
        btnEdit.addActionListener(e -> {
            int sel = tabArticles.getSelectedRow();
            if (sel < 0) return;
            Article art = articlesDAO.getAllArticles()
                    .get(tabArticles.convertRowIndexToModel(sel));
            openArticleDialog(art);
        });
        btnDel.addActionListener(e -> {
            int sel = tabArticles.getSelectedRow();
            if (sel < 0) return;
            int id = (int) modelArticles.getValueAt(sel, 0);
            if (JOptionPane.showConfirmDialog(this,
                    "Supprimer cet article ?", "Confirmez",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                articlesDAO.supprimerArticle(id);
                reloadArticles();
            }
        });

        return pnl;
    }

    /**
     * Recharge la liste des articles depuis la BDD.
     */
    private void reloadArticles() {
        modelArticles.setRowCount(0);
        for (Article a : articlesDAO.getAllArticles()) {
            modelArticles.addRow(new Object[]{
                    a.getId(),
                    a.getNom(),
                    a.getMarque(),
                    a.getDescription(),
                    String.format("%.2f", a.getPrix()),
                    a.getStock()
            });
        }
    }

    /**
     * Ouvre une boîte de dialogue pour ajouter ou modifier un article.
     *
     * @param art L'article à modifier (ou {@code null} pour une création)
     */
    private void openArticleDialog(Article art) {
        boolean isNew = (art == null);
        JDialog dlg = new JDialog(this,
                isNew ? "Ajouter un article" : "Modifier l'article", true);
        dlg.setSize(400, 400);
        dlg.setLocationRelativeTo(this);

        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField fNom = new JTextField(isNew ? "" : art.getNom());
        JTextField fMar = new JTextField(isNew ? "" : art.getMarque());
        JTextField fDesc = new JTextField(isNew ? "" : art.getDescription());
        JTextField fPrix = new JTextField(isNew ? "" : String.valueOf(art.getPrix()));
        JTextField fStock = new JTextField(isNew ? "" : String.valueOf(art.getStock()));

        p.add(new JLabel("Nom :"));        p.add(fNom);
        p.add(new JLabel("Marque :"));     p.add(fMar);
        p.add(new JLabel("Description:")); p.add(fDesc);
        p.add(new JLabel("Prix :"));       p.add(fPrix);
        p.add(new JLabel("Stock :"));      p.add(fStock);

        // Gestion de l'image
        JLabel lblImage = new JLabel(isNew ? "Aucune image sélectionnée" : "Image existante");
        JButton btnChoisirImage = new JButton("Choisir une image");
        final byte[][] imageBytes = new byte[1][];

        btnChoisirImage.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(dlg);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    java.io.File file = fileChooser.getSelectedFile();
                    lblImage.setText(file.getName());
                    Path path = file.toPath();
                    imageBytes[0] = Files.readAllBytes(path);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dlg, "Erreur lors du chargement de l'image.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        p.add(new JLabel("Image :"));
        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.add(btnChoisirImage, BorderLayout.WEST);
        imgPanel.add(lblImage, BorderLayout.CENTER);
        p.add(imgPanel);

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Annuler");
        JPanel bp = new JPanel();
        bp.add(ok);
        bp.add(cancel);

        ok.addActionListener(e -> {
            try {
                byte[] image = imageBytes[0];
                if (!isNew && image == null) {
                    image = art.getImage(); // conserver l'image actuelle si aucune nouvelle sélectionnée
                }
                Article x = new Article(
                        isNew ? 0 : art.getId(),
                        fNom.getText().trim(),
                        fMar.getText().trim(),
                        fDesc.getText().trim(),
                        Double.parseDouble(fPrix.getText().trim()),
                        Integer.parseInt(fStock.getText().trim()),
                        image
                );
                if (isNew) articlesDAO.createArticle(x);
                else articlesDAO.updateArticle(x);
                dlg.dispose();
                reloadArticles();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg,
                        "Veuillez vérifier vos saisies.","Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        cancel.addActionListener(e -> dlg.dispose());

        dlg.setLayout(new BorderLayout());
        dlg.add(p, BorderLayout.CENTER);
        dlg.add(bp, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    /**
     * Initialise les tableaux des commandes et détails.
     */
    private void initClientsTab() {
        modelCommandes = new DefaultTableModel(
                new String[]{"ID","User ID","Date","Total (€)","Statut"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabCommandes = new JTable(modelCommandes);

        modelDetails = new DefaultTableModel(
                new String[]{"Article","Prix","Quantité","Sous-total"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabDetails = new JTable(modelDetails);

        tabCommandes.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                int r = tabCommandes.getSelectedRow();
                if (r >= 0) {
                    int idCmd = (int) modelCommandes.getValueAt(r, 0);
                    reloadDetails(idCmd);
                }
            }
        });
    }

    /**
     * Construit le panel de gestion des commandes.
     *
     * @return JPanel configuré avec les composants nécessaires
     */
    private JPanel buildClientsPanel() {
        JPanel pnl = new JPanel(new BorderLayout(10,10));
        pnl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        initClientsTab();
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tabCommandes),
                new JScrollPane(tabDetails)
        );
        split.setDividerLocation(500);
        pnl.add(split, BorderLayout.CENTER);

        reloadCommandes();
        return pnl;
    }

    /**
     * Recharge la liste des commandes depuis la BDD.
     */
    private void reloadCommandes() {
        modelCommandes.setRowCount(0);
        List<Commande> l = commandeDAO.getAllCommandes();
        for (Commande c : l) {
            String statut = (c.getValider() == 1) ? "Payée" : "En cours";
            modelCommandes.addRow(new Object[]{
                    c.getId(),
                    c.getUtilisateurId(),
                    c.getDateCommande(),
                    String.format("%.2f", c.getTotal()),
                    statut
            });
        }
    }

    /**
     * Recharge les détails d'une commande spécifique.
     * @param commandeId L'identifiant de la commande à afficher
     */
    private void reloadDetails(int commandeId) {
        modelDetails.setRowCount(0);
        List<Panier> l = commandeDAO.getDetailsCommande(commandeId);
        for (Panier p : l) {
            double sous = p.getPrix() * p.getQuantite();
            modelDetails.addRow(new Object[]{
                    p.getNom(),
                    String.format("%.2f", p.getPrix()),
                    p.getQuantite(),
                    String.format("%.2f", sous)
            });
        }
    }
}
