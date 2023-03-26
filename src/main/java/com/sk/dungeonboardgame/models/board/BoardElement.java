package com.sk.dungeonboardgame.models.board;

import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import javafx.scene.image.ImageView;

public abstract class BoardElement {
    protected String name;
    protected ElementType type;
    protected boolean isObstacle;
    protected ImageView imageView;
    protected Position position;

    public BoardElement(String name, ImageView imageView, Position position, ElementType type, boolean isObstacle) {
        this.name = name;
        this.imageView = imageView;
        this.position = position;
        this.type = type;
        this.isObstacle = isObstacle;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Position getPosition() {
        return position;
    }

    public void updatePosition(Position position) {
        this.position = position;
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
