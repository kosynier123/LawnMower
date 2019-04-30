package com.bartz.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.EntityType;
import com.bartz.game.entities.Player;
import com.bartz.game.entities.obstacles.Stone;
import java.util.ArrayList;


public abstract class GameMap {

    protected Player player;
    protected ArrayList<Entity> obstacles;
    ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private int completionPercent;
    private Intersector intersector;
    private boolean collisionCirclesVisible;

    public GameMap(){
        obstacles = new ArrayList<Entity>();
        shapeRenderer = new ShapeRenderer();
        intersector = new Intersector();
    }

    public void render(OrthographicCamera cam, SpriteBatch batch){
        for (Entity obstacle : obstacles) {
            obstacle.render(batch);
        }
        player.render(batch);
    }

    public void update (float delta) {
        for (Entity obstacle : obstacles) {
            obstacle.update(delta);
        }
        player.update(delta);
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
        if (x + 2* TileType.TILE_SIZE < 0 || y + TileType.TILE_SIZE < 0 ||
                x > Gdx.graphics.getWidth() - 2 * TileType.TILE_SIZE || y > Gdx.graphics.getHeight())
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
        } return false;
    }


    /**
     * check if obstacle collide with other obstacle
     * @param obstacleCircle - obstacle mapped to circle
     * @return true if obstacles are overlaping
     */
    public boolean doesObstacleCollideWithObstacle(Circle obstacleCircle) {
        Circle mowerCircle = new Circle(player.getCircleMowerX(), player.getCircleMowerY(), player.getCircleMowerR() * 4);
        if (obstacles.size() == 0)
            return false;

        //ensure that it won't generate at starting position of mower
        else if (mowerCircle.overlaps(obstacleCircle)){
                System.out.println("row - collide with mower");
                return true;}
        else {
            for (Entity obstacle : obstacles) {
                Circle stoneCircle = new Circle(obstacle.getX() + obstacle.getWidth() / 2,
                        obstacle.getY() + obstacle.getHeight() / 2,
                        obstacle.getWidth() / 2 * EntityType.STONE.getScale());
                if (stoneCircle.overlaps(obstacleCircle)){
                    System.out.println("row - collide with obstacle");
                    return true;}
            }
        } return false;
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
                Circle stoneCircle = new Circle(obstacle.getX() + obstacle.getWidth() / 2,
                        obstacle.getY() + obstacle.getHeight() / 2,
                        obstacle.getWidth() / 2 * EntityType.STONE.getScale());

                if (stoneCircle.overlaps(mowerCircle)){
                    //delete touched stone and reduce mower health
                    obstacles.remove(obstacle);
                    player.reduceHealth();
                    return true;}
                }
            } return false;
        }

    public boolean isObstacleOnTile (int col, int row) {
        if (obstacles.size() == 0)
            return false;
        else {
            for (Entity obstacle : obstacles) {
                if (((Stone)obstacle).getColumn() == col && ((Stone)obstacle).getRow() == row)
                    return true;}
            }return false;
        }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
    public abstract void countCutableTiles();
    public abstract int getNrOfCutableTiles();
    public abstract boolean isCollisionCirclesVisible();
    public abstract void setCollisionCirclesVisible(boolean collisionCirclesVisible);
    public abstract void setStartText(boolean startText);
    public abstract void setMinNrOfStones(int minNrOfStones);
    public Player getPlayer() {
        return player;
    }


    public abstract int getCompletionPercent();
    public abstract void setTile(int layer, float x, float y);
}