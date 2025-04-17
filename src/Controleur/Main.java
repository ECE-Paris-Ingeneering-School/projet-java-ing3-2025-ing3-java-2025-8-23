package Controleur;

import Vue.PageConnexion;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PageConnexion().setVisible(true);
        });
    }
}