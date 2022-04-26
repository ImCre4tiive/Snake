package Snake;

import java.io.File;
import java.util.List;

public class SnakeGame {    
    private SnakeController controller = new SnakeController();
    private Snake snake;
    private Apple apple;
    private FileHandler filehandler;
    private File file;
    private List<String> stats_from_file;
    private int loopdelay = 75;
    private int speedvalue = 1;
    private int highscore = 0;
    private boolean GameStopped = false;
    private boolean GamePaused = false;
    private boolean first_startup = true;

    public SnakeGame(SnakeController controller) {
        this.controller = controller;
    }

    public void generateSnakeAndApple() {
        snake = new Snake();
        apple = snake.getApple();
    }

    public String update() {
        filehandler = controller.getFileHandler();
        stats_from_file = controller.getStatsFromFile();
        file = controller.getFile();

        try {
            if (GameStopped == false && GamePaused == false) {
                if (snake.IsAppleEaten() == true) {
                    snake.generateApple();
                    UpdateApple(snake.getApple());
                    controller.draw_apple(apple);
                    snake.IncreaseLengthOfSnake();
                    snake.IncreaseScore();
                    updateHighScore();
                    controller.show_stats();
                    controller.getScoreBoardHandler().UpdateScoreBoard(controller.getScoreBoard(), stats_from_file, controller.getPlayerName(), this);
                    filehandler.WriteToFile(file, stats_from_file);
                    if ((snake.getScore() % 2) == 0 && loopdelay >= 30) {
                        speedvalue += 1;
                        loopdelay -= 4;
                        return "restart";
                    }
                }
                if (snake.CheckCollision() == true) {
                    UpdateGameStopped(true);
                    controller.initialize();
                    loopdelay = 75;
                    speedvalue = 1;
                    return "restart";
                }
            }
            return "do nothing :-)";
            
        } catch (IllegalArgumentException e) {
            UpdateGameStopped(true);
            controller.initialize();
            loopdelay = 75;
            speedvalue = 1;
            return "restart";
            
        }
    }

    public void updateHighScore() {
        if (snake.getScore() > highscore) {
            highscore = snake.getScore();
        }
    }

    public int getHighScore() {
        return highscore;
    }

    public void UpdateApple(Apple apple) {
        controller.setApple(apple);
        this.apple = apple;
    }

    public void UpdateGameStopped(boolean GameStopped) {
        controller.setGameStopped(GameStopped);
        this.GameStopped = GameStopped;
    }

    public void UpdateGamePaused(boolean GamePaused) {
        controller.setGamePaused(GamePaused);
        this.GamePaused = GamePaused;
    }

    public Snake getSnake() {
        return snake;
    }

    public Apple getApple() {
        return apple;
    }

    public FileHandler getFilehandler() {
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

    public void setGameStopped(boolean gameStopped) {
        GameStopped = gameStopped;
    }

    public void setGamePaused(boolean gamePaused) {
        GamePaused = gamePaused;
    }

    
    
}
