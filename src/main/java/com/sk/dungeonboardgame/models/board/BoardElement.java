package com.sk.dungeonboardgame.models.board;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import javafx.scene.image.ImageView;

public abstract class BoardElement {
    protected String name;
    protected ElementType type;
    protected ImageView imageView;
    protected Position position;
    protected Tile tile;

    public BoardElement(String name, ImageView imageView, Position position) {
        this.name = name;
        this.imageView = imageView;
        this.position = position;
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

    public void updatePosition(Position position, Tile tile) {
        this.position = position;
        this.tile = tile;
    }

    public boolean isCollided(BoardElement el) {
        return this.position.isCollided(el.getPosition());
    }

    public Tile getTile() {
        return tile;
    }
    public ElementType getType() {
        return type;
    }
}
