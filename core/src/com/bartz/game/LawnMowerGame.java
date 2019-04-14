package com.bartz.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bartz.game.screens.MenuScreen;
import com.bartz.game.world.GameMap;

public class LawnMowerGame extends Game {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private GameMap gameMap;
	private BitmapFont font;
	private Stage stage;
	private Skin skin;
	private Viewport viewport;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(stage);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		setScreen(new MenuScreen(this, stage, batch, camera));
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

}
