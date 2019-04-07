package com.bartz.game.world;

import java.util.HashMap;

public enum Obstacles {
    STONE(1, true, "Stone" ),
    FENCE(2, true, " Fence" );

    private int id;
    private boolean collidable;
    private String name;

    Obstacles(int id, boolean collidable, String name){
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
