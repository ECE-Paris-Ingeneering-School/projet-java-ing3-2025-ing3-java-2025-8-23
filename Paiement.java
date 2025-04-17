package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paiement extends JFrame {
    private double prixCommande = 50.0; // Exemple de prix de commande
    private JLabel labelTotal;

    public Paiement() {
        setTitle("Paiement");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Titre ---
        JLabel titre = new JLabel("Informations de Paiement", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(titre, BorderLayout.NORTH);

        // --- Centre : Formulaire ---
        JPanel panelCentre = new JPanel();
        panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
        panelCentre.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // Moyen de paiement
        panelCentre.add(new JLabel("Mode de paiement :"));
        String[] moyens = {"Carte Bancaire", "Visa", "Paypal"};
        JComboBox<String> comboPaiement = new JComboBox<>(moyens);
        panelCentre.add(comboPaiement);

        // Infos carte
        panelCentre.add(new JLabel("Numéro de carte :"));
        JTextField champCarte = new JTextField();
        panelCentre.add(champCarte);

        panelCentre.add(new JLabel("Nom sur la carte :"));
        JTextField champNom = new JTextField();
        panelCentre.add(champNom);

        panelCentre.add(new JLabel("Date d'expiration (MM/AA) :"));
        JTextField champDate = new JTextField();
        panelCentre.add(champDate);

        panelCentre.add(new JLabel("Code de sécurité :"));
        JTextField champCode = new JTextField();
        panelCentre.add(champCode);

        panelCentre.add(Box.createVerticalStrut(15));

        // Livraison
        panelCentre.add(new JLabel("Mode de livraison :"));
        JRadioButton domicile = new JRadioButton("À domicile (+5€)");
        JRadioButton pointRelais = new JRadioButton("Point relais (+2€)");
        JRadioButton express = new JRadioButton("Express (+10€)");
        domicile.setSelected(true);

        ButtonGroup groupLivraison = new ButtonGroup();
        groupLivraison.add(domicile);
        groupLivraison.add(pointRelais);
        groupLivraison.add(express);

        panelCentre.add(domicile);
        panelCentre.add(pointRelais);
        panelCentre.add(express);

        panelCentre.add(Box.createVerticalStrut(15));

        // Prix total
        labelTotal = new JLabel("Prix total : " + calculerTotal(5.0) + "€");
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelCentre.add(labelTotal);

        // Écouteurs pour mettre à jour le prix total
        domicile.addActionListener(e -> labelTotal.setText("Prix total : " + calculerTotal(5.0) + "€"));
        pointRelais.addActionListener(e -> labelTotal.setText("Prix total : " + calculerTotal(2.0) + "€"));
        express.addActionListener(e -> labelTotal.setText("Prix total : " + calculerTotal(10.0) + "€"));

        add(panelCentre, BorderLayout.CENTER);

        // --- Bas : Boutons ---
        JPanel panelBas = new JPanel();

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new Panier().setVisible(true));
        });

        JButton btnFinaliser = new JButton("Finaliser mes achats");
        btnFinaliser.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnFinaliser.addActionListener(e -> {
            String dateExpiration = champDate.getText().trim();

            // Vérifie le format MM/AA
            if (!dateExpiration.matches("\\d{2}/\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez MM/AA.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] parts = dateExpiration.split("/");
            int mois = Integer.parseInt(parts[0]);
            int annee = Integer.parseInt(parts[1]);

            if (mois < 1 || mois > 12) {
                JOptionPane.showMessageDialog(this, "Le mois doit être entre 01 et 12.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Comparaison avec 03/25
            if (annee < 25 || (annee == 25 && mois <= 3)) {
                JOptionPane.showMessageDialog(this, "La date d'expiration de la carte est invalide (carte expirée).", "Carte expirée", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si tout est ok
            JOptionPane.showMessageDialog(this, "Merci pour votre commande ! 🎉");
            dispose();
            SwingUtilities.invokeLater(() -> new PagePrincipale().setVisible(true));
        });


        panelBas.add(btnRetour);
        panelBas.add(btnFinaliser);
        add(panelBas, BorderLayout.SOUTH);
    }

    private String calculerTotal(double livraison) {
        return String.format("%.2f", prixCommande + livraison);
    }
}
