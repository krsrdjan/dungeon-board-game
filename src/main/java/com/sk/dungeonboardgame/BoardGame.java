package com.sk.dungeonboardgame;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.mechanics.MonsterAnimationTimer;
import com.sk.dungeonboardgame.models.collectables.Coin;
import com.sk.dungeonboardgame.models.collectables.Collectable;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.models.creatures.Monster;
import com.sk.dungeonboardgame.models.weapons.BattleAxe;
import com.sk.dungeonboardgame.models.weapons.Claws;
import com.sk.dungeonboardgame.models.weapons.ShortSword;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardGame extends Application {

    private Tile grid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        grid = new Tile();

        Hero hero = new Hero("Fighter", 10, new ShortSword(), 7, 3);

        Monster[] monsters = new Monster[] {
            new Monster("Ninja", 10, new Claws(), 0, 2),
            new Monster("Dragon", 10, new BattleAxe(), 3, 7, "dragonborn.png"),
        };

        Collectable[] collectables = new Collectable[] {
                new Coin(10, 10, 5)
        };

        grid.setHero(hero);

        for(Monster m : monsters) {
            grid.addMonster(m);
        }

        for(Collectable c : collectables) {
            grid.addCollectable(c);
        }

        GridPane container = new GridPane();
        container.add(grid, 0,0);

        Scene scene = new Scene(container, Color.GRAY);
        primaryStage.setTitle("Dungeon Delve Board Game");

        setupGameControls(hero, scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupGameControls(Hero hero, Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                case S:
                case A:
                case D:
                    hero.move(event.getCode());
                    break;
                case I:
                    hero.attack(hero.getClosestMonster());
                    break;
                case O:
                    hero.endTurn();
                    MonsterAnimationTimer timer = new MonsterAnimationTimer(grid.getMonsters());
                    timer.start();
                    break;
            }
        });
    }
}
