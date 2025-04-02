import javax.swing.*;
import javax.swing.*;
import java.awt.*;


public class PageAccueil extends JFrame{

        public PageAccueil() {
            setTitle("Bievenue chez Rinted");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new BorderLayout());

            // Header
            JLabel headerLabel = new JLabel("Bienvenue chez Rinted", JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
            headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainPanel.add(headerLabel, BorderLayout.NORTH);

            // Navigation Panel
            JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            String[] sections = {"Home", "Marque", "Women", "Kids", "Sale", "Contact"};
            for (String section : sections) {
                JButton button = new JButton(section);
                button.setPreferredSize(new Dimension(100, 40));
                navPanel.add(button);
            }
            mainPanel.add(navPanel, BorderLayout.CENTER);

            // Footer
            JLabel footerLabel = new JLabel("© 2025 FashionHub - All Rights Reserved", JLabel.CENTER);
            footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainPanel.add(footerLabel, BorderLayout.SOUTH);

            

            add(mainPanel);
            setVisible(true);
        }

    }

