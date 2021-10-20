package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class StrengthBoost extends ItemsOnTheMap {

    public StrengthBoost(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "strengthpotion";
    }
}