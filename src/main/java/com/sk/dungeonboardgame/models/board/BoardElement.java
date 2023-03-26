package com.sk.dungeonboardgame.models.board;

import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.ImageView;

public abstract class BoardElement {
    protected String name;
    protected ElementType type;
    protected boolean isObstacle;
    protected ImageView imageView;
    protected Position position;
    protected Quadrant quadrant;

    public BoardElement(String name, ImageView imageView, Position position, ElementType type, boolean isObstacle) {
        this.name = name;
        this.imageView = imageView;
        this.position = position;
        this.type = type;
        this.isObstacle = isObstacle;
        this.quadrant = GameState.field.getQuadrant(position);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Position getPosition() {
        return position;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public void updatePosition(Position position) {
        this.position = position;
        this.quadrant = GameState.field.getQuadrant(position);
    }

    public boolean isCollided(BoardElement el) {
        return this.position.isCollided(el.getPosition());
    }
    public ElementType getType() {
        return type;
    }
    public boolean isObstacle() {
        return isObstacle;
    }
}
