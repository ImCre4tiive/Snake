package Snake;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//FXML
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SnakeController {
    @FXML private Button resume;
    @FXML private GridPane grid;
    @FXML private Label highscore;
    @FXML private Label score;
    @FXML private Label length;
    @FXML private Label speed;
    @FXML private Label errortext;
    @FXML private Label gameover;
    @FXML private Label gamepaused;
    @FXML private Label snake_text;
    @FXML private Label use_text;
    @FXML private Label to_move_text;
    @FXML private Label press_text;
    @FXML private Label to_pause_text;
    @FXML private Pane letterW;
    @FXML private Pane letterA;
    @FXML private Pane letterS;
    @FXML private Pane letterD;
    @FXML private Pane letterP;
    @FXML private GridPane scoreboard;
    @FXML private Button startgame;
    @FXML private Button playagain;
    @FXML private Button quitgame;
    @FXML private Button pausegame;
    
    
    private SnakeGame snakegame;
    private Snake snake;
    private Apple apple;
    private File file;
    private boolean StartMenuPassed = false;
    private boolean startup_approved = false;
    private boolean first_startup = true;
    private boolean existing_name = false;
    private boolean name_field_passed = false;
    private boolean GameStopped = false;
    private boolean GamePaused = false;
    private String playername;  
    private List<String> stats_from_file = new ArrayList<>();
    private List<Pane> bodypanes = new ArrayList<>();
    private FileHandler filehandler = new FileHandler();
    private ScoreBoardHandler scoreboardhandler = new ScoreBoardHandler();
    

    @FXML
    public void initialize() {
        if (first_startup == true) {
            snakegame = new SnakeGame(this);
            CheckSystemLanguage();
        }
        HideOverlayElements();
        
        snakegame.generateSnakeAndApple();
        snake = snakegame.getSnake();
        apple = snakegame.getApple();

        try {
            if (startup_approved == false) {
                filehandler.ReadFromFile(file, stats_from_file, playername, snakegame.getHighScore());
                show_stats();
                if (name_field_passed == false) {
                    if (ShowNameInputField() == false) {
                        first_startup = false;
                        initialize();
                    }
                    else {
                        name_field_passed = true;
                        initialize();
                        scoreboardhandler.UpdateScoreBoard(scoreboard, stats_from_file, playername, snakegame);
                    }
                }
                else {
                    if (StartMenuPassed == false) {
                        ShowStartMenu();
                    }
                    else {
                        startup_approved = true;
                        start();
                        start_loop();
                    }
                }
            }
            else {
                start();
            }
        }
        catch (Exception e) { 
            System.out.println("Du må skrive inn et navn for å spille!");
            Platform.exit();
        }
    }
    
    public void start() {
        snake.generate_snake();
        snake.generateApple();
        this.apple = snake.getApple();
        draw_snake(snake);
        draw_apple(apple);
        show_stats();
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
                            if (GameStopped == false && GamePaused == false) {
                                draw_snake(snake);
                                draw_apple(apple);
                                if (snakegame.update() == "restart") {
                                    timer.cancel();
                                    start_loop();
                                    
                                }
                            }
                            else if (GamePaused == true) {
                                ShowPauseMenu();
                                timer.cancel();
                            }
                            
                            else {
                                ShowGameOverMenu();
                            }
                        }
                        catch (Exception e) {
                            GameStopped = true;
                            snakegame.setGameStopped(true);
                            initialize();
                            snakegame.setLoopdelay(75);
                            snakegame.setSpeedvalue(1);
                            timer.cancel();
                            start_loop();
                        }
                        
                    }
                });
                }
        };
        timer.scheduleAtFixedRate(task, 0, snakegame.getLoopdelay());
    }

//==============================================================================================================================================================================
    //Menyer og overlay
    @FXML
    public void HideOverlayElements() {
        snake_text.setVisible(false);
        use_text.setVisible(false);
        to_move_text.setVisible(false);
        press_text.setVisible(false);
        to_pause_text.setVisible(false);
        letterW.setVisible(false);
        letterA.setVisible(false);
        letterS.setVisible(false);
        letterD.setVisible(false);
        letterP.setVisible(false);
        startgame.setVisible(false);
        errortext.setVisible(false);
        gameover.setVisible(false);
        playagain.setVisible(false);
        quitgame.setVisible(false);
        gamepaused.setVisible(false);
        resume.setVisible(false);
    }

    @FXML
    public void ShowStartMenu() {
        snake_text.setVisible(true);
        use_text.setVisible(true);
        to_move_text.setVisible(true);
        press_text.setVisible(true);
        to_pause_text.setVisible(true);
        letterW.setVisible(true);
        letterA.setVisible(true);
        letterS.setVisible(true);
        letterD.setVisible(true);
        letterP.setVisible(true);
        startgame.setVisible(true);
    }
    
    @FXML
    public void ShowGameOverMenu() {
        gameover.setVisible(true);
        playagain.setVisible(true);
        quitgame.setVisible(true);
    }

    @FXML
    public void ShowPauseMenu() {
        gamepaused.setVisible(true);
        resume.setVisible(true);
    }

    @FXML
    public void handleStartGameClick() {
        StartMenuPassed = true;
        initialize();
    }

    @FXML
    public void handlePlayAgainClick() {
        initialize();
        GameStopped = false;
        snakegame.setGameStopped(false);
        grid.requestFocus();
    }

    @FXML
    public void handleQuitGameClick() {
        Platform.exit();
        filehandler.WriteToFile(file, stats_from_file);
    }

    @FXML
    public void handlePauseClick() {
        if (GameStopped == false) {
            GamePaused = true;
            snakegame.setGamePaused(true);
            grid.requestFocus();
        }
        
    }

    @FXML
    public void handleResumeClick() {
        GamePaused = false;
        snakegame.setGamePaused(false);
        gamepaused.setVisible(false);
        resume.setVisible(false);
        start_loop();
        grid.requestFocus();
    }

    public void CheckSystemLanguage() {
        String language = System.getProperty("user.language");
        if (language.equals("en")) {
            // System.out.println("Språk = " + language);
            file = new File(System.getProperty("user.home") + "/Desktop", "SnakeStats.txt");
        }
        else if (language.equals("no")) {
            // System.out.println("Språk = " + language);
            file = new File(System.getProperty("user.home") + "/Skrivebord", "SnakeStats.txt");
        }
        else {
            DisplayErrorCode("Operativsystemets språk støttes ikke, filhåndtering vil ikke fungere.");
        }
    }

    public void DisplayErrorCode(String errormessage) {
        errortext.setText(errormessage);
        errortext.setVisible(true);
    }

    @FXML 
    public boolean ShowNameInputField() {
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

    public boolean ValidNameInput(String name) {
        return name.matches("^([a-zA-ZæøåÆØÅ]+\s)*[a-zA-ZæøåÆØÅ]+$");
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

    @FXML
    public void show_stats() {
        highscore.setText("Highscore: " + String.valueOf(snakegame.getHighScore()));
        score.setText("Score: " + String.valueOf(snake.getScore()));
        length.setText("Length: " + String.valueOf(snake.getSnake_body().size()));
        if (snakegame.getSpeedvalue() >= 11) {
            speed.setText("MAX SPEED!");
        }
        else {
            speed.setText("Speed: " + String.valueOf(snakegame.getSpeedvalue()));
        }
        
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (GameStopped == false) {
            try {
                switch(event.getCode()) {
                    case P:
                        if (GamePaused == false) {
                            GamePaused = true;
                            snakegame.setGamePaused(true);
                            grid.requestFocus();
                        }
                        else {
                            GamePaused = false;
                            snakegame.setGamePaused(false);
                            gamepaused.setVisible(false);
                            resume.setVisible(false);
                            start_loop();
                            grid.requestFocus();
                        }
                    default:
                        break;
                }
                if (GamePaused == false) {
                    snake.handleInput(event);
                }
                draw_snake(snake);
                draw_apple(apple);
            } catch (IllegalArgumentException e) {
                initialize();        
            }
        }
    }

    // public void UpdateScoreBoard() {
    //     boolean removeLine = false;
    //     String lineToBeRemoved = "";

    //     for (String line : stats_from_file) {
    //         if (line.contains(playername)) {
    //             lineToBeRemoved = line;
    //             removeLine = true;
    //         }
    //     }
    //     if (removeLine == true) {
    //         stats_from_file.remove(lineToBeRemoved);
    //     }
    //     stats_from_file.add(playername + "," + snakegame.getHighScore());
    //     Collections.sort(stats_from_file, new ScoreboardComparator());
    //     DisplayScoreBoardContent();
    // }

    // public void DisplayScoreBoardContent() {
    //     //Clear-er scoreboardet
    //     Node node = scoreboard.getChildren().get(0);
    //     scoreboard.getChildren().clear();
    //     scoreboard.getChildren().add(0,node);

    //     for (int i = 0; i < stats_from_file.size(); i++) {
    //         if (i > 9) {
    //             break;
    //         }

    //         String[] text = stats_from_file.get(i).split(",");
    //         for (int j = 0; j < 2; j++) {
    //             Label label = new Label(text[j]);
    //             label.setFont(new Font(20));
    //             label.setTextAlignment(TextAlignment.CENTER);
    //             scoreboard.add(label, j, i);
    //         }
    //     }
    // }

    public boolean TestExistingName(String name) {
        for (String string : stats_from_file) {
            if (string.split(",")[0].equals(name)) {
                return true;
            }
        }
        return false;
    }


    public FileHandler getFileHandler() {
        return filehandler;
    }

    public File getFile() {
        return file;
    }

    public List<String> getStatsFromFile() {
        return stats_from_file;
    }

    public void setGameStopped(boolean GameStopped) {
        this.GameStopped = GameStopped;
    }

    public void setGamePaused(boolean GamePaused) {
        this.GamePaused = GamePaused;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public GridPane getScoreBoard() {
        return scoreboard;
    }

    public void setScoreBoard(GridPane scoreboard) {
        this.scoreboard = scoreboard;
    }

    public ScoreBoardHandler getScoreBoardHandler() {
        return scoreboardhandler;
    }

    public String getPlayerName() {
        return playername;
    }

}


