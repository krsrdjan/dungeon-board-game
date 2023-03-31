package com.sk.dungeonboardgame.board;

import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.board.Wall;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.core.helpers.HelperMethods;
import com.sk.dungeonboardgame.state.GameState;
import com.sk.dungeonboardgame.state.Images;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Tile extends GridPane {
    private Position position;
    private List<BoardElement> elements = new ArrayList<BoardElement>();
    private Rectangle[][] squares;
    // tile neighbours
    HashMap<Direction, Tile> tileNeighbours = new HashMap<Direction, Tile>();

    //<editor-fold desc="Initialization">
    public Tile() {
        super();
        super.setBackground(
                new Background(
                        new BackgroundImage(
                                Images.tileImage,
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(
                                        GameState.tileSize * GameState.squareSize,
                                        GameState.tileSize * GameState.squareSize,
                                        false,
                                        false,
                                        false,
                                        false))
                )
        );

        this.squares = new Rectangle[GameState.tileSize][GameState.tileSize];

        initTile();
        initSetup();
    }

    private void initTile() {
        for(int row = 0; row < GameState.tileSize; row++) {
            for(int column = 0; column < GameState.tileSize; column++) {
                Color color = true
                        ? Color.TRANSPARENT
                        : new Color(0.2f, 0.2f, 0.2f, 0.95f);

                this.removeElement(squares[row][column]);
                squares[row][column] = new Rectangle(GameState.squareSize, GameState.squareSize, color);
                this.addSquare(squares[row][column], new Position(row, column));
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Element methods">
    public void addSquare(Rectangle rect, Position pos) {
        super.add(rect, pos.column, pos.row);
    }

    public void addElement(BoardElement element) {
        element.setTile(this);
        elements.add(element);
        System.out.println("Current: " + element.getPosition().toText());
        System.out.println("Relative: " + element.getPosition().getRelativePosition().toText());
        this.setPosition(element, element.getPosition().getRelativePosition());
    }

    public void removeElement(BoardElement element) {
        elements.remove(element);
        this.getChildren().remove(element.getImageView());
    }

    public void removeElement(Object obj) {
        this.getChildren().remove(obj);
    }

    public <T> List<T> getElements(ElementType type) {
        if(elements == null || elements.size() == 0) {
            return new ArrayList<T>();
        }

        return elements.stream().filter(x -> x.getType() == type).map(x -> (T)x).toList();
    }

    public <T> List<T> getElements(List<ElementType> types) {
        if(elements == null || elements.size() == 0) {
            return new ArrayList<T>();
        }

        return elements.stream().filter(x -> types.contains(x.getType())).map(x -> (T)x).toList();
    }

    public List<BoardElement> getObstacles() {
        return getElements(BoardElement.getObstacleTypes());
    }
    //</editor-fold>

    //<editor-fold desc="UI methods">
    public void setPosition(BoardElement element, Position position) {
        System.out.println("Started updating position for " + element.getType());
        this.getChildren().remove(element.getImageView());

        System.out.println("Element " + element.getType() + " removed");

        var imageView = element.getImageView();
        //add creature to new position
        imageView.setFitWidth(GameState.squareSize);
        imageView.setFitHeight(GameState.squareSize);
        super.add(imageView, position.column, position.row);
        System.out.println("Element " + element.getType() + " placed on position (" + position.column + ", " + position.row + ")");

        element.setPosition(position);
    }
    //</editor-fold>

    //<editor-fold desc="Tile Neighbour methods">
    public boolean hasNeighbour(Direction direction) {
        return tileNeighbours.containsKey(direction);
    }

    public Tile getNeighbour(Direction direction) {
        return tileNeighbours.get(direction);
    }

    public void setNeighbour(Direction direction, Tile tile) {
        tileNeighbours.put(direction, tile);
    }

    public void addNeighbour(Direction direction) {
        Tile neighbour = new Tile();
        neighbour.initSetup();
        GameState.field.addTile(neighbour, direction);
        tileNeighbours.put(direction, neighbour);
        neighbour.setNeighbour(HelperMethods.getReversedDirection(direction), this);
    }

    private void initSetup() {
        addElement(new Wall(new Position(0, 0)));
        addElement(new Wall(new Position(0, 3)));
        addElement(new Wall(new Position(3, 0)));
        addElement(new Wall(new Position(3, 3)));
    }
    //</editor-fold>
}
