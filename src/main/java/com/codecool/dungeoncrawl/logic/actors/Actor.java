package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.items.HealthBoost;
import com.codecool.dungeoncrawl.logic.items.ItemsOnTheMap;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Paper;
import com.codecool.dungeoncrawl.logic.items.StrengthBoost;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Actor implements Drawable {
    protected boolean isAlive = true;
    protected Cell cell;
    private int health;
    private int strength;
    private boolean isAbleToMove;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public int returnRandomCoordinate() {
        List<Integer> possibleMoves = new ArrayList<>();
        possibleMoves.add(-1);
        possibleMoves.add(0);
        possibleMoves.add(1);
        Random randomCoordinate = new Random();
        int randomElement = possibleMoves.get(randomCoordinate.nextInt(possibleMoves.size()));
        return randomElement;
    };

    public void moveActorRandomly(Actor actor) {
        int dx = returnRandomCoordinate();
        int dy = returnRandomCoordinate();
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.FLOOR))
        {
            actor.cell.setType(CellType.FLOOR);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        else {
            moveActorRandomly(actor);
        }
    }
    public void moveActorInLine(Actor actor, String direction) {
        int dx = 0;
        int dy = 0;
        if (direction.equals("north")) {
            dy = -1;
        } else if (direction.equals("south")) {
            dy = 1;
        } else if (direction.equals("west")) {
            dx = -1;
        } else if (direction.equals("east")) {
            dx = 1;
        }
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.FLOOR)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else {
            if (direction.equals("north")) {
//                moveActorInLine("south");
            } else if (direction.equals("south")) {
//                moveActorInLine("north");
            } else if (direction.equals("west")) {
//                moveActorInLine("east");
            } else if (direction.equals("east")) {
//                moveActorInLine("west");
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAbleToMove() {
        return isAbleToMove;
    }

    public void setAbleToMove(boolean ableToMove) {
        isAbleToMove = ableToMove;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

}
