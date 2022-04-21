package Snake;

import java.io.File;
import java.util.List;

public interface WriteAndReadToFile {

    public void ReadFromFile(File file, List<String> data, String playername, int score);

    public void WriteToFile(File file, List<String> data);
}
