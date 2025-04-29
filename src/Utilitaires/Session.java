package Utilitaires;

import Modele.Utilisateur;

/**
 * Gestionnaire de session utilisateur pour l'application.
 * Cette classe permet de stocker et gérer l'utilisateur actuellement connecté
 *
 * @author groupe 23 TD8
 */
public class Session {
    /**
     * Référence statique à l'utilisateur connecté
     * @see Modele.Utilisateur
     */
    private static Utilisateur utilisateurConnecte;

    /**
     * Définit l'utilisateur actuellement connecté.
     * @param user l'objet Utilisateur représentant l'utilisateur connecté
     */
    public static void setUtilisateur(Utilisateur user) {
        utilisateurConnecte = user;
    }

    /**
     * Récupère l'utilisateur connecté.
     * @return L'utilisateur connecté ou null si personne n'est connecté
     */
    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    /**
     * Déconnecte l'utilisateur courant.
     * Réinitialise la session en mettant à null la référence.
     */
    public static void clear() {
        utilisateurConnecte = null;
    }
}