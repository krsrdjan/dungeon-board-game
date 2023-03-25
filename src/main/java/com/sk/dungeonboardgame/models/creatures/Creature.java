package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class Creature extends BoardElement {
    int health;
    boolean isAlive = true;
    Weapon baseWeapon = null;


    public Creature(String name, ImageView imageView, Position position, int health, Weapon baseWeapon) {
        super(name, imageView, position);
        this.health = health;
        this.baseWeapon = baseWeapon;
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

    public boolean move(KeyCode key) {
        Position suggetedPosition = position.clone();

        switch (key) {
            case W:
                suggetedPosition.row--;
                break;
            case S:
                suggetedPosition.row++;
                break;
            case A:
                suggetedPosition.column--;
                break;
            case D:
                suggetedPosition.column++;
                break;
            default:
                return false;
        }

        System.out.println("Current position: " + position.row + " " + position.column + "Suggested position: " + suggetedPosition.row + " " + suggetedPosition.column);

        // position has already been taken
        if(tile.isPlaceTaken(suggetedPosition)) {
            return false;
        }

        position = suggetedPosition;

        tile.updatePosition(this, position);

        return true;
    }
}
