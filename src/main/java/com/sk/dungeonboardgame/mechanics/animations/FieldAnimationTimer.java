package com.sk.dungeonboardgame.mechanics.animations;

import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.state.GameState;
import javafx.animation.AnimationTimer;

public class FieldAnimationTimer extends AnimationTimer {
    private Direction direction;
    private double target = 0;
    private double quocient = 2;

    public FieldAnimationTimer(Direction direction) {
        GameState.isPlayerTurn = false;
        this.direction = direction;
        if(direction == Direction.DOWN || direction == Direction.RIGHT) {
            target = direction == Direction.DOWN ? GameState.offsetY : GameState.offsetX;
            quocient *= -1;
        }
    }

    @Override
    public void handle(long now) {
        handlee();
    }

    private void handlee() {
        if(direction == Direction.LEFT || direction == Direction.RIGHT) {
            if(compare(GameState.field.getTranslateX())) {
                GameState.field.setTranslateX(target);
                stop();
                GameState.isPlayerTurn = true;
                return;
            }
            GameState.field.setTranslateX(GameState.field.getTranslateX() + quocient);
        } else {
            if(compare(GameState.field.getTranslateY())) {
                GameState.field.setTranslateY(target);
                stop();
                GameState.isPlayerTurn = true;
                return;
            }
            GameState.field.setTranslateY(GameState.field.getTranslateY() + quocient);
        }
    }

    private boolean compare(double number) {
        if(direction == Direction.UP || direction == Direction.LEFT) {
            return number > target;
        } else {
            return number < target;
        }
    }
}
