package Vue; // Le package dans lequel se trouve cette classe

import javax.swing.*; // Import des composants Swing pour l'interface graphique
import java.awt.*; // Import des classes pour la gestion graphique (layout, couleurs, etc.)

/**
 * Cette classe représente la page "Mentions légales" de notre site.
 * Elle permet d'afficher les informations légales du site dans une interface claire.
 *
 * @author groupe 23 TD8
 */
public class PageMentionsLegales extends JFrame {

    // Déclaration des couleurs utilisées pour harmoniser l'interface
    private final Color PRIMARY_COLOR = new Color(30, 30, 45); // Bleu foncé pour les titres
    private final Color SECONDARY_COLOR = new Color(220, 53, 69); // Rouge pour les éléments secondaires (pas utilisé ici mais prêt)

    /**
     * Constructeur de la page de mentions légales.
     * Configure l'ensemble des éléments graphiques de la fenetre.
     */
    public PageMentionsLegales() {
        setTitle("Java Shopping - Mentions Légales");
        setSize(1000, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel title = new JLabel("MENTIONS LÉGALES", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 32));
        title.setForeground(PRIMARY_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        header.add(title, BorderLayout.CENTER);

        JButton backButton = new JButton("Retour");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backButton.setForeground(PRIMARY_COLOR);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.setContentAreaFilled(false); // Pas de fond coloré pour le bouton
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change le curseur au survol

        backButton.addActionListener(e -> {
            new PageAccueil().setVisible(true);
            dispose();
        });
        header.add(backButton, BorderLayout.WEST);

        mainPanel.add(header, BorderLayout.NORTH);

        JTextArea content = new JTextArea();
        content.setEditable(false);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setFont(new Font("SansSerif", Font.PLAIN, 14));

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

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    /**
     * Méthode main : permet de lancer uniquement cette page pour la tester.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageMentionsLegales().setVisible(true));
    }
}
