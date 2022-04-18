package Snake;

//Vanlig java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
    @FXML private TextField namefield;
    @FXML private Button okbutton;
    @FXML private GridPane scoreboard;

    private SnakeGameLoop loop = new SnakeGameLoop();
    private Snake snake;
    private Apple apple;
    private int highscore_controller;
    private boolean stop_game = false;
    private File file;
    // private File file = new File(System.getProperty("user.home") + "/Desktop", "SnakeStats.txt");
    private boolean startup_approved = false;
    private boolean first_startup = true;
    private boolean existing_name = false;
    private boolean valid_language = false;
    private String playername;  
    private List<String> stats_from_file = new ArrayList<>();


    @FXML
    public void initialize() {
        CheckSystemLanguage();
        // System.out.println(System.getProperty("user.language"));
        this.snake = new Snake();
        // if (valid_language == false) {
        //     if (ShowWindowsLanguageBox() == false) {
        //         initialize();
        //     }
        //     else {
        //         valid_language = true;
        //     }
        // }
        
        if (startup_approved == false) {
            handleReadFromFile();
            if (ShowNameInputField() == false) {
                first_startup = false;
                initialize();
            }
            else {
                startup_approved = true;
                start();
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

    // @FXML
    // private boolean ShowWindowsLanguageBox() {
    //     TextInputDialog popup = new TextInputDialog();
    //     popup.setTitle("Registrering av Windows-versjon");
    //     popup.setHeaderText("For å lagre statistikk fra spillet på riktig sted må du oppgi om du har Windows på 'norsk' eller 'engelsk'");
    //     popup.setContentText("Vennligst oppgi Windows-språk:");
    //     String input = popup.showAndWait().get();
    //     if (input.toLowerCase().equals("norsk")) {
    //         file = new File(System.getProperty("user.home") + "/Skrivebord", "SnakeStats.txt");
    //         return true;
    //     }
    //     else if (input.toLowerCase().equals("engelsk")) {
    //         file = new File(System.getProperty("user.home") + "/Desktop", "SnakeStats.txt");
    //         return true;
    //     }
    //     else {
    //         return false;
    //     }

    // }

    private void CheckSystemLanguage() {
        String language = System.getProperty("user.language");
        if (language.equals("en")) {
            file = new File(System.getProperty("user.home") + "/Desktop", "SnakeStats.txt");
        }
        else if (language.equals("no")) {
            file = new File(System.getProperty("user.home") + "/Skrivebord", "SnakeStats.txt");
        }
        else {
            DisplayErrorCode("Operativsystemets språk støttes ikke. Velg norsk eller engelsk for å spille.");
        }
        System.out.println("This.file er nå: " + file);
    }

    private void DisplayErrorCode(String errormessage) {
        Text text = new Text(500, 500, errormessage);
        text.setVisible(value);
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

    private boolean TestExistingName(String name) {
        for (String string : stats_from_file) {
            if (string.contains(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean ValidNameInput(String name) {
        return name.matches("^([a-zA-Z]+\s)*[a-zA-Z]+$");
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
        try (FileWriter filewriter = new FileWriter(file, false)) {
            for (String string : stats_from_file) {
                System.out.println("Skriver!");
                filewriter.write(string + "\n");
            }
        }

        catch (IOException IOe) {
            // System.out.println("Dette skjedde: " + IOe.getMessage());
            System.out.println("WriteToFile: " + IOe);
        }     
    }

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
                    if (line.length != 2 || !(line[0] instanceof String) || !(Integer.valueOf(line[1]) instanceof Integer) || TestExistingName(line[0])) {
                        throw new Exception();
                    }
                    
                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
                stats_from_file.add(nextline);
            }
            if (playername != null) {
                stats_from_file.add(playername + "," + highscore_controller);
            }

            Collections.sort(stats_from_file, new ScoreboardComparator());
        }
        catch (IOException e) {
            // System.out.println("Dette skjedde: " + e.getMessage());
            System.out.println("Readfromfile: " + e.toString());
            
        }
    }
}


