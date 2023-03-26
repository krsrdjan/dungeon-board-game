package com.sk.dungeonboardgame.models.collectables;

import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import javafx.scene.image.ImageView;

public abstract class Collectable extends BoardElement {
    public Collectable(String name, ImageView imageView, Position position) {
        super(name, imageView, position, ElementType.Collectable, false);
    }

    public abstract void collect();

}
