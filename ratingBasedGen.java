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
    int numEntries = 66;
    int topRatings = 16;

    try {
      File file = new File(database);
      Scanner scanner = new Scanner(file);

      // Skip the header line
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }

      double[] ratings = new double[numEntries];
      String[] ids = new String[numEntries];

      int index = 0;
      while (scanner.hasNextLine() && index < numEntries) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");

        // Assuming ratings are in the third column and IDs are in the fourth column
        double rating = Double.parseDouble(parts[2]);
        String id = parts[3];

        ratings[index] = rating;
        ids[index] = id;

        index++;
      }

      bubbleSort(ratings, ids);

      String[] strIDs = new String[topRatings];
      for (int i = 0; i < strIDs.length; i++) {
        strIDs[i] = String.valueOf(ids[i]);
      }
      scanner.close();
      return strIDs;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      String[] error = new String[1];
      error[0] = "error";
      return error;
    }
  }
}