
/**

  Server.java
  This class deals with the server based off of the Games.java class.

  Programmed by: Alex D., Aleks G., Maksim G., Kaan  U., and Ilya R.
  Date Created: Feb. 22, 2023
  Date Modified: June 8, 2023

*/
//imports necessary external classe for the class
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.security.*;
import com.sun.net.httpserver.*;
import java.util.logging.*;
import java.util.UUID;
import java.util.HashMap;

public class Server {
  private static final Logger logger = Logger.getLogger(Main.class.getName()); // start logger service
  private static final HashMap<String, String> sessionData = new HashMap<>(); // start sessionData hashmap which manages logged in users
  static pgordr pgordr = new pgordr(); //
  // initialize page order manager, this manager calulates the page order for
  // different users
  // page order manager will manage the order in which the games appear based on
  // user

  public void start() throws IOException {
    // create HttpServer object
    HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 80), 0);

    // create request handler for all endpoints

    // endpoint for logging in 
    server.createContext("/login", new RequestHandler());

    // endpoint for dashboard (content delivery)
    server.createContext("/dashboard", new RequestHandler());

    // endpoint for signing up 
    server.createContext("/signup", new RequestHandler());

    // endpoint for initialization (tells the client the corresponding name for each ID)
    server.createContext("/init", new RequestHandler());

    // endpoint for page order (tells the client how to order the dashabord)
    server.createContext("/pgordr", new RequestHandler());

    // endpoint to capture survey data 
    server.createContext("/survey", new RequestHandler());

    // endpoint to capture reviews
    server.createContext("/reviews", new RequestHandler());

    // create service executor
    ExecutorService executor = Executors.newFixedThreadPool(10);
    server.setExecutor(executor);
    server.start(); // start server
    logger.info("Server started on localhost:80");
  }

  static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      String path = exchange.getRequestURI().getPath();
      Validator validate = new Validator();
      String ip = exchange.getRemoteAddress().getAddress().getHostAddress();
      Headers headers = exchange.getResponseHeaders();
      logger.info(exchange.getRequestMethod() + " on " + path);
      switch (path) {
        case "/login":
          // code for login case//////////////////////////////////////////////
          if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"))
                .readLine();
            String username = null;
            String password = null;
            Pattern pattern = Pattern.compile("username=(.*)&password=(.*)");
            Matcher matcher = pattern.matcher(requestBody);
            if (matcher.matches()) {
              username = matcher.group(1);
              password = matcher.group(2);
              logger.info("Login attempt;" + ip + ";username:" + username + ";password:" + password);
            }

            // call query method on password string, capture NoSuchAlgorithmException
            HashQuery dbQuery = new HashQuery("userdb.csv");
            boolean hQueryCheck = false;
            try {
              hQueryCheck = dbQuery.query(username, password);
            } catch (NoSuchAlgorithmException e) {
              logger.info("Error: " + String.valueOf(e));
            }

            // send response containing "valid" header with value 1 or 0 depending on
            // hQueryCheck
            OutputStream responseBody = exchange.getResponseBody();
            String response = "";
            if (hQueryCheck) {
              logger.info("Login success;" + ip + ";username:" + username + ";password:" + password);
              headers.set("validity", "1");
              response = response + "Login succesful!\n";

              String sessionId = UUID.randomUUID().toString();
              sessionData.put(sessionId, username);
              headers.set("Username", username);
              headers.set("Session-ID", sessionId);

            } else {
              logger.info("Sending invalid login to " + ip); // debug
              headers.set("validity", "0");
              response = response + "Username or Password incorrect\n";
            }
            exchange.sendResponseHeaders(200, response.length());
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Successful login sent to: " + ip);
            responseBody.close();
          } else {
            // method not supported (any method but POST)
            logger.info(ip + " attempt !POST on /login");
            OutputStream responseBody = exchange.getResponseBody();
            String response = "Method not supported\n";
            exchange.sendResponseHeaders(200, response.length());
            responseBody.write(response.getBytes());
            logger.info("Method not supported sent to: " + ip);
            responseBody.close();
            responseBody.flush();
            responseBody.close();
          }
          // END OF LOGIN ENDPOINT
          // BEGINNING OF SIGNUP ENGPOINT
          break;
        case "/signup":
          if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"))
                .readLine();
            String username = null;
            String password = null;
            Pattern pattern = Pattern.compile("username=(.*)&password=(.*)");
            Matcher matcher = pattern.matcher(requestBody);
            if (matcher.matches()) {
              username = matcher.group(1);
              password = matcher.group(2);
              logger.info("Sign up attempt;" + ip + ";username:" + username + ";password:" + password);
            }
            HashQuery dbAddition = new HashQuery("userdb.csv");
            String response = "";
            OutputStream responseBody = exchange.getResponseBody();
            try {
              if (validate.validate(username, false)) {
                if (validate.validate(password, true)) {
                  boolean additionSuccess = dbAddition.add(username, password);
                  if (additionSuccess) {
                    logger.info("Added " + username + " and " + password + " to DB");
                    headers.set("validity", "1");
                    response = response + "Sign Up Succesful\n";
                  } else {
                    headers.set("validity", "0");
                    response = response + "Sign up Failure:\n";
                  }
                } else {
                  headers.set("validity", "0");
                  response = response + "Sign Up Failure:password\n";
                }
              } else {
                headers.set("validity", "0");
                response = response + "Sign Up Failure:username2\n";
              }
            } catch (Exception e) {
              logger.info("Error" + String.valueOf(e));
              response = "ERROR";
            }
            exchange.sendResponseHeaders(200, response.length());
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Sign up response sent to " + ip);
            responseBody.close();
          } else {
            // method not supported (any method but POST)
            logger.info(ip + " attempted !POST on /signup");

            OutputStream responseBody = exchange.getResponseBody();
            String response = "Method not supported\n";
            exchange.sendResponseHeaders(200, response.length());
            responseBody.write(response.getBytes());
            logger.info("Method not supported sent to: " + ip);
            responseBody.close();
            responseBody.flush();
            responseBody.close();
          }
          HashQuery hasher = new HashQuery("userdb.csv");
          break;
          // BEGINNING OF DASHBOARD ENDPOINT
        case "/dashboard":
          if ("GET".equals(exchange.getRequestMethod())) {
            // handle GET request
            logger.info(ip + " attempting GET on /dashboard");
            String response = "";
            String sessionId = exchange.getRequestHeaders().getFirst("Session-ID");
            String username = exchange.getRequestHeaders().getFirst("Username");
            OutputStream responseBody = exchange.getResponseBody();

            if (true /*sessionData.containsKey(sessionId) && sessionData.get(sessionId).equals(username)*/) {
              logger.info(ip + " attempting GET on /dashboard: session valid");
              String[] queryArgs = exchange.getRequestURI().getQuery().split("=");
              if (queryArgs[0].equals("inputID")) {
                String inputID = queryArgs[1];
                Game searchedGame = new Game(inputID);
                response = searchedGame.getJSON();
              } else {
                logger.info("Game initialization failed");
                response = "No Game Found";
              }
              headers.set("Content-Type", "application/json"); // all professional
              exchange.sendResponseHeaders(200, response.length()); // 200 OK
              responseBody.write(response.getBytes()); // write response
              responseBody.flush();
              logger.info("JSON data sent to " + ip);
              responseBody.close(); // finish writing
            } else {
              // request unsuccesful
              response = "Session expired, please login\n";
              responseBody = exchange.getResponseBody();
              exchange.sendResponseHeaders(200, response.length());
              responseBody.write(response.getBytes());
              responseBody.flush();
              logger.info("Session expiry sent to " + ip);
              responseBody.close();
            }
          } else { // handle POST (illegal request)
            logger.info(ip + " attempted illegal method on /dashboard");
            // handle POST request
            OutputStream responseBody = exchange.getResponseBody();
            String response = "Method not supported\n"; // send back method not supported same as GET for /login
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Method not supported sent to " + ip);
            responseBody.close();
          }
          break;
        case "/init":
          if ("GET".equals(exchange.getRequestMethod())) {
            headers.set("Content-Type", "application/json"); // all professional
            logger.info(ip + " attempting GET on /init");
            Parser parser = new Parser();
            String response = "";
            try {
              response = parser.getJsonGameDatabase("Games.csv");
            } catch (Exception e) {
              response = "Error, no games found";
            }
            OutputStream responseBody = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes()); // write response
            responseBody.flush();
            logger.info("JSON data sent to " + ip);
            responseBody.close();
          } else {
            logger.info(ip + " attempted illegal method on /dashboard");
            // handle POST request
            OutputStream responseBody = exchange.getResponseBody();
            String response = "Method not supported\n"; // send back method not supported same as GET for /login
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Method not supported sent to " + ip);
            responseBody.close();
          }
          break;
        case "/pgordr":
          String response;
          if ("GET".equals(exchange.getRequestMethod())) {
            logger.info(ip + " attempting GET on /pgordr");
            String sessionId = exchange.getRequestHeaders().getFirst("Session-ID");
            String username = exchange.getRequestHeaders().getFirst("Username");
            if (true /*sessionData.containsKey(sessionId) && sessionData.get(sessionId).equals(username)*/) {
              headers.set("Content-Type", "application/json");
              // request succesful
              
               try {
                response = pgordr.generate(username, "featured.csv", "Games.csv"); // generate page order for user
               } catch (Exception e) {
                logger.info("Page could not be generated: " + String.valueOf(e));
                response = "Page generation error";
               }
              OutputStream responseBody = exchange.getResponseBody();
              exchange.sendResponseHeaders(200, response.length());
              responseBody.write(response.getBytes());
              responseBody.flush();
              logger.info("Page order sent to " + ip);
              responseBody.close();
            } else {
              // request unsuccesful
              response = "Session expired, please login\n";
              OutputStream responseBody = exchange.getResponseBody();
              exchange.sendResponseHeaders(200, response.length());
              responseBody.write(response.getBytes());
              responseBody.flush();
              logger.info("Session expiry sent to " + ip);
              responseBody.close();
            }
          } else {
            // illegal method
            logger.info(ip + " attempted illegal method on /pgordr");
            OutputStream responseBody = exchange.getResponseBody();
            response = "Method not supported\n"; // send back method not supported
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Method not supported sent to " + ip);
            responseBody.close();
          }
          break;
        case "/survey":
          if ("POST".equals(exchange.getRequestMethod())) {
            logger.info(ip + " attempting POST on /survey");
            String username = exchange.getRequestHeaders().getFirst("Username");
            String[] genres = exchange.getRequestHeaders().getFirst("Genres").split(";");
          } else {
            // method not supported
            logger.info(ip + " attempted illegal method on /pgordr");
            OutputStream responseBody = exchange.getResponseBody();
            response = "Method not supported\n"; // send back method not supported
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Method not supported sent to " + ip);
            responseBody.close();
          }
        case "/reviews":
          if ("POST".equals(exchange.getRequestMethod())) {
            String username = exchange.getRequestHeaders().getFirst("Username");
            String review = exchange.getRequestHeaders().getFirst("Review");
            String rating = exchange.getRequestHeaders().getFirst("Rating");

            
            
          } else {
            // method not supported
          }
      }
    }
  }
}