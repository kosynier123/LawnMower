package com.bartz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.bartz.game.world.CustomGameMap;
import com.bartz.game.world.GameMap;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private TextureAtlas textureAtlas2;
	private Sprite mower;
	private OrthographicCamera camera;
	private Vector3 pos;

	private GameMap gameMap;

	@Override
	public void create () {

        batch = new SpriteBatch();
		textureAtlas2 = new TextureAtlas("mower.txt");
		mower = textureAtlas2.createSprite("mower");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		pos = new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,0);
		camera.update();

		gameMap = new CustomGameMap();
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(camera, batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
