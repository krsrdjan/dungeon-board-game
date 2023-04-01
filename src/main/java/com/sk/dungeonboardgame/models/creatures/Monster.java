package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import com.sk.dungeonboardgame.state.GameState;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Monster extends Creature {
    public int speedPoints = 2;

    public Monster(String name, Position position, int health, Weapon weapon) {
        super(name, new ImageView(new Image("/images/heroes/ninja.png")), position, health, weapon, ElementType.Monster);
    }

    public Monster(String name, Position position, int health, Weapon weapon, Image image) {
        super(name, new ImageView(image), position, health, weapon, ElementType.Monster);
    }

    @Override
    public void attacked(Weapon weapon) {
        super.attacked(weapon);

        if (this.health <= 0) {
            this.health = 0;
            GameState.field.removeElement(this);

            System.out.println(name + " is dead!");
        }
    }

    /*@Override
    public boolean move(KeyCode keyCode) {
        // check if place is already taken
        if(speedPoints > 0) {
            if(!super.move(keyCode)) {
                return false;
            }
            speedPoints--;
        }

        return true;
    }*/

    public boolean moveToClosestHero() {
        /*System.out.println(name + ": Moving to closest hero");

        Position difference = GameState.field.getHero().getPosition().getDifference(this.position);

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
        }*/

        return false;
    }

    public boolean isNearHero() {
        return GameState.field.getHero().getPosition().getDistance(position) <= 1;
    }
}
