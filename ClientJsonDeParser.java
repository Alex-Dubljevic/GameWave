// clien JSON parser by Aleksandar Gojovic 
// parses JSON data for the client 
import java.util.Hashtable;

class ClientJsonDeParser {
  public static Hashtable<String, String> parse (String JSON){
    Hashtable<String, String> hashtable = new Hashtable<>();
    JSON = JSON.replace("{","").replace("\"", "");
    String[] JSONObjArr = JSON.split(",");
    String[] currentLine;
    for (int i = 0; i < JSONObjArr.length;i++) {
      currentLine = JSONObjArr[i].split(":");
      hashtable.put(currentLine[0], currentLine[1]);
    }
    return hashtable;
  }
}