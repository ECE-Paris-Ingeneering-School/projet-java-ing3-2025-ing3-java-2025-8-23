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

public class Paiementbis extends JFrame {

    private int commandeId;
    private PanierDAO panierDAO   = new PanierDAO();
    private CommandeDAO commandeDAO = new CommandeDAO();

    public Paiementbis() {
        // Récupère la commande non payée
        Utilisateur user = Session.getUtilisateur();
        this.commandeId = commandeDAO.getOuCreateCommandeEnCours(user.getId());

        setTitle("Paiement de la commande n°" + commandeId);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel main = new JPanel(new BorderLayout(10, 20));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(main, BorderLayout.CENTER);

        // Calcul du total
        double total = calculerTotalAvecRemise();

        // Affichage sommaire du total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalPanel.add(new JLabel("Total à payer : "));
        JLabel lblTotal = new JLabel(NumberFormat.getCurrencyInstance().format(total));
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 18f));
        totalPanel.add(lblTotal);
        main.add(totalPanel, BorderLayout.NORTH);

        // (Vous pouvez conserver ici le choix carte/PayPal…)

        // Bouton de confirmation
        JButton confirmBtn = new JButton("Confirmer le paiement");
        confirmBtn.setBackground(new Color(56, 142, 60));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.addActionListener(e -> {
            // 1) Finaliser la commande (total + valider = 1)
            commandeDAO.finaliserCommande(commandeId, total);
            // 2) Laisser l’historique (panierDAO.viderPanierCommande si vous préférez)
            // 3) Message et retour à l’accueil
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

    /** Calcule le total avec remise 2 offerts tous les 10. */
    private double calculerTotalAvecRemise() {
        List<Panier> lignes = panierDAO.getPanier(commandeId);
        double total = 0;
        for (Panier p : lignes) {
            double prix     = p.getPrix();
            int    qte      = p.getQuantite();
            int    offres   = (qte / 10) * 2;
            int    factures= qte - offres;
            total += factures * prix;
        }
        return total;
    }
}