package com.sk.dungeonboardgame;

import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.models.weapons.ShortSword;
import com.sk.dungeonboardgame.state.GameState;
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
        Field field = new Field();

        GameState.field = field;

        Hero hero = new Hero("Fighter", new Position(6, 2), 10, new ShortSword());

        field.setHero(hero);

        GridPane container = new GridPane();
        container.add(field, 0,0);

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
                    //hero.attack();
                    break;
                case O:
                    hero.endTurn();
                    //MonsterAnimationTimer timer = new MonsterAnimationTimer(grid.getMonsters());
                    //timer.start();
                    break;
            }
        });
    }
}
