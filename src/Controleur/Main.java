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


/*import DAO.PanierDAO;
import Vue.Panierbis;

public class Main {
    public static void main(String[] args) {
        PanierDAO dao = new PanierDAO();

        dao.ajouterProduit(1, 2); // ajoute produit avec id=1, quantité=2
        dao.ajouterProduit(2, 1); // ajoute produit avec id=2, quantité=1

        Panierbis vuePanier = new Panierbis();
        vuePanier.afficherPanier();
    }
}

 */
