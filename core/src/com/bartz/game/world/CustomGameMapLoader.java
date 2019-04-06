package com.bartz.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.Random;

public class CustomGameMapLoader {
    private static Json json = new Json();
    private static final int SIZE_X = Gdx.graphics.getWidth()/ TileType.TILE_SIZE + 1 ;
    private static final int SIZE_Y = Gdx.graphics.getHeight()/ TileType.TILE_SIZE + 1;

    public static CustomGameMapData loadMap (String id, String name) {
        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".map");
        if (file.exists()) {
            CustomGameMapData data = json.fromJson(CustomGameMapData.class, file.readString());
            return data;
        } else {
            CustomGameMapData data = generateRandomMap(id, name);
            saveMap(data.id, data.name, data.map);
            return data;
        }
    }

    public static void saveMap(String id, String name, int[][][] map) {
        CustomGameMapData data = new CustomGameMapData();
        data.id = id;
        data.name = name;
        data.map = map;

        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".map");
        file.writeString(json.prettyPrint(data), false);
    }

    public static CustomGameMapData generateRandomMap (String id, String name) {
        CustomGameMapData mapData = new CustomGameMapData();
        mapData.id = id;
        mapData.name = id;
        mapData.map = new int[2][SIZE_Y][SIZE_X];

        Random random = new Random();

        for (int row = 0; row < SIZE_Y; row++){
            for (int col = 0; col < SIZE_X; col++){
                mapData.map[0][row][col] = TileType.CUT_GRASS.getId();
                if (random.nextBoolean()) {
                    mapData.map[1][row][col] = TileType.UNCUT_GRASS_DARK.getId();
                } else {
                    mapData.map[1][row][col] = TileType.UNCUT_GRASS_BRIGHT.getId();
                }
                //set not fully visible cells to be unreachable by player
                if (row == 0 || col == SIZE_X-1) {
                    mapData.map[1][row][col] = TileType.PATH.getId();
                }

            }
        }
        return mapData;
    }

}
