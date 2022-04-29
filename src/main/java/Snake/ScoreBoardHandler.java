package Snake;

import java.util.Collections;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ScoreBoardHandler {
    
    public void UpdateScoreBoard(GridPane scoreboard, List<String> stats_from_file, String playername, SnakeGame snakegame) {
        boolean removeLine = false;
        String lineToBeRemoved = "";

        for (String line : stats_from_file) {
            if (line.contains(playername)) {
                lineToBeRemoved = line;
                removeLine = true;
            }
        }
        if (removeLine == true) {
            stats_from_file.remove(lineToBeRemoved);
        }
        stats_from_file.add(playername + "," + snakegame.getHighScore());
        Collections.sort(stats_from_file, new ScoreboardComparator());
        DisplayScoreBoardContent(scoreboard, stats_from_file);
    }

    public void DisplayScoreBoardContent(GridPane scoreboard, List<String> stats_from_file) {
        //Clear-er scoreboardet
        Node node = scoreboard.getChildren().get(0);
        scoreboard.getChildren().clear();
        scoreboard.getChildren().add(0,node);

        for (int i = 0; i < stats_from_file.size(); i++) {
            if (i > 9) {
                break;
            }

            String[] text = stats_from_file.get(i).split(",");
            for (int j = 0; j < 2; j++) {
                Label label = new Label(text[j]);
                label.setFont(new Font(20));
                label.setTextAlignment(TextAlignment.CENTER);
                scoreboard.add(label, j, i);
            }
        }
    }

    //Har fjernet metodekallet til DisplayScoreBoardContent() for å kunne teste logikken i UpdateTestScoreBoard()
    public void UpdateTestScoreBoard(List<String> stats_from_file, String playername, SnakeGame snakegame) {
        boolean removeLine = false;
        String lineToBeRemoved = "";

        for (String line : stats_from_file) {
            if (line.contains(playername)) {
                lineToBeRemoved = line;
                removeLine = true;
            }
        }
        if (removeLine == true) {
            stats_from_file.remove(lineToBeRemoved);
        }
        stats_from_file.add(playername + "," + snakegame.getHighScore());
        Collections.sort(stats_from_file, new ScoreboardComparator());
    }
}
