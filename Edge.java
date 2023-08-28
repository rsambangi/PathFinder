
public class Edge implements edgeInterface {
  // Starting capital
  private Capital start;
  // Ending capital
  private Capital end;
  // Number of miles
  private int miles;

  public Edge(Capital start, Capital end, int miles) {
    this.start = start;
    this.end = end;
    this.miles = miles;
  }

  /**
   * Gets the starting capital
   */
  @Override
  public Capital getStart() {
    return start;
  }

  /**
   * Gets the ending capital
   */
  @Override
  public Capital getEnd() {
    return end;
  }

  /**
   * Gets the number of miles
   */
  @Override
  public int getMiles() {
    return miles;
  }


}
