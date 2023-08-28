import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

public interface RoutePlannerBackendInterface {

  // private ShortestPathGraphInterface graph;
  // private dotFileReader fileReader;
  // public RoutePlannerBackend(ShortestPathGraphInterface graph, dotFileReader fileReader);

  // Load the preconfigured graph data.
  public void loadData(String filename) throws FileNotFoundException;

  // Add a new edge to the backend graph.
  public void addEdge(edgeInterface edge) throws IllegalArgumentException;

  // Add a new capital to the graph
  public void addCapital(capitalInterface newCapital) throws IllegalArgumentException;

  // Search if capital exists already.
  public boolean containsCapital(capitalInterface capital);

  // Search if the input edge exists already.
  public boolean containsEdge(edgeInterface edge);

  // Calculate shortest path from one capital to another.
  public List<String> findShortestPath(String from, String to)
      throws NoSuchElementException;

  // Calculate shortest path with minimum and maximum distance limits.
  public List<String> findShortestPath(String from, String to,
      int minDistance, int maxDistance) throws NoSuchElementException;

}
