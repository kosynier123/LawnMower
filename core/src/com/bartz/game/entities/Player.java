package com.bartz.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bartz.game.world.GameMap;

public class Player extends Entity {
    private Texture image;
    private static final int SPEED = 50;

    public Player(float x, float y, GameMap map) {
        super(x, y, EntityType.PLAYER, map);
        image = new Texture("mower.png");
    }

    @Override
    public void update(float deltaTime) {
        moveX(Gdx.input.getAccelerometerY() * deltaTime * SPEED);
        moveY(Gdx.input.getAccelerometerX() * deltaTime * SPEED);
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
    }
}
