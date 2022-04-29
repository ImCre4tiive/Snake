package Snake;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileHandlerTest {
    
    private List<String> testdata = new ArrayList<>(Arrays.asList("Kåre,3", "Ola,6", "Hans,10"));
    private List<String> data_from_file = new ArrayList<>();
    private SnakeFileHandler filehandler = new SnakeFileHandler();
    private SnakeGame snakegame = new SnakeGame();

    
    @BeforeAll
    public void setup() {
        try (FileWriter filewriter = new FileWriter(snakegame.getFile("test_stats"), false)) {
            
            for (String string : testdata) {
                filewriter.write(string + "\n");
            }
        }
        catch (IOException IOe) {
            System.out.println("Dette skjedde: " + IOe.getMessage());
            // System.out.println("WriteToFile: " + IOe);
        }  
    }

    @BeforeEach
    public void setup2() {
        data_from_file.clear();
    }

    @Test
    public void testReadFromNonExistingFile() {
        //Tester at metoden catch-er IOException
        assertDoesNotThrow(() -> {
            filehandler.ReadFromFile(snakegame.getFile("fil_som_ikke_eksisterer"), data_from_file, "Test", 1);
        });
    }

    @Test
    public void testReadFromFile() {
        filehandler.ReadFromFile(snakegame.getFile("test_stats"), data_from_file, "Jakob", 16);
        assertEquals("[Hans,10, Ola,6, Kåre,3]", String.valueOf(data_from_file));
    }

    @Test
    public void testWriteToFileNonExistingFile() {
        //Tesre at metoden catch-er IOException
        assertDoesNotThrow(() -> {
            filehandler.WriteToFile(snakegame.getFile("random"), data_from_file);
        });
    }

    @Test
    public void testWriteToFile() {
        //Sjekker om innhold i test_stats-filen er lik innhold i test_stats_2 som er skrevet vha. filehandler.WriteToFile():
        filehandler.WriteToFile(snakegame.getFile("test_stats_2"), testdata);
        
        List<String> expected_data = new ArrayList<>();
        List<String> actual_data = new ArrayList<>();
        filehandler.ReadFromFile(snakegame.getFile("test_stats"), expected_data, "Tore", 10);
        filehandler.ReadFromFile(snakegame.getFile("test_stats_2"), actual_data, "Tore", 10);
        assertEquals(expected_data, actual_data);
    }

    @AfterAll
    public void cleanup() {
        snakegame.getFile("test_stats").delete();
        snakegame.getFile("fil_som_ikke_eksisterer").delete();
        snakegame.getFile("random").delete();
        snakegame.getFile("test_stats_2").delete();
    }
}

