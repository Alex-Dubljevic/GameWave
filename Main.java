/**

  This program serves as the central application for GameWave, where authorized users can login, view game pages as generated through the program, view comments on games, rate games, leave comments, etc. 


  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/


import java.io.*; //imports java.io.* class

public class Main {
  public static void main(String[] args) throws IOException {
    LoginGUI login = new LoginGUI(); // launch login client
    Server server = new Server(); //create server
    server.start(); //start server
  }
}