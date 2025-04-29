package Controleur;

import Vue.PageInscription;
import Vue.PageConnexion;
import DAO.UtilisateurDAO;
import Modele.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controleur pour la page d'inscription.
 * <p>
 * Ce controleur gère :
 * <ul>
 *     <li>La validation des données d'inscription</li>
 *     <li>La création d'un nouvel utilisateur dans la BDD</li>
 *     <li>La navigation retour vers la page de connexion</li>
 * </ul>
 * </p>
 *
 * @author groupe 23 TD8
 */
public class PageInscriptionControleur {

    private PageInscription vueInscription;
    private UtilisateurDAO utilisateurDAO;

    /**
     * Constructeur du contrOleur d'inscription.
     *
     * @param vueInscription la fenEtre d'inscription liée à ce contrOleur
     */
    public PageInscriptionControleur(PageInscription vueInscription) {
        this.vueInscription = vueInscription;
        this.utilisateurDAO = new UtilisateurDAO();
        initialiserControleur();
    }

    /**
     * Initialise les actions sur les boutons de la page d'inscription.
     */
    private void initialiserControleur() {
        vueInscription.getBoutonInscription().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscrireUtilisateur();
            }
        });

        vueInscription.getBoutonRetour().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retourPageConnexion();
            }
        });
    }

    /**
     * Essai d'inscrire un nouvel utilisateur
     * <p>
     * Regarde d'abord que tous les champs sont remplis et que les mots de passe sont les mêmes que dans la BDD.
     * Si tout est bon, crée un nouvel utilisateur dans la BDD
     * </p>
     */
    private void inscrireUtilisateur() {
        String prenom = vueInscription.getChampPrenom().getText();
        String nom = vueInscription.getChampNom().getText();
        String email = vueInscription.getChampEmail().getText();
        String adresse = vueInscription.getChampAdresse().getText();
        String motDePasse = new String(vueInscription.getChampMotDePasse().getPassword());
        String confirmerMotDePasse = new String(vueInscription.getChampConfirmerMotDePasse().getPassword());

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty()
                || adresse.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(vueInscription,
                    "Veuillez remplir tous les champs.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!motDePasse.equals(confirmerMotDePasse)) {
            JOptionPane.showMessageDialog(vueInscription,
                    "Les mots de passe ne correspondent pas.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utilisateur utilisateur = new Utilisateur(prenom, nom, email, motDePasse, adresse);

        if (utilisateurDAO.inscrireUtilisateur(utilisateur)) {
            JOptionPane.showMessageDialog(vueInscription,
                    "Inscription réussie !");
            vueInscription.dispose();
            new PageConnexion().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vueInscription,
                    "L'inscription a échoué. Veuillez réessayer.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gere le retour à la page de connexion quand l'utilisateur clique sur le bouton de retour.
     */
    private void retourPageConnexion() {
        vueInscription.dispose();
        new PageConnexion().setVisible(true);
    }
}
