package com.sk.dungeonboardgame.models.core;

import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.models.core.helpers.HelperMethods;
import com.sk.dungeonboardgame.state.GameState;

public class Position {
    public int row;
    public int column;

    public Position() {

    }
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isCollided(Position pos) {
        return this.row == pos.row && this.column == pos.column;
    }

    public double getDistance(Position pos) {
        int heroRow = this.row;
        int heroColumn = this.column;

        double x = Math.pow(Math.abs(pos.row - heroRow), 2);
        double y = Math.pow(Math.abs(pos.column - heroColumn), 2);

        return Math.round(Math.sqrt(x + y));
    }

    public Position getDifference(Position pos) {
        return new Position(this.row - pos.row, this.column - pos.column);
    }

    public Position clone() {
        return new Position(this.row, this.column);
    }

    public Position getRelativePosition() {
        Position pos = new Position(this.row % GameState.tileSize, this.column % GameState.tileSize);

        if(pos.row < 0) {
            pos.row += GameState.tileSize;
        }

        if(pos.column < 0) {
            pos.column += GameState.tileSize;
        }

        return pos;
    }

    public boolean isOnSameTile(Position pos) {
        Position startPos = new Position(this.row - pos.row % GameState.tileSize, this.column - pos.column % GameState.tileSize);

        return (HelperMethods.isNumberBetween(row, startPos.row, startPos.row + GameState.tileSize)
             && HelperMethods.isNumberBetween(row, startPos.row, startPos.row + GameState.tileSize));
    }

    public Position addRow(int add){
        return new Position(this.row + add, this.column);
    }

    public Position addColumn(int add){
        return new Position(this.row, this.column + add);
    }

    public static Position getRandomPosition(Position startPosition, int size) {
        return new Position(startPosition.row + (int)(Math.random() * size), startPosition.column + (int)(Math.random() * size));
    }

    public Position getPositionByDirection(Direction direction) {
        switch (direction) {
            case UP:
                return new Position(this.row - 1, this.column);
            case DOWN:
                return new Position(this.row + 1, this.column);
            case LEFT:
                return new Position(this.row, this.column - 1);
            case RIGHT:
                return new Position(this.row, this.column + 1);
            default:
                return null;
        }
    }

    public String toText() {
        return "Row: " + this.row + " Column: " + this.column;
    }
}
