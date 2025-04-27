package Vue;

import DAO.CommandeDAO;
import DAO.UtilisateurDAO;
import Modele.Commande;
import Modele.Panier;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Classe qui représente la page de profil d'un utilisateur connecté.
 * Elle permet de voir et modifier ses infos, et consulter l'historique de ses commandes.
 *
 * @author groupe 23 TD8
 */

public class PageProfil extends JFrame {
    /**
     * DAO pour gérer les opérations sur les utilisateurs.
     */
    private final UtilisateurDAO userDao     = new UtilisateurDAO();
    /**
     * DAO pour récupérer les commandes d'un utilisateur.
     */
    private final CommandeDAO commandeDAO    = new CommandeDAO();
    /**
     * Utilisateur actuellement connecté.
     */
    private Utilisateur user;

    // Composants Profil
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JTextField fldPrenom, fldNom, fldEmail, fldAdresse;
    private JPasswordField fldAncienMDP, fldNouveauMDP;

    /**
     * Constructeur de la page de profil.
     * Initialise l'interface graphique et affiche les informations de l'utilisateur.
     */
    public PageProfil() {
        user = Session.getUtilisateur();
        setTitle("Mon Profil - " + user.getPrenom() + " " + user.getNom());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JTabbedPane onglets = new JTabbedPane();
        onglets.addTab("Profil",      buildProfilPanel());
        onglets.addTab("Historique",  buildHistoriquePanel());

        add(onglets, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Construit le panneau affichant les infos du profil.
     * @return JPanel construit pour l'onglet profil
     */
    private JPanel buildProfilPanel() {
        JPanel main = new JPanel(new BorderLayout(10,10));
        main.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        formPanel   = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

        main.add(formPanel,   BorderLayout.CENTER);
        main.add(buttonPanel, BorderLayout.SOUTH);

        showLectureMode();

        return main;
    }

    /**
     * Affiche les informations du profil en mode lecture seule.
     */
    private void showLectureMode() {
        formPanel.removeAll();
        buttonPanel.removeAll();

        GridBagConstraints gbc = createGbc();
        int row = 0;

        addLabelValue("Prénom :", user.getPrenom(), gbc, row++);
        addLabelValue("Nom :",     user.getNom(),    gbc, row++);
        addLabelValue("Email :",   user.getEmail(),  gbc, row++);
        addLabelValue("Adresse :", user.getAdresse(),gbc, row++);

        gbc.gridy = row++;
        addLabelValue("Date d'inscription :",
                user.getDateInscription().toString(), gbc, row-1);

        String statut = switch(user.getRang()) {
            case 0 -> "Administrateur";
            case 1 -> "Nouveau client";
            case 2 -> "Ancien client";
            default -> "Inconnu";
        };
        addLabelValue("Statut :", statut, gbc, row++);

        JButton btnModifier    = new JButton("Modifier");
        JButton btnDeconnexion = new JButton("Déconnexion");
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnDeconnexion);

        btnModifier.addActionListener(e -> showEditionMode());
        btnDeconnexion.addActionListener(e -> {
            Session.clear();
            for (Window w : Window.getWindows()) w.dispose();
            new PageAccueil().setVisible(true);
        });

        formPanel.revalidate();
        formPanel.repaint();
    }

    /**
     * Passe en mode édition pour permettre à l'utilisateur de modifier ses infos.
     */
    private void showEditionMode() {
        formPanel.removeAll();
        buttonPanel.removeAll();

        GridBagConstraints gbc = createGbc();
        int row = 0;

        fldPrenom   = addLabelField("Prénom :",   user.getPrenom(),    gbc, row++);
        fldNom      = addLabelField("Nom :",      user.getNom(),       gbc, row++);
        fldEmail    = addLabelField("Email :",    user.getEmail(),     gbc, row++);
        fldAdresse  = addLabelField("Adresse :",  user.getAdresse(),   gbc, row++);

        // Ancien / Nouveau mot de passe
        gbc.gridy = row; gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Ancien mot de passe :"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        fldAncienMDP = new JPasswordField();
        fldAncienMDP.setEchoChar('•');
        fldAncienMDP.setPreferredSize(new Dimension(200,24));
        formPanel.add(fldAncienMDP, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Nouveau mot de passe :"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        fldNouveauMDP = new JPasswordField();
        fldNouveauMDP.setEchoChar('•');
        fldNouveauMDP.setPreferredSize(new Dimension(200,24));
        formPanel.add(fldNouveauMDP, gbc);

        // Boutons Sauvegarder / Annuler
        JButton btnSauvegarder = new JButton("Sauvegarder");
        JButton btnAnnuler     = new JButton("Annuler");
        buttonPanel.add(btnSauvegarder);
        buttonPanel.add(btnAnnuler);

        btnAnnuler.addActionListener(e -> showLectureMode());
        btnSauvegarder.addActionListener(e -> saveChanges());

        formPanel.revalidate();
        formPanel.repaint();
    }

    /**
     * Sauvegarde les changements faits par l'utilisateur sur son profil.
     * Vérifie aussi la modif du mot de passe
     */
    private void saveChanges() {
        String prenom  = fldPrenom.getText().trim();
        String nom     = fldNom.getText().trim();
        String email   = fldEmail.getText().trim();
        String adresse = fldAdresse.getText().trim();
        String ancien  = new String(fldAncienMDP.getPassword()).trim();
        String nouveau = new String(fldNouveauMDP.getPassword()).trim();

        if (prenom.isEmpty()||nom.isEmpty()||email.isEmpty()||adresse.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tous les champs sont obligatoires.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String mdpFinal = user.getMotDePasse();
        if (!ancien.isEmpty()||!nouveau.isEmpty()) {
            if (ancien.isEmpty()||nouveau.isEmpty()) {
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
        if (userDao.mettreAJourProfil(maj)) {
            Session.setUtilisateur(maj);
            user = maj;
            JOptionPane.showMessageDialog(this,
                    "Profil mis à jour !",
                    "OK", JOptionPane.INFORMATION_MESSAGE);
            showLectureMode();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Construit le panneau affichant l'historique des commandes de l'utilisateur.
     * @return JPanel construit pour l'onglet historique
     */
    private JPanel buildHistoriquePanel() {
        JPanel pnl = new JPanel(new BorderLayout(10,10));
        pnl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        String[] colsCmd = {"ID","Date","Total (€)","Statut"};
        DefaultTableModel mCmd = new DefaultTableModel(colsCmd,0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tblCmd = new JTable(mCmd);

        List<Commande> commandes = commandeDAO.getCommandesByUtilisateur(user.getId());

        for (Commande c : commandes) {
            String st = (c.getValider()==1) ? "Payée" : "En cours";
            mCmd.addRow(new Object[]{
                    c.getId(), c.getDateCommande(),
                    String.format("%.2f",c.getTotal()), st
            });
        }

        String[] colsDet = {"Article","Prix U. (€)","Quantité","Sous-total (€)"};
        DefaultTableModel mDet = new DefaultTableModel(colsDet,0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tblDet = new JTable(mDet);

        // Listener pour charger le détail au clic
        tblCmd.getSelectionModel().addListSelectionListener(evt->{
            if (!evt.getValueIsAdjusting()) {
                int r = tblCmd.getSelectedRow();
                if (r>=0) {
                    int idCmd = (int)mCmd.getValueAt(r,0);
                    mDet.setRowCount(0);
                    List<Panier> lignes =
                            commandeDAO.getDetailsCommande(idCmd);
                    for (Panier p : lignes) {
                        double sous = p.getPrix()*p.getQuantite();
                        mDet.addRow(new Object[]{
                                p.getNom(),
                                String.format("%.2f",p.getPrix()),
                                p.getQuantite(),
                                String.format("%.2f",sous)
                        });
                    }
                }
            }
        });

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tblCmd),
                new JScrollPane(tblDet)
        );
        split.setDividerLocation(180);

        pnl.add(split, BorderLayout.CENTER);
        return pnl;
    }

    /**
     * Crée une configuration par défaut pour le placement des composants dans un GridBagLayout.
     * @return GridBagConstraints configuré
     */
    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8,8,8,8);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }

    /**
     * Ajoute un label + une valeur au formulaire du profil.
     * @param label Texte du label
     * @param value Valeur à afficher
     * @param gbc Contraintes
     * @param row Ligne à laquelle ajouter l'élément
     */
    private void addLabelValue(String label, String value,
                               GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(new JLabel(value), gbc);
    }

    /**
     * Ajoute un label + un champ de texte au formulaire du profil.
     * @param label Texte du label
     * @param init Valeur du champ
     * @param gbc Contraintes
     * @param row Ligne à laquelle ajouter l'élément
     * @return Le JTextField créé
     */
    private JTextField addLabelField(String label, String init,
                                     GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField fld = new JTextField(init);
        fld.setPreferredSize(new Dimension(200,24));
        formPanel.add(fld, gbc);
        return fld;
    }
}
