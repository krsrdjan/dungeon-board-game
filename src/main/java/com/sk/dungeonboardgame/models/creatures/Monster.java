package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.models.weapons.Weapon;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Monster extends Creature {

    public int speedPoints = 2;

    private ImageView imageView = new ImageView(new Image("/images/heroes/ninja.png"));

    public Monster(String name, int health, Weapon claws, int row, int column) {
        super(name, health, claws, row, column);
    }

    public Monster(String name, int health, Weapon claws, int row, int column, String image) {
        super(name, health, claws, row, column);

        imageView = new ImageView(new Image("/images/heroes/" + image));
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void attacked(Weapon weapon) {
        super.attacked(weapon);

        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
            this.tile.removeMonster(this);

            System.out.println(name + " is dead!");
        }
    }

    @Override
    public boolean move(KeyCode keyCode) {
        // check if place is already taken
        if(speedPoints > 0) {
            if(!super.move(keyCode)) {
                return false;
            }
            speedPoints--;
        }

        return true;
    }

    @Override
    public void attack(Creature target) {
        if(isNearHero()) {
            super.attack(target);
        }
    }

    public boolean moveToClosestHero() {
        System.out.println(name + ": Moving to closest hero");

        int heroRow = tile.getHero().getCurrentRow();
        int heroColumn = tile.getHero().getCurrentColumn();

        int rowDiff = heroRow - currentRow;
        int columnDiff = heroColumn - currentColumn;

        if(rowDiff > 1) {
            move(KeyCode.S);
            return true;
        }
        if(rowDiff < -1) {
            move(KeyCode.W);
            return true;
        }
        if(columnDiff > 1) {
            move(KeyCode.D);
            return true;
        }
        if(columnDiff < -1) {
            move(KeyCode.A);
            return true;
        }

        return false;
    }

    public boolean isNearHero() {
        int heroRow = tile.getHero().getCurrentRow();
        int heroColumn = tile.getHero().getCurrentColumn();

        if(Math.abs(heroRow - currentRow) <= 1 && Math.abs(heroColumn - currentColumn) <= 1) {
            return true;
        }

        return false;
    }

    private boolean checkHeroExist(int row, int column) {
        int heroRow = tile.getHero().getCurrentRow();
        int heroColumn = tile.getHero().getCurrentColumn();

        return heroRow == row && heroColumn == column;
    }
}
