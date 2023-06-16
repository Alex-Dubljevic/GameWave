import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;

public class GameInfoPage extends JFrame {

    private JPanel contentPanel;
    private JLabel titleLabel;
    private JLabel reviewLabel;
    private JLabel genresLabel;
    private JLabel descriptionLabel;
    private JLabel ratingLabel;
    private JLabel imageLabel;
    private JButton backButton;
    private JButton submitButton;
    private JSlider ratingSlider;
    private Color backgroundColor3 = new Color(21,34,56);

    public GameInfoPage(String title, Icon image, String description, String rating, String genres, String username) {
        setTitle("Game Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setUndecorated(true);

        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(65, 90, 180));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title Label
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        contentPanel.add(titleLabel, gbc);

        // Image Label
        imageLabel = new JLabel(image);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        contentPanel.add(imageLabel, gbc);

        // Genres Label
        genresLabel = new JLabel("Genres: " + genres);
        genresLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        genresLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(genresLabel, gbc);

        // Rating Label
        ratingLabel = new JLabel("Rating: " + rating + " / 5");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        ratingLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(ratingLabel, gbc);

        // Description Label
        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        descriptionLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(descriptionLabel, gbc);

        // Description Text Area
        JTextArea descriptionTextArea = new JTextArea(description);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setBackground(new Color(65, 81, 128));
        descriptionTextArea.setForeground(Color.WHITE);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(descriptionScrollPane, gbc);

        // Leave a Review Label
        reviewLabel = new JLabel("Leave a review:");
        reviewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        reviewLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(reviewLabel, gbc);

        // Rating Slider
        ratingSlider = new JSlider(1, 5);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setBackground(new Color(65, 90, 180));
        ratingSlider.setForeground(Color.WHITE);
        ratingSlider.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(ratingSlider, gbc);

        // Submit Button
        submitButton = new JButton("Submit review");
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setBackground(backgroundColor3);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int rating = ratingSlider.getValue();
                JOptionPane.showMessageDialog(contentPanel, "Review submitted", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
  
                try {
                  //sets up conncection w/ the server
                  System.out.println("sending review...");
                URL url = new URL("http://0.0.0.0:80/reviews");
                  HttpURLConnection con = (HttpURLConnection) url.openConnection();
                  con.setRequestMethod("POST");
                  con.setDoOutput(true);
                  con.setRequestProperty("Game", title);
                  con.setRequestProperty("Rating", Integer.toString(rating));
                  int responseCode = con.getResponseCode();
                  con.disconnect();
                  submitButton.setEnabled(false);
      
                } catch (Exception ex) {
                  ex.printStackTrace();
              }
            }
        });
      
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        contentPanel.add(submitButton, gbc);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(backgroundColor3);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        contentPanel.add(backButton, gbc);

        setContentPane(contentPanel);
    }
}
