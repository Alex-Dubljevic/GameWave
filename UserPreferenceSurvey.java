/**

  UserPreferenceSurvey.java
  This class designs the main GUI for the user preference survey which allows us to get some info about the user to recommend them games

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserPreferenceSurvey extends JFrame implements ActionListener {
    //initializes fields of UserPreferenceSurvey
    private List<String> preferredGenres;
    private JButton[][] genreButtons;
    private JButton finishButton;
    private Color backgroundColor = new Color(65,81,128);
    private Color backgroundColor2 = new Color(65,90,180);
    private Color backgroundColor3 = new Color(21,34,56);


    //Opens main gui fro the survey
    public UserPreferenceSurvey() {
        setTitle("User Preference Survey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Help us Recommend You Games by Selecting at Least 5 Genres You Like!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(backgroundColor);
        add(titleLabel, BorderLayout.NORTH);

        JPanel genrePanel = new JPanel(new GridLayout(5, 5));

        // List of genres
        String[] genres = {
                "Adventure", "RPG", "Action", "Stealth", "MMO",
                "Shooter", "Multiplayer", "Singleplayer", "Sports", "Simulation",
                "Puzzle", "Fighting", "Open World", "Hack and Slash", "MOBA",
                "Roguelike", "Turn-Based", "Platform", "Indie", "Strategy",
                "Survival", "Horror", "Casual", "Battle Royale", "Racing"
        };

      //Creates a 5x5 grid of buttons (1 per genre) which the user can click on
        genreButtons = new JButton[5][5];
        preferredGenres = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JButton genreButton = new JButton(genres[i * 5 + j]);
                genreButton.setBackground(backgroundColor2);
                genreButton.setFocusPainted(false);
                genreButton.addActionListener(this);
                genrePanel.add(genreButton);
                genreButtons[i][j] = genreButton;
            }
        }

        add(genrePanel, BorderLayout.CENTER);
        //Adds button at the bottom to finish when the user is done. the button is only active when 5 or more genres have been selected by the user.
        finishButton = new JButton("Finish");
        finishButton.setEnabled(false);
        finishButton.addActionListener(this);
        add(finishButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      //If the user clicks on the finish button it checks if they have selected enough games before allowing them to continue
        if (e.getSource() == finishButton) {
            if (preferredGenres.size() >= 5) {
                // Save the preferred genres as a string array
                String[] preferredGenresArray = preferredGenres.toArray(new String[0]);
                System.out.println("Preferred Genres: " + String.join(", ", preferredGenresArray));
                //Opens the login
                LoginGUI login = new LoginGUI();
                dispose(); // Close the survey window
            } else {
                JOptionPane.showMessageDialog(this, "Please select at least 5 genres.");
            }
        } else {
          //Toggles the button on/off for the genre by changing the colour and adding or removing the genre from their liked genres list
            JButton clickedButton = (JButton) e.getSource();
            String genre = clickedButton.getText();
            if (preferredGenres.contains(genre)) {
                preferredGenres.remove(genre);
                clickedButton.setBackground(backgroundColor2);
                clickedButton.setForeground(null);
            } else {
                preferredGenres.add(genre);
                clickedButton.setBackground(backgroundColor3);
                clickedButton.setForeground(Color.WHITE);
            }
            //Enables the finish button when the user has selected 5 genres
            finishButton.setEnabled(preferredGenres.size() >= 5);
        }
    }
}
