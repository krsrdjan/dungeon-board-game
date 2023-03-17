package com.sk.dungeonboardgame.models.creatures;

import com.sk.dungeonboardgame.models.weapons.Weapon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Hero extends Creature {

    private int speedPoints = 2;
    private int attackPoints = 1;

    private ImageView imageView = new ImageView(new Image("/images/heroes/knight.png"));

    public Hero(String name, int health, Weapon weapon, int row, int column) {
        super(name, health, weapon, row, column);
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void attacked(Weapon weapon) {
        super.attacked(weapon);

        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
            this.tile.removeHero(this);

            System.out.println(name + " is dead!");
        }
    }

    @Override
    public void moveUp() {
        if(checkMonsterExist(currentRow - 1, currentColumn)) {
            return;
        }
        if (speedPoints > 0) {
            super.moveUp();
            speedPoints--;
        }
    }

    @Override
    public void moveDown() {
        if(checkMonsterExist(currentRow + 1, currentColumn)) {
            return;
        }
        if (speedPoints > 0) {
            super.moveDown();
            speedPoints--;
        }
    }

    @Override
    public void moveLeft() {
        if(checkMonsterExist(currentRow, currentColumn - 1)) {
            return;
        }
        if (speedPoints > 0) {
            super.moveLeft();
            speedPoints--;
        }
    }

    @Override
    public void moveRight() {
        if(checkMonsterExist(currentRow, currentColumn + 1)) {
            return;
        }

        if (speedPoints > 0) {
            super.moveRight();
            speedPoints--;
        }
    }

    @Override
    public void attack(Creature target) {
        if(isNearMonster() && attackPoints > 0) {
            super.attack(target);
            attackPoints--;
        }
    }

    private boolean checkMonsterExist(int row, int column) {
        for(Monster m : tile.getMonsters()) {
            int monsterRow = m.getCurrentRow();
            int monsterColumn = m.getCurrentColumn();

            if(monsterRow == row && monsterColumn == column) {
                return true;
            }
        }
        return false;
    }

    public boolean isNearMonster() {
        for(Monster m : tile.getMonsters()) {
           int monsterRow = m.getCurrentRow();
           int monsterColumn = m.getCurrentColumn();

           if(Math.abs(monsterRow - currentRow) <= 1 && Math.abs(monsterColumn - currentColumn) <= 1) {
              return true;
           }
        }
        return false;
    }

    public Monster getClosestMonster(){
        Monster closestMonster = null;
        int closestDistance = Integer.MAX_VALUE;

        for(Monster m : tile.getMonsters()) {
            int monsterRow = m.getCurrentRow();
            int monsterColumn = m.getCurrentColumn();

            int distance = Math.abs(monsterRow - currentRow) + Math.abs(monsterColumn - currentColumn);

            if(distance < closestDistance) {
                closestDistance = distance;
                closestMonster = m;
            }
        }
        return closestMonster;
    }

    public void endTurn() {
        tile.getMonsters().forEach(m -> m.monsterTurn());
        speedPoints = 2;
        attackPoints = 1;
    }
}
