package com.bartz.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.bartz.game.entities.Entity;
import com.bartz.game.entities.EntityType;
import com.bartz.game.entities.Player;
import com.bartz.game.entities.obstacles.Stone;

import java.util.Random;

public class CustomGameMap extends GameMap {

    String id;
    String name;
    int[][][] map;
    private int nrOfStones;
    private int nrOfCutableTiles;
    private Integer nrOfCutTilesAtBeginning;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter, parameterLife;
    BitmapFont font, fontLife;

    private TextureRegion[][] tiles;

    public CustomGameMap(){
        CustomGameMapData data = CustomGameMapLoader.loadMap("basic", "My lawn");
        this.id = data.id;
        this.name = data.name;
        this.map = data.map;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("VT323-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterLife = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 90;
        parameter.borderWidth = 10;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.FIREBRICK;

        parameterLife.size = 90;
        parameterLife.borderWidth = 10;
        parameterLife.borderColor = Color.BLACK;
        parameterLife.color = Color.CHARTREUSE;

        font = generator.generateFont(parameter);
        fontLife = generator.generateFont(parameterLife);
        tiles = TextureRegion.split(new Texture("textures.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
        Random random = new Random();
        //player = new Player(Gdx.graphics.getWidth() / 2 - 75 - EntityType.PLAYER.getWidth() * EntityType.PLAYER.getScale() / 2,
          //      Gdx.graphics.getHeight() / 2 - 75 - EntityType.PLAYER.getHeight() * EntityType.PLAYER.getScale() / 2 ,this);
        System.out.println("width " + getWidth() + " " + getHeight());
        player = new Player((getWidth()/2 - 2) * TileType.TILE_SIZE, (getHeight() / 2 - 2)  * TileType.TILE_SIZE, this);

        //nr of stones - at least 4
        nrOfStones = random.nextInt(10) + 4;
        generateStones(nrOfStones);
        countCutableTiles();
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
                        batch.draw(tiles[0][type.getId() - 1], col * TileType.TILE_SIZE,
                                row * TileType.TILE_SIZE);
                }
            }
        }
        super.render(camera, batch);
        font.draw(batch, "Progress: ", 80, Gdx.graphics.getHeight() - 50);
        font.draw(batch, String.valueOf(getCompletionPercent()) + "%", 500, Gdx.graphics.getHeight()-50);
        fontLife.draw(batch, "Life: ", 730, Gdx.graphics.getHeight() - 50);
        fontLife.draw(batch, String.valueOf(player.getHealth()), 960, Gdx.graphics.getHeight()-50);
        batch.end();

        // show collision circles for mower and stones
        showDebugCircles(true);


    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

    }

    @Override
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return super.getTileTypeByLocation(layer, (int) (x / TileType.TILE_SIZE), getHeight() -
                (int) (y / TileType.TILE_SIZE) - 1);
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

    @Override
    public void countCutableTiles() {
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if ((map[0][row][col] == TileType.UNCUT_GRASS_DARK.getId() ||
                        map[0][row][col] == TileType.UNCUT_GRASS_BRIGHT.getId()) && !isObstacleOnTile(col, row))
                    nrOfCutableTiles += 1;
            }
        }
        if (nrOfCutTilesAtBeginning == null)
            nrOfCutTilesAtBeginning = nrOfCutableTiles;
    }

    /**
     * Changing grass type to Cut after running a mower over it
     * @param layer - grass layer
     * @param x - x coordinate of mower center
     * @param y - y coordinate of mower center
     */
    @Override
    public void setTile(int layer, float x, float y){
        if (map[layer][getHeight() - (int) (y / TileType.TILE_SIZE)-1][(int) (x / TileType.TILE_SIZE)] != TileType.CUT_GRASS.getId())
        map[layer][getHeight() - (int) (y / TileType.TILE_SIZE)-1][(int) (x / TileType.TILE_SIZE)] = TileType.CUT_GRASS.getId();
        nrOfCutableTiles = 0;
        countCutableTiles();
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
            // System.out.println("stone" + i);
            // ensure that two stones will not be placed in overlapping positions
            do {
                // ensure to fit stones fully visible
                int col = random.nextInt(getWidth() - 2);
                //if (col == 0) col = 1;
                int row = random.nextInt(getHeight() - 4);
                //if (row == 0 || row == 1 || row == 2 ) row = 2;
                stone = new Stone(col * TileType.TILE_SIZE, row * TileType.TILE_SIZE, this, true);
                stoneCircle =  new Circle(stone.getX() + stone.getWidth() / 2,
                        stone.getY() + stone.getHeight() / 2, stone.getWidth() / 2 * EntityType.STONE.getScale());
            } while (doesObstacleCollideWithObstacle(stoneCircle));
            //add to obstacles list
            obstacles.add(stone);

        }
    }

    @Override
    public int getNrOfCutableTiles() {
        return nrOfCutableTiles;
    }

    private void showDebugCircles(boolean setting){
        if (setting) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            // render collision circle for mower
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 1, 0, 0.5f);
            shapeRenderer.circle(player.getCircleMowerX(), player.getCircleMowerY(), player.getCircleMowerR());
            shapeRenderer.end();
            //render collision circle for stones
            for (Entity obstacle : obstacles) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1, 0, 1, 0.5f);
                shapeRenderer.circle(obstacle.getX() + obstacle.getWidth() / 2, obstacle.getY() + obstacle.getHeight() / 2,
                        obstacle.getHeight() * EntityType.STONE.getScale() / 2);
                shapeRenderer.end();
            }


        }
    }

    @Override
    public int getCompletionPercent() {
        return (int)(100 - Math.floor(getNrOfCutableTiles()) / nrOfCutTilesAtBeginning * 100);
    }
}
