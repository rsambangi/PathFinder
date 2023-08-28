import java.util.List;

public interface ShortestPathGraphInterface<NodeType, EdgeType extends Number>
    extends GraphADT<NodeType, EdgeType> {
  // returns the shortest route list
  public List<NodeType> shortestPathData(NodeType start, NodeType end);

  // returns the number of miles for our shortest route
  public double shortestPathCost(NodeType start, NodeType end);
}
