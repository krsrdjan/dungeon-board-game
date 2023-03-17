package com.sk.dungeonboardgame.models.weapons;

import com.sk.dungeonboardgame.mechanics.Dice;

public class ShortSword implements Weapon {

    @Override
    public String getName() {
        return "Short sword";
    }

    @Override
    public int getDamage() {
        return Dice.rollD4();
    }
}
