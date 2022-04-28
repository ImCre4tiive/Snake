package Snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class ScoreBoardComparatorTest {
    
    private List<String> testdata;

    @BeforeEach
    public void setup() {
        testdata = new ArrayList<>(Arrays.asList("Kåre,3", "Ola,6", "Hans,10"));
    }

    @Test
    public void testComparator() {
        Collections.sort(testdata, new ScoreboardComparator());
        System.out.println(testdata);
        assertEquals("[Hans,10, Ola,6, Kåre,3]", String.valueOf(testdata));
        
    }
    


}
