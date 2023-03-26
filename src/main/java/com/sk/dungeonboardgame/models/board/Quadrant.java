package com.sk.dungeonboardgame.models.board;

import com.sk.dungeonboardgame.board.Field;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.creatures.Monster;
import com.sk.dungeonboardgame.models.weapons.Claws;
import com.sk.dungeonboardgame.state.GameState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Quadrant {
    private int id;
    private Position startPosition;
    private List<BoardElement> elements = new ArrayList<BoardElement>();
    private Rectangle[][] tileRectangles;
    private boolean isDiscovered = false;

    public Quadrant(int id, Position startPosition) {
        this.id = id;
        this.startPosition = startPosition;
        this.tileRectangles = new Rectangle[GameState.quadrantSize][GameState.quadrantSize];

        initQuadrant();
    }

    private void initQuadrant() {
        for(int row = 0; row < GameState.quadrantSize; row++) {
            for(int column = 0; column < GameState.quadrantSize; column++) {
                Color color = isDiscovered
                    ? Color.TRANSPARENT
                    : new Color(0.2f, 0.2f, 0.2f, 0.95f);

                GameState.field.removeElement(tileRectangles[row][column]);
                tileRectangles[row][column] = new Rectangle(GameState.squareSize, GameState.squareSize, color);
                GameState.field.addTile(tileRectangles[row][column], new Position(startPosition.row + row, startPosition.column + column));
            }
        }
    }

    public void setDiscovered() {
        setDiscovered(false);
    }

    public void setDiscovered(boolean initialQuadrant) {
        if(isDiscovered) {
            return;
        }

        isDiscovered = true;
        initQuadrant();

        // if this is initial quadrant - we don't want any monsters or walls
        if(initialQuadrant) {
            return;
        }
        // cast a monster!
        Monster m = new Monster("Ninja", new Position().getRandomPosition(this.getStartPosition(), GameState.quadrantSize), 7, new Claws());
        GameState.field.addElement(m);

        // cast random walls
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public int getId() {
        return id;
    }
}
