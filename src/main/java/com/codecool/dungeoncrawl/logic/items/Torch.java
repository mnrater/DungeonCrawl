package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Torch extends ItemsOnTheMap {

    public Torch(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "torch";
    }
}
