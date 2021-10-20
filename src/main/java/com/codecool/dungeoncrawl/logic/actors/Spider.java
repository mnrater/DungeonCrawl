package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Spider extends Actor {
    public Spider(Cell cell){
        super(cell);
        this.setStrength(3);
        this.setHealth(11);
    }

    public String getTileName() {
        return "spider";
    }
}
