import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Backend which works with the Algorithm Engineer to generate the route
 * 
 * @author rsamb
 *
 */
public class RoutePlannerBackendBD implements RoutePlannerBackendInterface {

  public ShortestPathGraphAE<String, Integer> graph;
  private dotFileReaderInterface fileReader;
  public List<capitalInterface> capitalList;
  public List<edgeInterface> edgeList;

  public RoutePlannerBackendBD(ShortestPathGraphAE<String, Integer> graph,
      dotFileReaderInterface fileReader) {
    this.graph = graph;
    this.fileReader = fileReader;
  }
  

  @Override
  public void loadData(String filename) throws FileNotFoundException {
    capitalList = fileReader.readCapitalsFromDotFile(filename);
    edgeList = fileReader.readEdgesFromDotFile(filename);
    for (capitalInterface c : capitalList) {
      graph.insertNode(c.getName());
    }
    for (edgeInterface e : edgeList) {
      graph.insertEdge(e.getStart().getName(), e.getEnd().getName(), e.getMiles());
    }

  }

  @Override
  public void addEdge(edgeInterface edge) throws IllegalArgumentException {
    if (!graph.containsEdge(edge.getStart().getName(), edge.getEnd().getName())) {
      graph.insertEdge(edge.getStart().getName(), edge.getEnd().getName(), edge.getMiles());
    } else {
      throw new IllegalArgumentException("Edge already exists");
    }

  }

  @Override
  public void addCapital(capitalInterface newCapital) throws IllegalArgumentException {
    if (containsCapital(newCapital) == false) {
      graph.insertNode(newCapital.getName());
    } else {
      throw new IllegalArgumentException("Capital already exists");
    }

  }

  @Override
  public boolean containsCapital(capitalInterface capital) {
    for (capitalInterface c : capitalList) {
      if (c.getName().equals(capital.getName())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsEdge(edgeInterface edge) {
    for (edgeInterface e : edgeList) {
      if (e.getStart().getName().equals(edge.getStart().getName())
          && e.getEnd().getName().equals(edge.getEnd().getName())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<String> findShortestPath(String from, String to)
      throws NoSuchElementException {

    List<String> capitals = graph.shortestPathData(from, to);

    return capitals;

  }

  @Override
  public List<String> findShortestPath(String from, String to,
      int minDistance, int maxDistance) throws NoSuchElementException {

    List<String> list = findShortestPath(from, to);
    double cost = graph.shortestPathCost(from, to);

    if (cost < minDistance || cost > maxDistance) {
      throw new NoSuchElementException("No path exists within this range");
    } else {
      return list;
    }
  }

}
