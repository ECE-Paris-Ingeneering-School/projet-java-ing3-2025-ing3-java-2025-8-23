package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

/**
 * Cette classe gère la page de contact de notre appli.
 * Elle permet d'afficher les infos de contact et un formulaire pour envoyer un message.
 */
public class PageContact extends JFrame {

    /** Couleur principale utilisée dans l'interface (bleu foncé). */
    private final Color PRIMARY_COLOR = new Color(30, 30, 45);

    /** Couleur secondaire utilisée dans l'interface (rouge). */
    private final Color SECONDARY_COLOR = new Color(220, 53, 69);

    /**
     * Constructeur de la page Contact.
     * Initialise tous les éléments graphiques de la fenêtre.
     */
    public PageContact() {
        setTitle("Java Shopping - Contact");
        setSize(1000, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Centre la fenêtre
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Ferme uniquement cette fenêtre

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel title = new JLabel("CONTACTEZ-NOUS", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 32));
        title.setForeground(PRIMARY_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        header.add(title, BorderLayout.CENTER);

        JButton backButton = new JButton("Retour");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backButton.setForeground(PRIMARY_COLOR);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.addActionListener(e -> {
            new PageAccueil().setVisible(true);
            dispose();
        });
        header.add(backButton, BorderLayout.WEST);

        mainContent.add(header);

        JPanel contactInfo = new JPanel();
        contactInfo.setLayout(new BoxLayout(contactInfo, BoxLayout.Y_AXIS));
        contactInfo.setBackground(Color.WHITE);
        contactInfo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        JLabel infoTitle = new JLabel("Informations de contact");
        infoTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoTitle.setForeground(PRIMARY_COLOR);
        infoTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel address = new JLabel("<html>Adresse :<br>10 Rue Sextius Michel<br>75015 Paris, France</html>");
        address.setFont(new Font("SansSerif", Font.PLAIN, 14));
        address.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel phone = new JLabel("Téléphone : +33 1 23 45 67 89");
        phone.setFont(new Font("SansSerif", Font.PLAIN, 14));
        phone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel email = new JLabel("<html>Email : <a href=''>contact@javashopping.com</a></html>");
        email.setFont(new Font("SansSerif", Font.PLAIN, 14));
        email.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        email.setAlignmentX(Component.LEFT_ALIGNMENT);
        email.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:contact@javashopping.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel hours = new JLabel("<html>Horaires d'ouverture :<br>Lundi-Vendredi : 9h-19h<br>Samedi : 10h-18h</html>");
        hours.setFont(new Font("SansSerif", Font.PLAIN, 14));
        hours.setAlignmentX(Component.LEFT_ALIGNMENT);

        contactInfo.add(infoTitle);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 15)));
        contactInfo.add(address);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        contactInfo.add(phone);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        contactInfo.add(email);
        contactInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        contactInfo.add(hours);

        mainContent.add(contactInfo);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Formulaire de contact",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 16),
                PRIMARY_COLOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Champs du formulaire
        gbc.gridy = 0;
        formPanel.add(new JLabel("Prénom :"), gbc);
        gbc.gridy++;
        JTextField prenomField = new JTextField(25);
        formPanel.add(prenomField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridy++;
        JTextField nomField = new JTextField(25);
        formPanel.add(nomField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Email :"), gbc);
        gbc.gridy++;
        JTextField emailField = new JTextField(25);
        formPanel.add(emailField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Sujet :"), gbc);
        gbc.gridy++;
        JComboBox<String> subjectCombo = new JComboBox<>(new String[]{
                "Question sur un produit", "Problème de commande", "Retour produit", "Autre"
        });
        formPanel.add(subjectCombo, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Message :"), gbc);
        gbc.gridy++;
        JTextArea messageArea = new JTextArea(5, 25);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(messageArea);
        formPanel.add(scroll, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton sendButton = new JButton("Envoyer");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(SECONDARY_COLOR);
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Votre message a été envoyé avec succès !\nNous vous répondrons dans les plus brefs délais.",
                    "Message envoyé",
                    JOptionPane.INFORMATION_MESSAGE);

            prenomField.setText("");
            nomField.setText("");
            emailField.setText("");
            messageArea.setText("");
        });
        formPanel.add(sendButton, gbc);

        mainContent.add(formPanel);

        setContentPane(scrollPane);
    }

    /**
     * Méthode principale pour tester uniquement la page Contact.
     * Lance une fenêtre indépendante avec cette page.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageContact().setVisible(true));
    }
}
