package com.sk.dungeonboardgame.board;

import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.board.TileVisibility;
import com.sk.dungeonboardgame.models.collectables.Collectable;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
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
    private List<BoardElement> elements = new ArrayList<>();
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
                Color color = isInHeroRange(new Position(row, column))
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

    private boolean isInHeroRange(Position pos) {
        var distance = hero.getPosition().getDistance(pos);

        return distance < GameState.visibilityRange;
    }

    public void updatePosition(BoardElement element, Position position) {
        this.getChildren().remove(element.getImageView());

        if(isInHeroRange(position)) {
            var imageView = element.getImageView();
            //add creature to new position
            imageView.setFitWidth(squareSize);
            imageView.setFitHeight(squareSize);
            super.add(imageView, position.column, position.row);
        }

        element.updatePosition(position, this);

        calculateVisibility();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        this.updatePosition(hero, hero.getPosition());
    }

    public Hero getHero() {
        return hero;
    }

    public void removeHero(Hero hero) {
        this.getChildren().remove(hero.getImageView());
    }

    public void addElement(BoardElement element) {
        elements.add(element);
        this.updatePosition(element, element.getPosition());
    }

    public void removeElement(BoardElement element) {
        elements.remove(element);
        this.getChildren().remove(element.getImageView());
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public boolean isPlaceTaken(Position position) {
        // validate borders first
        if(isOutOfBounds(position)) {
            return true;
        }

        // validate monsters positions
        for(Monster monster : monsters) {
            if(monster.getPosition().isCollided(position)) {
                return true;
            }
        }

        // validate hero position
        if(hero.getPosition().isCollided(position)) {
            return true;
        }

        return false;
    }

    private boolean isOutOfBounds(Position position) {
        if(position.row < 0 || position.row >= tileLength)
            return true;

        if(position.column < 0 || position.column >= tileLength)
            return true;

        return false;
    }

    public void processIfCollidedWithCollectable()  {
        elements.stream().filter(x -> x.getType() == ElementType.Coin).forEach(x -> {
            Collectable c = (Collectable)x;
            if(hero.isCollided(c)) {
                removeElement(c);
                c.collect();
            }
        });
    }
}
