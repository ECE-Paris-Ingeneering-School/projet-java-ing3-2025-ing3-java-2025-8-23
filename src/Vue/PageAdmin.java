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
import java.util.List;

public class PageAdmin extends JFrame {
    private JTabbedPane onglets;

    // --- INVENTAIRE ---
    private JTable tabArticles;
    private DefaultTableModel modelArticles;
    private ArticlesDAO articlesDAO = new ArticlesDAO();

    // --- CLIENTS / COMMANDES ---
    private JTable tabCommandes, tabDetails;
    private DefaultTableModel modelCommandes, modelDetails;
    private CommandeDAO commandeDAO = new CommandeDAO();
    private PanierDAO panierDAO     = new PanierDAO();

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
        onglets.addTab("Clients",    null, buildClientsPanel());

        add(onglets, BorderLayout.CENTER);

        // Déconnexion
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

    // ===== INVENTAIRE =====
    private void initInventaireTab() {
        String[] cols = {"ID","Nom","Marque","Description","Prix","Stock"};
        modelArticles = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabArticles = new JTable(modelArticles);
        reloadArticles();
    }

    private JPanel buildInventairePanel() {
        JPanel pnl = new JPanel(new BorderLayout(10,10));
        pnl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        pnl.add(new JScrollPane(tabArticles), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton btnAdd = new JButton("Ajouter");
        JButton btnEdit= new JButton("Modifier");
        JButton btnDel = new JButton("Supprimer");
        btns.add(btnAdd);
        btns.add(btnEdit);
        btns.add(btnDel);
        pnl.add(btns, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> openArticleDialog(null));
        btnEdit.addActionListener(e -> {
            int sel = tabArticles.getSelectedRow();
            if (sel < 0) return;
            Article art = articlesDAO
                    .getAllArticles().get(tabArticles.convertRowIndexToModel(sel));
            openArticleDialog(art);
        });
        btnDel.addActionListener(e -> {
            int sel = tabArticles.getSelectedRow();
            if (sel < 0) return;
            int id = (int) modelArticles.getValueAt(sel, 0);
            if (JOptionPane.showConfirmDialog(this,
                    "Supprimer cet article ?","Confirmez",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                articlesDAO.deleteArticle(id);
                reloadArticles();
            }
        });

        return pnl;
    }

    private void reloadArticles() {
        modelArticles.setRowCount(0);
        for (Article a : articlesDAO.getAllArticles()) {
            modelArticles.addRow(new Object[]{
                    a.getId(), a.getNom(), a.getMarque(),
                    a.getDescription(), String.format("%.2f", a.getPrix()),
                    a.getStock()
            });
        }
    }

    private void openArticleDialog(Article art) {
        boolean isNew = (art == null);
        JDialog dlg = new JDialog(this,
                isNew ? "Ajouter un article" : "Modifier l'article", true);
        dlg.setSize(400,350);
        dlg.setLocationRelativeTo(this);

        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JTextField fNom   = new JTextField(isNew ? "" : art.getNom());
        JTextField fMar   = new JTextField(isNew ? "" : art.getMarque());
        JTextField fDesc  = new JTextField(isNew ? "" : art.getDescription());
        JTextField fPrix  = new JTextField(isNew ? "" : String.valueOf(art.getPrix()));
        JTextField fStock = new JTextField(isNew ? "" : String.valueOf(art.getStock()));
        p.add(new JLabel("Nom :"));        p.add(fNom);
        p.add(new JLabel("Marque :"));     p.add(fMar);
        p.add(new JLabel("Description:")); p.add(fDesc);
        p.add(new JLabel("Prix :"));       p.add(fPrix);
        p.add(new JLabel("Stock :"));      p.add(fStock);

        JButton ok     = new JButton("OK");
        JButton cancel = new JButton("Annuler");
        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);

        ok.addActionListener(e -> {
            try {
                Article x = new Article(
                        isNew ? 0 : art.getId(),
                        fNom.getText().trim(),
                        fMar.getText().trim(),
                        fDesc.getText().trim(),
                        Double.parseDouble(fPrix.getText().trim()),
                        Integer.parseInt(fStock.getText().trim()),
                        null
                );
                if (isNew) articlesDAO.createArticle(x);
                else        articlesDAO.updateArticle(x);
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

    // ===== CLIENTS / COMMANDES =====
    private void initClientsTab() {
        modelCommandes = new DefaultTableModel(
                new String[]{"ID","User ID","Date","Total (€)","Statut"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabCommandes = new JTable(modelCommandes);

        modelDetails = new DefaultTableModel(
                new String[]{"Article","Prix","Quantité","Sous-total"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
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
