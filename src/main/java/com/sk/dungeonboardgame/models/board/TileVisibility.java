package com.sk.dungeonboardgame.models.board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileVisibility {
    public Color color;
    public int squareSize;
    public Rectangle rectangle;

    public TileVisibility(Color color, int squareSize) {
        this.color = color;
        rectangle = new Rectangle(squareSize, squareSize, color);
    }
}
