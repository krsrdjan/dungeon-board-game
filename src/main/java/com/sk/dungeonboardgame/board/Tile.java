package com.sk.dungeonboardgame.board;

import com.sk.dungeonboardgame.models.creatures.Creature;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.models.creatures.Monster;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Tile extends GridPane {

    private static final int tileLength = 8;
    private static final int squareSize = 100;

    private List<Monster> monsters = new ArrayList<>();
    private Hero hero;

    public Tile() {
        super();
        super.setBackground(
                new Background(
                    new BackgroundImage(
                            new Image(Tile.class.getResourceAsStream("/images/tiles/tile.png")),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(
                                        tileLength * squareSize / 2,
                                        tileLength * squareSize / 2,
                                        false,
                                        false,
                                        false,
                                        false))
                    )
                );

        for (int row = 0; row < tileLength; row++) {
            for (int column = 0; column < tileLength; column++) {
                super.add(new Rectangle(squareSize, squareSize, Color.TRANSPARENT), column, row);
            }
        }
    }

    public void updateCreaturePosition(Creature creature, int row, int column) {
        for (int i = 0; i < tileLength; i++) {
            for (int j = 0; j < tileLength; j++) {
                if(row == i && column == j) {
                    //remove creature from old position
                    this.getChildren().remove(creature.getImageView());

                    //add creature to new position
                    ImageView imageView = creature.getImageView();
                    imageView.setFitWidth(squareSize);
                    imageView.setFitHeight(squareSize);
                    super.add(imageView, j, i);

                    creature.updateCurrentPosition(row, column, this);
                }
            }
        }
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        this.updateCreaturePosition(hero, hero.getCurrentRow(), hero.getCurrentColumn());
    }

    public Hero getHero() {
        return hero;
    }

    public void removeHero(Hero hero) {
        this.getChildren().remove(hero.getImageView());
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
        this.updateCreaturePosition(monster, monster.getCurrentRow(), monster.getCurrentColumn());
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
        this.getChildren().remove(monster.getImageView());
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

}
