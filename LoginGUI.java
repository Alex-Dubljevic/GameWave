import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel usernameLabel, passwordLabel, titleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private Font font;
    private Color backgroundColor = new Color(65,81,128);
    private Color backgroundColor2 = new Color(65,90,180);

    public LoginGUI() {
        setTitle("Login");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        font = new Font("Arial", Font.PLAIN, 14);

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(backgroundColor);

        titleLabel = new JLabel("Welcome to Gamewave");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titleLabel, gbc);

        usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        loginButton.addActionListener(this);
        panel.add(loginButton, gbc);
        loginButton.setBackground(backgroundColor2);

        signupButton = new JButton("Signup");
        signupButton.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        signupButton.addActionListener(this);
        panel.add(signupButton, gbc);
        signupButton.setBackground(backgroundColor2);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            URL url = new URL("http://0.0.0.0:80/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String data = "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            // If login successful, open homepage
            if (con.getHeaderField("validity").equals("1")) {
                dispose(); // Close login window
                HomepageGUI homepage = new HomepageGUI();
                homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                homepage.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(panel, "Login Failed, Username or Password Incorrect");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
      
    }
    if (e.getSource() == signupButton) {
        dispose();
        SignupGUI signup = new SignupGUI();
    }  
  }
}