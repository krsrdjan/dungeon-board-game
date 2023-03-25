package com.sk.dungeonboardgame.models.core;

public class Position {
    public int row;
    public int column;

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
}
