package com.bartz.game.entities.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.EntityType;
import com.bartz.game.world.GameMap;

public class Stone extends Entity {
    private Texture image;
    private Sprite sprite;
    private boolean visible;
    private static final float SCALE = 0.4f;

    public Stone(float x, float y, GameMap map, Boolean visible) {
        super(x, y, EntityType.STONE, map);
        this.visible = visible;
        image = new Texture("stone.png");
        sprite = new Sprite(image, getWidth(), getHeight());
        sprite.setOriginCenter();
        sprite.setScale(SCALE);
        sprite.setPosition(x, y);
    }

    @Override
    protected void move(float amountX, float amountY){}

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (visible) sprite.draw(batch);
    }

}
