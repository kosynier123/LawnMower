package com.bartz.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.bartz.game.world.GameMap;

public class Player extends Entity {
    private Sprite sprite;
    private Texture image;
    private static final int SPEED = 100;
    private float amountX, amountY, myDegrees, screenPosX, screenPosZ;
    private int health;
    private static final float SCALE = 0.7f;
    private Circle circleMower;

    public Player(float x, float y, GameMap map) {
        super(x, y, EntityType.PLAYER, map);

        image = new Texture("mower.png");
        sprite = new Sprite(image, getWidth(), getHeight());

        //sprite.setOriginCenter();
        // set the center of rotation to be at center of cutting circle
        sprite.setOrigin(210f, 104f);
        // set starting
        sprite.rotate(90);
        sprite.setPosition(x, y);

        screenPosX = -Gdx.input.getAccelerometerX();
        screenPosZ = Gdx.input.getAccelerometerZ(); //z
        System.out.println("screen x " + screenPosX + " z " + screenPosZ);
        sprite.setScale(EntityType.PLAYER.getScale());

        circleMower = new Circle(pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY(),
                0.8f * sprite.getHeight()* EntityType.PLAYER.getScale() / 2);
        health = 4;
    }

    @Override
    public void update(float deltaTime) {
        //get input from phone position
        amountX = -Gdx.input.getAccelerometerX() * deltaTime * SPEED;
        amountY = Gdx.input.getAccelerometerZ() * deltaTime * SPEED;
        if (Math.abs(amountX) > 2 || Math.abs(amountY) > 2)  move(amountX, amountY);
            System.out.println("amout x " + amountX + " " + amountY);
            move(amountX, amountY);

        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);

    }

    @Override
    protected void move(float amountX, float amountY) {

        if (amountY > 4) amountY = 4;
        float newX = pos.x + amountX;
        float newY = pos.y + amountY;

        double myRadians = Math.atan2(newY - pos.y, newX - pos.x);
        if (Math.abs(myDegrees - (float) Math.toDegrees(myRadians)) > 5 )
        myDegrees = (float) Math.toDegrees(myRadians);
        //float myDegrees = 0;
        //System.out.println("pos x " + pos.x + " pos y " + pos.y);
        map.setTile(0,pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY());

        System.out.println("nrof " + map.getNrOfCutableTiles());
        if (!map.doesMowerCollideWithMap(newX, pos.y, (int) sprite.getOriginX(),(int) sprite.getOriginY())){
            sprite.setRotation(myDegrees);
            this.pos.x = newX;
        }

        if (!map.doesMowerCollideWithMap(pos.x, newY,(int) sprite.getOriginX(), (int) sprite.getOriginY())) {
            sprite.setRotation(myDegrees);
            this.pos.y = newY;
        }
        //setting collision circle for mower  - radius has to be lower than width - chosen to be 80%
        circleMower = new Circle(pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY(),
                0.8f * sprite.getHeight()*EntityType.PLAYER.getScale() / 2);
        map.doesMowerCollideWithObstacle(circleMower);
        //System.out.println("health " + health);

        //System.out.println("x " + pos.x + " y " + pos.y);
        sprite.setPosition(pos.x, pos.y);
    }

    public float getCircleMowerX() {
        return pos.x + sprite.getOriginX();
}

    public float getCircleMowerY() {
        return pos.y + sprite.getOriginY();
    }

    public float getCircleMowerR() {
        return 0.8f * sprite.getHeight() * EntityType.PLAYER.getScale()/2;
    }

    public void reduceHealth(){
        health -= 1;
    }

    public int getHealth(){
        return health;
    }

    public Circle getCircleMower() {
        return circleMower;
    }
}
