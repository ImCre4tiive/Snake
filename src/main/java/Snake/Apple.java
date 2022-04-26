package Snake;


public class Apple {
    private int X_Coordinate;
    private int Y_Coordinate;

    public Apple() {
        this.X_Coordinate = 0 + (int)(Math.random() * ((49 - 0) + 1));
        this.Y_Coordinate = 0 + (int)(Math.random() * ((49 - 0) + 1));
    }

    //Konstruktør til testing:
    public Apple(int X_Coordinate, int Y_Coordinate) {
        this.X_Coordinate = X_Coordinate;
        this.Y_Coordinate = Y_Coordinate;
    }

    public int getX_Coordinate() {
        return X_Coordinate;
    }

    public int getY_Coordinate() {
        return Y_Coordinate;
    }
}
