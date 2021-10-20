package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends ItemsOnTheMap {

    public Key(Cell cell) {
        super(cell);
//        this.setIsKey(true);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}