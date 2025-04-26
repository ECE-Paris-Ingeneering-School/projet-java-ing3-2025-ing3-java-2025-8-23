package Controleur;

import Vue.*;
import DAO.UtilisateurDAO;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.JOptionPane;
import java.time.LocalDate;

/**
 * Contrôleur de la page de connexion.
 *  • Authentifie l’utilisateur
 *  • Gère la promotion automatique 1→2
 *  • Affiche un message d’avantages si promotion
 *  • Dirige vers PageAdmin si rang=0, sinon PagePrincipale
 */
public class PageConnexionControleur {

    private PageConnexion vueConnexion;
    private UtilisateurDAO utilisateurDAO;

    public PageConnexionControleur(PageConnexion vueConnexion) {
        this.vueConnexion = vueConnexion;
        this.utilisateurDAO = new UtilisateurDAO();
        initialiserControleur();
    }

    private void initialiserControleur() {
        // Bouton "Se connecter"
        vueConnexion.getBoutonConnexion().addActionListener(e -> authentifierUtilisateur());
        // Bouton "S'inscrire"
        vueConnexion.getBoutonInscription().addActionListener(e -> {
            new PageInscription().setVisible(true);
            vueConnexion.dispose();
        });
    }

    private void authentifierUtilisateur() {
        String email = vueConnexion.getChampEmail().getText();
        String mdp   = new String(vueConnexion.getChampMotDePasse().getPassword());

        // 1) Récupérer l’utilisateur complet
        Utilisateur user = utilisateurDAO.seConnecter(email, mdp);

        if (user != null) {
            boolean vientDEtrePromu = false;

            // 2) Promotion auto si rang=1 et +1 mois
            if (user.getRang() == 1
                    && user.getDateInscription().plusMonths(1).isBefore(LocalDate.now())) {

                vientDEtrePromu =
                        utilisateurDAO.mettreAJourRang(user.getId(), 2);
                if (vientDEtrePromu) {
                    // Rafraîchir l’objet user
                    user = utilisateurDAO.seConnecter(email, mdp);
                }
            }

            // 3) Stocker en session
            Session.setUtilisateur(user);

            // 4) Message de connexion
            JOptionPane.showMessageDialog(vueConnexion, "Connexion réussie !");

            // 5) Si promotion, notifier des nouveaux avantages
            if (vientDEtrePromu) {
                JOptionPane.showMessageDialog(
                        vueConnexion,
                        "🎉 Félicitations !\nVous bénéficiez désormais d’avantages\net promotions supplémentaires !",
                        "Nouveaux Avantages",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            // 6) Redirection selon rang
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
