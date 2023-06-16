/**
  Parser.java
  This class parses information from within the database.
  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/

//imports external classes
import java.io.BufferedReader;
import java.io.FileReader;

class Parser { // start of parses class

  /*
  This method deals with the data stored in the json
  @param database info in the database
  @return json details
  */
  public String getJsonGameDatabase(String database) throws Exception {

    //bufferreader created
    BufferedReader br = new BufferedReader(new FileReader(database));
    String line = ""; //temporary and empty string created
    StringBuilder json = new StringBuilder();
    json.append("{");  // appends the json to add a "{" at the start
    while ((line = br.readLine()) != null) { //adds to the json
      String[] fields = line.split(",");
      json.append("\"").append(fields[0]).append("\":\"").append(fields[3]).append("\",");
    }
    br.close(); // closes bufferedreader
    if (json.length() > 1) { //modifies json length
      json.setLength(json.length() - 1);
    }

    json.append("}"); // appends the json to add a "}" at the end
    return json.toString(); //returns
  }
}