package Snake;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.time.Duration;

public class SnakeApp extends Application {

    boolean running = true;
    public static void main(String[] args) {
        Application.launch(args);
        
    }


    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Snake.fxml"));
        Parent root = loader.load();  
        SnakeController controller = loader.getController(); //En instans av SnakeController.java
        Scene scene = new Scene(root);  
        
        
    
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) {

                switch(event.getCode()) {

                case W:
                    controller.moveUp();
                    break;
                case S:
                    controller.moveDown();
                    break;
                case A:
                    controller.moveLeft();
                    break;
                case D:
                    controller.moveRight();
                    break;
                default:
                    break;
                }
                
            }
            
        });
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();

        

    }
}


