package com.bartz.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public enum EntityType {

    PLAYER("player", 367, 208, 0.7f),
    //FENCE("Fence", 300, 300 ),
    STONE("stone", 212, 181, 0.4f);

    private String id;
    private int width, height;
    private float scale;

    EntityType(String id, int width, int height, float scale) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.scale = scale;
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

    public float getScale() {
        return scale;
    }
}
