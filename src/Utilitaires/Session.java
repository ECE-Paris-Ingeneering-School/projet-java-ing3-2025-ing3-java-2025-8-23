package Utilitaires;

import Modele.Utilisateur;

public class Session {
    /** Référence statique à l'utilisateur connecté */
    private static Utilisateur utilisateurConnecte;

    /** Place un utilisateur dans la session */
    public static void setUtilisateur(Utilisateur user) {
        utilisateurConnecte = user;
    }

    /** Retourne l'utilisateur connecté, ou null si pas de session */
    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    /** Détruit la session (logout) */
    public static void clear() {
        utilisateurConnecte = null;
    }
}
