package com.sk.dungeonboardgame.mechanics;

import java.util.Random;

public class Dice {

    public static int rollD20() {
        return new Random().nextInt(20) + 1;
    }

    public static int rollD10() {
        return new Random().nextInt(10) + 1;
    }

    public static int rollD8() {
        return new Random().nextInt(8) + 1;
    }

    public static int rollD6() {
        return new Random().nextInt(6) + 1;
    }

    public static int rollD4() {
        return new Random().nextInt(4) + 1;
    }


}
