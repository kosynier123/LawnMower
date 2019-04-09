package com.bartz.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bartz.game.world.GameMap;


public class Player extends Entity {
    private Sprite sprite;
    private Texture image;
    private static final int SPEED = 100;
    private float amountX;
    private float amountY;
    private int health;
    private static final float SCALE = 0.7f;

    public Player(float x, float y, GameMap map) {
        super(x, y, EntityType.PLAYER, map);
        image = new Texture("mower.png");
        sprite = new Sprite(image, getWidth(), getHeight());
        //pos.y = Gdx.graphics.getHeight()/
        sprite.setOriginCenter();
        sprite.setScale(SCALE);
        health = 100;
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
        //System.out.println("he "+map.getHeight() + " wid " + map.getWidth() );
        map.setTile(0,pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY());

        //System.out.println("origin "+sprite.getOriginX() + sprite.getOriginY() );
        if (!map.doesMowerCollideWithMap(newX, pos.y, (int) sprite.getOriginX(),(int) sprite.getOriginY())){
            sprite.setRotation(myDegress);
            this.pos.x = newX;
        }

        if (!map.doesMowerCollideWithMap(pos.x, newY,(int) sprite.getOriginX(), (int) sprite.getOriginY())) {
            sprite.setRotation(myDegress);
            this.pos.y = newY;
        }
        System.out.println(map.doesMowerCollideWithObstacle(pos.x+getWidth()/2*SCALE,
                pos.y+getHeight()/2*SCALE) || map.doesMowerCollideWithObstacle(pos.x+getWidth()/2*SCALE,
                pos.y+getHeight()*SCALE) || map.doesMowerCollideWithObstacle(pos.x+getWidth()/2*SCALE,
                pos.y*SCALE) );
        sprite.setPosition(pos.x, pos.y);
    }

}
