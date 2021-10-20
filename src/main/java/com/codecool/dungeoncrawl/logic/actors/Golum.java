package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Golum extends Actor {
    public Golum(Cell cell) {
        super(cell);
        this.setStrength(2);
        this.setHealth(12);
    }

    public String getTileName() {
        return "golum";
    }
}