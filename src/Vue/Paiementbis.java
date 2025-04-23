package Vue;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class Paiementbis extends JFrame {

    public Paiementbis(double total) {
        setTitle("Paiement de la commande");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI(total);
    }

    private void initUI(double total) {
        setLayout(new BorderLayout(10, 10));

        // Panel principal avec padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Total de la commande
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalPanel.add(new JLabel("Total à payer : "));

        JLabel totalLabel = new JLabel(NumberFormat.getCurrencyInstance().format(total));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalPanel.add(totalLabel);

        // Options de paiement
        JPanel paymentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Méthode de paiement"));

        ButtonGroup group = new ButtonGroup();
        JRadioButton cbCard = new JRadioButton("Carte bancaire", true);
        JRadioButton cbPaypal = new JRadioButton("PayPal");
        JRadioButton cbTransfer = new JRadioButton("Virement bancaire");

        group.add(cbCard);
        group.add(cbPaypal);
        group.add(cbTransfer);

        paymentPanel.add(cbCard);
        paymentPanel.add(cbPaypal);
        paymentPanel.add(cbTransfer);

        // Détails carte (visible seulement si carte sélectionnée)
        JPanel cardDetails = new JPanel(new GridLayout(4, 2, 5, 5));
        cardDetails.add(new JLabel("Numéro de carte :"));
        cardDetails.add(new JTextField(16));
        cardDetails.add(new JLabel("Date expiration :"));
        cardDetails.add(new JTextField(5));
        cardDetails.add(new JLabel("Cryptogramme :"));
        cardDetails.add(new JTextField(3));
        cardDetails.setVisible(false);

        cbCard.addActionListener(e -> cardDetails.setVisible(cbCard.isSelected()));

        // Bouton de confirmation
        JButton confirmBtn = new JButton("Confirmer le paiement");
        confirmBtn.setBackground(new Color(56, 142, 60));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Paiement effectué avec succès !");
            this.dispose();
            // Ici vous pourriez vider le panier
        });

        // Assemblage des composants
        mainPanel.add(totalPanel, BorderLayout.NORTH);
        mainPanel.add(paymentPanel, BorderLayout.CENTER);
        mainPanel.add(cardDetails, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(confirmBtn, BorderLayout.SOUTH);
    }
}