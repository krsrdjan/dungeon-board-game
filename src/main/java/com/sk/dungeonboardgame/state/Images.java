package com.sk.dungeonboardgame.state;

import com.sk.dungeonboardgame.board.Field;
import javafx.scene.image.Image;

public class Images {
    public static Image hero = new Image("/images/heroes/knight.png");
    public static Image wall = new Image("/images/tiles/wall.png");
    public static Image tile = new Image(Field .class.getResourceAsStream("/images/tiles/tile.png"));
    public static Image ninja = new Image("/images/heroes/ninja.png");
    public static Image dragonborn = new Image("/images/heroes/dragonborn.png");
}
