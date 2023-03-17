package com.sk.dungeonboardgame.models.weapons;

import com.sk.dungeonboardgame.mechanics.Dice;

public class Claws implements Weapon {

    @Override
    public String getName() {
        return "Claws";
    }

    @Override
    public int getDamage() {
        return Dice.rollD4();
    }
}
