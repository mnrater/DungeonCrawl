package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.Golum;
import com.codecool.dungeoncrawl.logic.actors.Spider;
import com.codecool.dungeoncrawl.logic.items.HealthBoost;
import com.codecool.dungeoncrawl.logic.items.StrengthBoost;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Paper;
import com.codecool.dungeoncrawl.logic.items.Bones;
import com.codecool.dungeoncrawl.logic.items.Torch;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.SKELETON);
                            new Skeleton(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.GOLUM);
                            new Golum(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'p':
                            cell.setType(CellType.SPIDER);
                            new Spider(cell);
                            break;
                        case '+':
                            cell.setType(CellType.HEALTHPOTION);
                            new HealthBoost(cell);
                            break;
                        case 'o':
                            cell.setType(CellType.STRENGTHPOTION);
                            new StrengthBoost(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            new Key(cell);
                            break;
                        case 'x':
                            cell.setType(CellType.PAPER);
                            new Paper(cell);
                            break;
                        case 't':
                            cell.setType(CellType.BONES);
                            new Bones(cell);
                            break;
                        case 'l':
                            cell.setType(CellType.TORCH);
                            new Torch(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.DOORSCLOSED);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
