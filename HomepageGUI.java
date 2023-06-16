/**

  HomepageGUI.java
  This class designs the main GUI for the home page. It contains methods for contacting the server to load components for the homepage and game pages

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Base64;
import java.util.Arrays;

public class HomepageGUI extends JFrame implements ActionListener {
    private JPanel contentPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel page1;
    private JPanel page2;
    private JPanel page3;
    private JPanel page4;
    private JButton page1Button;
    private JButton page2Button;
    private JButton page3Button;
    private JButton page4Button;
    private JButton backButton;
    private Color backgroundColor = new Color(65,81,128);
    private Color backgroundColor2 = new Color(65,90,180);
    private Color backgroundColor3 = new Color(21,34,56);
    private String sessionID;
    private String username;

    //Opens main homepage
  
    public HomepageGUI(String sessionID, String username) {

      //Sets the user session id to the sessionid
      this.sessionID = sessionID;

      //Gets JSON list of all games and their ID's from server
      getsJSON();

      //Gets the page order as a 2d array from the server
      String[][] pageOrder = getPgOrder(sessionID,username);

      //Removes any leftover delimiters from the page order 2d array
      for(int i = 0; i < pageOrder.length; i++){
        pageOrder[i][0] = pageOrder[i][0].replaceAll("\\[", "");
        pageOrder[i][15] = pageOrder[i][15].replaceAll("\\]", "");
      }

        //sets the title and other basic components for the page like size
        setTitle("GameWave Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setUndecorated(true);

        //Sets the layout manager for the GUI
        contentPanel = new JPanel(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //Component that displays the logo
        Icon logo = new ImageIcon("imgs/gamewavelogoS.png");
        JLabel logoPanel = new JLabel(logo);
        headerPanel.add(logoPanel);

        //Component that displays the title
        JLabel titleLabel = new JLabel("Welcome to GameWave, " + username + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        headerPanel.setBackground(backgroundColor2);
        contentPanel.setBackground(backgroundColor);

        
        //Back to login button
        backButton = new JButton("Go Back");
        backButton.setBackground(backgroundColor3);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            dispose();
            LoginGUI login = new LoginGUI();
          }
        });
        headerPanel.add(backButton);

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Opens a Card Layout Panel for all the game pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Page 1 Panel
        page1 = new JPanel(new BorderLayout());
        JPanel tablePanel1 = new JPanel(new GridLayout(4, 4));
        tablePanel1.setBackground(backgroundColor);
        //Loops 16 times to create a 4x4 grid of buttons that each has a game on it
        for (int i = 0; i < 16; i++) {

            int index = i;
            JButton button = new JButton(imageFromID(pageOrder[0][i], sessionID, username));
            button.setBackground(backgroundColor);
          //When a game is clicked on it opens that corresponding page for it
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String[] gameInfo = getGameInfo(pageOrder[0][index], sessionID, username);
                    GameInfoPage game = new GameInfoPage(gameInfo[0], imageFromID(pageOrder[0][index], sessionID, username), gameInfo[3], gameInfo[2], gameInfo[1], username);
                    game.setVisible(true);
                  
                }
            });
            button.setHorizontalAlignment(JLabel.CENTER);
            tablePanel1.add(button);
        }
        page1.add(tablePanel1, BorderLayout.CENTER);
        cardPanel.add(page1, "Page 1");

        // Page 2 Panel same as above
        page2 = new JPanel(new BorderLayout());
        JPanel tablePanel2 = new JPanel(new GridLayout(4, 4));
        tablePanel2.setBackground(backgroundColor);
        for (int i = 0; i < 16; i++) {

            int index = i;
            JButton button = new JButton(imageFromID(pageOrder[1][i], sessionID, username));
            button.setBackground(backgroundColor);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String[] gameInfo = getGameInfo(pageOrder[1][index], sessionID, username);
                    GameInfoPage game = new GameInfoPage(gameInfo[0], imageFromID(pageOrder[1][index], sessionID, username), gameInfo[3], gameInfo[2], gameInfo[1], username);
                    game.setVisible(true);
                }
            });
            tablePanel2.add(button);
        }
        page2.add(tablePanel2, BorderLayout.CENTER);
        cardPanel.add(page2, "Page 2");

        // Page 3 Panel same as above
        page3 = new JPanel(new BorderLayout());
        JPanel tablePanel3 = new JPanel(new GridLayout(4, 4));
        tablePanel3.setBackground(backgroundColor);
        for (int i = 0; i < 16; i++) {

            int index = i;
            JButton button = new JButton(imageFromID(pageOrder[2][i], sessionID, username));
            button.setBackground(backgroundColor);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String[] gameInfo = getGameInfo(pageOrder[2][index], sessionID, username);
                    GameInfoPage game = new GameInfoPage(gameInfo[0], imageFromID(pageOrder[2][index], sessionID, username), gameInfo[3], gameInfo[2], gameInfo[1], username);
                    game.setVisible(true);
                }
            });
            tablePanel3.add(button);
        }
        page3.add(tablePanel3, BorderLayout.CENTER);
        cardPanel.add(page3, "Page 3");

        // Page 4 Panel same as above
        page4 = new JPanel(new BorderLayout());
        JPanel tablePanel4 = new JPanel(new GridLayout(4, 4));
        tablePanel4.setBackground(backgroundColor);
        for (int i = 0; i < 16; i++) {
          
            int index = i;
            JButton button = new JButton(imageFromID(pageOrder[3][i], sessionID, username));
            button.setBackground(backgroundColor);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                  String[] gameInfo = getGameInfo(pageOrder[3][index], sessionID, username);
                    GameInfoPage game = new GameInfoPage(gameInfo[0], imageFromID(pageOrder[3][index], sessionID, username), gameInfo[3], gameInfo[2], gameInfo[1], username);
                    game.setVisible(true);
                }
            });
            tablePanel4.add(button);
        }
        page4.add(tablePanel4, BorderLayout.CENTER);
        cardPanel.add(page4, "Page 4");

        contentPanel.add(cardPanel, BorderLayout.CENTER);

        // Navigation Panel which lets the user swap between pages on the home, has buttons for each specific page
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navPanel.setBackground(backgroundColor2);
        page1Button = new JButton("Explore");
        page1Button.setBackground(backgroundColor3);
        page1Button.setForeground(Color.WHITE);
        page1Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 1");
            }
        });
        navPanel.add(page1Button);

        page2Button = new JButton("Featured Games");
        page2Button.setBackground(backgroundColor3);
        page2Button.setFocusPainted(false);
        page2Button.setForeground(Color.WHITE);
        page2Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 2");
            }
        });
        navPanel.add(page2Button);

        page3Button = new JButton("Top Rated Games");
        page3Button.setBackground(backgroundColor3);
        page3Button.setForeground(Color.WHITE);
        page3Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 3");
            }
        });
        navPanel.add(page3Button);

        page4Button = new JButton("Recommended Games");
        page4Button.setBackground(backgroundColor3);
        page4Button.setForeground(Color.WHITE);
        page4Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 4");
            }
        });
        navPanel.add(page4Button);

        

        contentPanel.add(navPanel, BorderLayout.SOUTH);

        // Show the GUI
        setContentPane(contentPanel);
        setVisible(true);
    }

    //Allows buttons to have logic through anonymous classes
    public void actionPerformed(ActionEvent e){
    }

    //Method that contacts init server endpoint to get a list of games alongside their ID's
    public void getsJSON(){

    try {
            // Create the URL object with the endpoint URL
            URL url = new URL("http://0.0.0.0:80/init");

            // Create the HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create a BufferedReader to read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Store the response as a string
                String jsonResponse = response.toString();

                // Print the response
                System.out.println("JSON IS " + jsonResponse);
            } else {
                // Handle the case when the response code is not 200 (OK)
                System.out.println("Error: " + responseCode);
            }

            // Disconnect the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  //Takes in a game ID and contacts the server to get its image
  public Icon imageFromID(String id, String sessionID, String username){
       try {
            // Create the URL object with the endpoint URL and ID
            String endpoint = "http://0.0.0.0:80/dashboard?inputID=" + id;
            URL url = new URL(endpoint);

            // Create the HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            

            // Set the request method to GET and set sessionid and username headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Session-ID", sessionID);
            connection.setRequestProperty("Username", username);
          
                //Reads the inputstream for bytes from the server and builds a string out of them

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Store the response as a string
                String jsonResponse = response.toString();
                //Split the JSON response into its components
                String[] parse1 = jsonResponse.split("\"", 0);
                //Index 15 is the image bytes
                String responseParsed = parse1[15];
              
                // Decode the base64-encoded image to bytes
                byte[] imageBytes = Base64.getDecoder().decode(responseParsed);

                // Convert the image bytes to an ImageIcon
                Icon imageIcon = new ImageIcon(imageBytes);


         // Disconnect the connection
            connection.disconnect();
                  //Return the image
                  return imageIcon;
            
            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
  }
  //Method that gets the rest of the game info like the description, rating etc.
  public String[] getGameInfo(String id, String sessionID, String username){
       try {
            // Create the URL object with the endpoint URL and ID
            String endpoint = "http://0.0.0.0:80/dashboard?inputID=" + id;
            URL url = new URL(endpoint);

            // Create the HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            

            // Set the request method to GET and set sessionid and username headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Session-ID", sessionID);
            connection.setRequestProperty("Username", username);
          
                

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Store the response as a string
                String jsonResponse = response.toString();

                String[] parse1 = jsonResponse.split("\"", 0);


         //Similar to method for getting the image but just uses different indexes for each list item
                String title = parse1[3];
                String genres = parse1[7];
                genres = genres.replaceAll("\\[\\]", "");
                String rating = parse1[11];
                String description = parse1[19];

         //Decodes the description into a readable string
                byte[] descriptionBytes = Base64.getDecoder().decode(description);

                String decodedDescription = new String(descriptionBytes);

         // Disconnect the connection
            connection.disconnect();
             

                String[] parse2 = new String[] {title, genres, rating, decodedDescription};

                return parse2;

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
  }

  //Gets the page order from the pgordr endpoint from the server
  public String[][] getPgOrder(String sessionID, String username){

    try {
            // Create the URL object with the endpoint URL
            URL url = new URL("http://0.0.0.0:80/pgordr");

            // Create the HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            

            // Set the request method to GET and set sessionid and username headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Session-ID", sessionID);
            connection.setRequestProperty("Username", username);
          
          //Reads the inputstream to get the list of game ids
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Store the response as a string
                String jsonResponse = response.toString();

                System.out.println("this is the pgorder: " + jsonResponse);

      // Remove curly braces and split the input string into pages
        String[] pages = jsonResponse.replaceAll("[{}]", "").split(";");

        // Create a 2D string array to store the result
        String[][] result = new String[pages.length][16];

        // Iterate over each page and populate the 2D array
        for (int i = 0; i < pages.length; i++) {
            // Split each page into the page name and the array of IDs
            String[] pageParts = pages[i].split(",");
  

            // Copy the IDs into the result array
            result[i] = Arrays.copyOf(pageParts, 16);

            // Print the page name and the array of IDs (optional)
            System.out.println("is bottom " + Arrays.toString(result[i]));
            

              // Disconnect the connection
            connection.disconnect();
                
        }
        return result;
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    return null;
  }
  
}