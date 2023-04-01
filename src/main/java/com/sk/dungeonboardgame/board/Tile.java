package com.sk.dungeonboardgame.board;

import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.board.Wall;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.core.helpers.HelperMethods;
import com.sk.dungeonboardgame.models.creatures.Monster;
import com.sk.dungeonboardgame.models.weapons.BattleAxe;
import com.sk.dungeonboardgame.state.GameState;
import com.sk.dungeonboardgame.state.Images;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

import static java.util.Map.entry;

public class Tile extends GridPane {
    private Position position;
    private List<BoardElement> elements = new ArrayList<BoardElement>();
    private Rectangle[][] squares;
    private Map<List<Direction>, String> wallMap = Map.ofEntries(
        entry(Arrays.asList(new Direction[] { Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT }), "1001000020001001"),
        entry(Arrays.asList(new Direction[] { Direction.UP, Direction.DOWN, Direction.LEFT }), "1001100210001001"),
        entry(Arrays.asList(new Direction[] { Direction.UP, Direction.DOWN, Direction.RIGHT }), "1001000120011001"),
        entry(Arrays.asList(new Direction[] { Direction.UP, Direction.LEFT, Direction.RIGHT }), "1111000000201001"),
        entry(Arrays.asList(new Direction[] { Direction.UP, Direction.DOWN }), "1001102110011001"),
        entry(Arrays.asList(new Direction[] { Direction.UP, Direction.LEFT }), "1111100012001001"),
        entry(Arrays.asList(new Direction[] { Direction.UP, Direction.RIGHT }), "1111000100211001"),
        entry(Arrays.asList(new Direction[] { Direction.DOWN, Direction.LEFT, Direction.RIGHT }), "1001000020001111"),
        entry(Arrays.asList(new Direction[] { Direction.DOWN, Direction.LEFT, }), "1001100210001111"),
        entry(Arrays.asList(new Direction[] { Direction.DOWN, Direction.RIGHT }), "1001000120011111"),
        entry(Arrays.asList(new Direction[] { Direction.LEFT, Direction.RIGHT }), "1111000002001111")
    );

    // tile neighbours
    HashMap<Direction, Tile> tileNeighbours = new HashMap<Direction, Tile>();

    //<editor-fold desc="Initialization">
    public Tile() {
        super();
        super.setBackground(
                new Background(
                        new BackgroundImage(
                                Images.tile,
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

    public Node getNodeByPosition(Position pos) {
        for(Node node : this.getChildren()) {
            if(this.getRowIndex(node) == pos.row && this.getColumnIndex(node) == pos.column) {
                return node;
            }
        }

        return null;
    }

    //</editor-fold>

    //<editor-fold desc="UI methods">
    public void setPosition(BoardElement element, Position position) {
        this.getChildren().remove(element.getImageView());


        var imageView = element.getImageView();
        //add creature to new position
        imageView.setFitWidth(GameState.squareSize);
        imageView.setFitHeight(GameState.squareSize);
        super.add(imageView, position.column, position.row);

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
        neighbour.initSetup(direction);
        GameState.field.addTile(neighbour, direction);
        GameState.field.setTileNeighbours(neighbour);
    }

    //</editor-fold>

    public void initSetup(Direction direction) {
        String wall;
        if(direction == null) {
            wall = "1111100010001001";
        } else {
            Random rand = new Random();
            List<String> wallOptions = new ArrayList<String>();
            wallMap.keySet().stream().filter(x -> x.contains(direction)).forEach(x -> wallOptions.add(wallMap.get(x)));
            wall = wallOptions.get(rand.nextInt(wallOptions.size()));
        }

        System.out.println("wall: " + wall);

        for(int r = 0; r < GameState.tileSize; r++) {
            for(int c = 0; c < GameState.tileSize; c++) {
                char chr = wall.charAt(r * GameState.tileSize + c);

                switch(chr) {
                    case '0':
                        break;
                    case '1':
                        addElement(new Wall(new Position(r, c)));
                        break;
                    case '2':
                        Random rand = new Random();
                        addElement(new Monster("Ninja", new Position(r, c), 5, new BattleAxe(), rand.nextInt(2) == 0 ? Images.ninja : Images.dragonborn));

                        break;
                }
            }
        }
    }
}
