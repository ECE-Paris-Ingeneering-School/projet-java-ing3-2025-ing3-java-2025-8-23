import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PageAccueil extends JFrame {

    private JTextField searchField;
    private JTextArea resultArea;
    private List<String> cars;

    public PageAccueil() {
        setTitle("Bienvenue chez Rinted");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sample car data
        cars = new ArrayList<>();
        cars.add("Tesla Model S");
        cars.add("Renault Clio");
        cars.add("Peugeot 208");
        cars.add("BMW X5");
        cars.add("Toyota Corolla");
        cars.add("BMW X6");
        cars.add("BMW M4");
        cars.add("BMW M5");
        cars.add("Porsche 911 Turbo S");





        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Bienvenue chez VoitureLand", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.add(headerLabel, BorderLayout.NORTH);

        // Navigation Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        String[] sections = {"Home", "Marque", "Contact"};
        for (String section : sections) {
            JButton button = new JButton(section);
            button.setPreferredSize(new Dimension(100, 40));
            navPanel.add(button);
        }
        headerPanel.add(navPanel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        searchPanel.add(new JLabel("Rechercher voiture: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(new JScrollPane(resultArea));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCar();
            }
        });

        mainPanel.add(searchPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        JLabel footerLabel = new JLabel("© 2025 VoitureLand - All Rights Reserved", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        footerPanel.setBackground(Color.LIGHT_GRAY);
        footerPanel.add(footerLabel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void searchCar() {
        String query = searchField.getText().toLowerCase();
        StringBuilder results = new StringBuilder();
        for (String car : cars) {
            if (car.toLowerCase().contains(query)) {
                results.append(car).append("\n");
            }
        }
        resultArea.setText(results.length() > 0 ? results.toString() : "Aucune voiture trouvée.");
    }

    public static void main(String[] args) {
        new PageAccueil();
    }
}
