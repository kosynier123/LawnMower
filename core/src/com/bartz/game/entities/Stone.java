package com.bartz.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bartz.game.world.GameMap;

public class Stone extends Entity {
    private Texture image;
    private Sprite sprite;

    public Stone(float x, float y, GameMap map) {
        super(x, y, EntityType.STONE, map);
        image = new Texture("stone.png");
        sprite = new Sprite(image, getWidth(), getHeight());
        //pos.y = Gdx.graphics.getHeight()/
        sprite.setScale(0.3f);
        sprite.setOriginCenter();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
