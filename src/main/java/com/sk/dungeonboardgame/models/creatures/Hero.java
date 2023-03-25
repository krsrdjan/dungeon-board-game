package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Hero extends Creature {

    private int speedPoints = 2;
    private int attackPoints = 1;

    public Hero(String name, Position position, int health, Weapon weapon) {
        super(name, new ImageView(new Image("/images/heroes/knight.png")), position, health, weapon);
    }

    @Override
    public void attacked(Weapon weapon) {
        super.attacked(weapon);

        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
            this.tile.removeHero(this);

            System.out.println(name + " is dead!");
        }
    }

    @Override
    public boolean move(KeyCode keyCode) {
        if(!GameState.isPlyerTurn) {
            return false;
        }

        if(checkMonsterExist(position)) {
            return false;
        }
        if (speedPoints > 0) {
            if(!super.move(keyCode)) {
                return false;
            }
            speedPoints--;
        }

        tile.processIfCollidedWithCollectable();

        return true;
    }

    @Override
    public void attack(Creature target) {
        if(!GameState.isPlyerTurn) {
            return;
        }

        if(isNearMonster() && attackPoints > 0) {
            super.attack(target);
            attackPoints--;
        }
    }

    private boolean checkMonsterExist(Position pos) {
        for(Monster m : tile.getMonsters()) {
            if(position.isCollided(m.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public boolean isNearMonster() {
        for(Monster m : tile.getMonsters()) {
            if(position.getDistance(m.getPosition()) <= 1) {
                return true;
            }
        }
        return false;
    }

    public Monster getClosestMonster(){
        Monster closestMonster = null;
        double closestDistance = Integer.MAX_VALUE;

        for(Monster m : tile.getMonsters()) {
            double distance = position.getDistance(m.getPosition());

            if(distance < closestDistance) {
                closestDistance = distance;
                closestMonster = m;
            }
        }
        return closestMonster;
    }

    public void endTurn() {
        speedPoints = 2;
        attackPoints = 1;
        GameState.isPlyerTurn = false;
    }
}