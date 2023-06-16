/**

  GameRecommendation.java
  This game handles the recommendation of games for the user
  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

//import necessary external classes
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.net.*;

public class GameRecommendationSystem{

 /*
  Organizes the game recommednation system
  @param database database
  */
  
    public void recommend(String[] myGenres, String username) throws IOException{
        Map<String, List<String>> gameGenres = new HashMap<>();

        File gameFile = new File("Games.csv"); //initializes csv file
        Scanner gameReader = new Scanner(gameFile); //initializes scanner
        gameReader.nextLine();
        while (gameReader.hasNext()){
          String gameInfoMerged = gameReader.nextLine();
          String[] findID = gameInfoMerged.split(",");
          Game currentGame = new Game(findID[3]);
          gameGenres.put(currentGame.getTitle(), currentGame.getGenres());
        }
        gameReader.close();
        // Add more games and their genres...

        List<String> userGenres = new ArrayList<>(Arrays.asList(myGenres));

        Map<String, Integer> gameScores = new HashMap<>();
        for (String genre : userGenres) {
            for (Map.Entry<String, List<String>> entry : gameGenres.entrySet()) {
                String game = entry.getKey();
                List<String> genres = entry.getValue();
                if (genres.contains(genre)) {
                    gameScores.put(game, gameScores.getOrDefault(game, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedGames = new ArrayList<>(gameScores.entrySet());
        Collections.sort(sortedGames, (a, b) -> b.getValue().compareTo(a.getValue()));

        List<String> recommendedGames = new ArrayList<>();
        for (int i = 0; i < Math.min(sortedGames.size(), 16); i++) {
            recommendedGames.add(sortedGames.get(i).getKey());
        }

       for (int i = 0; i < recommendedGames.size(); i++) {
          String title = recommendedGames.get(i);
          String id = Game.titleToID(title);
          recommendedGames.set(i, id);
       }

        // Print the recommended games
        for (String game : recommendedGames) {
            System.out.println(game);
        }

        String joinedArray = String.join(",", recommendedGames); // Join the strings with a delimiter
        byte[] bytes = joinedArray.getBytes(StandardCharsets.UTF_8); // Convert the string to bytes
        String data = Base64.getEncoder().encodeToString(bytes); // Encode the bytes to base64

        
      try {
        System.out.println("test1234");
        String url = "http://0.0.0.0:80/survey";
        String requestBody = "username=" + username + "&data=" + data;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        // Set the request method to POST
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        // Write the request body to the output stream
        
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);   
        }  catch (Exception e) {
        e.printStackTrace();
        }

        // Get the response
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        // Handle the response as needed
        
        con.disconnect();
    } catch (Exception e) {
        e.printStackTrace();
        throw e;
    }
    
    }
}
