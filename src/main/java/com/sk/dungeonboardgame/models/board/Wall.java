package com.sk.dungeonboardgame.models.board;

import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.state.Images;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends BoardElement {

    public Wall(Position position) {
        super("Wall", new ImageView(Images.wall), position, ElementType.Wall);
    }
}
