package Snake;

public class SnakeGameLoop implements Runnable {
    
    private Thread gameThread;
    private int FPS = 5;
    private Snake snake;
    private SnakeController controller;

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        snake = new Snake();
        controller = new SnakeController();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //Oppdaterer hvert 0.01666 sekund
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null) {

            snake.move();
            controller.draw_snake(snake);

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}