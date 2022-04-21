package Snake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileHandler implements WriteAndReadToFile{

    @Override
    public void ReadFromFile(File file, List<String> data, String playername, int score) {
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("File er nå: " + file);
            
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
            if (playername != null) {
                data.add(playername + "," + score);
            }

            Collections.sort(data, new ScoreboardComparator());
        }
        catch (IOException e) {
            // System.out.println("Dette skjedde: " + e.getMessage());
            System.out.println("Readfromfile: " + e.toString());
            
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
            System.out.println("WriteToFile: " + IOe);
        }  
    }
    
    private boolean TestExistingName(String name, List<String> data) {
        for (String string : data) {
            if (string.contains(name)) {
                return true;
            }
        }
        return false;
    }
}
