package com.sk.dungeonboardgame.mechanics;

import com.sk.dungeonboardgame.models.creatures.Monster;
import com.sk.dungeonboardgame.state.GameState;
import javafx.animation.AnimationTimer;

import java.util.List;
import java.util.Random;

public class MonsterAnimationTimer extends AnimationTimer {
    private List<Monster> monsters;
    private int currentIdx = 0;
    public MonsterAnimationTimer(List<Monster> monsters) {
        this.monsters = monsters;
    }

    @Override
    public void handle(long now) {
        handlee();
    }

    private void handlee() {
        if(monsters.size() == 0) {
            GameState.isPlayerTurn = true;
            stop();
            return;
        }

        Monster monster = monsters.get(currentIdx);
        if(monster.speedPoints > 0) {
            if(!monster.moveToClosestHero()) {
                monster.speedPoints = 0;
            }
            delay();
        } else {
            monster.attack(GameState.field.getHero());

            currentIdx++;
            if(currentIdx >= monsters.size()) {
                GameState.turnCount++;
                GameState.isPlayerTurn = true;
                stop();
            }

            monster.speedPoints = 2;
        }
    }

    private void delay() {
        try {
            Random rand = new Random();
            long sleepTime = rand.nextLong(1000) + 500;
            System.out.println(sleepTime);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}