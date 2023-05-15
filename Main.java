import java.io.*;
public class Main {
  public static void main(String[] args) throws IOException {
    LoginGUI login = new LoginGUI(); // launch login client (for demo purposes)
    Server server = new Server(); //create server
    server.start(); //start server
  }
}