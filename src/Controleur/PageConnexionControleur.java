package Controleur;

import Vue.*;
import DAO.UtilisateurDAO;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.JOptionPane;
import java.time.LocalDate;

/**
 * Controleur de la page de connexion
 * <p>
 * Il gere la connexion de l'utilisateur, la promotion automatique
 * de rang (ancien client nouveau client) et la redirection vers la bonne page selon le statut de l'utilisateur
 * </p>
 *
 * Fonctionnalites principales :
 * <ul>
 *     <li>Connecte l'utilisateur via son mail et mot de passe</li>
 *     <li>Gere la promotion automatique de rang (après 1 mois)</li>
 *     <li>Affiche des messages d'information ou d'erreur</li>
 *     <li>Redirige l'utilisateur selon son rang (admin ou utilisateur normal)</li>
 * </ul>
 *
 * @author groupe 23 TD8
 */
public class PageConnexionControleur {

    private PageConnexion vueConnexion;
    private UtilisateurDAO utilisateurDAO;

    /**
     * Constructeur du contrôleur.
     *
     * @param vueConnexion la fenetre de connexion liee à ce controleur
     */
    public PageConnexionControleur(PageConnexion vueConnexion) {
        this.vueConnexion = vueConnexion;
        this.utilisateurDAO = new UtilisateurDAO();
        initialiserControleur();
    }

    /**
     * Initialise les boutons et leurs fonction (se connecter / s'inscrire).
     */
    private void initialiserControleur() {
        // Bouton "Se connecter"
        vueConnexion.getBoutonConnexion().addActionListener(e -> authentifierUtilisateur());
        // Bouton "S'inscrire"
        vueConnexion.getBoutonInscription().addActionListener(e -> {
            new PageInscription().setVisible(true);
            vueConnexion.dispose();
        });
    }

    /**
     * Tente la connexion de l'utilisateur avec les informations saisies.
     * <p>
     * Si la connexion marche :
     * <ul>
     *     <li>Gere une promotion automatique si l'utilisateur est eligible</li>
     *     <li>Stocke l'utilisateur en session</li>
     *     <li>Redirige vers la bonne page par rapport à son rang</li>
     * </ul>
     * Sinon, affiche une erreur.
     */
    private void authentifierUtilisateur() {
        String email = vueConnexion.getChampEmail().getText();
        String mdp = new String(vueConnexion.getChampMotDePasse().getPassword());

        Utilisateur user = utilisateurDAO.seConnecter(email, mdp);

        if (user != null) {
            boolean vientDEtrePromu = false;

            if (user.getRang() == 1
                    && user.getDateInscription().plusMonths(1).isBefore(LocalDate.now())) {

                vientDEtrePromu = utilisateurDAO.mettreAJourRang(user.getId(), 2);
                if (vientDEtrePromu) {
                    user = utilisateurDAO.seConnecter(email, mdp);
                }
            }

            Session.setUtilisateur(user);

            JOptionPane.showMessageDialog(vueConnexion, "Connexion réussie !");

            if (vientDEtrePromu) {
                JOptionPane.showMessageDialog(
                        vueConnexion,
                        "🎉 Félicitations !\nVous bénéficiez désormais d’avantages\net promotions supplémentaires !",
                        "Nouveaux Avantages",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }


            if (user.getRang() == 0) {
                for (java.awt.Window window : java.awt.Window.getWindows()) {
                    window.dispose();
                }
                new PageAdmin().setVisible(true);
            } else {
                for (java.awt.Window window : java.awt.Window.getWindows()) {
                    window.dispose();
                }
                new PageAccueil().setVisible(true);
            }

        } else {
            JOptionPane.showMessageDialog(
                    vueConnexion,
                    "Identifiants incorrects.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
