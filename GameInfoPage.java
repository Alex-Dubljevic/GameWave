/**

  GameInfoPage.java
  This class designs the main GUI for the game information page.

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

// imports necessary classes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameInfoPage extends JFrame {
    private JPanel contentPanel;
    private JLabel titleLabel;
    private JLabel imageLabel;
    private JTextArea descriptionTextArea;
    private JLabel ratingLabel;
    private JPanel reviewsPanel;
    private JTextArea reviewsTextArea;
    private JTextField reviewTextField;
    private JButton addReviewButton;
    private JButton backButton;

    private List<String> reviews;

    public GameInfoPage(String title, Icon image, String description, String rating) {
        setTitle("Game Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setUndecorated(true);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(65, 90, 180));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title Label
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Center Panel (Image and Description)
        JPanel centerPanel = new JPanel(new BorderLayout());
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Image Label
        imageLabel = new JLabel(image);
        centerPanel.add(imageLabel, BorderLayout.CENTER);

        // Description Text Area
        descriptionTextArea = new JTextArea(description);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setBackground(new Color(65, 81, 128));
        descriptionTextArea.setForeground(Color.WHITE);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setPreferredSize(new Dimension(300, 300));
        centerPanel.add(descriptionScrollPane, BorderLayout.WEST);

        // Rating Label
        ratingLabel = new JLabel("Rating: " + rating);
        ratingLabel.setForeground(Color.WHITE);
        contentPanel.add(ratingLabel, BorderLayout.SOUTH);

        // Back Button
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPanel.add(backButton, BorderLayout.EAST);

        // Reviews Panel
        reviewsPanel = new JPanel(new BorderLayout());
        reviewsPanel.setBackground(new Color(65, 90, 180));
        reviewsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        contentPanel.add(reviewsPanel, BorderLayout.SOUTH);

        // Reviews Label
        JLabel reviewsLabel = new JLabel("Reviews");
        reviewsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        reviewsLabel.setForeground(Color.WHITE);
        reviewsPanel.add(reviewsLabel, BorderLayout.NORTH);

        // Reviews Text Area
        reviewsTextArea = new JTextArea();
        reviewsTextArea.setEditable(false);
        reviewsTextArea.setLineWrap(true);
        reviewsTextArea.setBackground(new Color(65, 81, 128));
        reviewsTextArea.setForeground(Color.WHITE);
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsTextArea);
        reviewsScrollPane.setPreferredSize(new Dimension(300, 150));
        reviewsPanel.add(reviewsScrollPane, BorderLayout.CENTER);

        // Add Review Panel
        JPanel addReviewPanel = new JPanel(new BorderLayout());
        addReviewPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        reviewsPanel.add(addReviewPanel, BorderLayout.SOUTH);

        reviewTextField = new JTextField();
        reviewTextField.setPreferredSize(new Dimension(200, 30));
        addReviewPanel.add(reviewTextField, BorderLayout.CENTER);

        addReviewButton = new JButton("Add Review");
        addReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String review = reviewTextField.getText();
                if (!review.equals("")) {
                    reviews.add(review);
                    updateReviewsTextArea();
                    reviewTextField.setText("");
                }
            }
        });
        addReviewPanel.add(addReviewButton, BorderLayout.EAST);

        reviews = new ArrayList<>();

        setContentPane(contentPanel);
    }

    private void updateReviewsTextArea() {
        StringBuilder sb = new StringBuilder();
        for (String review : reviews) {
            sb.append("- ").append(review).append("\n");
        }
        reviewsTextArea.setText(sb.toString());
    }
}

