package com.bartz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.bartz.game.LawnMowerGame;

public class MenuScreen implements Screen{

    private LawnMowerGame game;
    private SpriteBatch batch;
    private ImageButton startButton;
    private ImageButton optionsButton;
    private ImageButton endButton;
    private Image titleImage;
    private Texture startTexture;
    private Texture optionsTexture;
    private Texture endTexture;
    private Stage stage;
    private Texture titleTexture;
    private Table table;

    private OrthographicCamera camera;

    public MenuScreen(LawnMowerGame game, Stage stage, SpriteBatch batch, OrthographicCamera camera) {
        //assigns the game we passed to the placeholder
        this.game = game;
        this.stage = stage;
        this.batch = batch;
        this.camera = camera;

        table = new Table();
        table.setFillParent(true);

        // initialize textures
        startTexture = new Texture("start.png");
        optionsTexture = new Texture("options.png");
        endTexture = new Texture("end.png");
        titleTexture = new Texture("title.png");

        // initialize actors
        titleImage = new Image(titleTexture);
        startButton = new ImageButton(new TextureRegionDrawable(startTexture));
        optionsButton = new ImageButton(new TextureRegionDrawable(optionsTexture));
        endButton = new ImageButton(new TextureRegionDrawable(endTexture));

        stage.addActor(titleImage);

        // add table to stage and buttons to to table
        stage.addActor(table);
        table.add(startButton);
        table.row();
        table.add(optionsButton);
        table.row();
        table.add(endButton);

        //set titleImage to be 0.8 of screen
        float scale = ((0.8f * titleTexture.getWidth()) / titleTexture.getWidth());

        titleImage.setScale(scale);

        titleImage.setPosition(Gdx.graphics.getWidth()/2 - titleTexture.getWidth()*scale/2,
                Gdx.graphics.getHeight()/4*3 - titleTexture.getHeight()*scale/2);



        startButton.setBounds(Gdx.graphics.getWidth() / 2 - startTexture.getWidth()/ 2,
                Gdx.graphics.getHeight() / 2 - startTexture.getHeight() / 2, startTexture.getWidth(), startTexture.getHeight());
        optionsButton.setBounds(Gdx.graphics.getWidth() / 2 - optionsTexture.getWidth()/ 2,
                Gdx.graphics.getHeight() / 2 -  optionsTexture.getHeight() / 2, optionsTexture.getWidth(), optionsTexture.getHeight());
        endButton.setBounds(Gdx.graphics.getWidth() / 2 - endTexture.getWidth()/ 2,
                Gdx.graphics.getHeight() / 2 - endTexture.getHeight() / 2, endTexture.getWidth(), endTexture.getHeight());
    }


    @Override
    public void render(float deltaTime){
        batch.begin();

        Gdx.gl.glClearColor(0,0,0,1);
        //Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        table.draw(batch, 1);
        titleImage.draw(batch, 1);

        camera.update();

        //makes the camera fit onto the available screen
        batch.setProjectionMatrix(camera.combined);
        batch.end();
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void show() {
        startButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, batch, camera));
                return true;
            }
        });

        endButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                return true;
            }
        });
    }

    @Override
    public void hide(){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){
        Gdx.input.setInputProcessor(null);
    }
}