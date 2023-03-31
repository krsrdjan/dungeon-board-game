package com.sk.dungeonboardgame.models.board;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BoardElement {
    protected String name;
    protected ElementType type;
    protected ImageView imageView;
    protected Position position;
    protected Tile tile;

    public BoardElement(String name, ImageView imageView, Position position, ElementType type) {
        this.name = name;
        this.imageView = imageView;
        this.position = position;
        this.type = type;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ElementType getType() {
        return type;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean isObstacle() {
        return getObstacleTypes().contains(type);
    }

    public static List<ElementType> getObstacleTypes() {
        return Arrays.stream(new ElementType[] {
                ElementType.Hero,
                ElementType.Monster,
                ElementType.Wall }).toList();
    }
}
