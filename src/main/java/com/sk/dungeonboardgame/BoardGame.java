package com.sk.dungeonboardgame;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.models.creatures.Monster;
import com.sk.dungeonboardgame.models.weapons.BattleAxe;
import com.sk.dungeonboardgame.models.weapons.Claws;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Hero hero = new Hero("Fighter", 10, new BattleAxe(),7,3);
        Monster monster = new Monster("Ninja", 10, new Claws(), 0,2);

        Tile tile = new Tile();
        tile.addMonster(monster);
        tile.setHero(hero);

        GridPane container = new GridPane();
        container.add(tile, 0,0);

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
                    hero.moveUp();
                    break;
                case S:
                    hero.moveDown();
                    break;
                case A:
                    hero.moveLeft();
                    break;
                case D:
                    hero.moveRight();
                    break;
                case I:
                    hero.attack(hero.getClosestMonster());
                    break;
                case O:
                    hero.endTurn();
                    break;
            }
        });
    }
}
