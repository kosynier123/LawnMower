package com.bartz.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bartz.game.screens.MenuScreen;
import com.bartz.game.world.GameMap;

public class LawnMowerGame extends Game {
	private OrthographicCamera camera;
	private GameMap gameMap;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		setScreen(new MenuScreen(this, camera));
	}

	@Override
	public void dispose () {
	}

}
