package com.sk.dungeonboardgame.models.collectables;

import com.sk.dungeonboardgame.board.Tile;
import javafx.scene.image.ImageView;

public abstract class Collectable {
    public String name;
    private int currentColumn;
    private int currentRow;

    public Collectable(String name, int column, int row) {
        this.name = name;
        this.currentColumn = column;
        this.currentRow = row;
    }

    public abstract ImageView getImageView();

    public abstract void collect();

    public void updateCurrentPosition(int row, int column, Tile tile) {
        this.currentRow = row;
        this.currentColumn = column;
        //this.tile = tile;
    }
}
