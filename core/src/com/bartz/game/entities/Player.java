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
    private float amountX, amountY, myDegrees, startPosX, startPosY;
    private int health;
    private Circle circleMower;

    public Player(float x, float y, GameMap map) {
        super(x, y, EntityType.PLAYER, map);

        image = new Texture("mower.png");
        sprite = new Sprite(image, getWidth(), getHeight());

        // set the center of rotation to be at center of cutting circle
        sprite.setOrigin(210f, 104f);

        // set starting
        sprite.rotate(90);
        sprite.setPosition(x, y);

        //save starting input for calibration
        startPosX = -Gdx.input.getAccelerometerX();
        startPosY = -Gdx.input.getAccelerometerY();

        sprite.setScale(EntityType.PLAYER.getScale());

        circleMower = new Circle(pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY(),
                0.8f * sprite.getHeight()* EntityType.PLAYER.getScale() / 2);

        health = 4;
    }

    @Override
    public void update(float deltaTime) {

        //get input from phone position
        amountX = -Gdx.input.getAccelerometerX() * deltaTime * SPEED;
        float newAccY = -Gdx.input.getAccelerometerY();

        amountY = getCalibratedAmountY(newAccY);
        //System.out.println("starting " + startPosY + " current " + newAccY + " am y " + amountY);

        // delete noises
        if (Math.abs(amountX) > 1 || Math.abs(amountY) > 1)
            move(amountX, amountY);
        //System.out.println("amout x " + amountX + " " + amountY);

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

        calculateMowerSpriteRotation(newX, newY);

        // change touched tile to be cut
        map.setTile(0,pos.x + sprite.getOriginX(), pos.y + sprite.getOriginY());

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

        // check if mower has touched a stone
        map.doesMowerCollideWithObstacle(circleMower);

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

    private float getCalibratedAmountY(float newAccY){
        float rangePlus;
        float rangeMinus;

        if (startPosY <= 0) {
            rangeMinus = -10 - startPosY;
            rangePlus = 20 + rangeMinus;
            if (newAccY <= startPosY) {
                amountY = ((startPosY - newAccY) * 10) / rangeMinus;
            } else
                amountY = ((newAccY - startPosY) * 10) / rangePlus;

        } else if (startPosY > 0){
            rangePlus = 10 - startPosY;
            rangeMinus = 20 - rangePlus;
            if (newAccY <= startPosY) {
                amountY = -(((startPosY - newAccY) * 10) / rangeMinus);
            } else
                amountY = ((newAccY - startPosY) * 10) / rangePlus;
        }
        return amountY;
    }

    private void calculateMowerSpriteRotation(float newX, float newY){
        double myRadians = Math.atan2(newY - pos.y, newX - pos.x);
        if (Math.abs(myDegrees - (float) Math.toDegrees(myRadians)) > 5 )
            myDegrees = (float) Math.toDegrees(myRadians);
    }
}
