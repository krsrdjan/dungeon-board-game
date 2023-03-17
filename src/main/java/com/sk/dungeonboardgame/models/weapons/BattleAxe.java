package com.sk.dungeonboardgame.models.weapons;

import com.sk.dungeonboardgame.mechanics.Dice;

public class BattleAxe implements Weapon {

    @Override
    public String getName() {
        return "Battle axe";
    }

    @Override
    public int getDamage() {
        return Dice.rollD8();
    }
}
