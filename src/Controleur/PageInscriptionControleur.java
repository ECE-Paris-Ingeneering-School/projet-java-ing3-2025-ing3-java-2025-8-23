package Controleur;

import Vue.PageInscription;
import Vue.PageAccueil;  // Retour à la page de connexion
import DAO.UtilisateurDAO;
import Modele.Utilisateur;

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
        String prenom = vueInscription.getChampPrenom().getText();
        String nom = vueInscription.getChampNom().getText();
        String email = vueInscription.getChampEmail().getText();
        String adresse = vueInscription.getChampAdresse().getText();
        String motDePasse = new String(vueInscription.getChampMotDePasse().getPassword());
        String confirmerMotDePasse = new String(vueInscription.getChampConfirmerMotDePasse().getPassword());

        // Vérification que les champs ne sont pas vides
        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty()
                || adresse.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(vueInscription,
                    "Veuillez remplir tous les champs.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérification que les mots de passe correspondent
        if (!motDePasse.equals(confirmerMotDePasse)) {
            JOptionPane.showMessageDialog(vueInscription,
                    "Les mots de passe ne correspondent pas.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Création de l'objet Utilisateur
        Utilisateur utilisateur = new Utilisateur(prenom, nom, email, motDePasse, adresse);

        // Insertion via le DAO
        if (utilisateurDAO.inscrireUtilisateur(utilisateur)) {
            JOptionPane.showMessageDialog(vueInscription,
                    "Inscription réussie !");
            vueInscription.dispose();
            // Par exemple, retour à la page de connexion
            new PageAccueil().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vueInscription,
                    "L'inscription a échoué. Veuillez réessayer.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
