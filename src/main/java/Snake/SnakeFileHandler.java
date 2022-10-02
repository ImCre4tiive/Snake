package Snake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SnakeFileHandler implements WriteAndReadToFile{

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void ReadFromFile(File file, List<String> data, String playername, int score) {
        
        try (Scanner scanner = new Scanner(file)) {
            
            while (scanner.hasNextLine()) {
                String nextline = scanner.nextLine();
                
                try {
                    String[] line = nextline.split(",");
                    if (line.length != 2 || !(line[0] instanceof String) || !(Integer.valueOf(line[1]) instanceof Integer) || TestExistingName(line[0], data)) {
                        throw new Exception();
                    }
                    
                } catch (Exception e) {
                    System.out.println(e);
                    continue;   
                }
                data.add(nextline);
            }
            Collections.sort(data, new ScoreboardComparator());
        }
        catch (IOException IOe) {
            System.out.println("Dette skjedde: " + IOe.getMessage());
        }
    }

    @Override
    public void WriteToFile(File file, List<String> data) {
        try (FileWriter filewriter = new FileWriter(file, false)) {
            
            for (String string : data) {
                filewriter.write(string + "\n");
            }
        }
        catch (IOException IOe) {
            System.out.println("Dette skjedde: " + IOe.getMessage());
        }  
    }
    
    private boolean TestExistingName(String name, List<String> data) {
        for (String string : data) {
            try {
                if (string.split(",")[0].equals(name)) {
                    return true;
                }
            }
            catch (Exception e) {
                continue;
            }
            
        }
        return false;
    }
}
