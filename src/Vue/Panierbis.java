package Vue;

import DAO.CommandeDAO;
import DAO.PanierDAO;
import Modele.Panier;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Panierbis extends JFrame {
    private JTable table;
    private JLabel totalLabel;
    private PanierDAO panierDAO;
    private int commandeId;

    public Panierbis() {
        setTitle("Votre Panier");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Récupère la commande en cours de l'utilisateur
        Utilisateur user = Session.getUtilisateur();
        commandeId = new CommandeDAO().getOuCreateCommandeEnCours(user.getId());

        panierDAO = new PanierDAO();
        initUI();
        chargerPanier();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Tableau non éditable
        String[] columns = {"Article", "Prix unitaire", "Quantité", "Sous-total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bas de page : boutons + total
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        JButton supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel >= 0) {
                List<Panier> list = panierDAO.getPanier(commandeId);
                int idPanier = list.get(sel).getId();
                panierDAO.supprimerArticle(idPanier);
                chargerPanier();
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un article.");
            }
        });

        JButton viderBtn = new JButton("Vider le panier");
        viderBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Vider entièrement votre panier ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                panierDAO.viderPanier(); // ou ajouter un filtre commande si besoin
                chargerPanier();
            }
        });

        JButton retourBtn = new JButton("Continuer mes achats");
        retourBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new PagePrincipale().setVisible(true));
        });

        JButton payerBtn = new JButton("Passer au paiement");
        payerBtn.setBackground(new Color(76, 175, 80));
        payerBtn.setForeground(Color.WHITE);
        payerBtn.setFont(payerBtn.getFont().deriveFont(Font.BOLD));
        payerBtn.addActionListener(e -> {
            // Calculer le total
            double total = panierDAO.getPanier(commandeId).stream()
                    .mapToDouble(item -> item.getPrix() * item.getQuantite())
                    .sum();
            if (total <= 0) {
                JOptionPane.showMessageDialog(this, "Panier vide !", "Erreur", JOptionPane.WARNING_MESSAGE);
            } else {
                SwingUtilities.invokeLater(() -> new Paiementbis(total).setVisible(true));
                dispose();
            }
        });

        buttonPanel.add(supprimerBtn);
        buttonPanel.add(viderBtn);
        buttonPanel.add(retourBtn);
        buttonPanel.add(payerBtn);

        totalLabel = new JLabel("Total : 0.00 €", JLabel.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(totalLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /** Charge et affiche uniquement les lignes de la commande de l’utilisateur */
    private void chargerPanier() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<Panier> list = panierDAO.getPanier(commandeId);
        double total = 0;
        for (Panier item : list) {
            double sous = item.getPrix() * item.getQuantite();
            total += sous;
            model.addRow(new Object[]{
                    item.getNom(),
                    String.format("%.2f €", item.getPrix()),
                    item.getQuantite(),
                    String.format("%.2f €", sous)
            });
        }
        totalLabel.setText(String.format("Total : %.2f €", total));
    }
}
