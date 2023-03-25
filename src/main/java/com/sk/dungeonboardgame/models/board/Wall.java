package com.sk.dungeonboardgame.models.board;

import com.sk.dungeonboardgame.models.core.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends BoardElement {

    public Wall(String name, Position position) {
        super(name, new ImageView(new Image("/images/tiles/wall.png")), position);
    }
}
