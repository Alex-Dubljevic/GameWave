//Server.java written by Aleksandar Gojovic
// implements game.java written by Maksim Grujic
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.security.*;
import com.sun.net.httpserver.*;
import java.util.logging.*;
import java.util.Base64;

public class Server {
  private static final Logger logger = Logger.getLogger(Main.class.getName()); // start logger service

  public static void start() throws IOException {
    // create HttpServer object
    HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 80), 0);

    // create request handler for all endpoints
    server.createContext("/login", new RequestHandler());
    server.createContext("/dashboard", new RequestHandler());
    server.createContext("/signup", new RequestHandler());
    server.createContext("/init", new RequestHandler());

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
      logger.info(exchange.getRequestMethod() + " on " + path);
      switch (path) {
        case "/login":
          // code for login case//////////////////////////////////////////////
          String ip = exchange.getRemoteAddress().getAddress().getHostAddress();
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
            Headers loginHeaders = exchange.getResponseHeaders();
            OutputStream responseBody = exchange.getResponseBody();
            String response = "";
            if (hQueryCheck) {
              logger.info("Login success;" + ip + ";username:" + username + ";password:" + password);
              loginHeaders.set("validity", "1");
              response = response + "Login succesful!\n";

              // session ID generation
              // HttpSession session =
              // exchange.getHttpContext().getServer().createContext("/").getHttpHandler().getSession(exchange);
              // // create session obj
              // String sessionId = UUID.randomUUID().toString(); // generate session
              // session.setAttribute("username", username); // store the session object
              // session.setMaxInactiveInterval(30 * 60); // set session expiry

            } else {
              logger.info("Sending invalid login to " + ip); // debug
              loginHeaders.set("validity", "0");
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
            Headers loginHeaders = exchange.getResponseHeaders();
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
        case "/signup":
          ip = exchange.getRemoteAddress().getAddress().getHostAddress();
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
            Headers loginHeaders = exchange.getResponseHeaders();
            OutputStream responseBody = exchange.getResponseBody();
            try {
              if (validate.validate(username, false)) {
                if (validate.validate(password, true)) {
                  boolean additionSuccess = dbAddition.add(username, password);
                  if (additionSuccess) {
                    logger.info("Added " + username + " and " + password + " to DB");
                    loginHeaders.set("validity", "1");
                    response = response + "Sign Up Succesful\n";
                  } else {
                    loginHeaders.set("validity", "0");
                    response = response + "Sign up Failure:\n";
                  }
                } else {
                  loginHeaders.set("validity", "0");
                  response = response + "Sign Up Failure:password\n";
                }
              } else {
                loginHeaders.set("validity", "0");
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
            Headers loginHeaders = exchange.getResponseHeaders();
              
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
          // BEGINNING OF DASHBOARD ENDPOINT
        case "/dashboard":
          ip = exchange.getRemoteAddress().getAddress().getHostAddress();
          if ("GET".equals(exchange.getRequestMethod())) {
            // handle GET request
            logger.info(ip + " attempting GET on /dashboard");
            Headers dashboardHeaders = exchange.getResponseHeaders();
            OutputStream responseBody = exchange.getResponseBody();
            String[] queryArgs = exchange.getRequestURI().getQuery().split("=");
            String response = "";
            if (queryArgs[0].equals("inputID")) {
              String inputID = queryArgs[1];
              System.out.println(inputID);
              Game searchedGame = new Game(inputID);
              String imgPath = "imgs/" + searchedGame.getImage();
              System.out.println(imgPath);
              File imageFile = new File(String.valueOf(imgPath));
              FileInputStream imageStream = new FileInputStream(imageFile);
              byte[] imageBytes = imageStream.readAllBytes();
              imageStream.close();
              String image = Base64.getEncoder().encodeToString(imageBytes);
              response = "{\"game\": \"" + searchedGame.getTitle() + "\", \"genre\": \""
                  + String.valueOf(searchedGame.getGenres()) + "\", \"rating\": \""
                  + String.valueOf(searchedGame.getRating()) + "\", \"image\": \"" + image + "\"}";
            } else {
              logger.info("Game initialization failed");
              response = "No Game Found";
            }
            dashboardHeaders.set("Content-Type", "application/json"); // all professional
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes()); // write response
            responseBody.flush();
            logger.info("JSON data sent to " + ip);
            responseBody.close(); // finish writing
          } else { // handle POST (illegal request)
            logger.info(ip + " attempted illegal method on /dashboard");
            // handle POST request
            Headers dashboardHeaders = exchange.getResponseHeaders();
            OutputStream responseBody = exchange.getResponseBody();
            String response = "Method not supported\n"; // send back method not supported same as GET for /login
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Method not supported sent to " + ip);
            responseBody.close();
          }
        case "/init":
          ip = exchange.getRemoteAddress().getAddress().getHostAddress();
          if ("GET".equals(exchange.getRequestMethod())) {
            logger.info(ip + " attempting GET on /init");
            Parser parser = new Parser();
            String response = "";
            try {
              response = parser.getJsonGameDatabase("Games.csv");
              System.out.println(response);
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
            Headers dashboardHeaders = exchange.getResponseHeaders();
            OutputStream responseBody = exchange.getResponseBody();
            String response = "Method not supported\n"; // send back method not supported same as GET for /login
            exchange.sendResponseHeaders(200, response.length()); // 200 OK
            responseBody.write(response.getBytes());
            responseBody.flush();
            logger.info("Method not supported sent to " + ip);
            responseBody.close();
          }
      }
    }
  }
}