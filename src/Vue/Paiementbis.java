package Vue;

import DAO.CommandeDAO;
import DAO.PanierDAO;
import Modele.Panier;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

/**
 * Fenêtre de paiement pour une commande en cours.
 * <p>
 * Cette classe représente l'interface graphique permettant à l'utilisateur
 * de finaliser le paiement de sa commande. Elle calcule automatiquement
 * le montant total avec les remises applicables.
 * </p>
 *
 * @author groupe 23 TD8
 */
public class Paiementbis extends JFrame {

    private int commandeId;
    private PanierDAO panierDAO = new PanierDAO();
    private CommandeDAO commandeDAO = new CommandeDAO();

    /**
     * Constructeur de la fenêtre de paiement.
     * <p>
     * Initialise la fenêtre avec les infos de la commande en cours
     * pour l'utilisateur connecté.
     * </p>
     */
    public Paiementbis() {
        Utilisateur user = Session.getUtilisateur();
        this.commandeId = commandeDAO.getOuCreateCommandeEnCours(user.getId());

        setTitle("Paiement de la commande n°" + commandeId);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    /**
     * Initialise l'interface utilisateur.
     * <p>
     * Crée les composants graphiques :
     * - Un panel affichant le total à payer
     * - Un bouton de confirmation de paiement
     * </p>
     */
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel main = new JPanel(new BorderLayout(10, 20));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(main, BorderLayout.CENTER);

        double total = calculerTotalAvecRemise();

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalPanel.add(new JLabel("Total à payer : "));
        JLabel lblTotal = new JLabel(NumberFormat.getCurrencyInstance().format(total));
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 18f));
        totalPanel.add(lblTotal);
        main.add(totalPanel, BorderLayout.NORTH);

        // bouton de confirmation
        JButton confirmBtn = new JButton("Confirmer le paiement");
        confirmBtn.setBackground(new Color(56, 142, 60));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.addActionListener(e -> {

            commandeDAO.finaliserCommande(commandeId, total);

            JOptionPane.showMessageDialog(
                    this,
                    "Paiement de " + NumberFormat.getCurrencyInstance().format(total)
                            + " effectué avec succès !",
                    "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            new PagePrincipale().setVisible(true);
        });
        add(confirmBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Calcule le total de la commande avec remise.
     * <p>
     * Applique la promotion pour chaque article du panier.
     * </p>
     *
     * @return Le montant total après remises
     */
    private double calculerTotalAvecRemise() {
        List<Panier> lignes = panierDAO.getPanier(commandeId);
        double total = 0;

        for (Panier p : lignes) {
            double prix = p.getPrix();
            int qte = p.getQuantite();
            int offres = (qte / 10);
            int factures = qte - offres;
            total += factures * prix;
        }
        return total;
    }
}