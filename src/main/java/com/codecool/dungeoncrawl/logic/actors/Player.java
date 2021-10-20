package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Player extends Actor {
    private String name = "";
//    private Cell cell;
    private boolean hasKey = false;
    private boolean pickUpKey = false;
    private boolean pickUpPaper = true;
    private boolean isAlive = true;
    private boolean escaped = false;


    public Player(Cell cell) {
        super(cell);
//        this.setCell(cell);
        this.setStrength(3);
        this.setHealth(14);
    }

    public String getTileName() {
        return "player";
    }

    public boolean isEscaped() {
        return escaped;
    }

    public void setEscaped(boolean escaped) {
        this.escaped = escaped;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    @Override
    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.WALL)) {
            if (this.getName().equals("Maciej") || this.getName().equals("Michał")){
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
        else if (nextCell.getType().equals(CellType.DOORSCLOSED)) {
            if (hasKey){
                this.setHasKey(false);
                nextCell.setType(CellType.DOORSOPEN);
            }
            else {
                doorsClosed();
            }
        }
        else if (nextCell.getType().equals(CellType.SPIDER) ||
                nextCell.getType().equals(CellType.GOLUM) ||
                nextCell.getType().equals(CellType.SKELETON)) {
            this.fighting(nextCell.getActor(), nextCell);
        }
        else if (nextCell.getType() == CellType.STRENGTHPOTION || nextCell.getType() == CellType.HEALTHPOTION) {
            this.itemPicking(nextCell.getItem(), nextCell);
            nextCell.setType(CellType.FLOOR);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        else if (nextCell.getType() == CellType.KEY) {
            this.pickUpKey(nextCell);
        }
        else if (nextCell.getType() == CellType.PAPER) {
            this.pickUpPaper(nextCell);
        }
        else if (nextCell.getType().equals(CellType.DOORSOPEN)) {
            setEscaped(true);
        }

        else {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    private void doorsClosed() {
        Stage doorsClosed = new Stage();
        Label cantGo = new Label("You can't go through closed doors. \n\nFind the key!");
        Button closeButton = new Button("Close");

        closeButton.setDefaultButton(true);

        closeButton.setOnAction(event -> {
            doorsClosed.close();
        });
        VBox vbox = new VBox(cantGo, closeButton);
        vbox.setAlignment(Pos.CENTER);
        Scene itemScene = new Scene(vbox, 250, 150);
        doorsClosed.setScene(itemScene);
        doorsClosed.show();
    }

    public void itemPicking(ItemsOnTheMap itemsOnTheMap, Cell nextCell) {
        if (nextCell.getItem() instanceof HealthBoost) {
            this.setHealth(this.getHealth() + 3);
            itemsOnTheMap.getCell().setItem(null);
        } else if (nextCell.getItem() instanceof StrengthBoost) {
            this.setStrength(this.getStrength() + 2);
            itemsOnTheMap.getCell().setItem(null);
        }
        else if (nextCell.getItem() instanceof Key) {
            if (pickUpKey) {
                itemsOnTheMap.getCell().setItem(null);
                this.setHasKey(true);
            }
        }
        else if (nextCell.getItem() instanceof Paper) {
            if (pickUpPaper) {
                itemsOnTheMap.getCell().setItem(null);
            }
        }

    }

    private void fighting(Actor actor, Cell nextCell) {
        actor.setHealth(actor.getHealth() - this.getStrength());
        if (actor.getHealth() > 0) {
            this.setHealth(this.getHealth() - actor.getStrength());
            if (this.getHealth() < 1) {
                this.setAlive(false);
                System.out.println(actor.isAlive);
                System.out.println(this.isAlive);
                this.cell.setType(CellType.FLOOR);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
                actor.getCell().setActor(null);
            }
        } else {
            actor.setAlive(false);
//            this.setHealth(this.getHealth() + 1);
//            this.setStrength(this.getStrength() + 1);
            System.out.println(actor.isAlive);
            System.out.println(this.isAlive);
            actor.cell.setType(CellType.FLOOR);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void pickUpKey(Cell nextCell) {
        Stage askForPickUpKey = new Stage();
        Label setName = new Label("Pick up key?");
        Button yesButton = new Button("Yes!");
        Button noButton = new Button("No!");

        yesButton.setDefaultButton(true);
        noButton.setCancelButton(true);

        yesButton.setOnAction(event -> {
            pickUpKey = true;
            this.itemPicking(nextCell.getItem(), nextCell);
            nextCell.setType(CellType.FLOOR);
            System.out.println(pickUpKey);
            System.out.println(hasKey);
            askForPickUpKey.close();

        });
        noButton.setOnAction(event -> {
            askForPickUpKey.close();
        });
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        VBox vbox = new VBox(setName, yesButton, noButton);
        vbox.setAlignment(Pos.CENTER);
        Scene itemScene = new Scene(vbox, 100, 150);
        askForPickUpKey.setScene(itemScene);
        askForPickUpKey.show();

    }

    public void pickUpPaper(Cell nextCell) {

        Stage askForPickUpPaper = new Stage();
        Label story = new Label("You have been kidnapped and dragged into a cave! \n\r" +
                "Now you have to survive this adventure and get outside! \n\r" +
                "It's going to be dangerous so make sure to collect all items, they may save your life. \n\r" +
                "Your adventure begins now!");
        Button letsgoButton = new Button("Let's go!");


        letsgoButton.setDefaultButton(true);


        letsgoButton.setOnAction(event -> {
            this.itemPicking(nextCell.getItem(), nextCell);
            nextCell.setType(CellType.FLOOR);
            askForPickUpPaper.close();
        });

        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        VBox vbox = new VBox(story, letsgoButton);
        vbox.setAlignment(Pos.CENTER);


        Scene itemScene = new Scene(vbox, 500, 250);

        askForPickUpPaper.setScene(itemScene);

        askForPickUpPaper.show();

    }
}
