package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.board.Tile;
import com.sk.dungeonboardgame.mechanics.animations.FieldAnimationTimer;
import com.sk.dungeonboardgame.mechanics.animations.MonsterAnimationTimer;
import com.sk.dungeonboardgame.models.core.Position;
import com.sk.dungeonboardgame.models.core.enums.Direction;
import com.sk.dungeonboardgame.models.core.enums.ElementType;
import com.sk.dungeonboardgame.models.weapons.Weapon;
import com.sk.dungeonboardgame.state.GameState;
import com.sk.dungeonboardgame.state.Images;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Hero extends Creature {

    private int speedPoints = 50;
    private int attackPoints = 1;

    public Hero(String name, Position position, int health, Weapon weapon) {
        super(name, new ImageView(Images.hero), position, health, weapon, ElementType.Hero);
    }

    //<editor-fold desc="Actions">
    @Override
    public boolean move(Direction direction) {
        if(!GameState.isPlayerTurn) {
            return false;
        }

        if (speedPoints > 0) {
            if(!super.move(direction)) {
                return false;
            }
            speedPoints--;
        }

        // validate if tile switch is needed
        if(super.isOnTileBorder(direction)) {
            Point2D pos = tile.getNodeByPosition(this.position).localToScene(0,0);

            int offsetXChange = 0;
            int offsetYChange = 0;

            System.out.println("Is on tile border.");
            // if tile does not exist, initialize new tile
            if(!tile.hasNeighbour(direction)) {
                tile.addNeighbour(direction);
                System.out.println("New tile added.");
                if(direction == Direction.UP && pos.getY() == 0) {
                    offsetYChange = -200;
                    GameState.field.setTranslateY(offsetYChange);
                } else if(direction == Direction.LEFT && pos.getX() == 0) {
                    offsetXChange = -200;
                    GameState.field.setTranslateX(offsetXChange);
                }
            }

            // move screen to proper direction (if needed)

            pos = tile.getNodeByPosition(this.position).localToScene(0,0);

            System.out.println("Should be moved outside: " + pos);
            boolean triggerAnimation = pos.getX() == offsetXChange || pos.getY() == offsetYChange;

            if(pos.getX() == GameState.screenWidth - GameState.squareSize) {
                GameState.offsetX = GameState.offsetX - 200;
                triggerAnimation = true;
            } else if(pos.getY() == GameState.screenHeight - GameState.squareSize) {
                GameState.offsetY = GameState.offsetY - 200;
                triggerAnimation = true;
            }

            if(triggerAnimation) {
                FieldAnimationTimer timer = new FieldAnimationTimer(direction);
                timer.start();
            }

        }

        System.out.println(position.toText() + " " + tile.getNodeByPosition(this.position).localToScene(0,0));

        return true;
    }
    //</editor-fold>

    @Override
    public void attacked(Weapon weapon) {
        super.attacked(weapon);

        if (this.health <= 0) {
            this.health = 0;
            //this.isAlive = false;
            GameState.field.removeHero(this);

            System.out.println(name + " is dead!");
        }
    }

    //@Override
    public void attack(Creature target) {
        if(!GameState.isPlayerTurn) {
            return;
        }

        /*if(isNearMonster() && attackPoints > 0) {
            super.attack(target);
            attackPoints--;
        }*/
    }

    public void endTurn() {
        speedPoints = 2;
        attackPoints = 1;
        GameState.isPlayerTurn = false;
    }
}