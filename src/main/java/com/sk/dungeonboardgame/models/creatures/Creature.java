package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public abstract class Creature extends BoardElement {
    int health;
    Weapon baseWeapon = null;

    public Creature(String name, ImageView imageView, Position position, int health, Weapon baseWeapon, ElementType type) {
        super(name, imageView, position, type);
        this.health = health;
        this.baseWeapon = baseWeapon;
    }

    public void attacked(Weapon weapon) {
        int damage = weapon.getDamage();
        this.health = this.health - damage;
        System.out.println(name + " hit by " + weapon.getName() + " for " + damage + " damage");
    }

    public boolean move(Direction direction) {
        Position suggetedPosition = position.getPositionByDirection(direction);

        if(suggetedPosition == null) {
            return false;
        }

        if(!GameState.field.isValidMove(this, suggetedPosition)) {
            return false;
        }

        position = suggetedPosition;

        if(isOutsideBorders(direction)) {
            tile.removeElement(this);
            tile = tile.getNeighbour(direction);
            tile.addElement(this);
        } else {
            tile.setPosition(this, position);
        }

        return true;
    }

    public boolean isOnTileBorder(Direction direction) {
        var relativePos = position.getRelativePosition();

        if(direction == Direction.UP && relativePos.row == 0) {
            return true;
        } else if(direction == Direction.DOWN && relativePos.row == 3) {
            return true;
        } else if(direction == Direction.LEFT && relativePos.column == 0) {
            return true;
        } else if(direction == Direction.RIGHT && relativePos.column == 3) {
            return true;
        }

        return false;
    }

    public boolean isOutsideBorders(Direction direction) {
        var relativePos = position;

        System.out.println("Is outside borders" + direction);
        System.out.println("X " + position.row + " " + position.column);

        if(direction == Direction.UP && position.row < 0) {
            return true;
        } else if(direction == Direction.DOWN && position.row > 3) {
            return true;
        } else if(direction == Direction.LEFT && position.column < 0) {
            return true;
        } else if(direction == Direction.RIGHT && position.column > 3) {
            return true;
        }

        return false;
    }
}
