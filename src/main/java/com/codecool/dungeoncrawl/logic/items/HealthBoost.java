package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;


public class HealthBoost extends ItemsOnTheMap {

    public HealthBoost(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "healthpotion";
    }

}
