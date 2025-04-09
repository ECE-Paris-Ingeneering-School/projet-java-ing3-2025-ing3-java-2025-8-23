import javax.swing.*;
import java.awt.*;

public class Catalogue extends JFrame {

    public Catalogue() {
        setTitle("Catalogue des Voitures - AutoLux");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER (Logo + Navigation)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 30, 30));
        header.setPreferredSize(new Dimension(getWidth(), 80));

        JLabel logo = new JLabel("🚗 AutoLux", SwingConstants.LEFT);
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setForeground(Color.WHITE);
        logo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        navBar.setOpaque(false);

        JButton btnAccueil = new JButton("Accueil");
        JButton btnCatalogue = new JButton("Catalogue");
        JButton btnContact = new JButton("Contact");

        styliserBouton(navBar, btnAccueil);
        styliserBouton(navBar, btnCatalogue);
        styliserBouton(navBar, btnContact);

        header.add(logo, BorderLayout.WEST);
        header.add(navBar, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // BARRE DE RECHERCHE
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(Color.LIGHT_GRAY);
        JTextField searchField = new JTextField(30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton searchButton = new JButton("🔍 Rechercher");
        styliserBouton(searchPanel, searchButton);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.CENTER);

        // PANEL DES VOITURES
        JPanel voituresPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        voituresPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        voituresPanel.setBackground(Color.WHITE);

        // Ajouter des voitures factices avec un design travaillé
        voituresPanel.add(createVoiturePanel("BMW", "M3", "50,000 €", "bmw.jpg"));
        voituresPanel.add(createVoiturePanel("Audi", "RS5", "55,000 €", "audi.jpg"));
        voituresPanel.add(createVoiturePanel("Mercedes", "AMG C63", "60,000 €", "mercedes.jpg"));
        voituresPanel.add(createVoiturePanel("Porsche", "911 Turbo", "150,000 €", "porsche.jpg"));
        voituresPanel.add(createVoiturePanel("Ferrari", "F8 Tributo", "250,000 €", "ferrari.jpg"));
        voituresPanel.add(createVoiturePanel("Lamborghini", "Huracan", "270,000 €", "lamborghini.jpg"));

        JScrollPane scrollPane = new JScrollPane(voituresPanel);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel createVoiturePanel(String marque, String modele, String prix, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 220));

        // Ajouter un effet d'ombre au survol
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createCompoundBorder(
                panel.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Charger l'image
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(270, 150, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        panel.add(imgLabel, BorderLayout.NORTH);

        // Infos voiture
        JLabel lblMarqueModele = new JLabel(marque + " " + modele, SwingConstants.CENTER);
        lblMarqueModele.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblPrix = new JLabel(prix, SwingConstants.CENTER);
        lblPrix.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrix.setForeground(Color.RED);

        // Bouton Acheter avec effet au survol
        JButton btnAcheter = new JButton("Acheter");
        btnAcheter.setBackground(new Color(34, 177, 76));
        btnAcheter.setForeground(Color.WHITE);
        btnAcheter.setFont(new Font("Arial", Font.BOLD, 12));
        btnAcheter.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Vous avez sélectionné : " + marque + " " + modele + " pour " + prix + ".",
                "Achat confirmé", JOptionPane.INFORMATION_MESSAGE));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoPanel.add(lblMarqueModele);
        infoPanel.add(lblPrix);
        infoPanel.add(btnAcheter);
        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void styliserBouton(JPanel panel, JButton bouton) {
        bouton.setBackground(new Color(45, 45, 45));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(bouton);
    }
}
