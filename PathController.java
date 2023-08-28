import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Frontend controller class which generates the output onto the GUI
 * 
 * @author rsamb
 *
 */
public class PathController {

  @FXML
  private TextField end;

  @FXML
  private TextField maxDistance;

  @FXML
  private TextField minDistance;

  @FXML
  private TextArea output;

  @FXML
  private TextField start;

  static String[][] capitalStateMap = CapitalStateMap.get();

  RoutePlannerBackendBD backend;
  String startCapital;
  String endCapital;
  int minDistanceVal = 0;
  int maxDistanceVal = 2147483647;

  public PathController() {
    dotFileReaderInterface routeLoader = new dotFileReader();

    ShortestPathGraphAE<String, Integer> graph;

    graph = new ShortestPathGraphAE<String, Integer>();

    RoutePlannerBackendBD backend = new RoutePlannerBackendBD(graph, routeLoader);

    try {
      backend.loadData("sample.gv");
    } catch (FileNotFoundException e) {
      // do nothing
    }

    this.backend = backend;

  }

  /**
   * On button click, this method validates and outputs the route
   * 
   * @param event
   */
  @FXML
  void generateRoute(ActionEvent event) {
    String startState = start.getText();
    String endState = end.getText();

    try {
      startCapital = validateStateChoice(startState);
      endCapital = validateStateChoice(endState);
    } catch (IllegalArgumentException except) {
      System.out.println("Invalid choice: " + except.getMessage());
    }

    int min = minDistanceVal;
    int max = maxDistanceVal;
    if (minDistance.getText().isEmpty() == false && maxDistance.getText().isEmpty() == false) {
      min = Integer.parseInt(minDistance.getText());
      max = Integer.parseInt(maxDistance.getText());
    }
    try {
      // 2147483647 is largest allowed int in Java
      maxDistanceVal = validateIntChoice(minDistanceVal, 2147483647, max);
    } catch (IllegalArgumentException except) {
      System.out.println("Invalid choice: " + except.getMessage());
    }

    try {
      // 2147483647 is largest allowed int in Java
      minDistanceVal = validateIntChoice(0, maxDistanceVal, min);
    } catch (IllegalArgumentException except) {
      System.out.println("Invalid choice: " + except.getMessage());
    }

    if (startCapital == null || endCapital == null) {
      System.out.println("Invalid choice: Start and end capitals must be set.");
    }
    if (startCapital == endCapital) {
      System.out.println("Invalid choice: Start and end capitals must be unique.");
    }

    output.setText(routePlanner());
  }

  /**
   * Represents the calculated route based on the result of findRoute()
   * 
   * @return a string representing the calculated route
   */
  public String routePlanner() {
    String route = new String();
    List<String> pathStrings = findRoute();
    for (String string : pathStrings) {
      route = route + string;
    }
    return "Your route is: " + route.substring(4, route.length());
  }

  /**
   * Calculates and creates a List<String> representation of the shortest route
   * 
   * @return a List of Strings representing the calculated route
   */
  public List<String> findRoute() {

    List<String> pathList;
    List<String> pathStrings = new ArrayList<String>();
    try {
      if (minDistanceVal > 0 || maxDistanceVal < 2147483647) {
        pathList =
            backend.findShortestPath(startCapital, endCapital, minDistanceVal, maxDistanceVal);
      } else {
        pathList = backend.findShortestPath(startCapital, endCapital);
      }

      for (int i = 0; i < pathList.size(); i++) {
        pathStrings.add(" -> " + pathList.get(i));
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return pathStrings;
  }

  /**
   * Validates a user's choice of integer based on given bounds
   * 
   * @param min  lower bound
   * @param max  upper bound
   * @param choiceInt inputted distance value
   * @return valid int
   * @throws IllegalArgumentException
   */
  private int validateIntChoice(int min, int max, int choiceInt) throws IllegalArgumentException {
    // Check if user has made a choice in valid range
    if (choiceInt > max || choiceInt < min) {
      throw new IllegalArgumentException("Out of range");
    }
    return choiceInt;
  }

  /**
   * Validates a user's choice of state based on presence in the US and in the graph
   * 
   * @param choice String state to get capital from
   * @return valid Capital
   * @throws IllegalArgumentException
   */
  private String validateStateChoice(String choice) throws IllegalArgumentException {
    // Check if user has made a choice
    if (choice.isEmpty()) {
      throw new IllegalArgumentException("Not a string");
    }
    String choiceStr = choice.trim().toLowerCase();
    for (int i = 0; i < capitalStateMap.length; i++) {
      if (capitalStateMap[i][0].toLowerCase().equals(choiceStr)) {
        Capital selectedCapital = new Capital(capitalStateMap[i][1], capitalStateMap[i][0]);
        if (backend.containsCapital(selectedCapital)) {
          return backend.graph.nodes.get(selectedCapital.getName()).data;
        }
      }
    }
    throw new IllegalArgumentException("Not a state");
  }

}
