package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Monster extends Creature {
    public int speedPoints = 2;

    public Monster(String name, Position position, int health, Weapon weapon) {
        super(name, new ImageView(new Image("/images/heroes/ninja.png")), position, health, weapon);
    }

    public Monster(String name, Position position, int health, Weapon weapon, String image) {
        super(name, new ImageView(new Image("/images/heroes/" + image)), position, health, weapon);
    }

    @Override
    public void attacked(Weapon weapon) {
        super.attacked(weapon);

        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
            this.tile.removeElement(this);

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

        Position difference = tile.getHero().getPosition().getDifference(this.position);

        if(difference.row > 1) {
            move(KeyCode.S);
            return true;
        }
        if(difference.row < -1) {
            move(KeyCode.W);
            return true;
        }
        if(difference.column > 1) {
            move(KeyCode.D);
            return true;
        }
        if(difference.column < -1) {
            move(KeyCode.A);
            return true;
        }

        return false;
    }

    public boolean isNearHero() {
        return tile.getHero().getPosition().getDistance(position) <= 1;
    }
}
