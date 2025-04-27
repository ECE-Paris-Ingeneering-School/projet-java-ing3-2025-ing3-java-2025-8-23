package Controleur;

import Vue.PageAccueil;
import Vue.PageConnexion;
import javax.swing.SwingUtilities;

/**
 * C'est la classe principale du projet
 * <p>
 * Elle permet de lancer l'application en ouvrant la page d'accueil
 * </p>
 *
 * @author groupe 23 TD8
 */
public class  Main {

    /**
     * Methode principale du programme
     * <p>
     * Elle utilise {@code SwingUtilities.invokeLater} pour etre sur que
     * l'interface graphique est créée,
     * ce qui est permet d'éviter des bugs d'affichage en java swing
     * </p>
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageAccueil().setVisible(true));
    }
}
