package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bones extends ItemsOnTheMap {

    public Bones(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "bones";
    }
}
