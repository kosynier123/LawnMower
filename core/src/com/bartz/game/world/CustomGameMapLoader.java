package com.bartz.game.world;

import com.badlogic.gdx.Gdx;
import java.util.Random;

public class CustomGameMapLoader {
    //private static Json json = new Json();
    private static final int SIZE_X = Gdx.graphics.getWidth()/ TileType.TILE_SIZE + 1 ;
    private static final int SIZE_Y = Gdx.graphics.getHeight()/ TileType.TILE_SIZE + 1;

    public static CustomGameMapData loadMap (String id, String name) {
            CustomGameMapData data = generateRandomMap(id, name);
            return data;
    }


    public static CustomGameMapData generateRandomMap (String id, String name) {
        CustomGameMapData mapData = new CustomGameMapData();
        mapData.id = id;
        mapData.name = id;
        mapData.map = new int[2][SIZE_Y][SIZE_X];

        Random random = new Random();

        for (int row = 0; row < SIZE_Y; row++){
            for (int col = 0; col < SIZE_X; col++){
                //mapData.map[0][row][col] = TileType.CUT_GRASS.getId();
                if (random.nextBoolean()) {
                    mapData.map[0][row][col] = TileType.UNCUT_GRASS_DARK.getId();
                } else {
                    mapData.map[0][row][col] = TileType.UNCUT_GRASS_BRIGHT.getId();
                }
                //set not fully visible cells to be unreachable by player
                if (row == 0 || col == SIZE_X - 1) {
                    mapData.map[0][row][col] = TileType.PATH.getId();
                }

            }
        }

        return mapData;
    }


}
