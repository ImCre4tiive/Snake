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
    
    private boolean toString1;
    private boolean toString2;
    private boolean toString3;

    public BodyPart(int X_Coordinate, int Y_Coordinate, boolean ishead, String direction) {
        if (X_Coordinate > -1 && X_Coordinate < 50 && Y_Coordinate > -1 && Y_Coordinate < 50 && valid_directions.contains(direction)) {
            this.X_Coordinate = X_Coordinate;
            this.Y_Coordinate = Y_Coordinate;
            this.ishead = ishead;
            this.direction = direction;
            toString1 = true;
            toString2 = false;
            toString3 = false;

        }
        else {
            throw new IllegalArgumentException("Her ble det noe feil i input. Den var: X=" + X_Coordinate + " ,Y=" + Y_Coordinate + " , ishead=" + ishead + " , direction= " + direction);
        }
    }

    public String getToString() {
        if (toString1 == true) {
            return "toString1";
        }
        else if (toString2 == true) {
            return "toString2";
        }
        else if (toString3 == true) {
            return "toString3";
        }
        else {
            throw new IllegalStateException("Ingen gjeldende toString!");
        }

    }

    public void setToString(String toString) {
        if (toString.equals("toString1")) {
            toString1 = true;
            toString2 = false;
            toString3 = false;
        }
        else if (toString.equals("toString2")) {
            toString1 = false;
            toString2 = true;
            toString3 = false;
            
        }
        else if (toString.equals("toString3")) {
            toString1 = false;
            toString2 = false;
            toString3 = true;
        }
        else {
            throw new IllegalArgumentException("Ugyldig toString!");
        }
    }

    public String toString() {
        if (toString1 == true) {
            return "(X = " + X_Coordinate + ", Y = " + Y_Coordinate + ", direction = " + direction + ", ishead = " + ishead + ")";
        }
        else if (toString2 == true) {
            return "(X=" + X_Coordinate + ",Y=" + Y_Coordinate + ")";
        }
        else if (toString3 == true) {
            return direction;
        }
        else {
            return "Her ble det noe surr";
        }
    }

    //===================================================================================================================================================================
    //Gettere og settere:
    
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

    public boolean getIshead() {
        return ishead;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


}
