package com.sk.dungeonboardgame.board;

import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.board.Quadrant;
import com.sk.dungeonboardgame.models.collectables.Collectable;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Field extends GridPane {
    private List<Quadrant> quadrants = new ArrayList<Quadrant>();
    private List<BoardElement> elements = new ArrayList<BoardElement>();
    private Hero hero;

    //<editor-fold desc="Initialization">
    public Field() {
        super();
        super.setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(Field.class.getResourceAsStream("/images/tiles/tile.png")),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(
                                        GameState.fieldSize * GameState.squareSize / 4,
                                        GameState.fieldSize * GameState.squareSize / 4,
                                        false,
                                        false,
                                        false,
                                        false))
                )
        );
    }

    public void initField() {
        int idCount = 0;

        for (int row = 0; row < GameState.fieldSize / GameState.quadrantSize; row++) {
            for (int column = 0; column < GameState.fieldSize / GameState.quadrantSize; column++) {
                quadrants.add(new Quadrant(idCount++, new Position(row * GameState.quadrantSize, column * GameState.quadrantSize)));
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Hero methods">
    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        this.updatePosition(hero, hero.getPosition());
    }

    public void removeHero(Hero hero) {
        this.getChildren().remove(hero.getImageView());
    }

    public void triggerCollectable() {
        List<Collectable> collectables = getElements(ElementType.Collectable);
        for(Collectable c : collectables) {
            if(hero.isCollided(c)) {
                c.collect();
                removeElement(c);
                return;
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Element methods">
    public void addElement(BoardElement element) {
        elements.add(element);
        this.updatePosition(element, element.getPosition());
    }

    public void removeElement(BoardElement element) {
        elements.remove(element);
        this.getChildren().remove(element.getImageView());
    }

    public <T> List<T> getElements(ElementType type) {
        if(elements == null || elements.size() == 0) {
            return new ArrayList<T>();
        }

        return elements.stream().filter(x -> x.getType() == type).map(x -> (T)x).toList();
    }

    public List<BoardElement> getObstacles() {
        if(elements == null || elements.size() == 0) {
            return new ArrayList<>();
        }

        return elements.stream().filter(x -> x.isObstacle()).toList();
    }
    //</editor-fold>

    //<editor-fold desc="Supporting actions for UI methods">
    public void addTile(ImageView imageView, Position pos) {
        super.add(imageView, pos.column, pos.row);
    }

    public void addTile(Rectangle rect, Position pos) {
        super.add(rect, pos.column, pos.row);
    }

    public void removeElement(Object obj) {
        this.getChildren().remove(obj);
    }

    public void updatePosition(BoardElement element, Position position) {
        System.out.println("Started updating position for " + element.getType());
        this.getChildren().remove(element.getImageView());

        System.out.println("Element " + element.getType() + " removed");

        var imageView = element.getImageView();
        //add creature to new position
        imageView.setFitWidth(GameState.squareSize);
        imageView.setFitHeight(GameState.squareSize);
        super.add(imageView, position.column, position.row);
        System.out.println("Element " + element.getType() + " placed on position (" + position.column + ", " + position.row + ")");

        element.updatePosition(position);
    }
    //</editor-fold>

    //<editor-fold desc="Quadrant logic">
    public void uncoverNearestQuadrant() {
        Position heroPos = this.hero.getPosition();

        uncoverQuadrant(getQuadrant(heroPos.addRow(-1)).getId());
        uncoverQuadrant(getQuadrant(heroPos.addRow(1)).getId());
        uncoverQuadrant(getQuadrant(heroPos.addColumn(-1)).getId());
        uncoverQuadrant(getQuadrant(heroPos.addColumn(1)).getId());
    }
    private void uncoverQuadrant(int id) {
        Quadrant quadrant = quadrants.get(id);

        if(hero.getQuadrant().getId() == id) {
            return;
        }

        if(quadrant != null) {
            quadrant.setDiscovered();
        }
    }

    public Quadrant getQuadrant(Position pos) {
        for(Quadrant q : quadrants) {
            if(q.getStartPosition().isInSameQuadrant(pos)) {
                return q;
            }
        }

        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Collider methods">
    public boolean isValidMove(Position pos) {
        // validate borders first
        if(isOutOfBounds(pos)) {
            System.out.println("Position is out of bounds");
            return false;
        }

        for(BoardElement el : getObstacles()) {
            if(pos.isCollided(el.getPosition())) {
                System.out.println("Position is taken by another element");
                return false;
            }
        }

        if(pos.isCollided(hero.getPosition())) {
            System.out.println("Position is taken by hero");
            return false;
        }

        System.out.println("Move is valid");
        return true;
    }

    private boolean isOutOfBounds(Position pos) {
        if (pos.row < 0 || pos.row >= GameState.fieldSize)
            return true;

        if (pos.column < 0 || pos.column >= GameState.fieldSize)
            return true;

        return false;
    }
    //</editor-fold>
}
