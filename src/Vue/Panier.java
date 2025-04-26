package Vue;

import javax.swing.*;
import java.awt.*;

public class Panier extends JFrame {
    public Panier() {
        setTitle("Mon Panier");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Texte de récapitulatif (temporaire)
        JTextArea recapText = new JTextArea("Récapitulatif de votre commande :\n\n- Article 1\n- Article 2\n\nTotal : 100€");
        recapText.setEditable(false);
        recapText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        recapText.setMargin(new Insets(20, 20, 20, 20));
        add(recapText, BorderLayout.CENTER);

        // Bouton de retour
        JButton btnRetour = new JButton("Retourner à mes achats");
        btnRetour.addActionListener(e -> {
            dispose();  // ferme la page panier
            SwingUtilities.invokeLater(() -> new PagePrincipale().setVisible(true));
        });

        // Bouton de paiement
        JButton btnPayer = new JButton("Payer");
        btnPayer.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new Paiement().setVisible(true));
        });

        JPanel panelBas = new JPanel();
        panelBas.add(btnRetour);
        panelBas.add(btnPayer);
        add(panelBas, BorderLayout.SOUTH);



    }
}
