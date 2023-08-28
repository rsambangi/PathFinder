public class Capital implements capitalInterface {

  // State of the capital
  private String state;
  // Name of the capital
  private String name;

  public Capital(String name, String state) {
    this.name = name;
    this.state = state;
  }

  /**
   * Gets the name of the capital
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the name of the state of the capital
   */
  @Override
  public String getState() {
    return state;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

}

