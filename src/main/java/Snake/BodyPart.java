package Snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BodyPart {
    
    private int X_Coordinate;
    private int Y_Coordinate;
    private boolean ishead;
    private String direction = "RIGHT";
    private List<String> valid_directions = new ArrayList<>(Arrays.asList("UP", "DOWN", "LEFT", "RIGHT"));

    public BodyPart(int X_Coordinate, int Y_Coordinate, boolean ishead, String direction) {
        if (X_Coordinate > -1 && X_Coordinate < 50 && Y_Coordinate > -1 && Y_Coordinate < 50 && valid_directions.contains(direction)) {
            this.X_Coordinate = X_Coordinate;
            this.Y_Coordinate = Y_Coordinate;
            this.ishead = ishead;
            this.direction = direction;
        }
        else {
            throw new IllegalArgumentException("Her ble det noe feil i input. Den var: X=" + X_Coordinate + " ,Y=" + Y_Coordinate + " , ishead=" + ishead + " , direction= " + direction);
        }
    }


    
    public int getX_Coordinate() {
        return X_Coordinate;
    }

    public void setX_Coordinate(int x_Coordinate) {
        X_Coordinate = x_Coordinate;
    }


    public int getY_Coordinate() {
        return Y_Coordinate;
    }

    public void setY_Coordinate(int y_Coordinate) {
        Y_Coordinate = y_Coordinate;
    }


    public boolean isIshead() {
        return ishead;
    }

    public void setIshead(boolean ishead) {
        this.ishead = ishead;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }



    public String toString() {
        // return " (X = " + X_Coordinate + " , Y = " + Y_Coordinate + ", direction = " + direction + ", ishead = " + ishead + ") ";
        return (direction);
    }


}
