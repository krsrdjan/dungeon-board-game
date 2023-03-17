package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.models.weapons.Weapon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Monster extends Creature {

    private int speedPoints = 2;

    private ImageView imageView = new ImageView(new Image("/images/heroes/ninja.png"));

    public Monster(String name, int health, Weapon claws, int row, int column) {
        super(name, health, claws, row, column);
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
    public void moveUp() {
        if(checkHeroExist(currentRow - 1, currentColumn)) {
            return;
        }
        if (speedPoints > 0) {
            super.moveUp();
            speedPoints--;
        }
    }

    @Override
    public void moveDown() {
        if(checkHeroExist(currentRow + 1, currentColumn)) {
            return;
        }
        if (speedPoints > 0) {
            super.moveDown();
            speedPoints--;
        }
    }

    @Override
    public void moveLeft() {
        if(checkHeroExist(currentRow, currentColumn - 1)) {
            return;
        }
        if (speedPoints > 0) {
            super.moveLeft();
            speedPoints--;
        }
    }

    @Override
    public void moveRight() {
        if(checkHeroExist(currentRow, currentColumn + 1)) {
            return;
        }

        if (speedPoints > 0) {
            super.moveRight();
            speedPoints--;
        }
    }

    @Override
    public void attack(Creature target) {
        if(isNearHero()) {
            super.attack(target);
        }
    }

    public void monsterTurn() {
        moveToClosestHero();
        attack(tile.getHero());
    }

    private void moveToClosestHero() {
        while (speedPoints > 0 && !isNearHero()) {
            System.out.println("Moving to closest hero");

            int heroRow = tile.getHero().getCurrentRow();
            int heroColumn = tile.getHero().getCurrentColumn();

            int rowDiff = heroRow - currentRow;
            int columnDiff = heroColumn - currentColumn;

            if(rowDiff > 1) {
                moveDown();
            }

            if(rowDiff < -1) {
                moveUp();
            }

            if(columnDiff > 1) {
                moveRight();
            }

            if(columnDiff < -1) {
                moveLeft();
            }
        }

        //reset speed
        speedPoints = 2;
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

        if(heroRow == row && heroColumn == column) {
            return true;
        }

        return false;
    }

}
