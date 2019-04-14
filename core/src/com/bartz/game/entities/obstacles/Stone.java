package com.bartz.game.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.EntityType;
import com.bartz.game.world.GameMap;

public class Stone extends Entity {
    private Texture image;
    private Sprite sprite;
    private boolean visible;
    public static final float SCALE = 0.4f;

    public Stone(float x, float y, GameMap map, Boolean visible) {
        super(x, y, EntityType.STONE, map);
        this.visible = visible;
        //TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
        //TextureAtlas.AtlasRegion regionStone = atlas.findRegion("stone");
        //sprite = new Sprite(regionStone);
        image = new Texture("stone.png");
        sprite = new Sprite(image, getWidth(), getHeight());
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
