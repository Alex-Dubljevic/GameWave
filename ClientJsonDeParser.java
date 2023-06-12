/*
Game.java

This class parses JSON data for the client.

Programmed by: Alex D., Aleks G., Maksim G., Ilya R., and Kaan U.
Last modified: Apr. 27th, 2023
*/ 

// import hashtable class
import java.util.Hashtable;

class ClientJsonDeParser {

  public static Hashtable<String, String> parse (String JSON){
    
    Hashtable<String, String> hashtable = new Hashtable<>();
    JSON = JSON.replace("{","").replace("\"", "");
    String[] JSONObjArr = JSON.split(","); // splits values in array based off of commas in values
    String[] currentLine; //array reading current line
    
    for (int i = 0; i < JSONObjArr.length;i++) {
      currentLine = JSONObjArr[i].split(":");
      hashtable.put(currentLine[0], currentLine[1]);
    }
    
    return hashtable;
  }
}