package Snake;

public class SnakeGameLoop implements Runnable {
    
    private Thread gameThread;
    private int FPS = 5;
    // private Snake snake;
    // private SnakeController controller = new SnakeController();

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
            // controller.print();
            System.out.println("Potet");

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