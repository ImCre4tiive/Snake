package Snake;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public final class SnakeController {
    @FXML
    private GridPane grid;

    @FXML
    private Pane snake;


    public double getX_Coordinate() {
        return GridPane.getColumnIndex(snake);
    }
    public double getY_Coordinate() {
        return GridPane.getRowIndex(snake);
    }
    public void setX_Coordinate(int X) {
        GridPane.setColumnIndex(snake, X);
    }
    public void setY_Coordinate(int Y) {
        GridPane.setRowIndex(snake, Y);
    }

    public void moveUp() {
        setY_Coordinate((int)getY_Coordinate() - 1);
    }
    public void moveDown() {
        setY_Coordinate((int)getY_Coordinate() + 1);
    }
    public void moveLeft() {
        setX_Coordinate((int)getX_Coordinate() - 1);
    }
    public void moveRight() {
        setX_Coordinate((int)getX_Coordinate() + 1);
    }
}


