package Snake;

//Vanlig java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

import javafx.application.Platform;
//FXML
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SnakeController {

    @FXML private GridPane grid;
    @FXML private Label highscore;
    @FXML private Label score;
    @FXML private Label length;
    @FXML private TextField namefield;
    @FXML private Button okbutton;
    @FXML private GridPane scoreboard;

    private Snake snake;
    private Apple apple;
    private int highscore_controller;
    private boolean stop_game = false;
    private File file = new File("C:\\Users\\jmnb1\\Desktop\\Snake", "SnakeStats.txt");;
    private int restartcount = 0;
    private String playername;
    private List<String> stats_from_file = new ArrayList<>();
    


    @FXML
    public void initialize() {
        this.snake = new Snake();
        if (restartcount == 0) {
            if (ShowNameInputField() == false) {
                initialize();
                System.out.println("Restarter");
            }
            else {
                restartcount += 1;
            }
        }
        snake.generate_snake();
        snake.generateAppleWithValidLocation();
        this.apple = snake.getApple();
        draw_snake(snake);
        draw_apple(apple);
        show_stats();
        handleReadFromFile();
        UpdateScoreBoard();
    }

    @FXML 
    private boolean ShowNameInputField() {
        TextInputDialog popup = new TextInputDialog();
        popup.setContentText("Vennligst skriv inn navnet ditt: ");
        Optional<String> input = popup.showAndWait();
        String stringinput = input.get();

        if (ValidNameInput(stringinput) == true) {
            snake.setPlayerName(stringinput);
            this.playername = stringinput;
            System.out.println(stringinput);
            popup.close();
            return true;
        }
        else {return false;}
        
    }

    private boolean ValidNameInput(String name) {
        return name.matches("[a-zA-Z]+");
    }

    @FXML
    public void draw_snake(Snake snake) {
        // Tømmer grid-et men beholder rutenettet (som er element med indeks 0 i grid.getChildren()-listen)
        Node node = grid.getChildren().get(0);
        grid.getChildren().clear();
        grid.getChildren().add(0,node);

        for (BodyPart bodypart : snake.getSnake_body()) {
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: green;");
            grid.add(pane, bodypart.getX_Coordinate(), bodypart.getY_Coordinate());
        }
    }

    @FXML
    public void draw_apple(Apple apple) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: red;");
        grid.add(pane, apple.getX_Coordinate(), apple.getY_Coordinate());
    }

    public void updateHighScore() {
        if (snake.getScore() > highscore_controller) {
            highscore_controller = snake.getScore();
        }
    }

    @FXML
    public void show_stats() {
        updateHighScore();
        highscore.setText("Highscore: " + String.valueOf(highscore_controller));
        score.setText("Score: " + String.valueOf(snake.getScore()));
        length.setText("Length: " + String.valueOf(snake.getSnake_body().size()));
    }



    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (this.stop_game == false) {
            try {
                this.snake.handleInput(event);
                if (snake.IsAppleEaten() == true) {
                    snake.setApple(new Apple());
                    this.apple = snake.getApple();
                    draw_apple(apple);
                    snake.IncreaseLengthOfSnake();
                    snake.IncreaseScore();
                    show_stats();
                    UpdateScoreBoard();
                    handleWriteToFile();
                }
                draw_snake(snake);
                draw_apple(apple);

            } catch (IllegalArgumentException e) {
                initialize();
                snake.increaseRestartCount();
                }
            }
            
        
        else {
            initialize();
            snake.increaseRestartCount();
            this.stop_game = false; 
        }

        if (snake.CheckCollision() == true) {
            initialize();
            snake.increaseRestartCount();
        }
        // grid.requestFocus();
    }

    @FXML
    public void handleNameInput(ActionEvent event) {
        snake.setPlayerName(namefield.getText());
        handleWriteToFile();
        handleReadFromFile();
        grid.requestFocus();
    }

    
    
    
    
    public void handleWriteToFile() {
        try (FileWriter filewriter = new FileWriter(file, true)) {
            for (String string : stats_from_file) {
                System.out.println("Skriver!");
                filewriter.write(string + "\n");
            }
        }
            // temporaryFile.createNewFile();
            // try (Scanner scanner = new Scanner(this.file)) {
            //     if (this.file.length() == 0) {
            //         filewriter.write(snake.getPlayerName() + "," + highscore_controller + "\n");
            //     }
            //     else {
            //         while (scanner.hasNextLine()) {
            //             String nextline = scanner.nextLine();
            //             if (nextline.contains(this.playername)) continue;
            //             filewriter.write(nextline + "\n");
            //             // System.out.println("Skrev " + nextline + " inn i " + temporaryFile);
            //         }
            //         filewriter.write(this.playername + "," + highscore_controller + "\n");
                    
            //     }
                
            // }
        catch (IOException IOe) {
            // System.out.println("Dette skjedde: " + IOe.getMessage());
            System.out.println("WriteToFile: " + IOe);
        }

            
    }
        
        // File fil2 = this.file;
        
        // fil2.delete();
        // System.out.println("this.file deleted");
        
        
        //Dette funker:
        // try (FileWriter filewriter = new FileWriter(this.file, true)) {
        //     filewriter.write(snake.getPlayerName() + "," + highscore_controller + "\n");
        // }  

    private void UpdateScoreBoard() {
        boolean removeLine = false;
        String lineToBeRemoved = "";

        for (String line : stats_from_file) {
            if (line.contains(playername)) {
                lineToBeRemoved = line;
                removeLine = true;
            }
        }
        if (removeLine == true) {
            stats_from_file.remove(lineToBeRemoved);
        }
        stats_from_file.add(playername + "," + highscore_controller);
        Collections.sort(stats_from_file, new ScoreboardComparator());
        DisplayScoreBoardContent();
    }

    private void DisplayScoreBoardContent() {
        //Clear-er scoreboardet
        Node node = scoreboard.getChildren().get(0);
        scoreboard.getChildren().clear();
        scoreboard.getChildren().add(0,node);

        System.out.println(stats_from_file);
        for (int i = 0; i < stats_from_file.size(); i++) {
            if (i > 9) {
                break;
            }

            String[] text = stats_from_file.get(i).split(",");
            for (int j = 0; j < 2; j++) {
                Label label = new Label(text[j]);
                label.setFont(new Font(20));
                label.setTextAlignment(TextAlignment.CENTER);
                scoreboard.add(label, j, i);
            }
        }
    }

    public void handleReadFromFile() {

        try (Scanner scanner = new Scanner(this.file)) {
            
            while (scanner.hasNextLine()) {
                String nextline = scanner.nextLine();
                
                try {
                    String[] line = nextline.split(",");
                    if (line.length != 2 || !(line[0] instanceof String) || !(Integer.valueOf(line[1]) instanceof Integer)) {
                        throw new Exception();
                    }
                    
                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
                stats_from_file.add(nextline);
            }
            stats_from_file.add(playername + "," + highscore_controller);

            Collections.sort(stats_from_file, new ScoreboardComparator());
        }
        catch (IOException e) {
            // System.out.println("Dette skjedde: " + e.getMessage());
            System.out.println("Readfromfile: " + e.toString());
            
        }
    }
}


