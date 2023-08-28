
public class CapitalBD implements capitalInterface {
  private String state, name;

  public CapitalBD(String name, String state) {
    this.name = name;
    this.state = state;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getState() {
    return state;
  }
  
  @Override
  public int hashCode() {
    return name.hashCode();
  }

}
