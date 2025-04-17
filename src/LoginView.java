import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    JTextField emailField = new JTextField(20);
    JPasswordField mdpField = new JPasswordField(20);
    JButton loginBtn = new JButton("Connexion");
    JButton registerBtn = new JButton("Créer un compte");

    public LoginView() {
        super("Page de Connexion");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(mdpField);

        JPanel btnPanel = new JPanel();
        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
