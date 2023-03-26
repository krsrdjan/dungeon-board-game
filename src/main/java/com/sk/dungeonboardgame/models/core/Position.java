package com.sk.dungeonboardgame.models.core;

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

    public boolean isInSameQuadrant(Position pos) {
        Position startPos = new Position(pos.row - pos.row % 4, pos.column - pos.column % 4);

        if(startPos.row <= row && startPos.row + 4 >= row && startPos.column <= column && startPos.column + 4 >= column)
            return true;

        if(startPos.row <= row && startPos.row + 4 >= row && startPos.column <= column && startPos.column + 4 >= column)
            return true;

        return false;
    }

    public Position addRow(int add){
        return new Position(this.row + add, this.column);
    }

    public Position addColumn(int add){
        return new Position(this.row, this.column + add);
    }

    public Position getRandomPosition(Position startPosition, int size) {
        return new Position(startPosition.row + (int)(Math.random() * size), startPosition.column + (int)(Math.random() * size));
    }
}
