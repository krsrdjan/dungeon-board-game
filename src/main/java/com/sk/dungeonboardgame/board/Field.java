package com.sk.dungeonboardgame.board;

import com.sk.dungeonboardgame.models.board.BoardElement;
import com.sk.dungeonboardgame.models.collectables.Collectable;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.core.helpers.HelperMethods;
import com.sk.dungeonboardgame.models.creatures.Creature;
import com.sk.dungeonboardgame.models.creatures.Hero;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Field extends GridPane {
    private List<Tile> tiles = new ArrayList<Tile>();
    private Hero hero;

    //<editor-fold desc="Initialization">
    public Field() {
        super();
        GameState.field = this;

        tiles.add(new Tile());
        tiles.get(0).initSetup(null);
        super.add(tiles.get(0), 0, 0);
    }

    //</editor-fold>

    //<editor-fold desc="Hero methods">
    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        addElement(hero);
    }

    public void removeHero(Hero hero) {
        this.getChildren().remove(hero.getImageView());
    }
    //</editor-fold>

    //<editor-fold desc="Tile methods">
    public void addTile(Tile tile, Direction direction) {
        addTile(tile, getTilePosition(hero.getTile()).getPositionByDirection(direction));
    }
    public void addTile(Tile tile, Position position) {
        if(position.column < 0 || position.row < 0) {
            List<Node> nodes = this.getChildren().stream().filter(x -> x != tile).toList();

            if (position.column < 0) {
                for (Node el : nodes) {
                    this.setColumnIndex(el, this.getColumnIndex(el) + 1);
                }
                position.column = 0;
            } else if (position.row < 0) {
                for (Node el : nodes) {
                    this.setRowIndex(el, this.getRowIndex(el) + 1);
                }
                position.row = 0;
            }
        }

        tiles.add(tile);
        super.add(tile, position.column, position.row);
    }

    private Tile getTileByAboslutePosition(Position position) {
        List<Tile> tileList = tiles.stream().filter(tile -> getTilePosition(tile).isOnSameTile(position)).toList();

        if(tileList.size() == 0) {
            return null;
        }

        return tileList.get(0);
    }

    private Position getTilePosition(Tile tile) {
        return new Position(this.getRowIndex(tile), this.getColumnIndex(tile));
    }

    public void setTileNeighbours(Tile tile) {
        Position pos = getTilePosition(tile);

        Hashtable<String, Direction> coords = new Hashtable<String, Direction>();
        coords.put((pos.row - 1) + "," + (pos.column - 1), Direction.UPLEFT);
        coords.put((pos.row - 1) + "," + (pos.column), Direction.UP);
        coords.put((pos.row - 1) + "," + (pos.column + 1), Direction.UPRIGHT);
        coords.put((pos.row) + "," + (pos.column - 1), Direction.LEFT);
        coords.put((pos.row) + "," + (pos.column + 1), Direction.RIGHT);
        coords.put((pos.row + 1) + "," + (pos.column - 1), Direction.DOWNLEFT);
        coords.put((pos.row + 1) + "," + (pos.column), Direction.DOWN);
        coords.put((pos.row + 1) + "," + (pos.column + 1), Direction.DOWNRIGHT);

        for(Node n : this.getChildren()) {
            int row = this.getRowIndex(n);
            int column = this.getColumnIndex(n);

            String key = row +  "," + column;

            if(coords.containsKey(key)) {
                MapNeighbours(tile, (Tile)n, coords.get(key));
            }
        }
    }

    private void MapNeighbours(Tile source, Tile destination, Direction direction) {
        source.setNeighbour(direction, destination);
        destination.setNeighbour(HelperMethods.getReversedDirection(direction), source);
    }
    //</editor-fold>

    //<editor-fold desc="Element methods">
    public void addElement(BoardElement element) {
        Tile tile = getTileByAboslutePosition(element.getPosition());

        if(tile == null) {
            return;
        }

        tile.addElement(element);
    }

    public void removeElement(BoardElement element) {
        Tile tile = getTileByAboslutePosition(element.getPosition());

        if(tile == null) {
            return;
        }

        tile.removeElement(element);
    }

    public <T> List<T> getElements(ElementType type) {
        List<ElementType> types = new ArrayList<ElementType>();
        types.add(type);
        return getElements(types);
    }

    public <T> List<T> getElements(List<ElementType> types) {
        List<T> elements = new ArrayList<T>();
        tiles.forEach(x -> elements.addAll(x.getElements(types)));
        return elements;
    }

    public List<BoardElement> getObstacles() {
        return getElements(BoardElement.getObstacleTypes());
    }

    //</editor-fold>

    //<editor-fold desc="Collider methods">
    public boolean isValidMove(Creature creature, Position pos) {
        // validate borders first
        for(BoardElement el : creature.getTile().getObstacles()) {
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
    //</editor-fold>
}
