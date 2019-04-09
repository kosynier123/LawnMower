package com.bartz.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.EntityType;
import com.bartz.game.entities.Player;
import com.bartz.game.entities.obstacles.Stone;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GameMap {

    protected ArrayList<Entity> entities;
    protected ArrayList<Entity> obstacles;
    public GameMap(){
        entities = new ArrayList<Entity>();
        obstacles = new ArrayList<Entity>();
        entities.add(new Player(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,this));
    }

    public void render(OrthographicCamera cam, SpriteBatch batch){
        for (Entity obstacle : obstacles) {
            obstacle.render(batch);
        }
        for (Entity entity : entities) {
            entity.render(batch);
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

    /*public boolean doesMowerCollideWithMap(float x, float y, int width, int height){
        if (x < 0 || y <0 || x + width > getPixelWidth() || y + height > getPixelHeight())
            return true;

        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++){
            for (int column = (int) (x / TileType.TILE_SIZE); column < Math.ceil((x + width) / TileType.TILE_SIZE); column++){
                for (int layer = 0; layer < getLayers(); layer++){
                    TileType type = getTileTypeByCoordinate(layer, column, row);
                    if (type != null && type.isCollidable()){
                        return true;
                    }
                }
            }
        }
        return false;
    }*/

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

    public boolean doesMowerCollideWithObstacle(float x, float y) {
        if (obstacles.size() == 0)
            return false;
        else {
            for (Entity obstacle : obstacles) {
                Array<Vector2> obstacleRect = new Array<Vector2>();
                obstacleRect.add(new Vector2(obstacle.getX(), obstacle.getY()));
                obstacleRect.add(new Vector2(obstacle.getX()  + obstacle.getWidth() * 0.4f, obstacle.getY()));
                obstacleRect.add(new Vector2(obstacle.getX(), obstacle.getY() + obstacle.getHeight() * 0.4f));
                obstacleRect.add(new Vector2(obstacle.getX()  + obstacle.getWidth() * 0.4f, obstacle.getY() + obstacle.getHeight() * 0.4f));
                Intersector intersector = new Intersector();
                if (intersector.isPointInPolygon(obstacleRect, new Vector2(x, y))) {
                    ((Stone) obstacle).setVisible(false);
                    return true;
                }
                System.out.println("x " + x + " y " + y + " ob x " + obstacle.getX() + "ob x + w" + (obstacle.getX() + obstacle.getWidth()*0.4) + " ob y" + obstacle.getY() + "ob y + h" + (obstacle.getY() + obstacle.getHeight()*0.4));
                /*if (obstacle.getType() == EntityType.STONE && obstacle.getX() <= x &&
                        (obstacle.getX() + obstacle.getWidth() * 0.4) >= x && obstacle.getY() <= y &&
                        (obstacle.getY() + obstacle.getHeight() * 0.4) >= y) {
                    ((Stone) obstacle).setVisible(false);
                    return true;
                }*/
            }
        } return false;
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

    public abstract void setTile(int layer, float x, float y);
}