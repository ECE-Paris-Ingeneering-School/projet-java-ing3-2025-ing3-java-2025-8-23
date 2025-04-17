package Controleur;

import Vue.PageConnexion;
import Vue.PageInscription;
import Vue.PagePrincipale;
import DAO.UtilisateurDAO;
import Modele.Utilisateur;
import Utilitaires.Session;

import javax.swing.JOptionPane;
import java.time.LocalDate;

/**
 * Contrôleur de la page de connexion.
 * Gère l’authentification, la promotion automatique du rang,
 * et affiche un message de nouveaux avantages si besoin.
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

        // 1) Récupération de l'utilisateur complet depuis la base
        Utilisateur user = utilisateurDAO.seConnecter(email, mdp);

        if (user != null) {
            boolean vientDEtrePromu = false;

            // 2) Promotion automatique si "nouveau client" (rang 1) et +1 mois depuis l'inscription
            if (user.getRang() == 1
                    && user.getDateInscription().plusMonths(1).isBefore(LocalDate.now())) {

                vientDEtrePromu = utilisateurDAO.mettreAJourRang(user.getId(), 2);
                if (vientDEtrePromu) {
                    // Rafraîchir l'objet utilisateur pour récupérer le nouveau rang
                    user = utilisateurDAO.seConnecter(email, mdp);
                }
            }

            // 3) Stockage en session
            Session.setUtilisateur(user);

            // 4) Message de connexion
            JOptionPane.showMessageDialog(vueConnexion, "Connexion réussie !");

            // 5) Si promotion, afficher le message spécial
            if (vientDEtrePromu) {
                JOptionPane.showMessageDialog(
                        vueConnexion,
                        "🎉 Félicitations! \nVous êtes désormais un ancien client\nVous bénéficiez désormais d'avantages et de\npromotions supplémentaires !",
                        "Nouveaux Avantages",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            // 6) Ouverture de la page principale
            new PagePrincipale().setVisible(true);
            vueConnexion.dispose();

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
