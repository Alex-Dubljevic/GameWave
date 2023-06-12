/* class ratingsAndReviews {
  private String raDB;
  private String reDB;
  ratingsAndReviews(String ratingDatabase, String reviewDatabase) {
    this.raDB = ratingDatabase;
    this.reDB = reviewDatabase;
  }

  //a type of 0 is generating or adding a rating, a type of 1 is generating or adding a review
  
  public String generate(String UID, boolean type) {
    if (type) {
      // generate reviews based on game
    } else {
      // generate ratings based on game
    }
    
  }

  public void add(String UID, boolean type) {
    if (type) {
      // add a review to the review database
      BufferedReader databaseReader = new BufferedReader(new FileReader(reDB));
    } else {
      // add a rating to the rating database
      BufferedReader databaseReader = new BufferedReader(new FileReader(raDB));
    }
  }

  
} */