package Snake;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SnakeGameTest {
    private SnakeController controller;
    private SnakeGame snakegame;
    private Snake snake;


    @BeforeEach
    public void setup() {
        controller = new SnakeController();
        snakegame = new SnakeGame();
        snakegame.setController(controller);
        snake = new Snake();
        snakegame.setSnake(snake);
        snakegame.getSnake().generate_test_snake();
        snake = snakegame.getSnake();
        snakegame.getSnake().generateTestApple(snake.getBodyPart(0).getX_Coordinate() + 2, snake.getBodyPart(0).getY_Coordinate());
        controller.setApple(snake.getApple());
    }

    @Test
    public void testUpdate() {
        //Eplet er 2 foran slangen, sjekker at alle verdier blir oppdatert på korrekt måte, og at funksjonen returnerer riktig string ved restart, nemlig "restart":
        snake.move();
        //Sjekker at Update() returnerer "ingenting" så lenge loopen IKKE skal restartes:
        assertEquals("ingenting", snakegame.testUpdate());
        assertEquals(false, snake.IsAppleEaten());
        snake.move();
        assertEquals(true, snake.IsAppleEaten());
        snakegame.testUpdate();
        //Sjekker om eplet har blitt spist og verdier er blitt oppdatert 
        assertEquals(10, snake.getApple().getX_Coordinate());
        assertEquals(10, snake.getApple().getY_Coordinate());
        assertEquals(10, controller.getApple().getX_Coordinate());
        assertEquals(10, controller.getApple().getY_Coordinate());
        assertEquals(5, snake.getSnake_body().size());
        assertEquals(1, snake.getScore());
        assertEquals(1, snakegame.getHighScore());
        assertEquals(false, snakegame.getGameStopped());
        assertEquals(false, controller.getGameStopped());

        
        //Sjekker at spillets fart øker ved at score = 2 og at Update() signaliserer til controller at farten skal økes ved å returnere "restart"
        snake.generateTestApple(snake.getBodyPart(0).getX_Coordinate() + 1, snake.getBodyPart(0).getY_Coordinate());
        snake.move();
        assertEquals("restart", snakegame.testUpdate());
        assertEquals(2, snakegame.getSpeedvalue());
        assertEquals(71, snakegame.getLoopdelay());


        //Sjekker at riktige verdier oppdateres ved kollisjon:
        snake.changeDirectonOfHead("UP");
        snake.move();
        snake.changeDirectonOfHead("LEFT");
        snake.move();
        snake.changeDirectonOfHead("DOWN");
        snake.move();
        assertEquals("restart", snakegame.testUpdate());
        assertEquals(true, snakegame.getGameStopped());
        assertEquals(true, controller.getGameStopped());
        assertEquals(75, snakegame.getLoopdelay());
        assertEquals(1, snakegame.getSpeedvalue());


    }

}
