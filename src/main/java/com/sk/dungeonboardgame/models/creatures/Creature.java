package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import javafx.scene.image.ImageView;

public abstract class Creature {

    String name;
    int health;
    boolean isAlive = true;
    Weapon baseWeapon = null;

    Tile tile;
    int currentRow;
    int currentColumn;


    public Creature(String name, int health, Weapon baseWeapon, int currentRow, int currentColumn) {
        this.name = name;
        this.health = health;
        this.baseWeapon = baseWeapon;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void attacked(Weapon weapon) {
        int damage = weapon.getDamage();
        this.health = this.health - damage;
        System.out.println(name + " hit by " + weapon.getName() + " for " + damage + " damage");
    }

    public void attack(Creature target) {
        if(this.isAlive) {
            target.attacked(baseWeapon);
        }
    }

    public abstract ImageView getImageView();

    public void updateCurrentPosition(int row, int column, Tile tile) {
        this.currentRow = row;
        this.currentColumn = column;
        this.tile = tile;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void moveLeft() {
        tile.updateCreaturePosition(this, this.currentRow, this.currentColumn - 1);
    }

    public void moveRight() {
        tile.updateCreaturePosition(this, this.currentRow, this.currentColumn + 1);
    }

    public void moveUp() {
        tile.updateCreaturePosition(this, this.currentRow - 1, this.currentColumn);
    }

    public void moveDown() {
        tile.updateCreaturePosition(this, this.currentRow + 1, this.currentColumn);
    }
}
