package Snake;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.input.KeyEvent;

public class Snake {
    private String direction_of_head = "RIGHT";
    private List<BodyPart> snake_body = new ArrayList<>();
    private Apple apple = new Apple();
    private int score = 0;
    private String playername;


    public void generate_snake() {
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                snake_body.add(new BodyPart(22, 25, true, "RIGHT"));
            }
            else {
                snake_body.add(new BodyPart(22-i, 25, false, "RIGHT"));
            }
        }
    }

    public void generateApple() {
        boolean loop = true;
        apple = new Apple();
        while (loop == true) {
            for (BodyPart bodypart : snake_body) {
                if (apple.getX_Coordinate() == bodypart.getX_Coordinate() && apple.getY_Coordinate() == bodypart.getY_Coordinate()) {
                    apple = new Apple();
                    continue;
                }
            loop = false;
            }
        }
    }

    public void IncreaseLengthOfSnake() {
        BodyPart tail = snake_body.get(snake_body.size()-1);
        String direction_of_tail = snake_body.get(snake_body.size()-1).getDirection();
        for (int i = 1; i < 3; i++) {
            if (direction_of_tail.equals("UP")) {
                if (tail.getY_Coordinate()+i < 49) {
                    snake_body.add(new BodyPart(tail.getX_Coordinate(), tail.getY_Coordinate()+i, false, direction_of_tail));
                }
            }
            if (direction_of_tail.equals("DOWN")) {
                if (tail.getY_Coordinate()-i > 0) {
                    snake_body.add(new BodyPart(tail.getX_Coordinate(), tail.getY_Coordinate()-i, false, direction_of_tail));
                }
            }
            if (direction_of_tail.equals("LEFT")) {
                if (tail.getX_Coordinate()+i < 49) {
                    snake_body.add(new BodyPart(tail.getX_Coordinate()+i, tail.getY_Coordinate(), false, direction_of_tail));
                }
                
            }
            if (direction_of_tail.equals("RIGHT")) {
                if (tail.getX_Coordinate()-i > 0) {
                    snake_body.add(new BodyPart(tail.getX_Coordinate()-i, tail.getY_Coordinate(), false, direction_of_tail));
                }
            }
        }
    }

    public boolean CheckCollision() {
        //Koden under er for å sjekke kollisjon med enten grid-limit eller slangekropp
        List<String> check_body_duplicate = new ArrayList<>();
        Set<String> set_body = new HashSet<>(); 
        
        //Sjekker om slangen er utenfor grid-et
        BodyPart head = getBodyPart(0);
        if (head.getX_Coordinate() > 49 || head.getX_Coordinate() < 0 || head.getY_Coordinate() > 49 || head.getY_Coordinate() < 0) {
            return true;
        }

        for (BodyPart bodypart : snake_body) {
            check_body_duplicate.add("(" + bodypart.getX_Coordinate() + "," + bodypart.getY_Coordinate() + ")");
        }
        //Sjekker om slangen kolliderer med seg selv
        for (String string : check_body_duplicate) {
            if (set_body.add(string) == false) {
                return true;
            }
        }
        return false;
    }

    public void changeDirectonOfHead(String direction) {
        if (this.direction_of_head.equals("UP")) {
            if (direction.equals("RIGHT") || direction.equals("LEFT")) {
                getBodyPart(0).setDirection(direction);
                this.direction_of_head = direction;
            }
        }
        else if (this.direction_of_head.equals("DOWN")) {
            if (direction.equals("RIGHT") || direction.equals("LEFT")) {
                getBodyPart(0).setDirection(direction);
                this.direction_of_head = direction;
            }
        }
        else if (this.direction_of_head.equals("LEFT")) {
            if (direction.equals("UP") || direction.equals("DOWN")) {
                getBodyPart(0).setDirection(direction);
                this.direction_of_head = direction;
            }
        }
        else if (this.direction_of_head.equals("RIGHT")) {
            if (direction.equals("UP") || direction.equals("DOWN")) {
                getBodyPart(0).setDirection(direction);
                this.direction_of_head = direction;
            }
        }
    }

    public void changeDirectonOfBodyParts() {
        List<String> directions_of_bodyparts_before_change = new ArrayList<>();
        for (BodyPart bodypart : snake_body) {
            directions_of_bodyparts_before_change.add(bodypart.getDirection());
        }

        for (int i = 1; i < snake_body.size(); i++) {
            if (!directions_of_bodyparts_before_change.get(i).equals(directions_of_bodyparts_before_change.get(i-1))) {
                getBodyPart(i).setDirection(directions_of_bodyparts_before_change.get(i-1));
            }
        }
    }

    public void handleInput(KeyEvent event) {
        switch(event.getCode()) {
            case W:
                changeDirectonOfHead("UP");
                break;
            case S:
                changeDirectonOfHead("DOWN");
                break;
            case A:
                changeDirectonOfHead("LEFT");
                break;
            case D:
                changeDirectonOfHead("RIGHT");
            default:
                break;
        }
    }

    public boolean IsAppleEaten() {
        BodyPart head = getBodyPart(0);
        if (head.getX_Coordinate() == apple.getX_Coordinate() && head.getY_Coordinate() == apple.getY_Coordinate()) {
            return true;
        }
        return false;
    }

    public void move() {
        for (BodyPart bodypart : snake_body) {
            if (bodypart.getDirection().equals("UP")) {
                bodypart.setY_Coordinate(bodypart.getY_Coordinate() - 1);
            }
            if (bodypart.getDirection().equals("DOWN")) {
                bodypart.setY_Coordinate(bodypart.getY_Coordinate() + 1);
            }
            if (bodypart.getDirection().equals("LEFT")) {
                bodypart.setX_Coordinate(bodypart.getX_Coordinate() - 1);
            }
            if (bodypart.getDirection().equals("RIGHT")) {
                bodypart.setX_Coordinate(bodypart.getX_Coordinate() + 1);
            }
        }
        changeDirectonOfBodyParts();
    }

    //========================================================================================================================================================================
    //Gettere og settere:
    public List<BodyPart> getSnake_body() {
        return snake_body;
    }

    public BodyPart getBodyPart(int index) {
        return this.snake_body.get(index);
    }

    public void setPlayerName(String playername) {
        this.playername = playername;
    }

    public Apple getApple() {
        return this.apple;
    }
    
    public void IncreaseScore() {
        this.score += 1;
    }

    public int getScore() {
        return this.score;
    }

    //========================================================================================================================================================================
    //Hjelpefunksjoner for testing:
    public void generate_test_snake() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                snake_body.add(new BodyPart(22, 25, true, "RIGHT"));
            }
            else {
                snake_body.add(new BodyPart(22-i, 25, false, "RIGHT"));
            }
        }
    }

    public void generate_test_snake_at_border() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                snake_body.add(new BodyPart(48, 25, true, "RIGHT"));
            }
            else {
                snake_body.add(new BodyPart(48-i, 25, false, "RIGHT"));
            }
        }
    }

    public void generateTestApple(int X_Coordinate, int Y_Coordinate) {
        this.apple = new Apple(X_Coordinate, Y_Coordinate);
    }

    public void changeToStringOfBodyParts(String toString) {
        for (BodyPart bodypart : snake_body) {
            bodypart.setToString(toString);
        }
    }

}


