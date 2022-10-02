package Snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreBoardHandlerTest {
    
    private SnakeGame snakegame;
    private List<String> testdata;
    private ScoreBoardHandler scoreboardhandler;

    @BeforeEach
    public void setup() {
        snakegame = new SnakeGame();
        snakegame.setHighScore(15);
        testdata = new ArrayList<>(Arrays.asList("Kåre,3", "Ola,6", "Hans,10"));
        scoreboardhandler = new ScoreBoardHandler();
    }

    @Test
    public void testUpdateScoreBoard() {
        scoreboardhandler.UpdateTestScoreBoard(testdata, "Einar", snakegame);
        assertEquals("[Einar,15, Hans,10, Ola,6, Kåre,3]", String.valueOf(testdata));
        snakegame.setHighScore(16);
        //Sjekker at funksjonen sletter linjen som viser Einars gamle poengskår, og legger til en ny linje med korrekt poengskår
        scoreboardhandler.UpdateTestScoreBoard(testdata, "Einar", snakegame);
        assertEquals("[Einar,16, Hans,10, Ola,6, Kåre,3]", String.valueOf(testdata));
    }




}
