package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Paper extends ItemsOnTheMap {

    public Paper(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "paper";
    }
}
