package com.bartz.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.Player;

import java.util.ArrayList;

public abstract class GameMap {

    protected ArrayList<Entity> entities;

    public GameMap(){
        entities = new ArrayList<Entity>();
        entities.add(new Player(40, 300, this));
    }

    public void render(OrthographicCamera cam, SpriteBatch batch){
        for (Entity entity : entities) {
            entity.render(batch);
        }

    }
    public void update (float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
        }
    }
    public abstract void dispose();

    /**
     * Gets tiles type by its location in game world at specified layer
     * @param layer
     * @param x
     * @param y
     * @return TileType
     */
    public TileType getTileTypeByLocation(int layer, float x, float y){
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }

    /**
     * Gets tiles type by its coordinate on map within specified layer
     * @param layer
     * @param column
     * @param row
     * @return TileType
     */
    public abstract TileType getTileTypeByCoordinate(int layer, int column, int row);

    public boolean doesRectCollideWithMap(float x, float y, int width, int height){
        if (x < 0 || y <0 || x + width > getPixelWidth() || y + height > getPixelHeight())
            return true;

        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++){
            for (int column = (int) (x / TileType.TILE_SIZE); column < Math.ceil((x + width) / TileType.TILE_SIZE); column++){
                for (int layer = 0; layer < getLayers(); layer++){
                    TileType type = getTileTypeByCoordinate(layer, column, row);
                    if (type != null && type.isCollidable())
                        return true;
                }
            }
        }
        return false;
    }
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

    public int getPixelWidth(){
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight(){
        return this.getHeight() * TileType.TILE_SIZE;
    }
}