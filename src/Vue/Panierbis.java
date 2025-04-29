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

/**
 * Fenêtre d'affichage et de gestion du panier d'achat.
 * <p>
 * Cette interface permet à l'utilisateur de :
 * - Visualiser les articles dans son panier
 * - Supprimer des articles ou vider complètement le panier
 * - Passer à l'étape de paiement
 * - Calculer automatiquement les remises (1 article offerts tous les 10)
 * </p>
 *
 * @author groupe 23 TD8
 */

public class Panierbis extends JFrame {
    private JTable table;
    private JLabel totalLabel;
    private PanierDAO panierDAO;
    private int commandeId;

    /**
     * Constructeur de la fenêtre du panier.
     * <p>
     * Initialise la fenêtre et charge automatiquement le panier
     * de l'utilisateur connecté.
     * </p>
     */

    public Panierbis() {
        setTitle("Votre Panier");
        setSize(600, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Récupère la commande non payée de l'utilisateur
        Utilisateur user = Session.getUtilisateur();
        commandeId = new CommandeDAO().getOuCreateCommandeEnCours(user.getId());

        panierDAO = new PanierDAO();
        initUI();
        chargerPanier();
    }

    /**
     * Initialise l'interface utilisateur.
     * <p>
     * Crée les composants principaux :
     * - Un tableau des articles
     * - Des boutons d'actions (supprimer, vider)
     * - Un affichage du total
     * </p>
     */

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

        // Supprimer un article
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

        // Vider la commande en cours
        JButton viderBtn = new JButton("Vider le panier");
        viderBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Vider entièrement votre panier ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                panierDAO.viderPanierCommande(commandeId);  // méthode à ajouter en DAO
                chargerPanier();
            }
        });

        // Continuer les achats
        JButton retourBtn = new JButton("Continuer mes achats");
        retourBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new PagePrincipale().setVisible(true));
        });

        // Passer au paiement
        JButton payerBtn = new JButton("Passer au paiement");
        payerBtn.setBackground(new Color(76, 175, 80));
        payerBtn.setForeground(Color.WHITE);
        payerBtn.setFont(payerBtn.getFont().deriveFont(Font.BOLD));
        payerBtn.addActionListener(e -> {
            double total = panierDAO.getPanier(commandeId).stream()
                    .mapToDouble(item -> item.getPrix() * item.getQuantite())
                    .sum();
            if (total <= 0) {
                JOptionPane.showMessageDialog(this, "Panier vide !", "Erreur", JOptionPane.WARNING_MESSAGE);
            } else {
                dispose();
                SwingUtilities.invokeLater(() -> new Paiementbis().setVisible(true));
            }
        });

        buttonPanel.add(supprimerBtn);
        buttonPanel.add(viderBtn);
        buttonPanel.add(retourBtn);
        buttonPanel.add(payerBtn);

        totalLabel = new JLabel("Total : 0.00 €", JLabel.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(totalLabel,  BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Charge les articles du panier dans le tableau.
     * <p>
     * Calcule automatiquement :
     * - Les remises (1 article offert tous les 10)
     * - Le sous-total par article
     * - Le total général
     * </p>
     */
    private void chargerPanier() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<Panier> liste = panierDAO.getPanier(commandeId);
        double total = 0;

        for (Panier item : liste) {
            double prixUnitaire = item.getPrix();
            int    qteTotale    = item.getQuantite();
            int    offres      = (qteTotale / 10);
            int    facturables = qteTotale - offres;
            double sousTotal   = facturables * prixUnitaire;
            total += sousTotal;

            String txtSous = String.format("%.2f €", sousTotal);
            if (offres > 0) {
                txtSous += String.format("  (offert(s): %d)", offres);
            }

            model.addRow(new Object[]{
                    item.getNom(),
                    String.format("%.2f €", prixUnitaire),
                    qteTotale,
                    txtSous
            });
        }

        totalLabel.setText(String.format("Total : %.2f €", total));
    }
}
