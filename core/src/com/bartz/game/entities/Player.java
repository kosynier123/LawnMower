package com.bartz.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bartz.game.world.GameMap;
import com.codeandweb.physicseditor.PhysicsShapeCache;

public class Player extends Entity {
    private Sprite sprite;
    private Texture image;
    private static final int SPEED = 100;
    private float amountX;
    private float amountY;
    private int health;
    private static final float SCALE = 0.7f;
    private Circle circleMower;
    private ShapeRenderer shapeRenderer;

    public Player(float x, float y, GameMap map) {
        super(x, y, EntityType.PLAYER, map);
        image = new Texture("mower.png");
        //TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
        //TextureAtlas.AtlasRegion regionMower = atlas.findRegion("mower");
        //sprite = new Sprite(regionMower);
        sprite = new Sprite(image, getWidth(), getHeight());
        sprite.setOriginCenter();
        sprite.setScale(SCALE);
        //System.out.println("first x (screen) " + Gdx.graphics.getWidth() +" "+ Gdx.graphics.getHeight());
        //System.out.println("first x " + x + " " + y);
        health = 100;
        shapeRenderer = new ShapeRenderer();
    }
    @Override
    public void update(float deltaTime) {
        //get input from phone position
        amountX = -Gdx.input.getAccelerometerX() * deltaTime * SPEED;
        amountY = Gdx.input.getAccelerometerZ() * deltaTime * SPEED;
        if (Math.abs(amountX) > 2 || Math.abs(amountY) > 2)  move(amountX, amountY);
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);

    }

    @Override
    protected void move(float amountX, float amountY) {
        float newX = pos.x + amountX;
        float newY = pos.y + amountY;

        double myRadians = Math.atan2(newY - pos.y, newX - pos.x);
        float myDegress = (float) Math.toDegrees(myRadians);
        //float myDegress = 0;

        map.setTile(0,pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY());

        if (!map.doesMowerCollideWithMap(newX, pos.y, (int) sprite.getOriginX(),(int) sprite.getOriginY())){
            sprite.setRotation(myDegress);
            this.pos.x = newX;
        }

        if (!map.doesMowerCollideWithMap(pos.x, newY,(int) sprite.getOriginX(), (int) sprite.getOriginY())) {
            sprite.setRotation(myDegress);
            this.pos.y = newY;
        }
        circleMower = new Circle(pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY(), sprite.getHeight()*SCALE/2);
        /*Array<Vector2> mowerRect = new Array<Vector2>();
        mowerRect.add(new Vector2(sprite.getX() + sprite.getWidth()*SCALE, sprite.getY() + sprite.getHeight()*SCALE ));
        mowerRect.add(new Vector2(sprite.getX(), sprite.getY() + sprite.getHeight()*SCALE));
        mowerRect.add(new Vector2(sprite.getX() + sprite.getWidth()*SCALE, sprite.getY()));
        mowerRect.add(new Vector2(sprite.getX(), sprite.getY()));*/

        map.doesMowerCollideWithObstacle(circleMower);

        System.out.println("x " + pos.x + " y " + pos.y);
        sprite.setPosition(pos.x, pos.y);
    }


}
