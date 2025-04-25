package Vue; // Le package dans lequel se trouve cette classe

import javax.swing.*; // Import des composants Swing
import java.awt.*; // Import des classes pour la gestion graphique
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // Pour gérer les événements (boutons, clics, etc.)

// Classe représentant la fenêtre "Mentions légales", hérite de JFrame
public class PageMentionsLegales extends JFrame {

    // Déclaration de deux couleurs personnalisées utilisées dans l'interface
    private final Color PRIMARY_COLOR = new Color(30, 30, 45); // Couleur principale (bleu foncé)
    private final Color SECONDARY_COLOR = new Color(220, 53, 69); // Couleur secondaire (rouge)

    // Constructeur de la page
    public PageMentionsLegales() {
        // Définition des propriétés de la fenêtre
        setTitle("Java Shopping - Mentions Légales");
        setSize(1000, 700); // Dimensions de la fenêtre
        setLocationRelativeTo(null); // Centre la fenêtre à l'écran
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Ferme seulement cette fenêtre, pas tout le programme

        // Création du panel principal avec une disposition en BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Fond blanc
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Marges internes

        // --- HEADER (haut de page) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE); // Fond blanc
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230))); // Ligne grise en bas

        // Titre centré
        JLabel title = new JLabel("MENTIONS LÉGALES", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 32)); // Police stylisée
        title.setForeground(PRIMARY_COLOR); // Couleur bleue foncée
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Marges autour du titre
        header.add(title, BorderLayout.CENTER); // Ajout au centre du header

        // --- BOUTON RETOUR ---
        JButton backButton = new JButton("Retour"); // Création du bouton
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backButton.setForeground(PRIMARY_COLOR); // Couleur du texte
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Padding
        backButton.setContentAreaFilled(false); // Fond transparent
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur main au survol

        // Action effectuée au clic sur le bouton "Retour"
        backButton.addActionListener(e -> {
            new PageAccueil().setVisible(true); // Ouvre la page d’accueil
            dispose(); // Ferme cette page
        });
        header.add(backButton, BorderLayout.WEST); // Ajoute le bouton à gauche du header

        // Ajout du header en haut du panel principal
        mainPanel.add(header, BorderLayout.NORTH);

        // --- CONTENU PRINCIPAL DES MENTIONS LÉGALES ---
        JTextArea content = new JTextArea(); // Zone de texte
        content.setEditable(false); // Non modifiable
        content.setLineWrap(true); // Retour automatique à la ligne
        content.setWrapStyleWord(true); // Coupe les lignes aux mots
        content.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Police simple

        // Texte des mentions légales
        content.setText(
                "MENTIONS LÉGALES\n\n" +
                        "En vigueur au 25/04/2025\n\n" +
                        "Conformément aux dispositions des Articles 6-III et 19 de la Loi n°2004-575 du 21 juin 2004 pour la " +
                        "Confiance dans l'économie numérique, dite L.C.E.N., il est porté à la connaissance des utilisateurs " +
                        "et visiteurs du site Java Shopping les présentes mentions légales.\n\n" +
                        "Le site Java Shopping est accessible à l'adresse suivante : www.javashopping.com (ci-après \"le Site\"). " +
                        "L'accès et l'utilisation du Site sont soumis aux présentes \"Mentions légales\" détaillées ci-après " +
                        "ainsi qu'aux lois et/ou règlements applicables.\n\n" +
                        "ÉDITEUR\n\n" +
                        "Le Site est édité par la société Java Shopping, société par actions simplifiée au capital de 50 000 euros, " +
                        "immatriculée au Registre du Commerce et des Sociétés de Paris sous le numéro 123 456 789, " +
                        "dont le siège social est situé : 10 Rue Sextius Michel, 75015 Paris.\n\n" +
                        "Numéro de TVA intracommunautaire : FR00123456789\n" +
                        "Adresse e-mail : contact@javashopping.com\n" +
                        "Directeur de la publication : Jean Dupont\n\n" +
                        "HÉBERGEMENT\n\n" +
                        "Le Site est hébergé par la société OVH, dont le siège social est situé : 2 rue Kellermann, 59100 Roubaix, France.\n\n" +
                        "PROPRIÉTÉ INTELLECTUELLE\n\n" +
                        "Tous les éléments accessibles sur le Site (textes, graphismes, images, logos, icônes, sons, logiciels, etc.) " +
                        "restent la propriété exclusive de Java Shopping et/ou de ses partenaires. Toute reproduction, représentation, " +
                        "modification, publication, adaptation de tout ou partie des éléments du Site, quel que soit le moyen ou le procédé " +
                        "utilisé, est interdite, sauf autorisation écrite préalable de Java Shopping.\n\n" +
                        "DONNÉES PERSONNELLES\n\n" +
                        "Conformément à la réglementation en vigueur, en particulier la loi n°78-17 du 6 janvier 1978 relative à " +
                        "l'informatique, aux fichiers et aux libertés modifiée et au Règlement Général sur la Protection des Données (RGPD), " +
                        "Java Shopping s'engage à protéger la confidentialité des informations personnelles fournies par les utilisateurs " +
                        "lors de leur navigation sur le Site.\n\n" +
                        "COOKIES\n\n" +
                        "Le Site utilise des cookies pour améliorer l'expérience utilisateur. En naviguant sur le Site, vous acceptez " +
                        "l'utilisation des cookies conformément à notre Politique de Cookies."
        );

        // Ajout du contenu dans un scrollPane pour permettre le défilement si nécessaire
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null); // Supprime les bordures par défaut
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Place le contenu au centre

        // Définit le panel principal comme contenu de la fenêtre
        setContentPane(mainPanel);
    }

    // Méthode main : permet de lancer cette page indépendamment pour test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageMentionsLegales().setVisible(true)); // Lance la fenêtre
    }
}
