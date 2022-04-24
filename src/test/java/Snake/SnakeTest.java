package Snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import Snake.Apple;
import Snake.BodyPart;
import Snake.Snake;

public class SnakeTest {
    
    private Snake snake;
    private Apple apple;
    
    @BeforeEach
    public void setup() {
        snake = new Snake();
        snake.generate_test_snake();
        snake.generateTestApple(snake.getBodyPart(0).getX_Coordinate() + 2, snake.getBodyPart(0).getY_Coordinate());
        apple = snake.getApple();
    }

    @Test
    public void testChangeToStringOfBodyParts() {

    }
    @Test
    public void testGenerateSnake() {
        System.out.println(snake.getSnake_body());
        //Sjekke at toString1 er gjeldende toString etter generering/oppretting:
        for (BodyPart bodypart : snake.getSnake_body()) {
            assertEquals("toString1", bodypart.getToString());
        }
        snake.changeToStringOfBodyParts("toString2");
        for (BodyPart bodypart : snake.getSnake_body()) {
            assertEquals("toString2", bodypart.getToString());
        }
        snake.changeToStringOfBodyParts("toString3");
        for (BodyPart bodypart : snake.getSnake_body()) {
            assertEquals("toString3", bodypart.getToString());
        }
        assertThrows(IllegalArgumentException.class, () -> snake.changeToStringOfBodyParts("toString4"));

        //Sjekke at snake-body blir opprettet på korrekt posisjon
        snake.changeToStringOfBodyParts("toString1");
        assertEquals("[(X = 22, Y = 25, direction = RIGHT, ishead = true), (X = 21, Y = 25, direction = RIGHT, ishead = false), (X = 20, Y = 25, direction = RIGHT, ishead = false)]", String.valueOf(snake.getSnake_body()));

        //Sjekker om hodet har ishead = true og at resten av kroppen ikke har det
        assertTrue(snake.getBodyPart(0).getIshead());
        
        int counter = 0;
        for (BodyPart bodypart : snake.getSnake_body()) {
            if (bodypart.getIshead() != true) {
                counter += 1;
            }
        }
        assertEquals(counter, snake.getSnake_body().size()-1);

    }

    @Test
    public void testMove() {
        snake.changeToStringOfBodyParts("toString2");
        assertEquals("[(X=22,Y=25), (X=21,Y=25), (X=20,Y=25)]", String.valueOf(snake.getSnake_body()));
        snake.move();
        assertEquals("[(X=23,Y=25), (X=22,Y=25), (X=21,Y=25)]", String.valueOf(snake.getSnake_body()));
        snake.move();
        snake.move();
        assertEquals("[(X=25,Y=25), (X=24,Y=25), (X=23,Y=25)]", String.valueOf(snake.getSnake_body()));
    }

    @Test
    public void testChangeDirectionOfHeadAndBody() {
        snake.changeToStringOfBodyParts("toString1");
        assertEquals("RIGHT", snake.getBodyPart(0).getDirection());
        snake.changeDirectonOfHead("UP");
        assertEquals("UP", snake.getBodyPart(0).getDirection());
        //Sjekke at man kun kan snu seg 90°
        snake.changeDirectonOfHead("DOWN");
        assertEquals("UP", snake.getBodyPart(0).getDirection());
        
        snake.move();
        // snake.move();
        assertEquals("[(X = 22, Y = 24, direction = UP, ishead = true), (X = 22, Y = 25, direction = UP, ishead = false), (X = 21, Y = 25, direction = RIGHT, ishead = false)]", String.valueOf(snake.getSnake_body()));
        snake.move();
        System.out.println(snake.getSnake_body());
        assertEquals("[(X = 22, Y = 23, direction = UP, ishead = true), (X = 22, Y = 24, direction = UP, ishead = false), (X = 22, Y = 25, direction = UP, ishead = false)]", String.valueOf(snake.getSnake_body()));
    }

    @Test
    public void testIsAppleEaten() {
        //Eplet er 2 "blokker" foran slangen - sjekker om det blir spist:
        assertFalse(snake.IsAppleEaten());
        snake.move();
        snake.move();
        assertTrue(snake.IsAppleEaten());
    }

    @Test
    public void testCheckCollision() {
        //Gjør slangen litt lengre for at den skal kunne krasje i seg selv, og tester dette: 
        snake.IncreaseLengthOfSnake();
        assertFalse(snake.CheckCollision());
        snake.changeDirectonOfHead("UP");
        snake.move();
        snake.changeDirectonOfHead("LEFT");
        snake.move();
        snake.changeDirectonOfHead("DOWN");
        snake.move();
        assertTrue(snake.CheckCollision());

        //
        snake.getSnake_body().clear();
        snake.generate_test_snake_at_border();
        assertFalse(snake.CheckCollision());
        snake.move();
        snake.move();
        //Hodet har nå X-koordinat = 50 og burde derfor være utenfor banen:
        assertTrue(snake.CheckCollision());
    }

    @Test
    public void testIncreaseLengthOfSnake() {
        assertEquals(3, snake.getSnake_body().size());
        snake.IncreaseLengthOfSnake();
        assertEquals(5, snake.getSnake_body().size());
        for (BodyPart bodypart : snake.getSnake_body()) {
            assertTrue(bodypart.getDirection().equals("RIGHT"));
        }
    }
}
