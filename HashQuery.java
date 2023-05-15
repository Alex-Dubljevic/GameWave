// hashquery by Aleksandar Gojovic
// hashes string and manages user database (fetches and adds)
import java.security.*;
import java.util.Base64;
import java.util.Hashtable;
import java.io.*;
import java.util.Scanner;

class HashQuery {
  private Hashtable<String, String> userDB = new Hashtable<>();
  private String database;
  
  public HashQuery(String database) throws IOException {

    this.database = database;
    // loop through all database values
    Scanner sc = new Scanner(new File(database));
    while (sc.hasNextLine()) {
      // store username & password in hashtable
      String[] values = sc.nextLine().split(":");
      userDB.put(values[0], values[1]);
    }
  }

  private String hash(String plainText) throws NoSuchAlgorithmException {
    // create SHA-512 instance
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    // generate hash
    byte[] hashedPassword = md.digest(plainText.getBytes());
    // convert byte array to base64 string and return it 
    return Base64.getEncoder().encodeToString(hashedPassword);
  }

  public boolean add(String username, String password) throws NoSuchAlgorithmException, IOException {
    if (userDB.containsKey(username)) {
      return false;
    } else {
      try {
        FileWriter fw = new FileWriter(database, true); // create filewriter
        BufferedWriter bw = new BufferedWriter(fw); // create bufferedwriter
        String newLine =username+":"+hash(password);
        bw.write(newLine);
        bw.newLine();       
        bw.close();
        fw.close();
        return true;
      } catch (Exception e) {
        return false;
      }
    }
  }
  public boolean query(String username, String password) throws NoSuchAlgorithmException {
    // initiate return value
    boolean ret = false;
    // query user db, check if username and password hash match
    if (userDB.containsKey(username)) {
      String hash = hash(password);
      if (userDB.get(username).equals(hash)) {
        ret = true;
      }
    } else {
      ret = false;
    }
    return ret;
  }
}