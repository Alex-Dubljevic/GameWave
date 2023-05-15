// validator by Aleksandar Gojovic
// validates username or password 
class Validator {
  public static boolean validate(String input, boolean type) {
    if (type) {
      if (input.length() < 8) {
      return false;
      } 
    }

    if (!type) {
        if (input.contains(":") || input.contains(",") || input.contains("\"") || input.contains("\'") || input.length() < 4) {
        return false;
      }
    }
    
    return true;
  }
}