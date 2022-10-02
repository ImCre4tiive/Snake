package Snake;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class SnakeApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        // Set the title of the primary stage
        primaryStage.setTitle("Snake");
        
        // Load the FXML data int
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SnakeApp.class.getResource("Snake.fxml"));
        // Create a new scene from that FXML data
        Scene root = new Scene(loader.load());
        
        // Set the scene and display the stage
        primaryStage.setScene(root);
        primaryStage.show();
    }
        public static void main(String[] args) {
        Application.launch(args);
    }
}


