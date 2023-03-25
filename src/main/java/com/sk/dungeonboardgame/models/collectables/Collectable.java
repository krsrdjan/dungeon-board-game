package com.sk.dungeonboardgame.models.collectables;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.core.Position;
import javafx.scene.image.ImageView;

public abstract class Collectable extends BoardElement {
    public Collectable(String name, ImageView imageView, Position position) {
        super(name, imageView, position);
    }

    public abstract void collect();

}
