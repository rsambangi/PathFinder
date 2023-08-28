import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * Driver class for the program which launches the application
 * 
 * @author rsamb
 *
 */
public class Main extends Application {

  @Override
  public void start(final Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("PathFinder.fxml"));
    
    Scene scene = new Scene(root);
    stage.setTitle("PathFinder");
    stage.setScene(scene);
    stage.show();
  }
  

  public static void main(String[] args) {
    Application.launch();
  }
}
