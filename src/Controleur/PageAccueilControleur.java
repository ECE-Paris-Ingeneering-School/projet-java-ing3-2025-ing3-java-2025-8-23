package Controleur;

import Vue.PageAccueil;
import Vue.PagePrincipale;
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
        vueAccueil.getBoutonConnexion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authentifierUtilisateur();
            }
        });
    }

    private void authentifierUtilisateur() {
        String adresseEmail = vueAccueil.getChampEmail().getText();
        String motDePasse = new String(vueAccueil.getChampMotDePasse().getPassword());

        // Utilisation du DAO pour vérifier directement dans la base de données
        if (utilisateurDAO.verifierIdentifiants(adresseEmail, motDePasse)) {
            JOptionPane.showMessageDialog(vueAccueil, "Connexion réussie !");
            // Affichage de la page d'accueil principale
            PagePrincipale pagePrincipale = new PagePrincipale();
            pagePrincipale.setVisible(true);
            vueAccueil.dispose();  // Fermer la fenêtre de connexion
        } else {
            JOptionPane.showMessageDialog(vueAccueil, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
