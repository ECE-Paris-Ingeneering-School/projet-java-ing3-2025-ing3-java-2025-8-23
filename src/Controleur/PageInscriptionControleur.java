package Controleur;

import Vue.PageInscription;
import DAO.UtilisateurDAO;
import Modele.Utilisateur;
import Vue.PageAccueil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageInscriptionControleur {
    private PageInscription vueInscription;
    private UtilisateurDAO utilisateurDAO;

    public PageInscriptionControleur(PageInscription vueInscription) {
        this.vueInscription = vueInscription;
        this.utilisateurDAO = new UtilisateurDAO();
        initialiserControleur();
    }

    private void initialiserControleur() {
        vueInscription.getBoutonInscription().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscrireUtilisateur();
            }
        });
    }

    private void inscrireUtilisateur() {
        // Récupération des valeurs saisies
        String email = vueInscription.getChampEmail().getText();
        String motDePasse = new String(vueInscription.getChampMotDePasse().getPassword());
        String confirmerMotDePasse = new String(vueInscription.getChampConfirmerMotDePasse().getPassword());
        String nom = vueInscription.getChampNom().getText();
        String prenom = vueInscription.getChampPrenom().getText();
        String telephone = vueInscription.getChampTelephone().getText();
        String adresse = vueInscription.getChampAdresse().getText();

        // Vérification que les mots de passe correspondent
        if (!motDePasse.equals(confirmerMotDePasse)) {
            JOptionPane.showMessageDialog(vueInscription, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Vous pouvez ajouter ici d'autres vérifications (ex. champs non vides)

        // Création d'un objet Utilisateur avec les infos saisies
        Utilisateur utilisateur = new Utilisateur(email, motDePasse, nom, prenom, telephone, adresse);

        // Appel de la méthode d'inscription dans le DAO
        if (utilisateurDAO.inscrireUtilisateur(utilisateur)) {
            JOptionPane.showMessageDialog(vueInscription, "Inscription réussie !");
            vueInscription.dispose();
            // Après inscription, soit ouvrir la page de connexion, soit passer directement à la page principale
            new PageAccueil().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vueInscription, "L'inscription a échoué.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
