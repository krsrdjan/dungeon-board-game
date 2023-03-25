package com.sk.dungeonboardgame;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.mechanics.MonsterAnimationTimer;
import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.collectables.Coin;
import com.sk.dungeonboardgame.models.collectables.Collectable;
import com.sk.dungeonboardgame.models.core.Position;
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

        Hero hero = new Hero("Fighter", new Position(7, 3), 10, new ShortSword());

        BoardElement[] elements = new BoardElement[] {
            new Monster("Ninja", new Position(0, 2), 10, new Claws()),
            new Monster("Dragon",  new Position(3, 7),10, new BattleAxe(), "dragonborn.png"),
            new Coin(new Position(8, 3), 3)
        };

        grid.setHero(hero);

        for(BoardElement element : elements) {
            grid.addElement(element);
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
