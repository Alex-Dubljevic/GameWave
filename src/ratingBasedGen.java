
/**
  ratingBasedGen.java
  This class organizes games within Games.csv and reorganizes them using bubble sorting based on highest to lowest.

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ratingBasedGen {

  /*
  Creates a class that bubblesorts based off of the given double values
  @param ratings complete list of ratings
  @param ids the corresponding id w/ the rating
  */
  private void bubbleSort(double[] ratings, String[] ids) {
    int n = ratings.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (ratings[j] < ratings[j + 1]) {
          // Swap ratings
          double tempRating = ratings[j];
          ratings[j] = ratings[j + 1];
          ratings[j + 1] = tempRating;

          // Swap IDs
          String tempId = ids[j];
          ids[j] = ids[j + 1];
          ids[j + 1] = tempId;
        }
      }
    }
  }

  public String[] generate(String database) {

    //sets ints for number of entries and number of games displayed @ the top
    int numEntries = 66;
    int topRatings = 16;

    try {
      //creates file that reads from db
      File file = new File(database);
      Scanner scanner = new Scanner(file); // scanner that looks through file

      // Skip the header line
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }

      //storage for ratings & ids
      double[] ratings = new double[numEntries]; 
      String[] ids = new String[numEntries];

      //index
      int index = 0;
      while (scanner.hasNextLine() && index < numEntries) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");

        // sets location of rating and id in correlation with Games.csv
        double rating = Double.parseDouble(parts[2]);
        String id = parts[3];

        ratings[index] = rating;
        ids[index] = id;

        index++;
      }

      bubbleSort(ratings, ids); //invokes bubbleSort method

      String[] strIDs = new String[topRatings]; //organizes games in terms of highest to lowest IDs
      for (int i = 0; i < strIDs.length; i++) {
        strIDs[i] = String.valueOf(ids[i]);
      }
      scanner.close(); //closes scanner
      return strIDs; //returns string of ID
    } catch (FileNotFoundException e) { //catches error
      e.printStackTrace();
      String[] error = new String[1];
      error[0] = "error";
      return error; //returns error
    }
  }
}