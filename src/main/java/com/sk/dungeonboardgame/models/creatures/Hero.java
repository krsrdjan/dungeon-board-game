package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Hero extends Creature {

    private int speedPoints = 50;
    private int attackPoints = 1;

    public Hero(String name, Position position, int health, Weapon weapon) {
        super(name, new ImageView(new Image("/images/heroes/knight.png")), position, health, weapon, ElementType.Hero);
    }

    //<editor-fold desc="Actions">
    @Override
    public boolean move(Direction direction) {
        System.out.println("move: " + direction);
        if(!GameState.isPlayerTurn) {
            return false;
        }

        if (speedPoints > 0) {
            if(!super.move(direction)) {
                return false;
            }
            speedPoints--;
        }

        // validate if tile switch is needed
        if(super.isOnTileBorder(direction) && !tile.hasNeighbour(direction)) {
            // if tile does not exist, initialize new tile
            tile.addNeighbour(direction);
            System.out.println("New tile added.");
        }

        return true;
    }
    //</editor-fold>

    @Override
    public void attacked(Weapon weapon) {
        super.attacked(weapon);

        if (this.health <= 0) {
            this.health = 0;
            //this.isAlive = false;
            GameState.field.removeHero(this);

            System.out.println(name + " is dead!");
        }
    }

    //@Override
    public void attack(Creature target) {
        if(!GameState.isPlayerTurn) {
            return;
        }

        /*if(isNearMonster() && attackPoints > 0) {
            super.attack(target);
            attackPoints--;
        }*/
    }

    public void endTurn() {
        speedPoints = 2;
        attackPoints = 1;
        GameState.isPlayerTurn = false;
    }
}