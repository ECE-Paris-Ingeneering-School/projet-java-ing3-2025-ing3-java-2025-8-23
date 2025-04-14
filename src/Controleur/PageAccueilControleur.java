package Controleur;

import Vue.PageAccueil;
import Vue.PagePrincipale;
import Vue.PageInscription;
import DAO.UtilisateurDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageAccueilControleur {

    private PageAccueil vueAccueil;
    private UtilisateurDAO utilisateurDAO;

    public PageAccueilControleur(PageAccueil vueAccueil) {
        this.vueAccueil = vueAccueil;
        this.utilisateurDAO = new UtilisateurDAO();
        initialiserControleur();
    }

    private void initialiserControleur() {
        // Action pour se connecter
        vueAccueil.getBoutonConnexion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authentifierUtilisateur();
            }
        });
        // Action pour s'inscrire
        vueAccueil.getBoutonInscription().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ouvrirPageInscription();
            }
        });
    }

    private void authentifierUtilisateur() {
        String adresseEmail = vueAccueil.getChampEmail().getText();
        String motDePasse = new String(vueAccueil.getChampMotDePasse().getPassword());

        // Vérification via la base de données
        if (utilisateurDAO.verifierIdentifiants(adresseEmail, motDePasse)) {
            JOptionPane.showMessageDialog(vueAccueil, "Connexion réussie !");
            new PagePrincipale().setVisible(true);
            vueAccueil.dispose();
        } else {
            JOptionPane.showMessageDialog(vueAccueil, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ouvrirPageInscription() {
        new PageInscription().setVisible(true);
        vueAccueil.dispose();
    }
}
