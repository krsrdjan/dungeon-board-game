package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class Creature {

    public String name;
    int health;
    boolean isAlive = true;
    Weapon baseWeapon = null;

    public Tile tile;
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

    public boolean move(KeyCode key) {
        int suggestedRow = this.currentRow;
        int suggestedColumn = this.currentColumn;

        switch (key) {
            case W:
                suggestedRow -= 1;
                break;
            case S:
                suggestedRow+= 1;
                break;
            case A:
                suggestedColumn -= 1;
                break;
            case D:
                suggestedColumn += 1;
                break;
            default:
                return false;
        }

        // position has already been taken
        if(tile.isPlaceTaken(suggestedColumn, suggestedRow)) {
            return false;
        }

        this.currentColumn = suggestedColumn;
        this.currentRow = suggestedRow;

        tile.updateCreaturePosition(this, this.currentRow, this.currentColumn);

        return true;
    }
}
