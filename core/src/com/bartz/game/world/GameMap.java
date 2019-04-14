package com.bartz.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.EntityType;
import com.bartz.game.entities.Player;
import com.bartz.game.entities.obstacles.Stone;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class GameMap {

    protected ArrayList<Entity> entities;
    protected ArrayList<Entity> obstacles;
    ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    public GameMap(){
        entities = new ArrayList<Entity>();
        obstacles = new ArrayList<Entity>();
        entities.add(new Player(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,this));
        shapeRenderer = new ShapeRenderer();
    }

    public void render(OrthographicCamera cam, SpriteBatch batch){
        for (Entity obstacle : obstacles) {
            obstacle.render(batch);
        }
        for (Entity player : entities) {
            player.render(batch);
        }


    }
    public void update (float delta) {
        for (Entity obstacle : obstacles) {
            obstacle.update(delta);
        }
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

    public boolean doesMowerCollideWithMap(float x, float y, int width, int height) {
        if (x + 2 * TileType.TILE_SIZE < 0 || y + TileType.TILE_SIZE < 0 || x > Gdx.graphics.getWidth() || y > Gdx.graphics.getHeight())
            return true;

        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
            for (int column = (int) (x / TileType.TILE_SIZE); column < Math.ceil((x + width) / TileType.TILE_SIZE); column++) {
                for (int layer = 0; layer < getLayers(); layer++) {
                    TileType type = getTileTypeByCoordinate(layer, column, row);
                    if (type != null && type.isCollidable()) {
                        return true;
                    }
                }
            }
        }return false;
    }

    /**
     * check if obstacle collide with other obstacle
     * @param obstacleCircle - obstacle mapped to circle
     * @return true if obstacles are overlaping
     */
    public boolean doesObstacleCollideWithObstacle(Circle obstacleCircle) {
        if (obstacles.size() == 0)
            return false;
        else {

            for (Entity obstacle : obstacles) {
                Circle stoneCircle = new Circle(obstacle.getX() + obstacle.getWidth()/2,
                        obstacle.getY() + obstacle.getHeight()/2, obstacle.getWidth()/2*0.4f);
                if (stoneCircle.overlaps(obstacleCircle)){
                    return true;}
            }
        }return false;
    }

    /**
     *
     * @param mowerCircle - area of mower which is responsible for cutting
     * @return true if mower center area collide with obstacle
     */
    public boolean doesMowerCollideWithObstacle(Circle mowerCircle) {
        if (obstacles.size() == 0)
            return false;
        else {

            for (Entity obstacle : obstacles) {
                Circle stoneCircle = new Circle(obstacle.getX() + obstacle.getWidth()/2,
                        obstacle.getY() + obstacle.getHeight()/2, obstacle.getWidth()/2*0.4f);
                if (stoneCircle.overlaps(mowerCircle)){
                    ((Stone) obstacle).setVisible(false);
                    return true;}
                }
            }return false;
        }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();


    public abstract void setTile(int layer, float x, float y);
}