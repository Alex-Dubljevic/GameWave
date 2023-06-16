/**

  Validator.java
  This class deals with the validators of the login page inputs.

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/
class Validator {

  /*
  Constructor method for the validation of passwords
  @param input user input
  @param type type of user input
  */
  public static boolean validate(String input, boolean type) {
    if (type) { 
      if (input.length() < 8) { // ensures user input is greater than 8
      return false;
      } 
    }

    
    if (!type) { 
      
        if (input.contains(":") || input.contains(",") || input.contains("\"") || input.contains("\'") || input.length() < 4) { //sets restricted user input so user input doesnt interfere w/ CSV 
        return false;
      }
    }
    
    return true;
  }
}