package Snake;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SnakeGameTest {
    private SnakeController controller;
    private SnakeGame snakegame;
    private Snake snake;


    @BeforeEach
    public void setup() {
        snakegame = new SnakeGame();
        snake = new Snake();
        snakegame.setSnake(snake);
        snakegame.getSnake().generate_test_snake();
        snake = snakegame.getSnake();
        snakegame.getSnake().generateTestApple(snake.getBodyPart(0).getX_Coordinate() + 2, snake.getBodyPart(0).getY_Coordinate());
    }

    @Test
    public void testUpdate() {
        //Eplet er 2 foran slangen, sjekker at alle verdier blir oppdatert på korrekt måte, og at funksjonen returnerer riktig string ved restart, nemlig "restart":
        snake.move();
        //Sjekker at Update() returnerer den tomme strengen "" så lenge loopen IKKE skal restartes:
        assertEquals("", snakegame.update());
        assertEquals(false, snake.IsAppleEaten());
        snake.move();
        assertEquals(true, snake.IsAppleEaten());
        snakegame.update();
        //Sjekker om eplet har blitt spist og verdier er blitt oppdatert 
        assertNotEquals(24, snake.getApple().getX_Coordinate());
        assertNotEquals(25, snake.getApple().getY_Coordinate());
        assertEquals(5, snake.getSnake_body().size());
        assertEquals(1, snake.getScore());
        assertEquals(1, snakegame.getHighScore());
        assertEquals(false, snakegame.getGameStopped());

        
        //Sjekker at spillets fart øker ved at score = 2 og at Update() signaliserer til controller at eplet er spist ved å returnere "APPLEEATEN"
        snake.generateTestApple(snake.getBodyPart(0).getX_Coordinate() + 1, snake.getBodyPart(0).getY_Coordinate());
        snake.move();
        assertEquals("APPLEEATEN", snakegame.update());
        assertEquals(2, snakegame.getSpeedvalue());
        assertEquals(71, snakegame.getLoopdelay());


        //Sjekker at riktige verdier oppdateres ved kollisjon:
        snake.changeDirectonOfHead("UP");
        snake.move();
        snake.changeDirectonOfHead("LEFT");
        snake.move();
        snake.changeDirectonOfHead("DOWN");
        snake.move();
        assertEquals("COLLISION", snakegame.update());
        assertEquals(true, snakegame.getGameStopped());
        assertEquals(75, snakegame.getLoopdelay());
        assertEquals(1, snakegame.getSpeedvalue());

    }

}
