package com.sk.dungeonboardgame.models.core.helpers;

import com.sk.dungeonboardgame.models.core.enums.Direction;
import javafx.scene.input.KeyCode;

public class HelperMethods {
    public static boolean isNumberBetween(int number, int min, int max) {
        return number >= min && number <= max;
    }
    public static Direction keyStrokeToDirection(KeyCode keyCode) {
        switch (keyCode) {
            case W:
                return Direction.UP;
            case S:
                return Direction.DOWN;
            case A:
                return Direction.LEFT;
            case D:
                return Direction.RIGHT;
            default:
                return null;
        }
    }

    public static Direction getReversedDirection(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            default:
                return null;
        }
    }
}
