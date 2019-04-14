package com.bartz.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.obstacles.Stone;

import java.util.Random;

public class CustomGameMap extends GameMap {

    String id;
    String name;
    int[][][] map;
    private int nrOfStones;

    private TextureRegion[][] tiles;

    public CustomGameMap(){
        CustomGameMapData data = CustomGameMapLoader.loadMap("basic", "My lawn");
        this.id = data.id;
        this.name = data.name;
        this.map = data.map;

        tiles = TextureRegion.split(new Texture("textures.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);

        Random random = new Random();
        nrOfStones = random.nextInt(10)+1;
        generateStones(nrOfStones);

    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int layer = 0; layer < getLayers(); layer++) {
            for (int row = 0; row < getHeight(); row++) {
                for (int col = 0; col < getWidth(); col++) {
                    TileType type = this.getTileTypeByCoordinate(layer, col, row);
                    if (type != null)
                        batch.draw(tiles[0][type.getId() - 1], col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
                }
            }
        }
        super.render(camera, batch);
        batch.end();

        // show collision circles for mower and stones
        for (Entity entity : entities) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 1, 0, 0.1f);
            shapeRenderer.circle(entity.getX() + entity.getWidth() / 2, entity.getY() + entity.getHeight() / 2,
                    entity.getHeight() * 0.7f / 2);
            shapeRenderer.end();
        }
        for (Entity obstacle : obstacles) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 1, 0.1f);
            shapeRenderer.circle(obstacle.getX() + obstacle.getWidth() / 2, obstacle.getY() + obstacle.getHeight() / 2,
                    obstacle.getHeight() * 0.4f / 2);
            shapeRenderer.end();
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {

    }

    @Override
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return super.getTileTypeByLocation(layer, (int) (x / TileType.TILE_SIZE), getHeight() - (int) (y / TileType.TILE_SIZE) - 1);
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int column, int row) {
        if (column < 0 || column >= getWidth() || row < 0 || row >= getHeight())
        return null;

        return TileType.getTileTypeById(map[layer][getHeight() - row - 1][column]);
    }


    @Override
    public int getWidth() {
        return map[0][0].length;
    }

    @Override
    public int getHeight() {
        return map[0].length;
    }

    @Override
    public int getLayers() {
        return map.length;
    }

    /**
     * Changing grass type to Cut after running a mower over it
     * @param layer - grass layer
     * @param x - x coordinate of mower center
     * @param y - y coordinate of mower center
     */
    @Override
    public void setTile(int layer, float x, float y){
        map[layer][getHeight() - (int) (y / TileType.TILE_SIZE)-1][(int) (x / TileType.TILE_SIZE)] = TileType.CUT_GRASS.getId();
    }

    /**
     * generate stone obstacles on the map in random position and add them to obstacles array
     * @param nrOfStones - indicate how many stones have to be created
     *
     */
    private void generateStones(int nrOfStones){
        Circle stoneCircle;
        Stone stone;
        Random random = new Random();
        for (int i=0; i <= nrOfStones; i++) {
            System.out.println("stone" + i);
            //ensure that two stones will not be places in overlaping positions
            do {
                stone = new Stone(random.nextInt(Gdx.graphics.getWidth())- 1.5f * TileType.TILE_SIZE,
                        random.nextInt(Gdx.graphics.getHeight() - (int) (0.5 * TileType.TILE_SIZE)), this, true);
                stoneCircle =  new Circle(stone.getX() + stone.getWidth()/2,
                        stone.getY() + stone.getHeight()/2, stone.getWidth()/2*0.4f);
            } while (doesObstacleCollideWithObstacle(stoneCircle));
            obstacles.add(stone);
        }
    }
}
