package Snake;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame {    
    private SnakeController controller = new SnakeController();
    private Snake snake;
    private Apple apple;
    private SnakeFileHandler filehandler = new SnakeFileHandler();
    private File file;
    private List<String> stats_from_file = new ArrayList<>();
    private int loopdelay = 75;
    private int speedvalue = 1;
    private int highscore = 0;
    private boolean GameStopped = false;
    private boolean GamePaused = false;

    public SnakeGame(SnakeController controller) {
        this.controller = controller;
    }

    public SnakeGame() {
    }

    public void setController(SnakeController controller) {
        this.controller = controller;
    }

    public void generateSnakeAndApple() {
        snake = new Snake();
        apple = snake.getApple();
    }


    public void setFileAndRead() {
        filehandler.setFile(getFile("SnakeStats"));
        file = filehandler.getFile();
        filehandler.ReadFromFile(file, stats_from_file, controller.getPlayerName(), highscore);
    }

    public File getFile(String filename) {
        return new File(Paths.get(".").toAbsolutePath().normalize().toString(), filename + ".txt");
    }

    public String update() {
        try {
            if (GameStopped == false && GamePaused == false) {
                if (snake.IsAppleEaten() == true) {
                    snake.generateApple();
                    UpdateApple(snake.getApple());
                    snake.IncreaseLengthOfSnake();
                    snake.IncreaseScore();
                    updateHighScore();
                    // filehandler.WriteToFile(file, stats_from_file);
                    if ((snake.getScore() % 2) == 0 && loopdelay >= 30) {
                        speedvalue += 1;
                        loopdelay -= 4;
                        
                    }
                    return "APPLEEATEN";
                }
                if (snake.CheckCollision() == true) {
                    GameStopped = true;
                    loopdelay = 75;
                    speedvalue = 1;
                    return "COLLISION";
                }
            }
            return "";
            
        } catch (Exception e) {
            GameStopped = true;
            loopdelay = 75;
            speedvalue = 1;
            return "RESTART";
        }
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void updateHighScore() {
        if (snake.getScore() > highscore) {
            highscore = snake.getScore();
        }
    }

    public int getHighScore() {
        return highscore;
    }

    public void setHighScore(int highscore) {
        this.highscore = highscore;
    }

    public void UpdateApple(Apple apple) {
        controller.setApple(apple);
        this.apple = apple;
    }

    // public void UpdateGameStopped(boolean GameStopped) {
    //     controller.setGameStopped(GameStopped);
    //     this.GameStopped = GameStopped;
    // }

    // public void UpdateGamePaused(boolean GamePaused) {
    //     controller.setGamePaused(GamePaused);
    //     this.GamePaused = GamePaused;
    // }

    public Snake getSnake() {
        return snake;
    }

    public Apple getApple() {
        return apple;
    }

    public SnakeFileHandler getFilehandler() {
        return filehandler;
    }

    public int getLoopdelay() {
        return loopdelay;
    }

    public void setLoopdelay(int loopdelay) {
        this.loopdelay = loopdelay;
    }

    public int getSpeedvalue() {
        return speedvalue;
    }

    public void setSpeedvalue(int speedvalue) {
        this.speedvalue = speedvalue;
    }

    public boolean getGameStopped() {
        return GameStopped;
    }

    public void setGameStopped(boolean gameStopped) {
        GameStopped = gameStopped;
    }

    public boolean getGamePaused() {
        return GamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        GamePaused = gamePaused;
    }

    public List<String> getStatsFromFile() {
        return stats_from_file;
    }

    public File getFile() {
        return file;
    }


}
