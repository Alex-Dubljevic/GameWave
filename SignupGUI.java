/**

  SignupGUI.java
  This class designs the main GUI for the signup page

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

//imports necessary classes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignupGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel usernameLabel, passwordLabel, titleLabel, confirmPasswordLabel, imageLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signupButton, backButton;
    private Font font;
    private Color backgroundColor = new Color(65,81,128);
    private Color backgroundColor2 = new Color(65,90,180);
    /*
    Sorts and organizes components in the Signup GUI
    */
    public SignupGUI() {

        //organizes display settings for the GUI proper
        setTitle("Signup");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        font = new Font("Arial", Font.PLAIN, 14);

        //organizes the panel details
        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(backgroundColor);

        //details for the title
        titleLabel = new JLabel("Gamewave Signup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 50, 20, 0);
        panel.add(titleLabel, gbc);

        //organizes info for icon
        Icon logo = new ImageIcon("imgs/logoanimated.gif");
        imageLabel = new JLabel(logo);
        imageLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 50, 10, 0);
        panel.add(imageLabel, gbc);

        //organizes info for the username label
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 10, 10);
        panel.add(usernameLabel, gbc);

        //organizes details for the username field
        usernameField = new JTextField(20);
        usernameField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 2;
      gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(usernameField, gbc);

        //organizes details for the password label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 10, 10);
        panel.add(passwordLabel, gbc);

        //organizes details for the signup GUI
        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(passwordField, gbc);

        //organizes details for pw confirmation GUI
        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 10, 10);
        panel.add(confirmPasswordLabel, gbc);

        //organizes details for the pw field  confirmation
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(confirmPasswordField, gbc);

        //organizes details for the signup button
        signupButton = new JButton("Signup");
        signupButton.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 30, 0, 0);
        signupButton.addActionListener(this);
        panel.add(signupButton, gbc);
        signupButton.setBackground(backgroundColor2);

        //organizes details for the back button
        backButton = new JButton("Back");
        backButton.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 30);
        backButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            dispose();
            LoginGUI login = new LoginGUI();
          }
        });
        panel.add(backButton, gbc);
        backButton.setBackground(backgroundColor2);

        add(panel);
        setVisible(true);
      
    }

    /*
    This method performs the action of confirmation for the inputted passwords/username
    */
    public void actionPerformed(ActionEvent e){

      String username = usernameField.getText();
      String password = new String(passwordField.getPassword());
      String passwordConfirm = new String(confirmPasswordField.getPassword());
      
      if (e.getSource() == signupButton && password.equals(passwordConfirm)) {
        
        
        //registers with the server
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
                
                UserPreferenceSurvey login = new UserPreferenceSurvey();
                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                login.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(panel, "Username or Password Invalid");
              System.out.println(con.getResponseMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } else {
                JOptionPane.showMessageDialog(panel, "Username or Password Invalid");
    }
  }
}
