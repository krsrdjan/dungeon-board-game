package com.sk.dungeonboardgame.board;

import com.sk.dungeonboardgame.models.board.TileVisibility;
import com.sk.dungeonboardgame.models.collectables.Collectable;
import com.sk.dungeonboardgame.models.creatures.Creature;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.models.creatures.Monster;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Tile extends GridPane {

    private static final int tileLength = 16;
    private static final int squareSize = 50;

    private TileVisibility[][] tileVisibility = new TileVisibility[16][16];

    private List<Monster> monsters = new ArrayList<>();
    private List<Collectable> collectables = new ArrayList<>();
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
                                        tileLength * squareSize / 4,
                                        tileLength * squareSize / 4,
                                        false,
                                        false,
                                        false,
                                        false))
                )
        );
    }

    public void calculateVisibility() {
        for (int row = 0; row < tileLength; row++) {
            for (int column = 0; column < tileLength; column++) {
                Color color = isInHeroRange(row, column)
                        ? Color.TRANSPARENT
                        : new Color(0.2f, 0.2f, 0.2f, 0.9f);

                TileVisibility tile = tileVisibility[column][row];

                if(tile == null) {
                    tile = tileVisibility[column][row] = new TileVisibility(color, squareSize);
                }
                else if(tile.color == color) {
                    continue;
                }

                super.getChildren().remove(tile.rectangle);
                tileVisibility[column][row] = new TileVisibility(color, squareSize);
                super.add(tileVisibility[column][row].rectangle, column, row);
            }
        }
    }

    private boolean isInHeroRange(int row, int column) {
        int heroRow = hero.getCurrentRow();
        int heroColumn = hero.getCurrentColumn();

        double x = Math.pow(Math.abs(row - heroRow), 2);
        double y = Math.pow(Math.abs(column - heroColumn), 2);

        return Math.round(Math.sqrt(x + y)) < GameState.visibilityRange;
    }

    public void updateCreaturePosition(Creature creature, int row, int column) {
        this.getChildren().remove(creature.getImageView());

        if(isInHeroRange(row, column)) {
            //add creature to new position
            ImageView imageView = creature.getImageView();
            imageView.setFitWidth(squareSize);
            imageView.setFitHeight(squareSize);
            super.add(imageView, column, row);
        }

        creature.updateCurrentPosition(row, column, this);
        calculateVisibility();
    }

    public void updateCollectablePosition(Collectable collectable, int row, int column) {

    }

    private void updatePosition() {

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

    public void addCollectable(Collectable collectable) {
        collectables.add(collectable);
        //this.updateCreaturePosition(monster, monster.getCurrentRow(), monster.getCurrentColumn());
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
        this.getChildren().remove(monster.getImageView());
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public boolean isPlaceTaken(int column, int row) {
        // validate borders first
        if(isOutOfBounds(column, row)) {
            return true;
        }

        // validate monsters positions
        for(Monster monster : monsters) {
            if(monster.getCurrentColumn() == column && monster.getCurrentRow() == row) {
                return true;
            }
        }

        // validate hero position
        if(hero.getCurrentColumn() == column && hero.getCurrentRow() == row) {
            return true;
        }

        return false;
    }

    private boolean isOutOfBounds(int column, int row) {
        if(column < 0 || column >= tileLength)
            return true;

        if(row < 0 || row >= tileLength)
            return true;

        return false;
    }
}
