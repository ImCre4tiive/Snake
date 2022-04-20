package Snake;

public class SnakeGameLoop implements Runnable {
    
    private Thread gameThread;
    private int FPS = 1;
    private boolean first_startup = true;
    // private Snake snake;
    private SnakeController controller;

    public SnakeGameLoop(SnakeController controller) {
        this.controller = controller;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        // snake = new Snake();
        
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; 
        double nextDrawTime = System.nanoTime() + drawInterval;

        
        while (gameThread != null) {
            
            update();

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

    public void update() {
        if (first_startup == true) {
            controller.start();
            first_startup = false;
        }
        else {
            controller.getSnake().move();
            controller.draw_snake(controller.getSnake());
        }
    }
}