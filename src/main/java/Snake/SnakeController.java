package Snake;

//Vanlig java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
//FXML
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML private Label errortext;
    @FXML private GridPane scoreboard;

    private Snake snake;
    private Apple apple;
    private int highscore_controller;
    private boolean GameStopped = false;
    private File file;
    // private File file = new File(System.getProperty("user.home") + "/Desktop", "SnakeStats.txt");
    private boolean startup_approved = false;
    private boolean first_startup = true;
    private boolean existing_name = false;
    private boolean valid_language = false;
    private String playername;  
    private List<String> stats_from_file = new ArrayList<>();
    private int counter = 0;
    private List<Pane> bodypanes = new ArrayList<>();
    private int loopdelay = 75;
    private FileHandler filehandler = new FileHandler();
    
    

    @FXML
    public void initialize() {
        System.out.println(FileHandler.class.getResource("SnakeStats/").getFile() + "Stats.txt");
        errortext.setVisible(false);
        if (first_startup == true) {
            CheckSystemLanguage();
        }
        this.snake = new Snake();
        if (startup_approved == false) {
            filehandler.ReadFromFile(file, stats_from_file, playername, highscore_controller);
            // handleReadFromFile();
            if (ShowNameInputField() == false) {
                first_startup = false;
                initialize();
            }
            else {
                startup_approved = true;
                start();
                start_loop();
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: green;");
                grid.add(pane, 10, 10);
                // pane.relocate(10, 10);
            }
        }
        else {
            start();
        }
    }
    

    private void start() {
        snake.generate_snake();
        snake.generateApple();
        this.apple = snake.getApple();
        draw_snake(snake);
        draw_apple(apple);
        show_stats();
        UpdateScoreBoard();
    }

    public Snake getSnake() {
        return snake;
    }

    public void start_loop() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                
                snake.move();
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        try {
                            draw_snake(snake);
                            draw_apple(apple);
                            if (snake.IsAppleEaten() == true) {
                                snake.generateApple();
                                apple = snake.getApple();
                                draw_apple(apple);
                                snake.IncreaseLengthOfSnake();
                                snake.IncreaseScore();
                                show_stats();
                                UpdateScoreBoard();
                                // handleWriteToFile();
                                filehandler.WriteToFile(file, stats_from_file);
                                if ((snake.getScore() % 2) == 0 && loopdelay >= 30) {
                                    loopdelay -= 4;
                                    System.out.println("Nå er loopdelay = " + loopdelay + " ms");
                                    timer.cancel();
                                    start_loop();
                                }
                            }
                            if (snake.CheckCollision() == true) {
                                initialize();
                                snake.increaseRestartCount();
                                loopdelay = 80;
                                timer.cancel();
                                start_loop();
                            }
                            
                            
                            
                        } catch (IllegalArgumentException e) {
                            initialize();
                            snake.increaseRestartCount();
                            loopdelay = 80;
                            timer.cancel();
                            start_loop();
                            
                        }
                    }
                });
                }
        };
        timer.scheduleAtFixedRate(task, 0, loopdelay);
    }


    private void CheckSystemLanguage() {
        String language = System.getProperty("user.language");
        if (language.equals("en")) {
            System.out.println("Språk = " + language);
            file = new File(System.getProperty("user.home") + "/Desktop", "SnakeStats.txt");
        }
        else if (language.equals("no")) {
            System.out.println("Språk = " + language);
            file = new File(System.getProperty("user.home") + "/Skrivebord", "SnakeStats.txt");
        }
        else {
            DisplayErrorCode("Operativsystemets språk støttes ikke. Velg norsk eller engelsk for å spille.");
        }
    }



    private void DisplayErrorCode(String errormessage) {
        errortext.setText(errormessage);
        errortext.setVisible(true);
    }

    @FXML 
    private boolean ShowNameInputField() {
        TextInputDialog popup = new TextInputDialog();
        if (first_startup == true) {
            popup.setTitle("Registrering");
            popup.setHeaderText("Registrering av navn");
            popup.setContentText("Skriv inn navnet ditt: ");
        }
        else if (existing_name == true && first_startup == false) {
            popup.setTitle("Registrering");
            popup.setHeaderText("Registrering av navn");
            popup.setContentText("Det navnet finnes allerede i statistikken, skriv inn et ekstra kjennemerke: ");
            existing_name = false;
        }
        else {
            popup.setTitle("Registrering");
            popup.setHeaderText("Registrering av navn");
            popup.setContentText("Ugyldig navn, prøv på nytt: ");
        }
        
        String input = popup.showAndWait().get();

        if (ValidNameInput(input) == true && TestExistingName(input) == false) {
            snake.setPlayerName(input);
            this.playername = input;
            popup.close();
            return true;
        }
        else if (ValidNameInput(input) == false && TestExistingName(input) == true) {
            existing_name = true;
            return false;
        }
        else if (ValidNameInput(input) == true && TestExistingName(input) == true) {
            existing_name = true;
            return false;
        }
        else {
            existing_name = false;
            return false;
        }
    }

    

    private boolean ValidNameInput(String name) {
        return name.matches("^([a-åA-Å]+\s)*[a-åA-Å]+$");
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
            bodypanes.add(pane);
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
        if (GameStopped == false) {
            try {
                this.snake.handleInput(event);
                
                // if (snake.IsAppleEaten() == true) {
                //     snake.setApple(new Apple());
                //     this.apple = snake.getApple();
                //     draw_apple(apple);
                //     snake.IncreaseLengthOfSnake();
                //     snake.IncreaseScore();
                //     show_stats();
                //     UpdateScoreBoard();
                //     handleWriteToFile();
                // }
                draw_snake(snake);
                draw_apple(apple);

            } catch (IllegalArgumentException e) {
                initialize();
                snake.increaseRestartCount();
                }
            }
            
        
        // else {
        //     initialize();
        //     snake.increaseRestartCount();
        //     GameStopped = false; 
        // }

        
        // grid.requestFocus();
    }

    public void ShowPauseMenu() {

    }


    
    // public void handleWriteToFile() {
    //     try (FileWriter filewriter = new FileWriter(file, false)) {
    //         for (String string : stats_from_file) {
    //             filewriter.write(string + "\n");
    //         }
    //     }

    //     catch (IOException IOe) {
    //         System.out.println("WriteToFile: " + IOe);
    //     }     
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

    private boolean TestExistingName(String name) {
        for (String string : stats_from_file) {
            if (string.contains(name)) {
                return true;
            }
        }
        return false;
    }

    // public void handleReadFromFile() {

    //     try (Scanner scanner = new Scanner(this.file)) {
    //         System.out.println("This.file er nå: " + this.file);
            
    //         while (scanner.hasNextLine()) {
    //             String nextline = scanner.nextLine();
                
    //             try {
    //                 String[] line = nextline.split(",");
    //                 if (line.length != 2 || !(line[0] instanceof String) || !(Integer.valueOf(line[1]) instanceof Integer) || TestExistingName(line[0])) {
    //                     throw new Exception();
    //                 }
                    
    //             } catch (Exception e) {
    //                 System.out.println(e);
    //                 continue;
    //             }
    //             stats_from_file.add(nextline);
    //         }
    //         if (playername != null) {
    //             stats_from_file.add(playername + "," + highscore_controller);
    //         }

    //         Collections.sort(stats_from_file, new ScoreboardComparator());
    //     }
    //     catch (IOException e) {
    //         // System.out.println("Dette skjedde: " + e.getMessage());
    //         System.out.println("Readfromfile: " + e.toString());
            
    //     }
    // }
}


