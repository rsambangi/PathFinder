import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * This class extends the BaseGraph data structure and the ShortestPathGraphInterface and contains
 * methods for computing the shortest path between two nodes, the cost of the path, and the minimum
 * spanning tree of the graph.
 */
public class ShortestPathGraphAE<NodeType, EdgeType extends Number> extends
    BaseGraphAE<NodeType, EdgeType> implements ShortestPathGraphInterface<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in it's node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referenced by the predecessor field (this field is
   * null within the SearchNode containing the starting node in it's node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references represent all of the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // check if the graph contains the start and end nodes
    if (!(containsNode(start)) || !(containsNode(end))) {
      // throw NoSuchElementException() if start and end node not in the graph
      throw new NoSuchElementException("Start or end nodes do not exist in the graph!");
    }
    // create a priority queue of search nodes
    PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<SearchNode>();

    // create a hashtable to store visited nodes
    Hashtable<NodeType, SearchNode> visitedNodes = new Hashtable<NodeType, SearchNode>();

    // add the first search node to the priority queue
    priorityQueue.add(new SearchNode(nodes.get(start), 0.0, null));

    // create a while loop to go through the priority queue
    while (!priorityQueue.isEmpty()) {
      // store the minimum node in the priority queue in a current search node object
      SearchNode currentSearchNode = priorityQueue.remove();
      // if the current search node's node is the end node then return
      // the current search node
      if (currentSearchNode.node.data == end) {
        return currentSearchNode;
      }
      // if loop to check if the visited nodes contains the current search node
      if (!(visitedNodes.containsValue(currentSearchNode))) {
        // put the current search node in the visited nodes hashtable
        visitedNodes.put(currentSearchNode.node.data, currentSearchNode);
        // go through all of the edges leaving from the current search node
        for (Edge edge : currentSearchNode.node.edgesLeaving) {
          // create a new cost object from the current search node's cost and the edge weight
          double newCost = currentSearchNode.cost + edge.data.doubleValue();
          // add the the edge's successor to the priority queue
          priorityQueue.add(new SearchNode(edge.successor, newCost, currentSearchNode));
        }
      }
    }
    // throw a NoSuchElementException if there is no path from start to end
    throw new NoSuchElementException("Path from start to end not found");
  }

  @Override
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // find the shortestPath with the provided start and end nodes
    SearchNode shortestPath = computeShortestPath(start, end);

    // created a path linked list object
    List<NodeType> path = new LinkedList<NodeType>();

    // add the shortest path to the linked list
    path.add(shortestPath.node.data);

    // while loop to go through the shortest path object
    while (shortestPath.predecessor != null) {
      // add the predecessor node data to the linked list
      path.add(shortestPath.predecessor.node.data);
      // change to the predecessor node to iterate through
      // the shortest path
      shortestPath = shortestPath.predecessor;
    }

    // create a new path object of ArrayList to flip the shortest path
    ArrayList<NodeType> newPath = new ArrayList<NodeType>();
    // for loop to flip the old path and add to the new path
    for (int i = path.size() - 1; i >= 0; i--) {
      // add nodes to the new path in reverse order
      newPath.add(path.get(i));
    }
    // return the new flipped path
    return newPath;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path from the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  @Override
  public double shortestPathCost(NodeType start, NodeType end) {
    // store the shortest path cost in an object
    SearchNode shortestPath = computeShortestPath(start, end);
    // return the shortest path cost
    return shortestPath.cost;
  }

}
