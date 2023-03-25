package com.sk.dungeonboardgame.models.collectables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin extends Collectable {

    public int amount;
    private ImageView imageView = new ImageView(new Image("/images/items/gold.png"));

    public Coin(int column, int row, int amount) {
        super("Coin", column, row);

        this.amount = amount;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void collect() {

    }
}
