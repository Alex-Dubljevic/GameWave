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
  
  public String getJsonGameDatabase(String database) throws Exception {
  
    BufferedReader br = new BufferedReader(new FileReader(database));
    String line = "";
    StringBuilder json = new StringBuilder();
    json.append("{");
    while ((line = br.readLine()) != null) {
      String[] fields = line.split(",");
      json.append("\"").append(fields[0]).append("\":\"").append(fields[3]).append("\",");
    }
    br.close();
    if (json.length() > 1) {
      json.setLength(json.length() - 1);
    }

    json.append("}");
    return json.toString();
  }
}