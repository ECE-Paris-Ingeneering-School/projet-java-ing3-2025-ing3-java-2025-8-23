package Vue;

import Utilitaires.Session;
import DAO.UtilisateurDAO;
import Modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Profil Utilisateur en lecture/édition, avec GridBagLayout pour un rendu soigné.
 */
public class PageProfil extends JFrame {
    private final UtilisateurDAO dao = new UtilisateurDAO();
    private Utilisateur user;

    // Conteneurs
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;

    // Composants lecture
    private JLabel lblPrenom, lblNom, lblEmail, lblAdresse, lblInscription, lblStatut;

    // Composants édition
    private JTextField fldPrenom, fldNom, fldEmail, fldAdresse;
    private JPasswordField fldAncienMDP, fldNouveauMDP;

    public PageProfil() {
        user = Session.getUtilisateur();
        initUI();
        showLectureMode();
    }

    private void initUI() {
        setTitle("Profil Utilisateur");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        formPanel   = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showLectureMode() {
        formPanel.removeAll();
        buttonPanel.removeAll();

        GridBagConstraints gbc = createGbc();
        int row = 0;

        // Prénom
        addLabelValue("Prénom :", user.getPrenom(), gbc, row++);
        // Nom
        addLabelValue("Nom :", user.getNom(), gbc, row++);
        // Email
        addLabelValue("Email :", user.getEmail(), gbc, row++);
        // Adresse
        addLabelValue("Adresse :", user.getAdresse(), gbc, row++);
        // Date d'inscription
        addLabelValue("Inscrit le :", user.getDateInscription().toString(), gbc, row++);
        // Statut
        String statut = switch(user.getRang()) {
            case 0 -> "Administrateur";
            case 1 -> "Nouveau client";
            case 2 -> "Ancien client";
            default -> "Inconnu";
        };
        addLabelValue("Statut :", statut, gbc, row++);

        // Boutons
        JButton btnModifier    = new JButton("Modifier");
        JButton btnDeconnexion = new JButton("Déconnexion");
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnDeconnexion);

        btnModifier.addActionListener((ActionEvent e) -> showEditionMode());
        btnDeconnexion.addActionListener((ActionEvent e) -> {
            Session.clear();
            for (Window w : Window.getWindows()) w.dispose();
            new PageConnexion().setVisible(true);
        });

        revalidate(); repaint();
    }

    private void showEditionMode() {
        formPanel.removeAll();
        buttonPanel.removeAll();

        GridBagConstraints gbc = createGbc();
        int row = 0;

        // Champ Prénom
        fldPrenom = addLabelField("Prénom :", user.getPrenom(), gbc, row++);
        // Champ Nom
        fldNom    = addLabelField("Nom :", user.getNom(), gbc, row++);
        // Champ Email
        fldEmail  = addLabelField("Email :", user.getEmail(), gbc, row++);
        // Champ Adresse
        fldAdresse= addLabelField("Adresse :", user.getAdresse(), gbc, row++);

        // Ancien / Nouveau mot de passe côte à côte
        gbc.gridy = row; gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Ancien mot de passe :"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        fldAncienMDP = new JPasswordField();
        fldAncienMDP.setEchoChar('•');
        fldAncienMDP.setPreferredSize(new Dimension(200, 24));
        formPanel.add(fldAncienMDP, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Nouveau mot de passe :"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        fldNouveauMDP = new JPasswordField();
        fldNouveauMDP.setEchoChar('•');
        fldNouveauMDP.setPreferredSize(new Dimension(200, 24));
        formPanel.add(fldNouveauMDP, gbc);

        // Lecture seule date + statut sous les champs
        row++;
        addLabelValue("Inscrit le :", user.getDateInscription().toString(), gbc, row++);
        addLabelValue("Statut :", switch(user.getRang()) {
            case 0 -> "Administrateur";
            case 1 -> "Nouveau client";
            case 2 -> "Ancien client";
            default -> "Inconnu";
        }, gbc, row++);

        // Boutons Sauvegarder / Annuler
        JButton btnSauvegarder = new JButton("Sauvegarder");
        JButton btnAnnuler     = new JButton("Annuler");
        buttonPanel.add(btnSauvegarder);
        buttonPanel.add(btnAnnuler);

        btnAnnuler.addActionListener((ActionEvent e) -> showLectureMode());
        btnSauvegarder.addActionListener((ActionEvent e) -> saveChanges());

        revalidate(); repaint();
    }

    private void saveChanges() {
        String prenom = fldPrenom.getText().trim();
        String nom    = fldNom.getText().trim();
        String email  = fldEmail.getText().trim();
        String adresse= fldAdresse.getText().trim();
        String ancien = new String(fldAncienMDP.getPassword()).trim();
        String nouveau= new String(fldNouveauMDP.getPassword()).trim();

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || adresse.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Les champs Prénom, Nom, Email et Adresse sont obligatoires.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String mdpFinal = user.getMotDePasse();
        if (!ancien.isEmpty() || !nouveau.isEmpty()) {
            if (ancien.isEmpty() || nouveau.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Renseignez l'ancien ET le nouveau mot de passe.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!ancien.equals(user.getMotDePasse())) {
                JOptionPane.showMessageDialog(this,
                        "Ancien mot de passe incorrect.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mdpFinal = nouveau;
        }

        Utilisateur maj = new Utilisateur(
                user.getId(),
                prenom, nom, email, mdpFinal, adresse,
                user.getDateInscription(),
                user.getRang()
        );

        if (dao.mettreAJourProfil(maj)) {
            Session.setUtilisateur(maj);
            user = maj;
            JOptionPane.showMessageDialog(this,
                    "Profil mis à jour avec succès !",
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            showLectureMode();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper pour créer GridBagConstraints de base
    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx= 1.0;
        return gbc;
    }

    // Ajoute une ligne lecture : JLabel(label) + JLabel(value)
    private void addLabelValue(String label, String value, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel(label), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        JLabel val = new JLabel(value);
        val.setFont(val.getFont().deriveFont(Font.BOLD));
        formPanel.add(val, gbc);
    }

    // Ajoute une ligne édition : JLabel(label) + JTextField(initial) et renvoie le JTextField
    private JTextField addLabelField(String label, String initial, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel(label), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField fld = new JTextField(initial);
        fld.setPreferredSize(new Dimension(200, 24));
        formPanel.add(fld, gbc);
        return fld;
    }
}
