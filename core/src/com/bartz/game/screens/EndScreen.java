package com.bartz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bartz.game.LawnMowerGame;
import com.bartz.game.world.CustomGameMap;
import com.bartz.game.world.GameMap;

public class EndScreen extends ScreenAdapter {
    LawnMowerGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    Stage stage;
    Table table;
    Texture exitTexture;
    ImageButton exitButton;
    Texture gameOverTexture;
    Image gameOverImage;

    public EndScreen(LawnMowerGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        batch = new SpriteBatch();

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Texture gameOverTexture = new Texture("gameOver.png");
        Texture exitTexture = new Texture("exit.png");

        exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));
        gameOverImage = new Image(new TextureRegionDrawable(gameOverTexture));
        gameOverImage.setScaling(Scaling.stretch);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(gameOverImage);
        table.row();
        table.add(exitButton);
        table.row();

        exitButton.setBounds(Gdx.graphics.getWidth() / 2 - exitTexture.getWidth()/ 2,
                Gdx.graphics.getHeight() / 2 - exitTexture.getHeight() / 2,
                exitTexture.getWidth(), exitTexture.getHeight());
    }


    @Override
    public void render(float delta) {
        batch.begin();
        Gdx.gl.glClearColor(0,0,0,1);
        //Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        table.draw(batch, 1);

        camera.update();

        //makes the camera fit onto the available screen
        batch.setProjectionMatrix(camera.combined);
        batch.end();
    }

    @Override
    public void show() {
        exitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game, camera));
                return true;
            }
        });
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        batch.dispose();
    }
}
