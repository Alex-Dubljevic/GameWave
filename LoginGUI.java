/**

  LoginGUI.java
  This class designs the main GUI for the login page.

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

//imports necessary external classes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel usernameLabel, passwordLabel, titleLabel, imageLabel, helpLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private Font font;
    private Color backgroundColor = new Color(65,81,128);
    private Color backgroundColor2 = new Color(65,90,180);

    /*
    Sorts and organizes components in the Login GUI
    */
    public LoginGUI() {

        //sets up size, title, and other characteristics of the sceen
        setTitle("Login"); 
        setSize(800, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        font = new Font("Arial", Font.PLAIN, 14);
      
        //organizes panel
        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(backgroundColor);

      
        //organizes title label
        titleLabel = new JLabel("Welcome to Gamewave");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));


        //organizes grid
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 50, 20, 0);
        panel.add(titleLabel, gbc);

        //organizes logo gui
        Icon logo = new ImageIcon("imgs/logoanimated.gif");
        imageLabel = new JLabel(logo);
        imageLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 50, 10, 0);
        panel.add(imageLabel, gbc);



        //organizes username label
        usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(usernameLabel, gbc);


        //sets username field
        usernameField = new JTextField(20);
        usernameField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        //organizes label for pw
        passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(passwordLabel, gbc);
      
        //organizes display for pw
        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        //organizes gui for login button
        loginButton = new JButton("Login");
        loginButton.setFocusPainted(false);
        loginButton.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 70);
        loginButton.addActionListener(this);
        panel.add(loginButton, gbc);
        loginButton.setBackground(backgroundColor2);
        //gui for signup button
        signupButton = new JButton("Signup");
        signupButton.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 70, 0, 0);
        signupButton.addActionListener(this);
        panel.add(signupButton, gbc);
        signupButton.setBackground(backgroundColor2);

        //help label
        helpLabel = new JLabel("Need Help? Go to linktr.ee/gamewave");
        helpLabel.setFont(font);
        helpLabel.setForeground(Color.WHITE);
        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(helpLabel, gbc);

        //adds panel and makes gui visible
        add(panel);
        setVisible(true);
    }


    /*
    This method performs the action of verification between username and passwords. 
    */
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
        //initializes String username and String password
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            //cross references w/ the server regarding username and its respective pw
            URL url = new URL("http://0.0.0.0:80/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            //initializes String data
            String data = "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            // If login successful, open homepage
            if (con.getHeaderField("validity").equals("1")) {
                dispose(); // Close login window
                HomepageGUI homepage = new HomepageGUI(con.getHeaderField("Session-ID"), username);
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
  } // end of actionPreformed method
} // end of LoginGUI.java class