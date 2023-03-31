package com.sk.dungeonboardgame.state;

import com.sk.dungeonboardgame.board.Field;

public class GameState {
    // settings
    public static int tileSize = 4;
    public static int squareSize = 50;
    // state
    public static Field field;
    public static boolean isPlayerTurn = true;
    public static int turnCount = 1;
}