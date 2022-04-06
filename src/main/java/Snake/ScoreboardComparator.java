package Snake;

import java.util.Comparator;

public class ScoreboardComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return Integer.parseInt(o2.split(",")[1]) - Integer.parseInt(o1.split(",")[1]);
    }
}
