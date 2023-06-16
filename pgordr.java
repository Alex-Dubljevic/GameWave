/**

  pgorder.java
  This class organizes the specific page order for the displayed games within the homepage.

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

//imports necessary classes
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Base64;

class pgordr { // start of pgordr class

  /*
  generation of the page order
  @param username username
  @param featuredDatabase games featured in the database
  @param gameDatabase complete games of database
  @return a toString of the json
  */
  
  public String generate(String username, String featuredDatabase, String gameDatabase, String recommendationsDatabase) throws Exception {
    String line = "";
    BufferedReader featured = new BufferedReader(new FileReader(featuredDatabase));
    BufferedReader random = new BufferedReader(new FileReader(gameDatabase));
    BufferedReader recommended = new BufferedReader(new FileReader(recommendationsDatabase));
    StringBuilder json = new StringBuilder();
    String[][] games = new String[4][16]; // matrix to store games before JSON 


    json.append("{");

    // generate line 1 of the matrix by creating an array from games.csv then
    // scrambling it and inserting it into the matrix
    line = "";
    List<String> preScrambledPage1 = new ArrayList<>();
    while ((line = random.readLine()) != null) {
      String[] values = line.split(",");
      preScrambledPage1.add(values[3]); // Add the 3rd entry to the list
    }
    // call static shuffle to shuffle array list
    Collections.shuffle(preScrambledPage1);

    // create array and store in matrix
    String[] page1Arr = new String[16];
    for (int i = 0; i < 16; i++) {
      page1Arr[i] = preScrambledPage1.get(i);
    }
    games[0] = page1Arr;


    
    // gen str array from featured.csv and store on page 2 of the matrix
    line = featured.readLine();
    String[] page2Arr = line.split(",");
    games[1] = page2Arr;
    featured.close();


    
    // generate page 3 (top rated)
    ratingBasedGen page3generator = new ratingBasedGen();
    String[] page3Arr = page3generator.generate(gameDatabase);
    games[2] = page3Arr;

    // generate page 4 (recommended)
    while((line = recommended.readLine()) != null) {
      String[] lineArr = line.split(",");
      if (lineArr[0].equals(username)) {
        line = new String(Base64.getDecoder().decode(lineArr[1]), java.nio.charset.StandardCharsets.UTF_8);
        games[3] = line.split(",");
        break;
      }
    }

    // assemble page 1-4 json using matrix
    for (int k = 0; k < 4; k++) {
      json.append("[");
      for (int i = 0; i < games[k].length; i++) {
        json.append(games[k][i]);
        if (i != games[k].length - 1) {
          json.append(",");
        }
      }
      json.append("];");
    }
    json.append("}");

    return json.toString();
  }
}
