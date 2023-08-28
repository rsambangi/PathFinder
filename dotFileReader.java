import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class dotFileReader implements dotFileReaderInterface {

  /**
   * Reads and generates list of capitals from the preloaded in graph file
   */
  @Override
  public List<capitalInterface> readCapitalsFromDotFile(String filename)
      throws FileNotFoundException {
    List<capitalInterface> capitals = new ArrayList<>();

    File file = new File(filename);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (!line.contains("->") && line.contains("label")) {
        int start = line.indexOf("\"");
        int end = line.lastIndexOf("\"");
        String capitalName = line.substring(start + 1, end);
        String capitalState = line.substring(0, start - 8).trim();
        Capital c = new Capital(capitalName, capitalState);
        capitals.add(c);
      }
    }
    scanner.close();

    return capitals;
  }

  /**
   * Reads and generates list of edges from preloaded graph file
   */
  @Override
  public List<edgeInterface> readEdgesFromDotFile(String filename) throws FileNotFoundException {
    List<edgeInterface> edges = new ArrayList<>();

    String capitalStartName = null;
    String capitalEndName = null;

    File file = new File(filename);
    Scanner scanner = new Scanner(file);

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.contains("->")) {
        int start = line.indexOf("\"");
        int end = line.lastIndexOf("\"");
        int miles = Integer.parseInt(line.substring(start + 1, end));
        int arrowIndex = line.indexOf("-");
        int bracketIndex = line.indexOf("[");
        String startingCapital = line.substring(0, arrowIndex - 1).trim();
        String endingCapital = line.substring(arrowIndex + 3, bracketIndex - 1).trim();
        List<capitalInterface> capitals = readCapitalsFromDotFile(filename);
        for (int i = 0; i < capitals.size(); i++) {
          if (capitals.get(i).getName().equals(startingCapital)) {
            capitalStartName = capitals.get(i).getName();
          }
          if (capitals.get(i).getName().equals(endingCapital)) {
            capitalEndName = capitals.get(i).getName();
          }

        }
        Capital s = new Capital(capitalStartName, "placeholder");
        Capital e = new Capital(capitalEndName, "placeholder");
        edges.add(new Edge(s, e, miles));
      }
    }


    scanner.close();
    return edges;
  }


}
