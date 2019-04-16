package com.bartz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bartz.game.LawnMowerGame;
import com.bartz.game.world.CustomGameMap;
import com.bartz.game.world.GameMap;

//using screenAdapter will allow to overwrite only chosen Screen methods
public class GameScreen extends ScreenAdapter {
    LawnMowerGame game;
    GameMap gameMap;
    OrthographicCamera camera;
    SpriteBatch batch;

    public GameScreen(LawnMowerGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        batch = new SpriteBatch();
        gameMap = new CustomGameMap();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, batch);
        if (gameMap.getPlayer().getHealth() == 0)
            game.setScreen(new EndScreen(game, camera));
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameMap.dispose();
        batch.dispose();

    }
}
