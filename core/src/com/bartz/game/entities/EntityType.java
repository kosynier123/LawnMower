package com.bartz.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public enum EntityType {

    PLAYER("player", 367, 208),
    FENCE("Fence", 300, 300 ),
    STONE("stone", 300, 300);

    private String id;
    private int width, height;

    EntityType(String id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
