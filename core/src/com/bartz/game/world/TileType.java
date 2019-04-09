package com.bartz.game.world;

import java.util.HashMap;

public enum TileType {
    UNCUT_GRASS_DARK(6, false, "Uncut grass"),
    UNCUT_GRASS_BRIGHT(5, false, "Uncut grass" ),
    CUT_GRASS(1, false, "Cut grass"),
    PATH(2,true, "Stone path"),
    TERRACE_TILE(4, false, "Terrace tile" );
    //STONE_TILE(3, true, "Stone" );
    //FENCE(7, true, " Fence" );*/

    public static final int TILE_SIZE = 75;
    private int id;
    private boolean collidable;
    private String name;

    TileType(int id, boolean collidable, String name){
        this.id = id;
        this.collidable = collidable;
        this.name = name;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static HashMap<Integer, TileType> tileTypeHashMap;

    static {
        tileTypeHashMap = new HashMap<Integer, TileType>();
        for (TileType tileType : TileType.values()){
            tileTypeHashMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById(int id){
        return tileTypeHashMap.get(id);
    }
}
