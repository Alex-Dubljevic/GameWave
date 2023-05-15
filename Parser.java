// parser by Aleksandar Gojovic
// parses data into a JSON 
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;

class Parser {
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