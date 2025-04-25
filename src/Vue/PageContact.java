package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class PageContact extends JFrame {

    // Couleurs personnalisées pour la charte graphique
    private final Color PRIMARY_COLOR = new Color(30, 30, 45);         // Bleu foncé
    private final Color SECONDARY_COLOR = new Color(220, 53, 69);      // Rouge

    // Constructeur de la page Contact
    public PageContact() {
        // Configuration de la fenêtre principale
        setTitle("Java Shopping - Contact");
        setSize(1000, 700);
        setLocationRelativeTo(null); // centre la fenêtre sur l'écran
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // ferme uniquement cette fenêtre

        // Création du panel principal qui contiendra tout le contenu
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS)); // empile verticalement
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // marges intérieures

        // Ajout d'une barre de défilement verticale
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // vitesse de défilement

        // ---------- En-tête ----------
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230))); // ligne grise en bas

        // Titre principal
        JLabel title = new JLabel("CONTACTEZ-NOUS", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 32));
        title.setForeground(PRIMARY_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // marges verticales
        header.add(title, BorderLayout.CENTER);

        // Bouton de retour vers la page d'accueil
        JButton backButton = new JButton("Retour");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backButton.setForeground(PRIMARY_COLOR);
        backButton.setContentAreaFilled(false); // pas de fond
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.addActionListener(e -> {
            new PageAccueil().setVisible(true); // ouvrir la page d'accueil
            dispose(); // fermer la page actuelle
        });
        header.add(backButton, BorderLayout.WEST);

        // Ajout de l'en-tête au contenu principal
        mainContent.add(header);

        // ---------- Bloc d'informations de contact ----------
        JPanel contactInfo = new JPanel();
        contactInfo.setLayout(new BoxLayout(contactInfo, BoxLayout.Y_AXIS));
        contactInfo.setBackground(Color.WHITE);
        contactInfo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // marges

        // Titre de la section
        JLabel infoTitle = new JLabel("Informations de contact");
        infoTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoTitle.setForeground(PRIMARY_COLOR);
        infoTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Adresse
        JLabel address = new JLabel("<html>Adresse :<br>10 Rue Sextius Michel<br>75015 Paris, France</html>");
        address.setFont(new Font("SansSerif", Font.PLAIN, 14));
        address.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Téléphone
        JLabel phone = new JLabel("Téléphone : +33 1 23 45 67 89");
        phone.setFont(new Font("SansSerif", Font.PLAIN, 14));
        phone.setAlignmentX(Component.LEFT_ALIGNMENT);

        // E-mail (cliquable pour ouvrir un client mail)
        JLabel email = new JLabel("<html>Email : <a href=''>contact@javashopping.com</a></html>");
        email.setFont(new Font("SansSerif", Font.PLAIN, 14));
        email.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        email.setAlignmentX(Component.LEFT_ALIGNMENT);
        email.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Ouvre le client de messagerie avec l'adresse préremplie
                    Desktop.getDesktop().mail(new URI("mailto:contact@javashopping.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Horaires d'ouverture
        JLabel hours = new JLabel("<html>Horaires d'ouverture :<br>Lundi-Vendredi : 9h-19h<br>Samedi : 10h-18h</html>");
        hours.setFont(new Font("SansSerif", Font.PLAIN, 14));
        hours.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Ajout des composants au panel d'informations
        contactInfo.add(infoTitle);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 15)));
        contactInfo.add(address);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        contactInfo.add(phone);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        contactInfo.add(email);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        contactInfo.add(hours);

        // Ajout au panel principal
        mainContent.add(contactInfo);

        // ---------- Formulaire de contact ----------
        JPanel formPanel = new JPanel(new GridBagLayout()); // pour un placement précis
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Formulaire de contact",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 16),
                PRIMARY_COLOR));

        // Configuration du gestionnaire de placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // marges entre composants

        // Champ Prénom
        gbc.gridy = 0;
        formPanel.add(new JLabel("Prénom :"), gbc);
        gbc.gridy++;
        JTextField prenomField = new JTextField(25);
        formPanel.add(prenomField, gbc);

        // Champ Nom
        gbc.gridy++;
        formPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridy++;
        JTextField nomField = new JTextField(25);
        formPanel.add(nomField, gbc);

        // Champ Email
        gbc.gridy++;
        formPanel.add(new JLabel("Email :"), gbc);
        gbc.gridy++;
        JTextField emailField = new JTextField(25);
        formPanel.add(emailField, gbc);

        // Champ Sujet (liste déroulante)
        gbc.gridy++;
        formPanel.add(new JLabel("Sujet :"), gbc);
        gbc.gridy++;
        JComboBox<String> subjectCombo = new JComboBox<>(new String[]{
                "Question sur un produit", "Problème de commande", "Retour produit", "Autre"
        });
        formPanel.add(subjectCombo, gbc);

        // Champ Message (zone de texte multiligne avec scroll)
        gbc.gridy++;
        formPanel.add(new JLabel("Message :"), gbc);
        gbc.gridy++;
        JTextArea messageArea = new JTextArea(5, 25);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(messageArea);
        formPanel.add(scroll, gbc);

        // Bouton Envoyer
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton sendButton = new JButton("Envoyer");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(SECONDARY_COLOR);
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(e -> {
            // Affiche une boîte de dialogue à l'envoi
            JOptionPane.showMessageDialog(this,
                    "Votre message a été envoyé avec succès !\nNous vous répondrons dans les plus brefs délais.",
                    "Message envoyé",
                    JOptionPane.INFORMATION_MESSAGE);

            // Réinitialise les champs
            prenomField.setText("");
            nomField.setText("");
            emailField.setText("");
            messageArea.setText("");
        });
        formPanel.add(sendButton, gbc);

        // Ajout du formulaire au contenu principal
        mainContent.add(formPanel);

        // Mise en place du panel principal dans la fenêtre avec scroll
        setContentPane(scrollPane);
    }

    // Méthode main pour lancer uniquement cette fenêtre (utile pour tester)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageContact().setVisible(true));
    }
}
