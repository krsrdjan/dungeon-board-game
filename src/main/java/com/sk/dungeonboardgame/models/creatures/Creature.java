package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.models.board.Quadrant;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public abstract class Creature extends BoardElement {
    int health;
    boolean isAlive = true;
    Weapon baseWeapon = null;


    public Creature(String name, ImageView imageView, Position position, int health, Weapon baseWeapon, ElementType type) {
        super(name, imageView, position, type, true);
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
        if(!GameState.field.isValidMove(suggetedPosition)) {
            return false;
        }

        position = suggetedPosition;

        GameState.field.updatePosition(this, position);

        return true;
    }
}
