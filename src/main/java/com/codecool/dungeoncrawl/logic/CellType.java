package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    PLAYER("player"),
    STRENGTHPOTION("strengthpotion"),
    HEALTHPOTION("healthpotion"),
    KEY("key"),
    GOLUM("golum"),
    SKELETON("skeleton"),
    SPIDER("spider"),
    DOORSCLOSED("doorsclosed"),
    PAPER("paper"),
    BONES("bones"),
    TORCH("torch"),
    DOORSOPEN("doorsopen");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
