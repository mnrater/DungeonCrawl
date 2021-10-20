package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class ItemsOnTheMap implements Drawable {
    private Cell cell;
    private int  boost;
//    private boolean isKey = false;

    public ItemsOnTheMap(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getBoost() {
        return boost;
    }

    public void setBoost(int boost){
        this.boost = boost;
    }

//    public void setIsKey(boolean isKey){
//        this.isKey = true;
//    }

}
