import java.io.FileNotFoundException;
import java.util.List;

public interface dotFileReaderInterface {
  public List<capitalInterface> readCapitalsFromDotFile(String filename)
      throws FileNotFoundException;

  public List<edgeInterface> readEdgesFromDotFile(String filename)
      throws FileNotFoundException;
}
