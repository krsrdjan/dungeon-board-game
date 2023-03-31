package com.sk.dungeonboardgame;

import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.mechanics.MonsterAnimationTimer;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.core.helpers.HelperMethods;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.models.weapons.ShortSword;
import com.sk.dungeonboardgame.state.GameState;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoardGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Field field = new Field();

        Hero hero = new Hero("Fighter", new Position(1, 2), 10, new ShortSword());
        field.setHero(hero);

        Scene scene = new Scene(field, 800, 800, Color.BLACK);

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
                    hero.move(HelperMethods.keyStrokeToDirection(event.getCode()));
                    break;
                case I:
                    //hero.attack();
                    break;
                case O:
                    hero.endTurn();
                    MonsterAnimationTimer timer = new MonsterAnimationTimer(GameState.field.getElements(ElementType.Monster));
                    timer.start();
                    break;
            }
        });
    }
}
