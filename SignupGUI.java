import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignupGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel usernameLabel, passwordLabel, titleLabel, confirmPasswordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signupButton;
    private Font font;
    private Color backgroundColor = new Color(65,81,128);
    private Color backgroundColor2 = new Color(65,90,180);

    public SignupGUI() {
        setTitle("Signup");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        font = new Font("Arial", Font.PLAIN, 14);

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(backgroundColor);

        titleLabel = new JLabel("Gamewave Signup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titleLabel, gbc);

        usernameLabel = new JLabel("     Username: ");
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

        passwordLabel = new JLabel("     Password: ");
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

        confirmPasswordLabel = new JLabel("Confirm Password: ");
        confirmPasswordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(confirmPasswordField, gbc);

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

    public void actionPerformed(ActionEvent e){
      
      if (e.getSource() == signupButton) {
        
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            URL url = new URL("http://0.0.0.0:80/signup");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String data = "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();
       
            // If signup successful, open homepage
            if (con.getHeaderField("validity").equals("1")) {
                dispose(); // Close login window
                HomepageGUI homepage = new HomepageGUI();
                homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                homepage.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(panel, "Username or Password Invalid");
              System.out.println(con.getResponseMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
  }
}
