package Vue;

import DAO.PanierDAO;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Panierbis extends JFrame {

    private JTable table;
    private JLabel totalLabel;
    private PanierDAO panierDAO;

    public Panierbis() {
        setTitle("Votre Panier");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panierDAO = new PanierDAO();
        initUI();
        chargerPanier();

    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Modèle de table pour le panier
        String[] columns = {"Article", "Prix unitaire", "Quantité", "Sous-total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel pour les boutons et le total
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Boutons
        JPanel buttonPanel = new JPanel();

        JButton supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(e -> supprimerArticle());

        JButton viderBtn = new JButton("Vider le panier");
        viderBtn.addActionListener(e -> viderPanier());

        JButton retourBtn = new JButton("Continuer mes achats");
        retourBtn.addActionListener(e -> this.dispose());

        buttonPanel.add(supprimerBtn);
        buttonPanel.add(viderBtn);
        buttonPanel.add(retourBtn);

        JButton payerBtn = new JButton("Passer au paiement");
        payerBtn.setBackground(new Color(76, 175, 80)); // Vert
        payerBtn.setForeground(Color.WHITE);
        payerBtn.setFont(payerBtn.getFont().deriveFont(Font.BOLD));
        payerBtn.addActionListener(e -> ouvrirFenetrePaiement());

        buttonPanel.add(payerBtn);

        // Total
        totalLabel = new JLabel("Total : 0.00 €", JLabel.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(totalLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void chargerPanier() {  // Renommé de changerPanier() à chargerPanier() pour plus de clarté
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Vide la table

        List<Panier> panier = panierDAO.getPanier();
        double total = 0;

        for (Panier item : panier) {
            double sousTotal = item.getPrix() * item.getQuantite();
            total += sousTotal;

            model.addRow(new Object[]{
                    item.getNom(),
                    String.format("%.2f €", item.getPrix()),  // Correction: virgule au lieu de point-virgule
                    item.getQuantite(),
                    String.format("%.2f €", sousTotal)
            });
        }

        totalLabel.setText(String.format("Total : %.2f €", total));
    }

    private void supprimerArticle() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            // Correction: récupérer l'ID différemment car la table affiche des Strings
            // Vous devez soit:
            // 1. Stocker les IDs dans un tableau séparé
            // 2. Ou mieux, récupérer l'ID depuis la liste des paniers
            List<Panier> panier = panierDAO.getPanier();
            if (selectedRow < panier.size()) {
                int id = panier.get(selectedRow).getId();
                panierDAO.supprimerArticle(id);
                chargerPanier();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article à supprimer.");
        }
    }

    private void viderPanier() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment vider votre panier ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            panierDAO.viderPanier();
            chargerPanier();
        }
    }

    private void ouvrirFenetrePaiement() {
        // Calculer le total
        double total = 0;
        List<Panier> panier = panierDAO.getPanier();
        for (Panier item : panier) {
            total += item.getPrix() * item.getQuantite();
        }

        if (total <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Votre panier est vide !",
                    "Panier vide",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Calculer le total juste avant le lambda
        double paymentTotal = panierDAO.getPanier().stream()
                .mapToDouble(p -> p.getPrix() * p.getQuantite())
                .sum();

        SwingUtilities.invokeLater(() -> {
            new Paiementbis(paymentTotal).setVisible(true);
        });


        this.dispose();
    }
}