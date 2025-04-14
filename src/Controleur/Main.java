package Controleur;

import Vue.PageAccueil;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PageAccueil().setVisible(true);
        });
    }
}
