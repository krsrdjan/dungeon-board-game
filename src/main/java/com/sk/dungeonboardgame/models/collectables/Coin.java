package com.sk.dungeonboardgame.models.collectables;

import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin extends Collectable {
    public int amount;

    public Coin(Position position, int amount) {
        super("Coin", new ImageView(new Image("/images/items/gold.png")), position);
        this.amount = amount;
    }

    @Override
    public void collect() {

    }
}
