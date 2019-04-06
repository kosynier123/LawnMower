package com.bartz.game.world;

public enum Obstacles {
    STONE(1, true, "Stone" ),
    FENCE(2, true, " Fence" );

    private int id;
    private boolean collidable;
    private String name;

    Obstacles(int id, boolean collidable, String name){}
}
